package com.waylau.hmos.douyin.player.ui.widget.media;

import com.waylau.hmos.douyin.player.core.VideoPlayer.IVideoPlayer;

import ohos.agp.components.Component;
import ohos.agp.graphics.Surface;
import ohos.agp.graphics.SurfaceOps;

/**
 * interface for surface view which will bind to video player.
 */
public interface IRenderView {
    Component getView();

    void setVideoSize(int videoWidth, int videoHeight);

    void setPlaybackWindowSize(int width, int height);

    void addRenderCallback(IRenderCallback callback);

    void removeRenderCallback(IRenderCallback callback);

    interface ISurfaceHolder {
        void bindToMediaPlayer(IVideoPlayer vp);

        IRenderView getRenderView();

        SurfaceOps getSurfaceHolder();

        Surface openSurface();
    }

    interface IRenderCallback {
        /**
         * SurfaceCreated Callback
         *
         * @param holder holder
         * @param width  could be 0
         * @param height could be 0
         */
        void onSurfaceCreated(ISurfaceHolder holder, int width, int height);

        /**
         * SurfaceChanged Callback
         *
         * @param holder holder
         * @param format could be 0
         * @param width  width
         * @param height height
         */
        void onSurfaceChanged(ISurfaceHolder holder, int format, int width, int height);

        /**
         * SurfaceDestroyed Callback
         *
         * @param holder holder
         */
        void onSurfaceDestroyed(ISurfaceHolder holder);
    }
}
