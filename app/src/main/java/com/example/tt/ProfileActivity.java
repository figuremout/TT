package com.example.tt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tt.myView.MyRadarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.tt.util.DateUtil.differentDays;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton back, edit_username_img, fingerprint;
    private Button logout;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private TextView email,
            sign_date, notSign_intro, signed_intro1, signed_intro2, signed_intro3, signed_intro4, since_last_sign, continuous_times;
    private EditText edit_username;
    private InputMethodManager imm;
    private String currentEmail;
    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private String date_str;
    private Vibrator vibrator; //震动
    private MyRadarView radar;
    private TextView radar_reminder;

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

        try {
            initView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initEvent();
    }

    private void initView() throws ParseException {
        // 初始化SharedPreference
        preferences = getSharedPreferences("shared", MODE_PRIVATE);
        editor = preferences.edit();
        currentEmail = preferences.getString("currentEmail", "");

        back = findViewById(R.id.profile_back);
        logout = findViewById(R.id.logout);
        edit_username_img = findViewById(R.id.edit_username_img);
        edit_username = findViewById(R.id.profile_edit_username);
        fingerprint = findViewById(R.id.fingerprint);

        // 初始化一系列打卡前后的描述文字
        notSign_intro = findViewById(R.id.notSign_intro);
        signed_intro1 = findViewById(R.id.signed_intro1);
        signed_intro2 = findViewById(R.id.signed_intro2);
        signed_intro3 = findViewById(R.id.signed_intro3);
        signed_intro4 = findViewById(R.id.signed_intro4);
        since_last_sign = findViewById(R.id.since_last_sign);
        continuous_times = findViewById(R.id.continuous_times);
        // 初始化打卡日期
        sign_date = findViewById(R.id.sign_date);
        simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");// HH:mm:ss
        date = new Date(System.currentTimeMillis());//获取当前时间
        date_str = simpleDateFormat.format(date);
        sign_date.setText(date_str);

        // 设置email和username
        email = findViewById(R.id.profile_email);
        String currentEmail = preferences.getString("currentEmail", "");
        email.setText(currentEmail);
        edit_username.setText(preferences.getString(currentEmail+"#username", ""));
        edit_username.setBackground(null); // 去除下划线

        // 初始化雷达图
        radar = findViewById(R.id.radar);
        radar_reminder = findViewById(R.id.textView9);
        radar.setData(initRadar());


        // 初始化软键盘控制
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // 根据当日是否已打卡初始化打卡UI
        keep_signed();

        // 初始化震动
        vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
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
                if(edit_username.isEnabled()){
                    // 是编辑状态，要保存数据并变成非编辑状态
                    edit_username_img.setImageResource(R.drawable.ic_baseline_edit_24);
                    edit_username.setEnabled(false);
                    // 隐藏软键盘
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    // 保存数据
                    String new_username = edit_username.getText().toString();
                    currentEmail = preferences.getString("currentEmail", "");
                    editor.putString(currentEmail+"#username", new_username);
                    editor.apply();
                    // 刷新各界面上的旧用户名
                    MainActivity.username_show.setText(new_username);
                }else{
                    // 不是编辑状态，要变成编辑状态
                    edit_username_img.setImageResource(R.drawable.submit);
                    edit_username.setEnabled(true);
                    // 令编辑框获得焦点并打开软键盘
                    edit_username.requestFocus();
                    imm.showSoftInput(edit_username, 0);
                    // 编辑框内容初始化为当前用户名，并将光标移动到最后
                    currentEmail = preferences.getString("currentEmail", "");
                    String username_str = preferences.getString(currentEmail+"#username", "");
                    edit_username.setText(username_str);
                    edit_username.setSelection(edit_username.getText().length());
                }
            }
        });

        // 打卡按钮
        fingerprint.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                try {
                    after_sign();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 震动
                vibrator.vibrate(300);
                return true;// 过滤单击事件，避免长按时长按事件、单击事件都触发
            }
        });
    }

    /**
     * 登录后的操作
     */
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

    /**
     * 打卡后当日会保持已打卡状态
     */
    private void keep_signed() throws ParseException {
        String signDate = preferences.getString(currentEmail+"#signDate", "");
        if(signDate.equals(date_str)){
            // 当日已打卡
            after_sign();
        }else{
            // 当日未打卡，无事发生
        }
    }
    /**
     * 打卡后的操作
     */
    private void after_sign() throws ParseException {
        // 界面效果切换
        notSign_intro.setVisibility(View.INVISIBLE);
        signed_intro1.setVisibility(View.VISIBLE);
        signed_intro2.setVisibility(View.VISIBLE);
        signed_intro3.setVisibility(View.VISIBLE);
        signed_intro4.setVisibility(View.VISIBLE);
        since_last_sign.setVisibility(View.VISIBLE);
        continuous_times.setVisibility(View.VISIBLE);
        // 按钮图片切换、失活
        fingerprint.setImageResource(R.drawable.celebrate);
        fingerprint.setEnabled(false);// setClickable(false)仍会显示点击效果
        // 更新最近打卡日期和连续打卡数
        int period;
        String last_sign_date_str = preferences.getString(currentEmail+"#signDate", "");
        if(last_sign_date_str == ""){
            // 第一次打卡值为空
            period = -1;
        }else{
            Date last_sign_date = simpleDateFormat.parse(last_sign_date_str); //将日期字符串解析为Date对象
            period = differentDays(last_sign_date, date);
        }
        int times = preferences.getInt(currentEmail+"#signTimes", 0);
        if(period == 1 || period == -1){
            // 若相隔1天或第一次打卡，连续打卡数加一
            editor.putInt(currentEmail+"#signTimes", ++times);
        }else if(period == 0){
            // 当天已签到，但初始化打卡界面时仍要呼叫after_sign()函数
            // 连续打卡日数不变，因此times值不变
        }
        else{
            // 若相隔多天，连续打卡数清零
            times = 0;
            editor.putInt(currentEmail+"#signTimes", times);
        }
        editor.putString(currentEmail+"#signDate", date_str);
        editor.apply();
        // 设置UI上的距离上次打卡日数和连续打卡数
        since_last_sign.setText(period+"");
        continuous_times.setText(times+"");
    }

    private double[] initRadar(){
        String[] affairIDList = preferences.getString(currentEmail+"#affairIDList", "").split(",");
        // 事务总数：当前保存的所有事务的数量 反映用户忙闲
        double affairNum = affairIDList.length;
        // 打卡考勤：连续打卡次数
        double signTimes = preferences.getInt(currentEmail+"#signTimes", 0);
        // 事务完成率：已完成事务/事务总数
        int done_affairNum = 0;
        for(String affairID:affairIDList){
            if(preferences.getBoolean(currentEmail+"#affairID="+affairID+"#status", false)){
                done_affairNum++;
            }
        }
        double done_rate = (done_affairNum / affairNum)*100;
        // 内存洁癖：删除事务数/删除事务数+事务总数
        int del_affairNum = preferences.getInt(currentEmail+"#delAffairNum", 0);
        double del_rate = del_affairNum / (del_affairNum+affairNum) * 100;
        // 乐于分享：qq空间分享数+qq聊天次数
        double socialTimes = preferences.getInt(currentEmail+"#socialTimes", 0);

        // 处理成适合显示的大小
        double[] data = new double[5];
        data[0] = (affairNum+50)/100<1 ? affairNum+50:100;
        data[1] = (signTimes*5+50)/100<1 ? signTimes*5+50:100;
        data[2] = done_rate==0 ? 50:done_rate;
        data[3] = del_rate==0 ? 50:del_rate;
        data[4] = (socialTimes*5+50)/100<1 ? socialTimes*5+50:100;
        for(double i : data){
            Log.d("12345", "radar"+i);
        }

        // 找到值最小的索引
        int min_id = 0;
        double min = data[0];
        for(int i = 0; i < data.length; i++){
            if(data[i] < min){
                min = data[i];
                min_id = i;
            }
        }
        // 设置提示语
        switch (min_id){
            case 0:
                radar_reminder.setText("要多多记录哦！");
                break;
            case 1:
                radar_reminder.setText("打卡能起到督促的作用哦(ง •̀_•́)ง");
                break;
            case 2:
                radar_reminder.setText("光说不练铁FIVE(￣へ￣)");
                break;
            case 3:
                radar_reminder.setText("多清理对运行速度有帮助哦( ˙˘˙ )");
                break;
            case 4:
                radar_reminder.setText("快去和小伙伴分享这个宝藏APP吧！");
                break;
        }
        return data;
    }


}