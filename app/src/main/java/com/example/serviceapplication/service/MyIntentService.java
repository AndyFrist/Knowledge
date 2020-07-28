package com.example.serviceapplication.service;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.serviceapplication.activity.IntentServiceActivity;


public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    /**
     * 是否正在运行
     */
    private boolean isRunning;

    /**
     *进度
     */
    private int count;



    public MyIntentService() {
        super("MyIntentService");
        Log.e(TAG,"MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG,"onHandleIntent");
        System.out.println("所在线程------"+Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
            isRunning = true;
            count = 0;
            while (isRunning) {
                count++;
                if (count >= 100) {
                    isRunning = false;
                }
                Thread.sleep(50);
                sendThreadStatus(count);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送进度消息
     */
    private void sendThreadStatus(int progress) {
        Intent intent = new Intent(IntentServiceActivity.ACTION_TYPE_THREAD);
        intent.putExtra("status", "线程运行中...");
        intent.putExtra("progress", progress);
       sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"线程结束运行..." + count);
    }
}