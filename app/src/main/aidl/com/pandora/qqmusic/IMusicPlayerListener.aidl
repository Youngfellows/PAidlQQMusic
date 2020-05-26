package com.pandora.qqmusic;
import com.pandora.qqmusic.Song;

/**
 * 播放器状态回调接口
 */
interface IMusicPlayerListener {

    /**
     * 开始播放
     */
    void start(in Song song);

    /**
     * 暂停播放
     */
    void pause();

    /**
     * 停止播放
     */
    void stop();

    /**
     * 当前播放进度
     * @param currentTime: 当前播放时间点
     * @param totalTime: 总时长
     */
    void progress(long currentTime,long totalTime);
}
