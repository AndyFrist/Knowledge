package com.example.serviceapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class PayService extends Service{
    public PayService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("服务绑定了");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("服务启动了");
        return super.onStartCommand(intent, flags, startId);
    }

    private Binder binder = new PayAidlInterface.Stub() {


        @Override
        public boolean pay(int money) throws RemoteException {
            System.out.println("支付宝到账" +money);
            return true;
        }


    };
}
