package com.example.tt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tt.MainActivity;
import com.example.tt.R;
import com.example.tt.util.Const;
import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.MarkdownEditText;
import com.yydcdut.markdown.MarkdownProcessor;
import com.yydcdut.markdown.callback.OnLinkClickCallback;
import com.yydcdut.markdown.callback.OnTodoClickCallback;
import com.yydcdut.markdown.loader.DefaultLoader;
import com.yydcdut.markdown.syntax.edit.EditFactory;
import com.yydcdut.markdown.theme.ThemeDefault;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import rx.Subscriber;
//import com.yydcdut.rxmarkdown.RxMDConfiguration;

/**
 * 文本编辑界面
 * markdown实时渲染
 */
public class editAffairActivity extends AppCompatActivity {
    private MarkdownEditText editContent;
    private  MarkdownProcessor markdownProcessor;
    private ImageButton back, showMd, helpMd;
    private EditText editTitle;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String currentEmail, currentAffairID, currentAffairTitle, currentAffairContent, userEditContentCache;
    private Boolean helpMd_isShow = false, isRender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_affair);

        getSupportActionBar().hide();//隐藏标题栏
        // 设置状态栏颜色 有用
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//添加Flag把状态栏设为可绘制模式
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        initView();
        initEvent();

        // 实时渲染
        if(isRender){
            markdown();
        }
    }

    private void initView(){
        // 初始化sharedPreferences
        preferences = getSharedPreferences("shared", MODE_PRIVATE);
        editor = preferences.edit();
        currentEmail = preferences.getString("currentEmail", "");
        currentAffairID = preferences.getString("currentAffairID", "");
        currentAffairTitle = preferences.getString(currentEmail+"#affairID="+currentAffairID+"#title", "");
        currentAffairContent = preferences.getString(currentEmail+"#affairID="+currentAffairID+"#content", "");
        isRender = preferences.getBoolean(currentEmail+"#settings#isRender", false);

        // 初始化内容编辑框
        editContent = findViewById(R.id.editTextTextMultiLine);
        editContent.setBackground(null);// 取消下划线
        editContent.setText(currentAffairContent);
        // 初始化标题编辑框
        editTitle = findViewById(R.id.editTextTextPersonName);
        editTitle.setText(currentAffairTitle);

        back = findViewById(R.id.imageButton3);
        showMd = findViewById(R.id.showMd_button);
        helpMd = findViewById(R.id.helpMd_button);
    }
    private void initEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 编辑活动退出慢，是因为在stop之前要调用HomeFragment的onResume()方法刷新界面
                finish();
            }
        });

        // 观察模式按钮事件
        showMd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMdActivity.startShowActivity(editAffairActivity.this, editContent.getText().toString(), false);
            }
        });

        // 生成帮助markdown
        helpMd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(helpMd_isShow){
                    // 若已显示help文档，重新显示用户编辑内容
                    editContent.setText(userEditContentCache);
                    if(isRender){
                        markdown();
                    }
                }else{
                    // 若未显示help文档，保存用户编辑内容并显示help文档
                    userEditContentCache = editContent.getText().toString();
                    editContent.setText(Const.contentExample);
                    if(isRender){
                        markdown();
                    }
                }
                helpMd_isShow = !helpMd_isShow;
            }
        });
    }

    /**
     * 渲染函数
     */
    private void markdown() {
        // 设置实时渲染效果
        MarkdownConfiguration markdownConfiguration = new MarkdownConfiguration.Builder(this)
                .setDefaultImageSize(50, 50)
                .setBlockQuotesLineColor(getResources().getColor(R.color.colorPrimaryLight)) // 引用块提示线颜色
                // 各级标题大小
                .setHeader1RelativeSize(1.6f)
                .setHeader2RelativeSize(1.5f)
                .setHeader3RelativeSize(1.4f)
                .setHeader4RelativeSize(1.3f)
                .setHeader5RelativeSize(1.2f)
                .setHeader6RelativeSize(1.1f)
                // 水平分割线颜色
                .setHorizontalRulesColor(getResources().getColor(R.color.md_HorizontalRulesColor))
                // 水平分割线高度
                .setHorizontalRulesHeight(8)
                // 引用提示线颜色
                .setBlockQuotesLineColor(getResources().getColor(R.color.md_BlockQuotesLineColor))
                // 引用块背景颜色(3层)
                .setBlockQuotesBgColor(getResources().getColor(R.color.md_BlockQuotesBgColor),
                        getResources().getColor(R.color.md_BlockQuotesBgColor),
                        getResources().getColor(R.color.md_BlockQuotesBgColor))
                // 行内代码背景颜色
                .setCodeBgColor(getResources().getColor(R.color.md_CodeBgColor))
                // 待办事项颜色
                .setTodoColor(getResources().getColor(R.color.md_TodoColor))
                // 待办事项完成后颜色
                .setTodoDoneColor(getResources().getColor(R.color.md_TodoDoneColor))
                // 无序列表颜色
                .setUnOrderListColor(getResources().getColor(R.color.md_UnOrderListColor))
                .build();
        markdownProcessor = new MarkdownProcessor(this);
        markdownProcessor.config(markdownConfiguration);
        markdownProcessor.factory(EditFactory.create());
        markdownProcessor.live(editContent);
    }

    /**
     * 调试用
     */
    @Override
    protected void onPause() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 结束编辑页面活动时保存内容
                editor.putString(currentEmail+"#affairID="+currentAffairID+"#title", editTitle.getText().toString());
                editor.putString(currentEmail+"#affairID="+currentAffairID+"#content", editContent.getText().toString());
                editor.apply();
            }
        }).start();
        super.onPause();
        Log.d("12345", "Pauseedit");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("12345", "Resumeedit");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("12345", "Restartedit");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("12345", "Startedit");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("12345", "Stopedit");
    }


}