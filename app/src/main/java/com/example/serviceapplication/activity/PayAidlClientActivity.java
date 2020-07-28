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
import android.widget.Toast;

import com.example.serviceapplication.PayAidlInterface;
import com.example.serviceapplication.R;

/**参考文章
 *https://www.jianshu.com/p/5043a1a69269
 */

public class PayAidlClientActivity extends AppCompatActivity {
    private PayAidlInterface payAidlInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.serviceapplication", "com.example.serviceapplication.PayService"));
        bindService(intent, new MyServiceConnection(), Service.BIND_AUTO_CREATE);
    }

    class MyServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            payAidlInterface = PayAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
    public void onClick(View view){

        try {
            boolean b = payAidlInterface.pay(123);
            if (b) {
                Toast.makeText(this,"支付成功",Toast.LENGTH_LONG).show();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
