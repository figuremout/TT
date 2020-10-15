package com.example.tt.ui.done;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tt.R;
import com.example.tt.dialogView.DeleteAffairActivity;
import com.example.tt.editAffairActivity;
import com.example.tt.util.DateUtil;
import com.example.tt.util.myHttp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class DoneFragment extends Fragment {

    private DoneViewModel doneViewModel;
    private LinearLayout ll;
    private Button item_button;
    private View root;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String currentEmail;
    private ImageView empty_img;
    private TextView empty_text, item_date, item_title, item_content;
    private CheckBox item_check;
    private Boolean isRecycle;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        doneViewModel =
                ViewModelProviders.of(this).get(DoneViewModel.class);
        root = inflater.inflate(R.layout.fragment_done, container, false);

        initView();
        Log.d("12345", "oncreatefragment");

        // 获取所有事件并显示
        try {
            getAllDoneAffair();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return root;
    }


    private void initView(){
        ll = root.findViewById(R.id.ll); // 元素需要到root里去找

        // sharedPreferences
        preferences = requireActivity().getSharedPreferences("shared", MODE_PRIVATE);
        editor = preferences.edit();
        currentEmail = preferences.getString("currentEmail", "");

        // 事务列表为空的提示
        empty_img = root.findViewById(R.id.imageView11);
        empty_text = root.findViewById(R.id.textView22);

        // 初始化下拉刷新layout
        swipeRefreshLayout = root.findViewById(R.id.refreshLayout);
        //设置进度条的颜色主题，最多能设置四种 加载颜色是循环播放的，只要没有完成刷新就会一直循环
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        // 设定下拉圆圈的背景
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT); // LARGE

        //设置下拉刷新的监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getAllDoneAffair();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 重新加载完成，收起下拉进度
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    // 读取所有事务并添加事务项（需要界面重新加载才能显示）
    // todo 优化，提升速度
    private void getAllDoneAffair() throws ParseException {
        isRecycle = preferences.getBoolean(currentEmail+"#settings#isRecycle", false);
        // 清空界面
        ll.removeAllViews();
        // 重新获取当前账户，避免错误
        currentEmail = preferences.getString("currentEmail", "");
        if(currentEmail.trim().length()==0){
            // 未登录
            if_empty(true);
        }else{
            // 已登录
            final String[] affairList_str = new String[1];
            final JSONArray[] affairsArray = new JSONArray[1];
            Thread getAllAffairsThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    affairList_str[0] = myHttp.getHTTPReq("/getAllAffairs?email="+currentEmail);
                    affairsArray[0] = myHttp.getJsonArray(affairList_str[0]);
                }
            });
            getAllAffairsThread.start();
            try {
                getAllAffairsThread.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            if(affairsArray[0].length()==0){
                // 该账户尚无事务，避免用空字符串创建一个事务
                if_empty(true);
                return;
            }else{
                // 该账户已有事务，获取所有已完成事务并显示
                if_empty(true);
                for (int i = 0; i < affairsArray[0].length(); i++){
                    try {
                        JSONObject affair = affairsArray[0].getJSONObject(i);
                        if(affair.getBoolean("status")){
                            addItem(affair.getString("affairID"));
                            if_empty(false);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 添加事务项
    /**
     * 事务项的所有控件都在这里控制
     * @param affairID
     * @throws ParseException
     */
    private void addItem(final String affairID) throws ParseException {
        final View new_view = View.inflate(getContext(), R.layout.affair_item, null); // getContext()在fragment中获取上下文
        item_button = new_view.findViewById(R.id.itemButton);
        item_date = new_view.findViewById(R.id.textView24);
        item_title = new_view.findViewById(R.id.textView23);
        item_check = new_view.findViewById(R.id.checkBox);
        item_content = new_view.findViewById(R.id.textView6);

        final String[] affair_str = new String[1];
        final JSONObject[] affair = new JSONObject[1];
        Thread getOneAffairThread = new Thread(new Runnable() {
            @Override
            public void run() {
                affair_str[0] = myHttp.getHTTPReq("/getOneAffair?email="+currentEmail+"&affairID="+affairID);
                affair[0] = myHttp.getJsonObject(affair_str[0]);
            }
        });
        getOneAffairThread.start();
        try {
            getOneAffairThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 获取该事务设置的日期，并显示
        try {
            String affairDate_str = affair[0].getString("date");
            item_date.setText(affairDate_str);
            // 获取当前日期，判断该事务是否已过期
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            Date date = new Date(System.currentTimeMillis());//获取当前时间
            Date affairDate = simpleDateFormat.parse(affairDate_str);
            if(DateUtil.differentDays(date, affairDate) < 0){
                // 事务已过期
                item_date.setTextColor(getResources().getColor(R.color.outofdate));
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        try {
            // 显示事务Title
            String title = affair[0].getString("title");
            item_title.setText(title);

            // 显示事务Content
            String content = affair[0].getString("content");
            item_content.setText(content);

            // checkBox初始化
            final Boolean isChecked = Boolean.parseBoolean(affair[0].getString("status"));
            item_check.setChecked(isChecked);
        }catch (Exception e){
            e.printStackTrace();
        }

        // 将事务ID赋给按钮Text，用于调试，实际字体颜色设为透明不显示
        item_button.setText(affairID);
        // 单击跳转到事务编辑界面
        item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("12345", "ohhhhhhh");
                // 将存储数据currentAffairID设置为本按钮对应的事务ID
                editor.putString("currentAffairID", affairID);
                editor.apply();
                Intent intent = new Intent(getActivity(), editAffairActivity.class);
                startActivity(intent);
            }
        });
        // 长按跳出是否删除弹窗
        item_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // 删除该事务存储的所有信息
//                deleteItem(new_view);
                DeleteAffairActivity delete = new DeleteAffairActivity();
                delete.initDeleteDialog(getContext(), preferences, affairID);
                return true;
            }
        });

        // checkbox初始化
        item_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    // 选中了
                    Thread updateOneAffairThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            myHttp.postHTTPReq("/updateOneAffair", "email="+currentEmail+"&affairID="+affairID+"&status=true");
                        }
                    });
                    updateOneAffairThread.start();

                    // 回收模式下，勾选事件直接消失
                    isRecycle = preferences.getBoolean(currentEmail+"#settings#isRecycle", false);
                    if(isRecycle){
                        ll.removeView(new_view);
                        if (ll.getChildCount()==0){ // 当前列表显示的事务项数为0时
                            if_empty(true);
                        }
                    }
                }else{
                    // 未选中
                    ll.removeView(new_view);
                    Thread updateOneAffairThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            myHttp.postHTTPReq("/updateOneAffair", "email="+currentEmail+"&affairID="+affairID+"&status=false");
                        }
                    });
                    updateOneAffairThread.start();
                }
                editor.apply();
            }
        });
        ll.addView(new_view); //添加一个View
    }

    private void if_empty(Boolean is_empty){
        if(is_empty){
            empty_text.setVisibility(View.VISIBLE);
            empty_img.setVisibility(View.VISIBLE);
        }else{
            empty_text.setVisibility(View.INVISIBLE);
            empty_img.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStart() {
        Log.d("12345", "onstartfragment");
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 登录后以及退出登录后，都会执行onResume()，在这里刷新事务列表可覆盖这两种情况
        try {
            getAllDoneAffair();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("12345", "onresumefragment");
    }

    @Override
    public void onPause() {
        Log.d("12345", "onpausefragment");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("12345", "onstopfragment");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("12345", "ondestroyfragment");
        super.onDestroy();
    }
}