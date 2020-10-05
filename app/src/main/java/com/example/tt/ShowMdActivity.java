package com.example.tt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tt.util.OKLoader;
import com.example.tt.util.myHttp;
import com.google.android.material.snackbar.Snackbar;
import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.MarkdownProcessor;
import com.yydcdut.markdown.MarkdownTextView;
import com.yydcdut.markdown.callback.OnLinkClickCallback;
import com.yydcdut.markdown.callback.OnTodoClickCallback;
import com.yydcdut.markdown.loader.MDImageLoader;
import com.yydcdut.markdown.syntax.text.TextFactory;
import com.yydcdut.markdown.theme.ThemeSunburst;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

/**
 * 观察模式
 * markdown更精细渲染
 */
public class ShowMdActivity extends AppCompatActivity {
    public static final String EXTRA_CONTENT = "extra_content";
    public static final String EXTRA_RX = "is_rx";
    private ImageButton close;
    private Toast mToast;
    private TextView showMd_title, showMd_username_date;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String currentEmail, currentAffairID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_md);

        //隐藏标题栏
        getSupportActionBar().hide();
        // 设置状态栏颜色 有用
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//添加Flag把状态栏设为可绘制模式
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        initView();
        initEvent();

        // 渲染
        showMd();
    }

    private void initView(){
        // 初始化SharedPreferences
        preferences = getSharedPreferences("shared", MODE_PRIVATE);
        editor = preferences.edit();
        currentEmail = preferences.getString("currentEmail", "");
        currentAffairID = preferences.getString("currentAffairID", "");

        close = findViewById(R.id.imageButton2);

        final String[] doc_str = new String[1];
        final JSONObject[] doc = new JSONObject[1];
        final String[] username = new String[1];
        Thread getOneAffairThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doc_str[0] = myHttp.getHTTPReq("/getOneAffair?email="+currentEmail+"&affairID="+currentAffairID);
                    username[0] = myHttp.getHTTPReq("/getUsername?email="+currentEmail);
                    doc[0] = myHttp.getJsonObject(doc_str[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        getOneAffairThread.start();
        try {
            getOneAffairThread.join();
            // 显示事件标题，用户名+事件日期
            showMd_title = findViewById(R.id.textView3);
            showMd_title.setText(doc[0].getString("title"));
            showMd_username_date = findViewById(R.id.textView4);
            showMd_username_date.setText(username[0]+ " - "+ doc[0].getString("date"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void initEvent(){
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // 观察模式按钮触发此事件
    // 启动ShowMdActivity界面
    public static void startShowActivity(Activity activity, String content, boolean isRx) {
        Intent intent = new Intent(activity, ShowMdActivity.class);
        intent.putExtra(EXTRA_CONTENT, content);
        intent.putExtra(EXTRA_RX, isRx);
        activity.startActivity(intent);
    }

    private void showMd(){
        MarkdownTextView markdownTextView = (MarkdownTextView) findViewById(R.id.txt_md_show);
        markdownTextView.setMovementMethod(LinkMovementMethod.getInstance());
        String content = getIntent().getStringExtra(EXTRA_CONTENT);
        boolean isRx = false; // 直接置为false，本项目中不使用rx方式
        MDImageLoader mdImageLoader = null;
        mdImageLoader = new OKLoader(this);
        markdown(markdownTextView, content, mdImageLoader);
    }
    private void markdown(final TextView textView, String content, MDImageLoader imageLoader) {
        // 渲染效果配置
        MarkdownConfiguration markdownConfiguration = new MarkdownConfiguration.Builder(this)
                .setDefaultImageSize(50, 50)
                .setHeader1RelativeSize(1.6f)
                .setHeader2RelativeSize(1.5f)
                .setHeader3RelativeSize(1.4f)
                .setHeader4RelativeSize(1.3f)
                .setHeader5RelativeSize(1.2f)
                .setHeader6RelativeSize(1.1f)
                // 引用提示线颜色
                .setBlockQuotesLineColor(getResources().getColor(R.color.md_BlockQuotesLineColor))
                // 引用块背景颜色(3层)
                .setBlockQuotesBgColor(getResources().getColor(R.color.md_BlockQuotesBgColor),
                        getResources().getColor(R.color.md_BlockQuotesBgColor),
                        getResources().getColor(R.color.md_BlockQuotesBgColor))
                .setHorizontalRulesColor(getResources().getColor(R.color.md_HorizontalRulesColor))
                .setCodeBgColor(getResources().getColor(R.color.md_CodeBgColor))
                .setTodoColor(getResources().getColor(R.color.md_TodoColor))
                .setTodoDoneColor(getResources().getColor(R.color.md_TodoDoneColor))
                .setUnOrderListColor(getResources().getColor(R.color.md_UnOrderListColor))
                .setRxMDImageLoader(imageLoader)
                .setHorizontalRulesHeight(8)
                // 链接颜色
                .setLinkFontColor(getResources().getColor(R.color.md_LinkFontColor))
                .showLinkUnderline(false)
                .setTheme(new ThemeSunburst())
                // 点击链接的callback事件
                .setOnLinkClickCallback(new OnLinkClickCallback() {
                    @Override
                    public void onLinkClicked(View view, String link) {
                        // 跳转网页
                        Intent intent= new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(link);
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                })
                // 点击待办事项的callback事件
                .setOnTodoClickCallback(new OnTodoClickCallback() {
                    @Override
                    public CharSequence onTodoClicked(View view, String line, int lineNumber) {
                        // line是该行的内容，lineNumber是该行的行数
                        return textView.getText();
                    }
                })
                .build();
        MarkdownProcessor processor = new MarkdownProcessor(this);
        processor.factory(TextFactory.create());
        processor.config(markdownConfiguration);
        textView.setText(processor.parse(content));
    }
}