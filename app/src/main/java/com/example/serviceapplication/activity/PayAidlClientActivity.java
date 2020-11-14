package com.example.serviceapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.serviceapplication.PayAidlInterface;
import com.example.serviceapplication.R;

/**参考文章
 *https://www.jianshu.com/p/5043a1a69269
 */

public class PayAidlClientActivity extends AppCompatActivity implements View.OnClickListener {
    private PayAidlInterface payAidlInterface;
    private Button button0,button1, button2;
    private  MyServiceConnection myServiceConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
       myServiceConnection = new MyServiceConnection();
    }

    class MyServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            payAidlInterface = PayAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("服务onServiceDisconnected");
        }
    }
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.button0:
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.example.serviceapplication", "com.example.serviceapplication.PayService"));
                bindService(intent, myServiceConnection, Service.BIND_AUTO_CREATE);
                break;
            case R.id.button1:
                try {
                    boolean b = payAidlInterface.pay(123);
                    if (b) {
                        Toast.makeText(this,"支付成功",Toast.LENGTH_LONG).show();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button2:
                try {
                    unbindService(myServiceConnection);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this,"没有连接到服务",Toast.LENGTH_LONG).show();
                }
                break;

        }

    }
}
