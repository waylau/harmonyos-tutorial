package com.waylau.hmos.douyin.player.core.VideoPlayer;

import ohos.agp.graphics.SurfaceOps;
import ohos.global.resource.BaseFileDescriptor;
import ohos.media.common.Source;

/**
 * Interface of Player Abstraction including core playback abilities.
 */
public interface IVideoPlayer {
    void setSurface(SurfaceOps surfaceOps);

    void setSource(Source source);

    void setSource(BaseFileDescriptor assetFD);

    void prepare();

    void start();

    void stop();

    void pause();

    int getVideoWidth();

    int getVideoHeight();

    boolean isPlaying();

    /**
     * Changes the playback position.
     *
     * @param microseconds unit:microseconds
     */
    void rewindTo(long microseconds);

    long getCurrentPosition();

    long getDuration();

    boolean release();

    void setScreenOnWhilePlaying(boolean screenOn);

    void reset();

    float getPlaybackSpeed();

    boolean setPlaybackSpeed(float playbackSpeed);

    void setPlayerListeners(
            PlayerPreparedListener pl,
            PlaybackCompleteListener cl,
            RewindToCompleteListener rl,
            MessageListener ml,
            ErrorListener el,
            VideoSizeChangedListener vl,
            BufferChangedListener bl);

    interface BufferChangedListener {
        void onBufferChangedListener(int percent);
    }

    interface VideoSizeChangedListener {
        void onVideoSizeChanged(int width, int height);
    }

    interface ErrorListener {
        void onError(int errorType, int errorCode);
    }

    interface MessageListener {
        void onMessage(int type, int extra);
    }

    interface PlayerPreparedListener {
        void onPrepared();
    }

    interface PlaybackCompleteListener {
        void onPlaybackComplete();
    }

    interface RewindToCompleteListener {
        void onRewindComplete();
    }

    interface OnErrorListener {
        boolean onError(IVideoPlayer vp, int framework_err, int imp_err);
    }
}
