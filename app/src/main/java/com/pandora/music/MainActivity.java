package com.pandora.music;

import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pandora.qqmusic.manager.QQPlayerManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 播放
     *
     * @param view
     */
    public void onStart(View view) {
        try {
            QQPlayerManager.getInstance().start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停
     *
     * @param view
     */
    public void onPause(View view) {
        try {
            QQPlayerManager.getInstance().pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止
     *
     * @param view
     */
    public void onStop(View view) {
        try {
            QQPlayerManager.getInstance().stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 快进
     *
     * @param view
     */
    public void onFastForward(View view) {
        try {
            QQPlayerManager.getInstance().fastForward(3);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 快退
     *
     * @param view
     */
    public void onFastBack(View view) {
        try {
            QQPlayerManager.getInstance().fastBack(5);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
