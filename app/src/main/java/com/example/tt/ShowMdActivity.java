package com.example.tt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.android.material.snackbar.Snackbar;
import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.MarkdownProcessor;
import com.yydcdut.markdown.MarkdownTextView;
import com.yydcdut.markdown.callback.OnLinkClickCallback;
import com.yydcdut.markdown.callback.OnTodoClickCallback;
import com.yydcdut.markdown.loader.MDImageLoader;
import com.yydcdut.markdown.syntax.text.TextFactory;
import com.yydcdut.markdown.theme.ThemeSunburst;

/**
 * 观察模式
 * markdown更精细渲染
 */
public class ShowMdActivity extends AppCompatActivity {
    public static final String EXTRA_CONTENT = "extra_content";
    public static final String EXTRA_RX = "is_rx";
    private ImageButton close;
    private Toast mToast;

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
        close = findViewById(R.id.imageButton2);
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
                        toast(link);
                    }
                })
                // 点击待办事项的callback事件
                .setOnTodoClickCallback(new OnTodoClickCallback() {
                    @Override
                    public CharSequence onTodoClicked(View view, String line, int lineNumber) {
                        toast("line:" + line + "\n" + "line number:" + lineNumber);
                        return textView.getText();
                    }
                })
                .build();
        MarkdownProcessor processor = new MarkdownProcessor(this);
        processor.factory(TextFactory.create());
        processor.config(markdownConfiguration);
        textView.setText(processor.parse(content));
    }

    private void toast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(ShowMdActivity.this, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}