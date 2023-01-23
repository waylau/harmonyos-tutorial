/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.view;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.api.IVideoPlayModule;
import com.waylau.hmos.shortvideo.api.IVideoPlayer;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.*;
import ohos.agp.components.Component.TouchEventListener;
import ohos.app.Context;
import ohos.multimodalinput.event.TouchEvent;

/**
 * PlayerLoading
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class PlayerLoading extends ComponentContainer implements IVideoPlayModule, TouchEventListener {
    private static final int HALF_NUMBER = 2;
    private static final int ANIM_ROTATE = 360;
    private static final int ANIM_DURATION = 2000;
    private static final int ANIM_LOOPED_COUNT = -1;
    private IVideoPlayer mPlayer;
    private Image mLoading;
    private AnimatorProperty mLoadingAnim;

    /**
     * constructor of PlayerLoading
     *
     * @param context context
     */
    public PlayerLoading(Context context) {
        this(context, null);
    }

    /**
     * constructor of PlayerLoading
     *
     * @param context context
     * @param attrSet attSet
     */
    public PlayerLoading(Context context, AttrSet attrSet) {
        this(context, attrSet, null);
    }

    /**
     * constructor of PlayerLoading
     *
     * @param context context
     * @param attrSet attSet
     * @param styleName styleName
     */
    public PlayerLoading(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        initView(context);
    }

    private void initView(Context context) {
        Component loadingContainer =
                LayoutScatter.getInstance(context).parse(ResourceTable.Layout_video_player_loading_layout, null, false);
        if (loadingContainer.findComponentById(ResourceTable.Id_image_loading) instanceof Image) {
            mLoading = (Image) loadingContainer.findComponentById(ResourceTable.Id_image_loading);
            initAnim();
        }
        addComponent(loadingContainer);
        hide();
        setTouchEventListener(this);
    }

    private void initAnim() {
        int with = mLoading.getWidth() / HALF_NUMBER;
        int height = mLoading.getHeight() / HALF_NUMBER;
        mLoading.setPivotX(with);
        mLoading.setPivotY(height);
        mLoadingAnim = mLoading.createAnimatorProperty();
        mLoadingAnim.rotate(ANIM_ROTATE).setDuration(ANIM_DURATION).setLoopedCount(ANIM_LOOPED_COUNT);
    }

    private void initListener() {
        mPlayer.addPlayerStatuCallback(statu -> mContext.getUITaskDispatcher().asyncDispatch(() -> {
            switch (statu) {
                case PREPARING:
                case BUFFERING:
                    show();
                    break;
                case PLAY:
                    hide();
                    break;
                default:
                    break;
            }
        }));
    }

    /**
     * show of PlayerLoading
     */
    public void show() {
        if (mLoadingAnim.isPaused()) {
            mLoadingAnim.resume();
        } else {
            mLoadingAnim.start();
        }
        setVisibility(VISIBLE);
    }

    /**
     * hide of PlayerLoading
     */
    public void hide() {
        setVisibility(INVISIBLE);
        mLoadingAnim.pause();
    }

    @Override
    public void bind(IVideoPlayer player) {
        mPlayer = player;
        initListener();
    }

    @Override
    public void unbind() {
        mLoadingAnim.release();
    }

    @Override
    public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
        return true;
    }
}
