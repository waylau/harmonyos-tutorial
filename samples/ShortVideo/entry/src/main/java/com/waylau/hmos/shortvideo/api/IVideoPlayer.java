/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.api;

import com.waylau.hmos.shortvideo.player.VideoPlayer;
import com.waylau.hmos.shortvideo.constant.PlayerStatus;
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
     * removePlayerStatuCallback
     *
     * @param callback callback
     */
    void removePlayerStatuCallback(StatuChangeListener callback);

    /**
     * addPlayerViewCallback
     *
     * @param callback callback
     */
    void addPlayerViewCallback(ScreenChangeListener callback);

    /**
     * removePlayerViewCallback
     *
     * @param callback callback
     */
    void removePlayerViewCallback(ScreenChangeListener callback);

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
     * getVolume
     *
     * @return float
     */
    float getVolume();

    /**
     * set play volume
     *
     * @param volume 0~1
     */
    void setVolume(float volume);

    /**
     * set play speed
     *
     * @param speed 0~12
     */
    void setPlaySpeed(float speed);

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
     * getPlayerStatu
     *
     * @return PlayerStatu
     */
    PlayerStatus getPlayerStatu();

    /**
     * resizeScreen
     *
     * @param width width
     * @param height height
     */
    void resizeScreen(int width, int height);

    /**
     * openGesture
     *
     * @param isOpen isOpen
     */
    void openGesture(boolean isOpen);

    /**
     * openGesture
     *
     * @return isGestureOpen
     */
    boolean isGestureOpen();
}
