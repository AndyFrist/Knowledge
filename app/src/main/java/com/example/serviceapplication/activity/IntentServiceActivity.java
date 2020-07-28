package com.example.serviceapplication.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceapplication.R;
import com.example.serviceapplication.service.MyIntentService;

/** 参考博客
 * https://www.jianshu.com/p/be97093783d6
 */

public class IntentServiceActivity extends AppCompatActivity {
    private static final String TAG = "IntentServiceActivity";
    /**
     * 状态文字
     */
    TextView tv_status;

    /**
     * 进度文字
     */
    TextView tv_progress;

    /**
     * 进度条
     */
    ProgressBar progressbar;


    private MyBroadcastReceiver mBroadcastReceiver;
    public final static String ACTION_TYPE_THREAD = "action.type.thread";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        tv_status = findViewById(R.id.tv_status);
        tv_progress = findViewById(R.id.tv_progress);
        progressbar = findViewById(R.id.progressbar);

        //注册广播
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_TYPE_THREAD);
        registerReceiver(mBroadcastReceiver, intentFilter);

        initView();
    }

    public void initView() {
        tv_status.setText("线程状态：未运行");
        progressbar.setMax(100);
        progressbar.setProgress(0);
        tv_progress.setText("0%");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        unregisterReceiver(mBroadcastReceiver);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_TYPE_THREAD.equals(intent.getAction())) {//更改UI
                int progress = intent.getIntExtra("progress", 0);
                String status = intent.getStringExtra("status");
                tv_status.setText("线程状态：" + status);
                progressbar.setProgress(progress);
                tv_progress.setText(progress + "%");
                if (progress >= 100) {
                    tv_status.setText("线程结束");
                }
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Intent intent = new Intent(IntentServiceActivity.this, MyIntentService.class);
                startService(intent);
                Log.e(TAG, "开启服务");
                break;
        }
    }
}