/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.provider;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.util.LogUtil;
import com.waylau.hmos.shortvideo.view.PlayerLoading;
import com.waylau.hmos.shortvideo.view.PlayerView;
import com.waylau.hmos.shortvideo.view.PlayerController;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.PageSliderProvider;
import ohos.app.Context;

import java.util.List;

/**
 * VideoPlayer PageSliderProvider
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class VideoPlayerPageSliderProvider extends PageSliderProvider {
    private static final String TAG = VideoPlayerPageSliderProvider.class.getSimpleName();

    private PlayerView playerView;
    private PlayerLoading loadingView;
    private PlayerController controllerView;

    // 数据源，每个页面对应list中的一项
    private List<VideoInfo> list;
    private Context mContext;

    public VideoPlayerPageSliderProvider(List<VideoInfo> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object createPageInContainer(ComponentContainer componentContainer, int i) {
        LogUtil.info(TAG, "createPageInContainer, i:" + i);

        Component videoPlayerViewLayout =
            LayoutScatter.getInstance(mContext).parse(ResourceTable.Layout_video_player_view_layout, null, false);
        playerView = (PlayerView)videoPlayerViewLayout.findComponentById(ResourceTable.Id_player_view);
        loadingView = (PlayerLoading)videoPlayerViewLayout.findComponentById(ResourceTable.Id_loading_view);
        controllerView = (PlayerController)videoPlayerViewLayout.findComponentById(ResourceTable.Id_controller_view);
        componentContainer.addComponent(videoPlayerViewLayout);

        // 视图绑定数据
        VideoInfo videoInfo = list.get(i);
        playerView.bind(videoInfo);
        loadingView.bind(videoInfo);
        controllerView.bind(videoInfo);

        // 初始化就播放首页
        if (i == 0) {
            playerView.enablePlay();
        }

        return videoPlayerViewLayout;
    }

    @Override
    public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {
        LogUtil.info(TAG, "destroyPageFromContainer, i:" + i);
        componentContainer.removeComponent((Component)o);
    }

    @Override
    public boolean isPageMatchToObject(Component component, Object o) {
        // 可添加具体处理逻辑
        return true;
    }
}