// IMusicPlayer.aidl
package com.pandora.qqmusic;

import com.pandora.qqmusic.IMusicPlayerListener;
/**
 * 音乐播放器控制
 */
interface IMusicPlayer {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    //void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,double aDouble, String aString);

    /**
     * 开始播放
     */
    void start();

    /**
     * 暂停播放
     */
    void pause();

    /**
     * 停止播放
     */
    void stop();

    /**
     * 快进播放
     */
    void fastForward(long time);

    /**
     * 快退播放
     */
    void fastBack(long time);

    /**
     * 注册播放状态回调
     */
    void registerListener(IMusicPlayerListener listener);

    /**
     * 注销播放状态回调
     */
    void unregisterListener(IMusicPlayerListener listener);
}
