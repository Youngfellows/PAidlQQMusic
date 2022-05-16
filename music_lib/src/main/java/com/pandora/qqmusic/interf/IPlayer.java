package com.pandora.qqmusic.interf;


import android.os.RemoteException;

public interface IPlayer {

    /**
     * 开始播放
     */
    void start() throws RemoteException;

    /**
     * 暂停播放
     */
    void pause() throws RemoteException;

    /**
     * 停止播放
     */
    void stop() throws RemoteException;

    /**
     * 快进播放
     */
    void fastForward(long time) throws RemoteException;

    /**
     * 快退播放
     */
    void fastBack(long time) throws RemoteException;
}
