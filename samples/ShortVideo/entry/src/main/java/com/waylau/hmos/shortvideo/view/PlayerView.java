/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.view;

import com.waylau.hmos.shortvideo.api.IVideoInfoBinding;
import com.waylau.hmos.shortvideo.api.IVideoPlayer;
import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.manager.GestureDetector;
import com.waylau.hmos.shortvideo.util.LogUtil;
import ohos.agp.components.*;
import ohos.agp.components.surfaceprovider.SurfaceProvider;
import ohos.agp.graphics.Surface;
import ohos.agp.graphics.SurfaceOps;
import ohos.agp.window.service.WindowManager;
import ohos.app.Context;

/**
 * PlayerView
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class PlayerView extends DependentLayout implements IVideoInfoBinding, Component.LayoutRefreshedListener {
    private static final String TAG = PlayerView.class.getSimpleName();
    private IVideoPlayer videoPlayer;
    private SurfaceProvider surfaceProvider;
    private Surface surface;
    private PlayerGestureView gestureView;
    private GestureDetector gestureDetector;
    private int viewWidth;
    private int viewHeight;
    private boolean isPlay = Boolean.FALSE;

    /**
     * constructor of PlayerView
     *
     * @param context context
     */
    public PlayerView(Context context) {
        this(context, null);
    }

    /**
     * constructor of PlayerView
     *
     * @param context context
     * @param attrSet attSet
     */
    public PlayerView(Context context, AttrSet attrSet) {
        this(context, attrSet, null);
    }

    /**
     * constructor of PlayerView
     *
     * @param context context
     * @param styleName styleName
     */
    public PlayerView(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);

        // 设置窗体透明。否则会挡住播放内容，除非设置pinToZTop为true
        WindowManager.getInstance().getTopWindow().get().setTransparent(true);
        initView();
        initListener();
        setLayoutRefreshedListener(this);
    }

    private void initView() {
        surfaceProvider = new SurfaceProvider(mContext);
        LayoutConfig layoutConfig = new LayoutConfig();
        layoutConfig.addRule(LayoutConfig.CENTER_IN_PARENT);
        surfaceProvider.setLayoutConfig(layoutConfig);
        addComponent(surfaceProvider);
        addGestureView();
    }

    private void addGestureView() {
        gestureView = new PlayerGestureView(mContext);
        LayoutConfig config = new LayoutConfig(Constants.NUMBER_300, ComponentContainer.LayoutConfig.MATCH_CONTENT);
        config.addRule(LayoutConfig.CENTER_IN_PARENT);
        gestureView.setLayoutConfig(config);
        addComponent(gestureView);
    }

    private void initListener() {
        gestureDetector = new GestureDetector(gestureView);
        surfaceProvider
            .setTouchEventListener((component, touchEvent) -> canGesture() && gestureDetector.onTouchEvent(touchEvent));
        surfaceProvider.getSurfaceOps().ifPresent(surfaceOps -> surfaceOps.addCallback(new SurfaceOps.Callback() {
            @Override
            public void surfaceCreated(SurfaceOps surfaceOps) {
                LogUtil.info(TAG, "surfaceCreated surfaceOps:" + surfaceOps);

                surface = surfaceOps.getSurface();
                if (videoPlayer != null) {
                    videoPlayer.addSurface(surface);

                    // 判断是否需要立即播放
                    if (isPlay) {
                        videoPlayer.play();
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceOps surfaceOps, int info, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceOps surfaceOps) {}
        }));
    }

    private boolean canGesture() {
        return gestureDetector != null && videoPlayer != null && videoPlayer.isGestureOpen();
    }

    private void updateVideoSize(double videoScale) {
        if (videoScale > 1) {
            surfaceProvider.setWidth(viewWidth);
            surfaceProvider.setHeight((int)Math.min(viewWidth / videoScale, viewHeight));
        } else {
            surfaceProvider.setHeight(viewHeight);
            surfaceProvider.setWidth((int)Math.min(viewHeight * videoScale, viewWidth));
        }
    }

    @Override
    public void bind(VideoInfo VideoInfo) {
        this.videoPlayer = VideoInfo.getVideoPlayer();
        gestureView.bind(VideoInfo);
    }

    @Override
    public void unbind() {
        surfaceProvider.removeFromWindow();
        surfaceProvider = null;
        surface = null;
    }

    /**
     * 自适应屏幕大小
     */
    @Override
    public void onRefreshed(Component component) {
        int newWidth = component.getWidth();
        int newHeight = component.getHeight();
        double videoScale = videoPlayer.getVideoScale();
        if (videoScale != Constants.NUMBER_NEGATIVE_1 && (newWidth != viewWidth || newHeight != viewHeight)) {
            viewWidth = newWidth;
            viewHeight = newHeight;
            mContext.getUITaskDispatcher().asyncDispatch(() -> updateVideoSize(videoScale));
        }
    }

    /**
     * 启动播放
     */
    public void enablePlay() {
        isPlay = Boolean.TRUE;
    }
}
