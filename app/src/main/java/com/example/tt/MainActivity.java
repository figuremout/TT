package com.example.tt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private String Tag = "12345";
    private Tencent mTencent;// 新建Tencent实例用于调用分享方法
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageButton login_img, settings_img;
    private Button login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag, "Create");
        setContentView(R.layout.activity_main);

//        // 设置状态栏透明
//        Window window = this.getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); // 顶部状态栏
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); // 底部导航栏
//        window.setStatusBarColor(Color.TRANSPARENT);
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        // 设置状态栏颜色 有用
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//添加Flag把状态栏设为可绘制模式
        window.setStatusBarColor(Color.parseColor("#63B3EF"));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        initView();
        initEvent();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance("1110702464", this.getApplicationContext());
        // 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
        Log.d(Tag, "create done");
    }
    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
    }
    private void initEvent(){
        // add button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    // 初始化菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(Tag, "onCreateOptions menu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        {/**
         * 菜单渲染完成后找到菜单上按钮并添加事件
         */
            // goto login page
            login_img = findViewById(R.id.loginImg);
            login_img.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            login_button = findViewById(R.id.button3);
            login_button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

            // goto settings page
            settings_img = findViewById(R.id.imageButton);
            settings_img.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
            });
        }
        Log.d(Tag, "onCreateOptions menu done");
        return true;
    }
    // 菜单项监听
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(Tag, "onOptionsItemSelected");
        switch (item.getItemId()){
            case R.id.action_share:// 分享按钮
                doShareToQQ();
                break;
        }
        Log.d(Tag, "onOptionsItemSelected done");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d(Tag, "onSupportNavigateUp");

        Log.d(Tag, "onSupportNavigateUp done");

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag, "Pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag, "Resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Tag, "Restart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag, "Start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag, "Stop");
    }


    /**
     * share with qq friends
     */
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            // 操作成功
            Toast.makeText(MainActivity.this, "onComplete:", Toast.LENGTH_SHORT);
        }
        protected void doComplete(JSONObject values) {
            Toast.makeText(MainActivity.this, "doComplete:", Toast.LENGTH_SHORT);
        }
        @Override
        public void onError(UiError e) {
            // 分享异常
            Toast.makeText(MainActivity.this, "Error:", Toast.LENGTH_SHORT);
        }
        @Override
        public void onCancel() {
            // 取消分享
            Toast.makeText(MainActivity.this, "Cancel:", Toast.LENGTH_SHORT);
        }
    }
    IUiListener shareListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(Tag, "-->onActivityResult " + requestCode + " resultCode=" + resultCode); // 10103 == share
        Tencent.onActivityResultData(requestCode, resultCode, data, shareListener);
        Tencent.handleResultData(data, shareListener);
    }

    private void doShareToQQ() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "测试：分享到QQ空间");// 标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "别人的app，不行。我们的app，行！");// 摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"http://www.zjmpage.com/TT/README.html");// 内容地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://b182.photo.store.qq.com/psb?/V11Upd1r0SLvmz/UvLQ.j9dYQWZR9scwprHiOB3UQh1t6EB59JnpeBDY64!/b/dLYAAAAAAAAA&bo=iAGWAQAAAAARFz4!&rf=viewer_4");// 网络图片地址　　
         params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "TickTock");// 应用名称
//        分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
//        QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。
//        QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        // 分享操作要在主线程中完成
        if(null != mTencent){
            mTencent.shareToQQ(MainActivity.this, params, shareListener);
        }
    }
}




