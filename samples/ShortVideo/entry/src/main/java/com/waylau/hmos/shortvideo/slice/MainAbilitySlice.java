/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.slice;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.player.VideoPlayer;
import com.waylau.hmos.shortvideo.api.IVideoPlayer;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.util.CommonUtil;
import com.waylau.hmos.shortvideo.view.PlayerLoading;
import com.waylau.hmos.shortvideo.view.PlayerView;
import com.waylau.hmos.shortvideo.view.PlayerController;
import com.waylau.hmos.shortvideo.provider.VideoPlayerPageSliderProvider;
import com.waylau.hmos.shortvideo.util.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.PageSlider;
import ohos.agp.components.TabList;
import ohos.app.Context;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.utils.zson.ZSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private PlayerView playerView;
    private PlayerLoading loadingView;
    private PlayerController controllerView;

    // 视频信息列表
    private final List<VideoInfo> videoInfoList = new ArrayList<>();
    int index = 0;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        initData();

        // 初始化UI组件
        initUi();
    }

    private void initData() {
        String resourcePath = "resources/rawfile/videoinfo.json";
        String videosJson = CommonUtil.getJsonFileToString(this, resourcePath);

        // json字符串转成对象集合
        List<VideoInfo> videoInfos = ZSONArray.stringToClassList(videosJson, VideoInfo.class);
        videoInfoList.clear();
        videoInfoList.addAll(videoInfos);

        // 处理视频对象
        for (VideoInfo bean : videoInfos) {
            IVideoPlayer player = new VideoPlayer.Builder(getContext()).setFilePath(bean.getVideoPath()).create();
            player.getLifecycle().onStart();
            bean.setVideoPlayer(player);
        }
    }

    private void initUi() {
        // 初始化TabList标签栏
        initTabList();

        // 初始化PageSlider
        initPageSlider();
    }

    private void initTabList() {
        TabList tabList = (TabList)findComponentById(ResourceTable.Id_tab_list);
        TabList.Tab tab = tabList.new Tab(getContext());
        tab.setText("首页");
        tabList.addTab(tab, true); // 默认选中
        TabList.Tab tab2 = tabList.new Tab(getContext());
        tab2.setText("✚");
        tabList.addTab(tab2);
        TabList.Tab tab3 = tabList.new Tab(getContext());
        tab3.setText("我");
        tabList.addTab(tab3);

        // 各个Tab的宽度也会根据TabList的宽度而平均分配
        tabList.setFixedMode(true);
    }

    private void initPageSlider() {

        PageSlider pageSlider = (PageSlider)findComponentById(ResourceTable.Id_page_slider);
        VideoPlayerPageSliderProvider videoPlayerPageSliderProvider =
            new VideoPlayerPageSliderProvider(videoInfoList, this);
        pageSlider.setProvider(videoPlayerPageSliderProvider);
        pageSlider.setReboundEffect(true);
        pageSlider.addPageChangedListener(new PageSlider.PageChangedListener() {
            @Override
            public void onPageSliding(int itemPos, float itemPosOffset, int itemPosPixles) {}

            @Override
            public void onPageSlideStateChanged(int state) {}

            @Override
            public void onPageChosen(int itemPos) {
                LogUtil.info(TAG, "onPageChosen itemPos:" + itemPos);

                index = itemPos;

                if (itemPos > 0) {
                    getPlayer(itemPos - 1).getLifecycle().onBackground();
                }
                if (itemPos < videoInfoList.size() - 1) {
                    getPlayer(itemPos + 1).getLifecycle().onBackground();
                }

                IVideoPlayer player = getPlayer(itemPos);
                player.getLifecycle().onForeground();
                getGlobalTaskDispatcher(TaskPriority.DEFAULT).delayDispatch(() -> player.play(), Constants.NUMBER_1000);
            }
        });

        // 启动播放
        pageSlider.setCurrentPage(index);
        getPlayer(index).getLifecycle().onForeground();
        getGlobalTaskDispatcher(TaskPriority.DEFAULT).delayDispatch(() -> getPlayer(index).play(),
            Constants.NUMBER_1000);
    }

    private IVideoPlayer getPlayer(int itemPos) {
        VideoInfo videoInfo = videoInfoList.get(itemPos);
        return videoInfo.getVideoPlayer();
    }

    Context getMainContext() {
        return this;
    }

    @Override
    public void onActive() {
        LogUtil.info(TAG, "onActive is called");
        super.onActive();

    }

    @Override
    public void onForeground(Intent intent) {
        LogUtil.info(TAG, "onForeground is called");
        getPlayer(index).getLifecycle().onForeground();
        super.onForeground(intent);
    }

    @Override
    protected void onBackground() {
        LogUtil.info(TAG, "onBackground is called");
        getPlayer(index).getLifecycle().onBackground();
        super.onBackground();
    }

    @Override
    protected void onStop() {
        LogUtil.info(TAG, "onStop is called");
        loadingView.unbind();
        controllerView.unbind();
        playerView.unbind();
        getPlayer(index).getLifecycle().onStop();
        super.onStop();
    }

}
