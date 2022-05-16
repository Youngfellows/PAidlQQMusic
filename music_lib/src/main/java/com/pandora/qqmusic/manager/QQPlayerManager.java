package com.pandora.qqmusic.manager;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.pandora.qqmusic.interf.IPlayer;
import com.pandora.qqmusic.utils.IntentUtil;
import com.pandora.qqmusic.IMusicPlayer;
import com.pandora.qqmusic.IMusicPlayerListener;
import com.pandora.qqmusic.Song;

public class QQPlayerManager implements IPlayer {

    private String TAG = this.getClass().getSimpleName();

    /**
     * AIDL服务应用的包名
     */
    private static final String PACKAGE_NAME = "com.pandora.qqmusic";

    /**
     * AIDL服务详情
     */
    private static final String SERVICE_NAME = "com.pandora.qqmusic.service.QQPlayerService";

    /**
     * 启动QQ音乐服务的Actiom
     */
    private static final String ACTION_QQ_MUSIC = "com.pandora.qqmusic.ACTION_QQ_MUSIC";

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 远程AIDL服务
     */
    private IMusicPlayer mMusicPlayer;

    /**
     * 是否绑定成功
     */
    private boolean isBind;

    private QQPlayerManager() {

    }

    private static class Holder {
        private static QQPlayerManager INSTANCE = new QQPlayerManager();
    }

    public static QQPlayerManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        this.mContext = context;
        binderService();
    }

    /**
     * 检测是否初始化
     *
     * @return
     */
    private boolean checkInit() {
        if (mContext == null) {
            throw new IllegalArgumentException("mContext is null ... ");
        }
        return true;
    }

    /**
     * 绑定服务
     */
    private void binderService() {
        checkInit();
        Log.d(TAG, "binderService isBind = " + isBind);
        if (!isBind) {
            try {
                Intent intent = new Intent();
                //intent.setClassName(PACKAGE_NAME, SERVICE_NAME);
                intent.setAction(ACTION_QQ_MUSIC);
                intent = IntentUtil.createExplicitFromImplicitIntent(mContext, intent);
                Log.d(TAG, "intent = " + intent);
                if (intent != null) {
                    //绑定服务
                    this.isBind = mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "绑定QQ_Music服务异常,未安装APK: " + e.getMessage());
                Log.w(TAG, Log.getStackTraceString(e));
            }

            if (isBind) {
                Log.d(TAG, "QQ_Music服务绑定成功了...");
            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            try {
                Log.i(TAG, "onServiceConnected 绑定服务成功...");
                mMusicPlayer = IMusicPlayer.Stub.asInterface(iBinder);
                try {
                    iBinder.linkToDeath(mDeathRecipient, 0); //注册死亡监听
                    mMusicPlayer.registerListener(mMusicPlayerListener);//注册服务端播放状态回调
                } catch (RemoteException e) {
                    Log.e(TAG, "onServiceConnected 注册监听回调异常了: " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.e(TAG, "onServiceConnected 绑定服务异常了: " + e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected 解绑服务: mMusicPlayer = " + mMusicPlayer);
            if (mMusicPlayer != null) {
                //注销监听回调
                try {
                    mMusicPlayer.unregisterListener(mMusicPlayerListener);
                    mMusicPlayer = null;
                    isBind = false;
                } catch (RemoteException e) {
                    Log.e(TAG, "onServiceDisconnected 注销注册监听回调异常: " + e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                    Log.e(TAG, "onServiceDisconnected: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 播放状态的监听回调
     */
    private IMusicPlayerListener.Stub mMusicPlayerListener = new IMusicPlayerListener.Stub() {
        @Override
        public void start(Song song) throws RemoteException {
            Log.d(TAG, "start: song: " + song);
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
        public void progress(long currentTime, long totalTime) throws RemoteException {
            Log.d(TAG, "progress: " + ((currentTime * 1.0 / totalTime) * 100) + "%");
        }
    };

    /**
     * 服务死亡监听
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG, "binderDied binder is died ,mMusicPlayer = " + mMusicPlayer);
            if (mMusicPlayer != null) {
                mMusicPlayer.asBinder().unlinkToDeath(mDeathRecipient, 0);
                mMusicPlayer = null;//置空
                isBind = false;
                binderService();//从新绑定服务
            }
        }
    };

    /**
     * 解绑服务
     */
    private void unbindService() {
        try {
            Log.i(TAG, "disconnect mic manager service ,isBind = " + isBind);
            if (isBind) {
                mContext.unbindService(mConnection);
            }
            isBind = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start() throws RemoteException {
        Log.d(TAG, "start: ");
        checkInit();
        if (mMusicPlayer != null) {
            mMusicPlayer.start();
        }
    }

    @Override
    public void pause() throws RemoteException {
        Log.d(TAG, "pause: ");
        checkInit();
        if (mMusicPlayer != null) {
            mMusicPlayer.pause();
        }
    }

    @Override
    public void stop() throws RemoteException {
        Log.d(TAG, "stop: ");
        checkInit();
        if (mMusicPlayer != null) {
            mMusicPlayer.stop();
        }
    }

    @Override
    public void fastForward(long time) throws RemoteException {
        Log.d(TAG, "fastForward: ");
        checkInit();
        if (mMusicPlayer != null) {
            mMusicPlayer.fastForward(time);
        }
    }

    @Override
    public void fastBack(long time) throws RemoteException {
        Log.d(TAG, "fastBack: ");
        checkInit();
        if (mMusicPlayer != null) {
            mMusicPlayer.fastBack(time);
        }
    }
}
