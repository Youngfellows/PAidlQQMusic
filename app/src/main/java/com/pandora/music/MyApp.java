package com.pandora.music;

import android.app.Application;
import android.content.Context;

import com.pandora.qqmusic.manager.QQPlayerManager;


public class MyApp extends Application {

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
        QQPlayerManager.getInstance().init(this);
    }

}
