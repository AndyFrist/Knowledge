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
        System.out.println("服务onBind");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("服务onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("服务onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("服务onDestroy");
    }

    private Binder binder = new PayAidlInterface.Stub() {


        @Override
        public boolean pay(int money) throws RemoteException {
            System.out.println("支付宝到账" +money);
            return true;
        }


    };
}
