package com.example.tt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

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

        // 返回按钮
        ImageButton back = findViewById(R.id.imageButton3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // dark theme switch
        Switch darkTheme_switch = findViewById(R.id.switch2);
        darkTheme_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(SettingsActivity.this, "Dark theme!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SettingsActivity.this, "Default theme!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // teenager mode switch
        Switch teenager_switch = findViewById(R.id.switch1);
        teenager_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(SettingsActivity.this, "Teenager Mode!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SettingsActivity.this, "Default Mode!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // github button
        Button github = findViewById(R.id.button4);
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
        Button customer_service = findViewById(R.id.button5);
        customer_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "We will reply you as soon as possible!", Toast.LENGTH_SHORT).show();
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