package com.example.tt;

import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();//隐藏标题栏
        // 设置状态栏透明
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        // register clickable textview
        TextView register = findViewById(R.id.textView5);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_to_register();
            }
        });

        // signin clickable textview
        TextView signin = findViewById(R.id.textView2);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_to_signin();
            }
        });

        // back clickable textview x2
        TextView back_1 = findViewById(R.id.textView7);
        back_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView back_2 = findViewById(R.id.textView8);
        back_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // EditText
        final EditText login_email, login_pwd, register_name, register_email, register_pwd;
        login_email = findViewById(R.id.editTextTextEmailAddress);
        login_pwd = findViewById(R.id.editTextTextPassword);
        register_name = findViewById(R.id.editTextTextPersonName);
        register_email = findViewById(R.id.editTextTextEmailAddress2);
        register_pwd = findViewById(R.id.editTextTextPassword2);

        // login button
        Button login_button = findViewById(R.id.button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_email.getText().toString();
                String pwd = login_pwd.getText().toString();

                if(email.trim().length()==0){
                    login_email.setError("Email can't be empty!");
                    return;
                }
                if(pwd.trim().length()==0){
                    login_pwd.setError("Password can't be empty!");
                    return;
                }
                Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        // register button
        Button register_button = findViewById(R.id.button2);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = register_name.getText().toString();
                String email = register_email.getText().toString();
                String pwd = register_pwd.getText().toString();

                if(username.trim().length()==0){
                    register_name.setError("Username can't be empty!");
                    return;
                }
                if(email.trim().length()==0){
                    register_email.setError("Email can't be empty!");
                    return;
                }
                if(pwd.trim().length()==0){
                    register_pwd.setError("Password can't be empty!");
                    return;
                }
                Toast.makeText(LoginActivity.this, "Register Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void switch_to_register(){
        // email out
        EditText login_email = findViewById(R.id.editTextTextEmailAddress);
        login_email.setVisibility(View.GONE);
//        login_email.setAnimation(AnimationUtils.makeOutAnimation(this,false));

        // password out
        EditText login_password = findViewById(R.id.editTextTextPassword);
        login_password.setVisibility(View.GONE);
//        login_password.setAnimation(AnimationUtils.makeOutAnimation(this, false));

        // login button out
        Button login_button = findViewById(R.id.button);
        login_button.setVisibility(View.GONE);
//        login_button.setAnimation(AnimationUtils.makeOutAnimation(this, false));

        // register clickable text out
        TextView register = findViewById(R.id.textView5);
        register.setVisibility(View.GONE);
//        register.setAnimation(AnimationUtils.makeOutAnimation(this, false));

        // back clickable text out
        TextView back_1 = findViewById(R.id.textView7);
        back_1.setVisibility(View.GONE);

        // username in
        EditText register_username = findViewById(R.id.editTextTextPersonName);
        register_username.setVisibility(View.VISIBLE);
        register_username.setAnimation(AnimationUtils.makeInAnimation(this,false));

        // email in
        EditText register_email = findViewById(R.id.editTextTextEmailAddress2);
        register_email.setVisibility(View.VISIBLE);
        register_email.setAnimation(AnimationUtils.makeInAnimation(this,false));

        // password in
        EditText register_password = findViewById(R.id.editTextTextPassword2);
        register_password.setVisibility(View.VISIBLE);
        register_password.setAnimation(AnimationUtils.makeInAnimation(this,false));

        // register button in
        Button register_button = findViewById(R.id.button2);
        register_button.setVisibility(View.VISIBLE);
        register_button.setAnimation(AnimationUtils.makeInAnimation(this,false));

        // signin clickable text in
        TextView signin = findViewById(R.id.textView2);
        signin.setVisibility(View.VISIBLE);
        signin.setAnimation(AnimationUtils.makeInAnimation(this, false));

        // back clickable text in
        TextView back_2 = findViewById(R.id.textView8);
        back_2.setVisibility(View.VISIBLE);
        back_2.setAnimation(AnimationUtils.makeInAnimation(this, false));
    }

    public void switch_to_signin(){
        // email in
        EditText login_email = findViewById(R.id.editTextTextEmailAddress);
        login_email.setVisibility(View.VISIBLE);
        login_email.setAnimation(AnimationUtils.makeInAnimation(this,true));

        // password in
        EditText login_password = findViewById(R.id.editTextTextPassword);
        login_password.setVisibility(View.VISIBLE);
        login_password.setAnimation(AnimationUtils.makeInAnimation(this, true));

        // login button in
        Button login_button = findViewById(R.id.button);
        login_button.setVisibility(View.VISIBLE);
        login_button.setAnimation(AnimationUtils.makeInAnimation(this, true));

        // register clickable text in
        TextView register = findViewById(R.id.textView5);
        register.setVisibility(View.VISIBLE);
        register.setAnimation(AnimationUtils.makeInAnimation(this, true));

        // back clickable text in
        TextView back_1 = findViewById(R.id.textView7);
        back_1.setVisibility(View.VISIBLE);
        back_1.setAnimation(AnimationUtils.makeInAnimation(this, true));

        // username out
        EditText register_username = findViewById(R.id.editTextTextPersonName);
        register_username.setVisibility(View.GONE);
//        register_username.setAnimation(AnimationUtils.makeOutAnimation(this,true));

        // email out
        EditText register_email = findViewById(R.id.editTextTextEmailAddress2);
        register_email.setVisibility(View.GONE);
//        register_email.setAnimation(AnimationUtils.makeOutAnimation(this,true));

        // password out
        EditText register_password = findViewById(R.id.editTextTextPassword2);
        register_password.setVisibility(View.GONE);
//        register_password.setAnimation(AnimationUtils.makeOutAnimation(this,true));

        // register button out
        Button register_button = findViewById(R.id.button2);
        register_button.setVisibility(View.GONE);
//        register_button.setAnimation(AnimationUtils.makeOutAnimation(this,true));

        // signin clickable text out
        TextView signin = findViewById(R.id.textView2);
        signin.setVisibility(View.GONE);
//        signin.setAnimation(AnimationUtils.makeOutAnimation(this, true));

        // back clickable text out
        TextView back_2 = findViewById(R.id.textView8);
        back_2.setVisibility(View.GONE);

    }
}