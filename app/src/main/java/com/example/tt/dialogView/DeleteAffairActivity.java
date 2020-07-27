package com.example.tt.dialogView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tt.R;

public class DeleteAffairActivity extends AppCompatActivity {

    private AlertDialog.Builder alertDialog;
    private AlertDialog alertDialog_show;
    private TextView delete_reminder, title_text, id_text;
    private SharedPreferences.Editor editor;
    private String currentEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_affair);
    }

    public void initDeleteDialog(final Context context, final SharedPreferences preferences, final String affairID){
        View view1 = View.inflate(context, R.layout.activity_delete_affair, null);
        // 初始化控件
        delete_reminder = view1.findViewById(R.id.textView5);
        title_text = view1.findViewById(R.id.textView25);
        id_text = view1.findViewById(R.id.textView26);
        // 初始化SharedPreferences
        currentEmail = preferences.getString("currentEmail", "");
        editor = preferences.edit();

        // 设置事件标题
        title_text.setText("Title: "+preferences.getString(currentEmail+"#affairID="+affairID+"#title", ""));
        // 设置事件ID
        id_text.setText("AffairID: "+ affairID);

        // 设置并展示弹窗
        alertDialog = new AlertDialog.Builder(context);
        alertDialog
                .setTitle("Delete Affair")
                .setView(view1)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.remove(currentEmail+"#affairID="+affairID+"#title");
                        editor.remove(currentEmail+"#affairID="+affairID+"#content");
                        editor.remove(currentEmail+"#affairID="+affairID+"#date");
                        editor.remove(currentEmail+"#affairID="+affairID+"#status");
                        String[] affairIDList = preferences.getString(currentEmail+"#affairIDList", "").split(",");
                        String new_affairIDList = "";
                        for (String id:affairIDList){
                            if(id.equals(affairID)){
                                // 不将删除事务ID加入新affairIDList
                            }else{
                                // 其他事务仍加入新affairIDList
                                new_affairIDList = new_affairIDList+id+",";
                            }
                        }
                        editor.putString(currentEmail+"#affairIDList", new_affairIDList);
                        // 删除事务总数加一
                        editor.putInt(currentEmail+"#delAffairNum", preferences.getInt(currentEmail+"#delAffairNum", 0)+1);
                        editor.apply();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 不将数据删除
                    }
                })
                .create();
        alertDialog_show = alertDialog.show();
    }
}