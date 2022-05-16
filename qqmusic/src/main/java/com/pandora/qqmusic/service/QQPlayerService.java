package com.pandora.qqmusic.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.pandora.qqmusic.IMusicPlayer;
import com.pandora.qqmusic.IMusicPlayerListener;
import com.pandora.qqmusic.Song;
import com.pandora.qqmusic.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.List;

public class QQPlayerService extends Service {

    private String TAG = this.getClass().getSimpleName();

    /**
     * 播放状态的回调
     */
    private List<IMusicPlayerListener> mPlayerListeners;

    public QQPlayerService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        NotificationUtils.startForeground(this);
        mPlayerListeners = new ArrayList<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        Log.d(TAG, "onStartCommand:: action=" + action);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return mMusicPlayerBinder;
    }

    private Binder mMusicPlayerBinder = new IMusicPlayer.Stub() {
        @Override
        public void start() throws RemoteException {
            Log.d(TAG, "start: ");
            for (IMusicPlayerListener playerListener : mPlayerListeners) {
                Song song = new Song("江南", "林俊杰", "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=%E7%BE%8E%E5%A5%B3&step_word=&hs=2&pn=0&spn=0&di=213730&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1155699197%2C3962848975&os=1796037508%2C1123141572&simid=3381608182%2C53423279&adpicid=0&lpn=0&ln=3248&fr=&fmq=1590500917797_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=girl&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic.feizl.com%2Fupload%2Fallimg%2F170615%2F19403Ac9-3.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bujtzs_z%26e3Bv54AzdH3Fip4sAzdH3Fl0b09_9_z%26e3Bip4&gsm=2&rpstart=0&rpnum=0&islist=&querylist=&force=undefined");
                playerListener.start(song);//回调开始播

                for (int i = 0; i < 100; i++) {
                    playerListener.progress(i, 100);//回调当前进度
                }
            }
        }

        @Override
        public void pause() throws RemoteException {
            Log.d(TAG, "pause: ");
        }

        @Override
        public void stop() throws RemoteException {
            Log.d(TAG, "stop: ");
        }

        @Override
        public void fastForward(long time) throws RemoteException {
            Log.d(TAG, "fastForward: time: " + time);
        }

        @Override
        public void fastBack(long time) throws RemoteException {
            Log.d(TAG, "fastBack: time: " + time);
        }

        @Override
        public void registerListener(IMusicPlayerListener listener) throws RemoteException {
            Log.d(TAG, "registerListener: ");
            mPlayerListeners.add(listener);
        }

        @Override
        public void unregisterListener(IMusicPlayerListener listener) throws RemoteException {
            Log.d(TAG, "unregisterListener: ");
            mPlayerListeners.remove(listener);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
