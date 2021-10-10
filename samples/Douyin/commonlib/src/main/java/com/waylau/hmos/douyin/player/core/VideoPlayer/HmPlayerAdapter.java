package com.waylau.hmos.douyin.player.core.VideoPlayer;

import ohos.agp.graphics.SurfaceOps;
import ohos.app.Context;
import ohos.global.resource.BaseFileDescriptor;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.common.Source;
import ohos.media.player.Player;

/**
 * An Adapter for System default player to fit with IVideoPlayer interface.
 */
public class HmPlayerAdapter implements IVideoPlayer {
    /**
     * Indicates a media file loading error, please check whether the network is available.
     */
    public static final int ERROR_LOADING_RESOURCE = 1;

    /**
     * Indicates invalid operation.
     */
    public static final int ERROR_INVALID_OPERATION = 62980137;

    private static final HiLogLabel LABEL = new HiLogLabel(0, 0, "HmPlayerAdapter");

    private Player videoPlayer;

    public HmPlayerAdapter(Context context) {
        videoPlayer = new Player(context);
    }

    @Override
    public void setSurface(SurfaceOps surfaceOps) {
        HiLog.info(LABEL, "setSurface");
        videoPlayer.setSurfaceOps(surfaceOps);
    }

    @Override
    public void setSource(Source source) {
        HiLog.info(LABEL, "setSource");
        videoPlayer.setSource(source);
    }

    @Override
    public void setSource(BaseFileDescriptor assetFD) {
        HiLog.info(LABEL, "setAssetFD");
        videoPlayer.setSource(assetFD);
    }

    @Override
    public void prepare() {
        HiLog.info(LABEL, "prepare");
        videoPlayer.prepare();
    }

    @Override
    public void start() {
        HiLog.info(LABEL, "start");
        videoPlayer.play();
    }

    @Override
    public void stop() {
        HiLog.info(LABEL, "stop");
        videoPlayer.stop();
    }

    @Override
    public void pause() {
        HiLog.info(LABEL, "pause");
        videoPlayer.pause();
    }

    @Override
    public int getVideoWidth() {
        int videoWidth = videoPlayer.getVideoWidth();
        HiLog.info(LABEL, "getVideoWidth = " + videoWidth);
        return videoWidth;
    }

    @Override
    public int getVideoHeight() {
        int videoHeight = videoPlayer.getVideoHeight();
        HiLog.info(LABEL, "getVideoHeight = " + videoHeight);
        return videoHeight;
    }

    @Override
    public boolean isPlaying() {
        boolean nowPlaying = videoPlayer.isNowPlaying();
        HiLog.info(LABEL, "isPlaying? = " + nowPlaying);
        return nowPlaying;
    }

    @Override
    public void rewindTo(long microseconds) {
        HiLog.info(LABEL, "rewindTo " + microseconds);
        videoPlayer.rewindTo(microseconds);
    }

    @Override
    public long getCurrentPosition() {
        int currentTime = videoPlayer.getCurrentTime();
        HiLog.info(LABEL, "getCurrentPosition = " + currentTime);
        return currentTime;
    }

    @Override
    public long getDuration() {
        int duration = videoPlayer.getDuration();
        HiLog.info(LABEL, "getDuration = " + duration);
        return duration;
    }

    @Override
    public boolean release() {
        HiLog.info(LABEL, "release");
        return videoPlayer.release();
    }

    @Override
    public void setScreenOnWhilePlaying(boolean screenOn) {
        HiLog.info(LABEL, "setScreenOnWhilePlaying: " + screenOn);
        videoPlayer.enableScreenOn(screenOn);
    }

    @Override
    public void reset() {
        HiLog.info(LABEL, "reset");
        videoPlayer.reset();
    }

    @Override
    public float getPlaybackSpeed() {
        float playbackSpeed = videoPlayer.getPlaybackSpeed();
        HiLog.info(LABEL, "getPlaybackSpeed = " + playbackSpeed);
        return playbackSpeed;
    }

    @Override
    public boolean setPlaybackSpeed(float playbackSpeed) {
        HiLog.info(LABEL, "setPlaybackSpeed = " + playbackSpeed);
        return videoPlayer.setPlaybackSpeed(playbackSpeed);
    }

    @Override
    public void setPlayerListeners(
            PlayerPreparedListener pl,
            PlaybackCompleteListener cl,
            RewindToCompleteListener rl,
            MessageListener ml,
            ErrorListener el,
            VideoSizeChangedListener vl,
            BufferChangedListener bl) {
        videoPlayer.setPlayerCallback(
                new Player.IPlayerCallback() {
                    @Override
                    public void onPrepared() {
                        pl.onPrepared();
                    }

                    @Override
                    public void onMessage(int info, int extra) {
                        ml.onMessage(info, extra);
                    }

                    @Override
                    public void onError(int error, int extra) {
                        el.onError(error, extra);
                    }

                    @Override
                    public void onResolutionChanged(int code, int extra) {
                        vl.onVideoSizeChanged(code, extra);
                    }

                    @Override
                    public void onPlayBackComplete() {
                        cl.onPlaybackComplete();
                    }

                    @Override
                    public void onRewindToComplete() {
                        rl.onRewindComplete();
                    }

                    @Override
                    public void onBufferingChange(int code) {
                        bl.onBufferChangedListener(code);
                    }

                    @Override
                    public void onNewTimedMetaData(Player.MediaTimedMetaData mediaTimedMetaData) {
                    }

                    @Override
                    public void onMediaTimeIncontinuity(Player.MediaTimeInfo mediaTimeInfo) {
                    }
                });
    }
}
