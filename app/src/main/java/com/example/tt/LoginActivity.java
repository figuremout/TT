package com.example.tt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.*;

/**
 * 登录、注册是使用SharedPreference实现的伪登录注册，账号在本地持久化存储
 */
public class LoginActivity extends AppCompatActivity {
    private TextView register,signin,back_1,back_2;
    private EditText login_email, login_pwd, register_name, register_email, register_pwd;
    private Button login_button,register_button;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        register = findViewById(R.id.switchToRegister);
        signin = findViewById(R.id.switchToLogin);
        back_1 = findViewById(R.id.finishLogin);
        back_2 = findViewById(R.id.finishRegister);

        // EditText
        login_email = findViewById(R.id.login_email);
        login_pwd = findViewById(R.id.login_pwd);
        register_name = findViewById(R.id.register_username);
        register_email = findViewById(R.id.register_email);
        register_pwd = findViewById(R.id.register_pwd);

        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);

        preferences = getSharedPreferences("shared", MODE_PRIVATE);
        editor = preferences.edit();
    }
    private void initEvent(){
        // register clickable textview
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_to_register();
            }
        });

        // signin clickable textview
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_to_signin();
            }
        });

        // back clickable textview x2
        back_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        back_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // login button
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_email.getText().toString();
                String pwd = login_pwd.getText().toString();
                if(email.trim().length()==0){
                    login_email.setError(getResources().getString(R.string.emptyEmail));
                    return;
                }
                if(pwd.trim().length()==0){
                    login_pwd.setError(getResources().getString(R.string.emptyPassword));
                    return;
                }

                // 判断邮箱是否已存在
                String is_exist = preferences.getString(email+"#username", "");
                if(is_exist.trim().length()==0){
                    login_email.setError(getResources().getString(R.string.accountNotExist));
                    return;
                }else{
                    // 判断邮箱是否合法
                    String email_pattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
                    boolean is_mail = Pattern.matches(email_pattern, email);
                    if(!is_mail){
                        login_email.setError(getResources().getString(R.string.illegalEmail));
                        login_email.setText("");
                        return;
                    }
                    String correct_pwd = preferences.getString(email+"#pwd", "");
                    if(pwd.equals(correct_pwd)){
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.loginSuccess), Toast.LENGTH_SHORT).show();
                        // 写入currentEmail
                        editor.putString("currentEmail", email);
                        editor.apply();

                        // 执行登录后的改变
                        after_login(email);
                        finish();
                    }else{
                        login_pwd.setError(getResources().getString(R.string.wrongPassword));
                    }
                }
            }
        });

        // register button
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = register_name.getText().toString();
                String email = register_email.getText().toString();
                String pwd = register_pwd.getText().toString();

                if(username.trim().length()==0){
                    register_name.setError(getResources().getString(R.string.emptyUsername));
                    return;
                }
                if(email.trim().length()==0){
                    register_email.setError(getResources().getString(R.string.emptyEmail));
                    return;
                }
                if(pwd.trim().length()==0){
                    register_pwd.setError(getResources().getString(R.string.emptyPassword));
                    return;
                }

                // 判断邮箱是否已注册
                String is_exist = preferences.getString(email+"#username", "");
                if(is_exist.trim().length()==0){
                    // 判断邮箱是否合法
                    String email_pattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
                    boolean is_mail = Pattern.matches(email_pattern, email);
                    if(!is_mail){
                        register_email.setError(getResources().getString(R.string.illegalEmail));
                        register_email.setText("");
                        return;
                    }
                    // 以邮箱为唯一标识，存储账号信息
                    editor.putString(email+"#username", username);
                    editor.putString(email+"#pwd", pwd);
                    editor.apply();
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.registerSuccess), Toast.LENGTH_SHORT).show();

                    // 自动填充信息到登录界面
                    switch_to_signin();
                    login_email.setText(email);
                    login_pwd.setText(pwd);
                }else{
                    // 该邮箱已注册
                    register_email.setError(getResources().getString(R.string.emailExist));
                    register_email.setText("");
                }


            }
        });
    }
    public void switch_to_register(){
        // email out
        EditText login_email = findViewById(R.id.login_email);
        login_email.setVisibility(View.GONE);
//        login_email.setAnimation(AnimationUtils.makeOutAnimation(this,false));

        // password out
        EditText login_password = findViewById(R.id.login_pwd);
        login_password.setVisibility(View.GONE);
//        login_password.setAnimation(AnimationUtils.makeOutAnimation(this, false));

        // login button out
        Button login_button = findViewById(R.id.login_button);
        login_button.setVisibility(View.GONE);
//        login_button.setAnimation(AnimationUtils.makeOutAnimation(this, false));

        // register clickable text out
        TextView register = findViewById(R.id.switchToRegister);
        register.setVisibility(View.GONE);
//        register.setAnimation(AnimationUtils.makeOutAnimation(this, false));

        // back clickable text out
        TextView back_1 = findViewById(R.id.finishLogin);
        back_1.setVisibility(View.GONE);

        // username in
        EditText register_username = findViewById(R.id.register_username);
        register_username.setVisibility(View.VISIBLE);
        register_username.setAnimation(AnimationUtils.makeInAnimation(this,false));

        // email in
        EditText register_email = findViewById(R.id.register_email);
        register_email.setVisibility(View.VISIBLE);
        register_email.setAnimation(AnimationUtils.makeInAnimation(this,false));

        // password in
        EditText register_password = findViewById(R.id.register_pwd);
        register_password.setVisibility(View.VISIBLE);
        register_password.setAnimation(AnimationUtils.makeInAnimation(this,false));

        // register button in
        Button register_button = findViewById(R.id.register_button);
        register_button.setVisibility(View.VISIBLE);
        register_button.setAnimation(AnimationUtils.makeInAnimation(this,false));

        // signin clickable text in
        TextView signin = findViewById(R.id.switchToLogin);
        signin.setVisibility(View.VISIBLE);
        signin.setAnimation(AnimationUtils.makeInAnimation(this, false));

        // back clickable text in
        TextView back_2 = findViewById(R.id.finishRegister);
        back_2.setVisibility(View.VISIBLE);
        back_2.setAnimation(AnimationUtils.makeInAnimation(this, false));
    }

    public void switch_to_signin(){
        // email in
        EditText login_email = findViewById(R.id.login_email);
        login_email.setVisibility(View.VISIBLE);
        login_email.setAnimation(AnimationUtils.makeInAnimation(this,true));

        // password in
        EditText login_password = findViewById(R.id.login_pwd);
        login_password.setVisibility(View.VISIBLE);
        login_password.setAnimation(AnimationUtils.makeInAnimation(this, true));

        // login button in
        Button login_button = findViewById(R.id.login_button);
        login_button.setVisibility(View.VISIBLE);
        login_button.setAnimation(AnimationUtils.makeInAnimation(this, true));

        // register clickable text in
        TextView register = findViewById(R.id.switchToRegister);
        register.setVisibility(View.VISIBLE);
        register.setAnimation(AnimationUtils.makeInAnimation(this, true));

        // back clickable text in
        TextView back_1 = findViewById(R.id.finishLogin);
        back_1.setVisibility(View.VISIBLE);
        back_1.setAnimation(AnimationUtils.makeInAnimation(this, true));

        // username out
        EditText register_username = findViewById(R.id.register_username);
        register_username.setVisibility(View.GONE);
//        register_username.setAnimation(AnimationUtils.makeOutAnimation(this,true));

        // email out
        EditText register_email = findViewById(R.id.register_email);
        register_email.setVisibility(View.GONE);
//        register_email.setAnimation(AnimationUtils.makeOutAnimation(this,true));

        // password out
        EditText register_password = findViewById(R.id.register_pwd);
        register_password.setVisibility(View.GONE);
//        register_password.setAnimation(AnimationUtils.makeOutAnimation(this,true));

        // register button out
        Button register_button = findViewById(R.id.register_button);
        register_button.setVisibility(View.GONE);
//        register_button.setAnimation(AnimationUtils.makeOutAnimation(this,true));

        // signin clickable text out
        TextView signin = findViewById(R.id.switchToLogin);
        signin.setVisibility(View.GONE);
//        signin.setAnimation(AnimationUtils.makeOutAnimation(this, true));

        // back clickable text out
        TextView back_2 = findViewById(R.id.finishRegister);
        back_2.setVisibility(View.GONE);
    }

    /**
     * 登录成功后需要改变界面、控件
     */
    private void after_login(String email){
        // 登录按钮消失，个人信息展示
        MainActivity.login_button.setVisibility(View.INVISIBLE);
        MainActivity.username_show.setVisibility(View.VISIBLE);
        MainActivity.email_show.setVisibility(View.VISIBLE);
        String username = preferences.getString(email+"#username", "");
        MainActivity.username_show.setText(username);
        MainActivity.email_show.setText(email);

        // 头像点击事件重写
        MainActivity.login_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        // 头像图片
        MainActivity.login_img.setImageResource(R.drawable.logged_user);
    }
}