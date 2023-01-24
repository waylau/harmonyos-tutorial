/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.api;

import com.waylau.hmos.shortvideo.player.VideoPlayer;
import com.waylau.hmos.shortvideo.constant.PlayerStatusEnum;
import ohos.agp.graphics.Surface;


/**
 * Video Player interface
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public interface IVideoPlayer {
    /**
     * addSurface
     *
     * @param surface surface
     */
    void addSurface(Surface surface);

    /**
     * addPlayerStatuCallback
     *
     * @param callback callback
     */
    void addPlayerStatuCallback(StatuChangeListener callback);

    /**
     * play
     */
    void play();

    /**
     * replay
     */
    void replay();

    /**
     * reload
     *
     * @param filepath filepath
     * @param startMillisecond startMillisecond
     */
    void reload(String filepath, int startMillisecond);

    /**
     * resume
     */
    void resume();

    /**
     * pause
     */
    void pause();

    /**
     * getCurrentPosition
     *
     * @return current position
     */
    int getCurrentPosition();

    /**
     * getDuration
     *
     * @return duration
     */
    int getDuration();

    /**
     * getVideoScale
     *
     * @return double
     */
    double getVideoScale();

    /**
     * rewindTo
     *
     * @param startMicrosecond startMicrosecond(ms)
     */
    void rewindTo(int startMicrosecond);

    /**
     * isPlaying
     *
     * @return isPlaying
     */
    boolean isPlaying();

    /**
     * stop
     */
    void stop();

    /**
     * release
     */
    void release();

    /**
     * getLifecycle
     *
     * @return ImplLifecycle
     */
    IVideoPlayerLifecycle getLifecycle();

    /**
     * getBuilder
     *
     * @return Builder
     */
    VideoPlayer.Builder getBuilder();

    /**
     * getPlayerStatus
     *
     * @return PlayerStatusEnum
     */
    PlayerStatusEnum getPlayerStatus();
}
