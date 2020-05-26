package com.pandora.qqmusic;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.pandora.qqmusic.service.QQPlayerService;

public class MyApp extends Application {

    private String TAG = this.getClass().getSimpleName();

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        init();
    }

    public static Context getContext() {
        return mContext;
    }

    private void init() {
        startService();
    }


    /**
     * 初始化服务
     */
    private void startService() {
        Intent intent = new Intent();
        intent.setClass(this, QQPlayerService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i(TAG, "> Android 8.0 ");
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }
}
