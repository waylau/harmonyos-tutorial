package com.waylau.hmos.douyin.player.ui.widget.media;

import com.waylau.hmos.douyin.utils.ScreenUtils;

import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.utils.Point;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.lang.ref.WeakReference;

/**
 * Used to measure the optimal playback window size
 */
public final class MeasureHelper {
    /**
     * log label.
     */
    public static final HiLogLabel LABEL = new HiLogLabel(0, 0, "Measure");

    private final WeakReference<Component> weakView;

    private int videoWidth;
    private int videoHeight;

    private int playbackWindowWidth;
    private int playbackWindowHeight;

    private int measuredWidth;
    private int measuredHeight;

    /**
     * constructor of MeasureHelper.
     *
     * @param view Component to be measured.
     */
    public MeasureHelper(Component view) {
        weakView = new WeakReference<>(view);
    }

    /**
     * get measured component.
     *
     * @return measured component.
     */
    public Component getView() {
        if (weakView == null) {
            return null;
        }
        return weakView.get();
    }

    /**
     * Set video size,used to adapt playback window size(SurfaceView size)
     *
     * @param videoWidth  video width.
     * @param videoHeight video height.
     */
    public void setVideoSize(int videoWidth, int videoHeight) {
        this.videoWidth = videoWidth;
        this.videoHeight = videoHeight;
    }

    /**
     * Set playback window size
     *
     * @param width  window width.
     * @param height window height.
     */
    public void setPlaybackWindowSize(int width, int height) {
        this.playbackWindowWidth = width;
        this.playbackWindowHeight = height;
    }

    /**
     * Calculate playback window size(SurfaceView size) by video size and parent container{@link VideoPlayerView} size
     * <p>Default calculate method:isPortrait?window width fit parent container width,
     * window height fit parent container height
     *
     * <p>Suggest: parent container{@link VideoPlayerView} "width:match_parent","height:Exact value like 250vp"
     *
     * @param context component context.
     */
    public void doMeasure(Context context) {
        if (videoWidth == 0 || videoHeight == 0) {
            HiLog.error(LABEL, "Please call setVideoSize() to set the video size first.");
            return;
        }
        Point screenSize = ScreenUtils.getScreenSize(context);
        int screenWidth = screenSize.getPointXToInt();
        int screenHeight = screenSize.getPointYToInt();

        if (playbackWindowWidth == ComponentContainer.LayoutConfig.MATCH_PARENT) {
            playbackWindowWidth = screenWidth;
        }

        if (playbackWindowHeight == ComponentContainer.LayoutConfig.MATCH_PARENT) {
            playbackWindowHeight = screenWidth;
        }

        boolean isPortrait = screenHeight >= screenWidth;

        // get the width and height of Video Surface, the adaption rule of Video Surface:
        // Portrait - match height defined in Layout XML
        // Landscape - full screen
        int videoBoxWidth = isPortrait ? playbackWindowWidth : screenWidth;
        int videoBoxHeight = isPortrait ? playbackWindowHeight : screenHeight;
        HiLog.debug(LABEL, "VideoBox View width = " + videoBoxWidth + " height = " + videoBoxHeight);

        // Calculating Video Surface Ratio according to the width and height of Video.
        float max = Math.max((float) videoWidth / (float) videoBoxWidth, (float) videoHeight / (float) videoBoxHeight);

        // Generally one edge is following the length of screen and the other edge will be calculated.
        measuredWidth = (int) Math.ceil((float) videoWidth / max);
        measuredHeight = (int) Math.ceil((float) videoHeight / max);
        HiLog.debug(LABEL, "After calculation SurfaceView width = " + measuredWidth + " height = " + measuredHeight);
    }

    /**
     * Obtain measured width.
     *
     * @return screen width.
     */
    public int getMeasuredWidth() {
        HiLog.debug(LABEL, "getMeasuredWidth = " + measuredWidth);
        return measuredWidth;
    }

    /**
     * Obtain measured height
     *
     * @return screen height.
     */
    public int getMeasuredHeight() {
        HiLog.debug(LABEL, "getMeasuredHeight = " + measuredHeight);
        return measuredHeight;
    }
}
