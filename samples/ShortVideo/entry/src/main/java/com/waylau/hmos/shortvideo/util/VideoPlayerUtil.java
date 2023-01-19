package com.waylau.hmos.shortvideo.util;

import ohos.agp.graphics.Surface;
import ohos.app.Context;
import ohos.global.resource.RawFileDescriptor;
import ohos.media.common.Source;
import ohos.media.player.Player;

import java.io.IOException;

/**
 * 播放器工具类
 */
public class VideoPlayerUtil {
    // 播放器
    private Player mPlayer;

    /**
     * 单例，构造方法私有
     */
    private VideoPlayerUtil() {
    }

    /**
     * 获取单例
     *
     * @return 单例
     */
    public static VideoPlayerUtil getInstance() {
        return SingletonHolder.sInstance;
    }

    /**
     * 静态内部类，为了单例使用
     */
    private static class SingletonHolder {
        private static final VideoPlayerUtil sInstance = new VideoPlayerUtil();
    }


    /**
     * 设置播放配置
     *
     * @param context   上下文
     * @param videoPath 视频路径
     * @param surface   播放窗口
     */
    public void setPlayerConfig(Context context, String videoPath, Surface surface) {
        if (mPlayer == null) {
            mPlayer = new Player(context);
        }
        try {
            RawFileDescriptor fileDescriptor = context.getResourceManager().getRawFileEntry(videoPath).openRawFileDescriptor();
            Source source = new Source(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartPosition(),
                    fileDescriptor.getFileSize()
            );
            // 设置媒体文件
            mPlayer.setSource(source);
            // 设置播放窗口
            mPlayer.setVideoSurface(surface);
            // 循环播放
            mPlayer.enableSingleLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 改变播放位置
     */
    public void rewindTo(long microseconds) {
        if (mPlayer == null) {
            return;
        }
        mPlayer.rewindTo(microseconds);
    }

    /**
     * 播放视频
     */
    public void playerVideo() {
        if (mPlayer == null) {
            return;
        }
        // 准备播放
        mPlayer.prepare();
        // 开始播放
        mPlayer.play();
    }


    /**
     * 暂停视频
     */
    public void pauseVideo() {
        if (mPlayer == null) {
            return;
        }
        mPlayer.pause();
    }

    /**
     * 停止视频
     */
    public void stopVideo() {
        if (mPlayer == null) {
            return;
        }
        mPlayer.stop();
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mPlayer == null) {
            return;
        }
        mPlayer.release();
        mPlayer = null;
    }

    /**
     * 是否播放
     */
    public boolean isPlaying() {
        if (mPlayer == null) {
            return false;
        }
        return mPlayer.isNowPlaying();
    }

    /**
     * 获取播放位置，单位为毫秒。
     */
    public int getCurrentTime() {
        if (mPlayer == null) {
            return 0;
        }
        return mPlayer.getCurrentTime();
    }
}
