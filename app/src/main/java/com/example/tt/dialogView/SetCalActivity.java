package com.example.tt.dialogView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.tt.MainActivity;
import com.example.tt.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SetCalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_cal);
    }


    /**
     * 初始化日历弹窗，在AddAffairActivity中调用
     */
    public static void initCalDialog(final Context context, final SharedPreferences preferences){
        View view2 = View.inflate(context, R.layout.activity_set_cal, null);
        // 初始化控件
        final CalendarView calendarView = view2.findViewById(R.id.calendarView);
        Button setTimeButton = view2.findViewById(R.id.button8);
        Button setAlarmButton = view2.findViewById(R.id.button9);
        Button setRepeatButton = view2.findViewById(R.id.button10);
        // 初始化SharedPreferences
        final SharedPreferences.Editor editor = preferences.edit();

        // 获取系统时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        final Date date = new Date(System.currentTimeMillis());//获取当前时间
        String date_str = simpleDateFormat.format(date);

        // 初始化preference中的当前事务的时间，若用户没选择日期则默认为当天日期
        editor.putString("currentAffairDate", date_str);

        // 初始化日历选择事件
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                // i-年 i1-月 i2-日
                // 注意！！！！i1-月 范围是0-11
                // 注意！！！！日期统一存储格式是yyyy.mm.dd，单位数日期仍显示两位
                int real_month = i1+1;
                String month,day;
                if(real_month/10 == 0){
                    // 月数为单位数
                    month = "0"+real_month;
                }else {
                    month = Integer.toString(real_month);
                }
                if(i2/10 == 0){
                    // 日数为单位数
                    day = "0"+i2;
                }else{
                    day = Integer.toString(i2);
                }
                editor.putString("currentAffairDate", i+"."+month+"."+day);
            }
        });

        // 点击设置提醒按钮事件
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 设置并展示弹窗
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog
                .setTitle(R.string.setDate)
                .setView(view2)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.apply();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 不将数据存进去
                    }
                })
//                .setNeutralButton("普通按钮", new DialogInterface.OnClickListener() {//添加普通按钮
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if(calendarView.getVisibility() == View.VISIBLE){
//                            calendarView.setVisibility(View.INVISIBLE);
//                        }else{
//                            calendarView.setVisibility(View.VISIBLE);
//                        }
//                    }
//                })
                .create();
        alertDialog.show();
    }
}