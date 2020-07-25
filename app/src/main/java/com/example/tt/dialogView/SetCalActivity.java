package com.example.tt.dialogView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.tt.MainActivity;
import com.example.tt.R;

public class SetCalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_cal);
    }


    /**
     * 初始化日历弹窗，在AddAffairActivity中调用
     */
    public static void initCalDialog(Context context){
        //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//                AlertDialog alertDialog1 = new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("这是标题")//标题
//                        .setMessage("这是内容")//内容
//                        .setIcon(R.drawable.radar)//图标
//
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Toast.makeText(MainActivity.this, "这是确定按钮", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Toast.makeText(MainActivity.this, "这是取消按钮", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNeutralButton("普通按钮", new DialogInterface.OnClickListener() {//添加普通按钮
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Toast.makeText(MainActivity.this, "这是普通按钮按钮", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .create();
//                alertDialog1.show();
        View view2 = View.inflate(context, R.layout.activity_set_cal, null);
        // 设置并展示弹窗
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog
                .setTitle("Set Time")
                .setView(view2)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNeutralButton("普通按钮", new DialogInterface.OnClickListener() {//添加普通按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        alertDialog.show();
    }
}