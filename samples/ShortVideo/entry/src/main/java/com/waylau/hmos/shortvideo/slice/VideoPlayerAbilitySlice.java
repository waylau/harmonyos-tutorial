/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.slice;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.api.IVideoPlayer;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.player.VideoPlayer;
import com.waylau.hmos.shortvideo.util.LogUtil;
import com.waylau.hmos.shortvideo.util.ScreenUtil;
import com.waylau.hmos.shortvideo.view.PlayerLoading;
import com.waylau.hmos.shortvideo.view.PlayerView;
import com.waylau.hmos.shortvideo.view.PlayerController;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.DependentLayout;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.bundle.AbilityInfo;

/**
 * VideoPlayer AbilitySlice
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class VideoPlayerAbilitySlice extends AbilitySlice {
    private static final String TAG = VideoPlayerAbilitySlice.class.getSimpleName();
    private IVideoPlayer player;
    private DependentLayout parentLayout;
    private PlayerView playerView;
    private PlayerLoading loadingView;
    private PlayerController controllerView;
    private String url = "entry/resources/base/media/3.mp4";

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_video_player_view_layout);
        player = new VideoPlayer.Builder(this).setFilePath(url).create();
        player.getLifecycle().onStart();
        initComponent();
    }

    private void initComponent() {
        parentLayout = (DependentLayout)findComponentById(ResourceTable.Id_parent);
        playerView = (PlayerView)findComponentById(ResourceTable.Id_player_view);
        loadingView = (PlayerLoading)findComponentById(ResourceTable.Id_loading_view);
        controllerView = (PlayerController)findComponentById(ResourceTable.Id_controller_view);

        playerView.bind(player);
        loadingView.bind(player);
        controllerView.bind(player);
    }

    @Override
    protected void onOrientationChanged(AbilityInfo.DisplayOrientation displayOrientation) {
        super.onOrientationChanged(displayOrientation);
        int screenWidth = ScreenUtil.getScreenWidth(this);
        parentLayout.setWidth(screenWidth);
        player.openGesture(displayOrientation == AbilityInfo.DisplayOrientation.LANDSCAPE);
    }

    @Override
    public void onActive() {
        super.onActive();
        getGlobalTaskDispatcher(TaskPriority.DEFAULT).delayDispatch(() -> player.play(), Constants.NUMBER_1000);
    }

    @Override
    public void onForeground(Intent intent) {
        player.getLifecycle().onForeground();
        super.onForeground(intent);
    }

    @Override
    protected void onBackground() {
        LogUtil.info(TAG, "onBackground is called");
        player.getLifecycle().onBackground();
        super.onBackground();
    }

    @Override
    protected void onStop() {
        LogUtil.info(TAG, "onStop is called");
        loadingView.unbind();
        controllerView.unbind();
        playerView.unbind();
        player.getLifecycle().onStop();
        super.onStop();
    }
}
