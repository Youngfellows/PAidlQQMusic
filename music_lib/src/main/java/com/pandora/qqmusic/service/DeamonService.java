package com.pandora.qqmusic.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.pandora.qqmusic.manager.QQPlayerManager;

public class DeamonService extends Service {

    private String TAG = this.getClass().getSimpleName();

    /**
     * 保活进程服务的ACTION
     */
    public static String ACTION_DEAMON = "com.pandora.qqmusic.ACTION_DEAMON";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate:: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        Log.d(TAG, "onStartCommand:: action=" + action);
        if (ACTION_DEAMON == action) {
            QQPlayerManager.getInstance().init(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy:: ");
    }
}
