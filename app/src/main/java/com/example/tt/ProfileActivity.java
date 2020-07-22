package com.example.tt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton back, edit_username_img;
    private Button logout;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private TextView email, username;
    private EditText edit_username;
    private InputMethodManager imm;
    private String currentEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //隐藏标题栏
        getSupportActionBar().hide();
        // 设置状态栏透明
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        initView();
        initEvent();
    }

    private void initView(){
        // 初始化SharedPreference
        preferences = getSharedPreferences("shared", MODE_PRIVATE);
        editor = preferences.edit();
        currentEmail = preferences.getString("currentEmail", "");

        back = findViewById(R.id.profile_back);
        logout = findViewById(R.id.logout);
        edit_username_img = findViewById(R.id.edit_username_img);
        edit_username = findViewById(R.id.profile_edit_username);

        // 设置email和password
        email = findViewById(R.id.profile_email);
        String currentEmail = preferences.getString("currentEmail", "");
        email.setText(currentEmail);
        username = findViewById(R.id.profile_username);
        username.setText(preferences.getString(currentEmail+"#username", ""));

        // 初始化软键盘控制
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    private void initEvent(){
        // 返回按钮事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 注销账户按钮事件
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("currentEmail", "");
                editor.apply();
                after_logout();
                finish();
            }
        });

        // 更改用户名按钮事件
        edit_username_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 判断是否是编辑状态
                if(username.getVisibility() == View.VISIBLE){
                    // 不是编辑状态，要变成编辑状态
                    edit_username_img.setImageResource(R.drawable.submit);
                    username.setVisibility(View.INVISIBLE);
                    edit_username.setVisibility(View.VISIBLE);
                    // 令编辑框获得焦点并打开软键盘
                    edit_username.requestFocus();
                    imm.showSoftInput(edit_username, 0);
                    // 编辑框内容初始化为当前用户名，并将光标移动到最后
                    currentEmail = preferences.getString("currentEmail", "");
                    String username_str = preferences.getString(currentEmail+"#username", "");
                    edit_username.setText(username_str);
                    edit_username.setSelection(edit_username.getText().length());
                }else{
                    // 是编辑状态，要保存数据并变成非编辑状态
                    edit_username_img.setImageResource(R.drawable.submit);
                    edit_username_img.setImageResource(R.drawable.ic_baseline_edit_24);
                    username.setVisibility(View.VISIBLE);
                    edit_username.setVisibility(View.INVISIBLE);
                    // 隐藏软键盘
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    // 保存数据
                    String new_username = edit_username.getText().toString();
                    currentEmail = preferences.getString("currentEmail", "");
                    editor.putString(currentEmail+"#username", new_username);
                    editor.apply();
                    // 刷新各界面上的旧用户名
                    username.setText(new_username);
                    MainActivity.username_show.setText(new_username);
                }
            }
        });
        
    }

    private void after_logout(){
        // 登录按钮显示，个人信息消失
        MainActivity.login_button.setVisibility(View.VISIBLE);
        MainActivity.username_show.setVisibility(View.INVISIBLE);
        MainActivity.email_show.setVisibility(View.INVISIBLE);

        // 头像点击事件重写
        MainActivity.login_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        // 头像图片
        MainActivity.login_img.setImageResource(R.drawable.user);
    }
}