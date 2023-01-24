/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.view;

import com.waylau.hmos.shortvideo.api.IVideoInfoBinding;
import com.waylau.hmos.shortvideo.api.IVideoPlayer;
import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.manager.GestureDetector;
import com.waylau.hmos.shortvideo.slice.MainAbilitySlice;
import com.waylau.hmos.shortvideo.util.LogUtil;
import ohos.agp.components.*;
import ohos.agp.components.surfaceprovider.SurfaceProvider;
import ohos.agp.graphics.Surface;
import ohos.agp.graphics.SurfaceOps;
import ohos.agp.window.service.WindowManager;
import ohos.app.Context;
import ohos.app.dispatcher.task.TaskPriority;

import java.util.Optional;

/**
 * PlayerView
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class PlayerView extends DependentLayout implements IVideoInfoBinding, Component.LayoutRefreshedListener {
    private static final String TAG = PlayerView.class.getSimpleName();
    private IVideoPlayer player;
    private SurfaceProvider surfaceView;
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

        // 不设置窗体透明会挡住播放内容，除非设置pinToZTop为true
        WindowManager.getInstance().getTopWindow().get().setTransparent(true);
        initView();
        initListener();
        setLayoutRefreshedListener(this);
    }

    private void initView() {
        surfaceView = new SurfaceProvider(mContext);
        LayoutConfig layoutConfig = new LayoutConfig();
        layoutConfig.addRule(LayoutConfig.CENTER_IN_PARENT);
        surfaceView.setLayoutConfig(layoutConfig);
        addComponent(surfaceView);
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
        surfaceView
            .setTouchEventListener((component, touchEvent) -> canGesture() && gestureDetector.onTouchEvent(touchEvent));
        surfaceView.getSurfaceOps().ifPresent(surfaceOps -> surfaceOps.addCallback(new SurfaceOps.Callback() {
            @Override
            public void surfaceCreated(SurfaceOps surfaceOps) {
                LogUtil.info(TAG, "surfaceCreated surfaceOps:" + surfaceOps);

                surface = surfaceOps.getSurface();
                if (player != null) {
                    player.addSurface(surface);

                    if(isPlay) {
                        player.play();
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
        return gestureDetector != null && player != null && player.isGestureOpen();
    }

    private void updateVideoSize(double videoScale) {
        if (videoScale > 1) {
            surfaceView.setWidth(viewWidth);
            surfaceView.setHeight((int)Math.min(viewWidth / videoScale, viewHeight));
        } else {
            surfaceView.setHeight(viewHeight);
            surfaceView.setWidth((int)Math.min(viewHeight * videoScale, viewWidth));
        }
    }

    @Override
    public void bind(VideoInfo VideoInfo) {
        this.player = VideoInfo.getVideoPlayer();
        gestureView.bind(VideoInfo);
        this.player.addPlayerViewCallback((width, height) -> mContext.getUITaskDispatcher().asyncDispatch(() -> {
            if (width > 0) {
                setWidth(width);
            }
            if (height > 0) {
                setHeight(height);
            }
        }));
    }

    @Override
    public void unbind() {
        surfaceView.removeFromWindow();
        surfaceView = null;
        surface = null;
    }

    @Override
    public void onRefreshed(Component component) {
        int newWidth = component.getWidth();
        int newHeight = component.getHeight();
        double videoScale = player.getVideoScale();
        if (videoScale != Constants.NUMBER_NEGATIVE_1 && (newWidth != viewWidth || newHeight != viewHeight)) {
            viewWidth = newWidth;
            viewHeight = newHeight;
            mContext.getUITaskDispatcher().asyncDispatch(() -> updateVideoSize(videoScale));
        }
    }

    public void enablePlay() {
        isPlay = Boolean.TRUE;
    }
}
