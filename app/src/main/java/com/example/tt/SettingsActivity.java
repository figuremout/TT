package com.example.tt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private ImageButton back;
    private Switch render_switch,recycle_switch;
    private Button github,customer_service;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String currentEmail;
    private Boolean isRender, isRecycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().hide();//隐藏标题栏
        // 设置状态栏透明
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        initView();
        initEvent();
    }
    private void initView(){
        back = findViewById(R.id.profile_back);
        render_switch = findViewById(R.id.switch2);
        recycle_switch = findViewById(R.id.switch1);
        github = findViewById(R.id.button4);
        customer_service = findViewById(R.id.button5);
        // 初始化SharedPreferences
        preferences = getSharedPreferences("shared", MODE_PRIVATE);
        editor = preferences.edit();
        currentEmail = preferences.getString("currentEmail", "");
        isRender = preferences.getBoolean(currentEmail+"#settings#isRender", false);
        isRecycle = preferences.getBoolean(currentEmail+"#settings#isRecycle", false);
    }
    private void initEvent(){
        // 返回按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // real-time rendering switch
        render_switch.setChecked(isRender);
        render_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean(currentEmail+"#settings#isRender", true);
                }else{
                    editor.putBoolean(currentEmail+"#settings#isRender", false);
                }
                editor.apply();
            }
        });
        // recycle done-affairs switch
        recycle_switch.setChecked(isRecycle);
        recycle_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean(currentEmail+"#settings#isRecycle", true);
                }else{
                    editor.putBoolean(currentEmail+"#settings#isRecycle", false);
                }
                editor.apply();
            }
        });
        // github button
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "Welcome to my GitHub repo!", Toast.LENGTH_SHORT).show();
                // 跳转网页
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://github.com/githubzjm/TT");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        // customer service button
        customer_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "We will reply you as soon as possible!", Toast.LENGTH_SHORT).show();
                // 社交次数加一
                String currentEmail = preferences.getString("currentEmail", "");
                editor.putInt(currentEmail+"#socialTimes", preferences.getInt(currentEmail+"#socialTimes", 0)+1);
                editor.apply();
                // 跳转网页
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=2427394482&version=1&src_type=web&web_src=githubzjm.github.io");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }
}