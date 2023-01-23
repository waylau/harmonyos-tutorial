/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.slice;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.player.VideoPlayer;
import com.waylau.hmos.shortvideo.api.IVideoPlayer;
import com.waylau.hmos.shortvideo.constant.Constants;
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

import java.util.ArrayList;

/**
 * 主页面
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private IVideoPlayer[] players;
    private PlayerView playerView;
    private PlayerLoading loadingView;
    private PlayerController controllerView;
    private String[] urls = new String[] {"entry/resources/base/media/01e3ac03579e7a31010370038557efd0c5_258.mp4",
        "entry/resources/base/media/01e396fb6e9cef2e010370038505c76ef6_258.mp4",
        "entry/resources/base/media/01e3a18bad9cf3d701037003852f0a6326_258.mp4",
        "entry/resources/base/media/big_buck_bunny.mp4", "entry/resources/base/media/huawei_mate_40.mp4",
        "entry/resources/base/media/trailer.mp4"};
    int index = 0;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化UI组件
        initUi();
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
        int size = urls.length;
        players = new IVideoPlayer[size];
        for (int i = 0; i < size; i++) {
            players[i] = new VideoPlayer.Builder(getContext()).setFilePath(urls[i]).create();
            players[i].getLifecycle().onStart();
        }

        PageSlider pageSlider = (PageSlider)findComponentById(ResourceTable.Id_page_slider);
        VideoPlayerPageSliderProvider videoPlayerPageSliderProvider = new VideoPlayerPageSliderProvider(getData(), this, players);
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
                    players[itemPos - 1].getLifecycle().onBackground();
                }
                if (itemPos < players.length - 1) {
                    players[itemPos + 1].getLifecycle().onBackground();
                }
                players[itemPos].getLifecycle().onForeground();

                getGlobalTaskDispatcher(TaskPriority.DEFAULT).delayDispatch(() -> players[itemPos].play(),
                    Constants.NUMBER_1000);
            }
        });

        getGlobalTaskDispatcher(TaskPriority.DEFAULT).delayDispatch(() -> players[index].play(), Constants.NUMBER_1000);
    }

    private ArrayList<VideoPlayerPageSliderProvider.DataItem> getData() {
        ArrayList<VideoPlayerPageSliderProvider.DataItem> dataItems = new ArrayList<>();
        dataItems.add(new VideoPlayerPageSliderProvider.DataItem("Page A"));
        dataItems.add(new VideoPlayerPageSliderProvider.DataItem("Page B"));
        dataItems.add(new VideoPlayerPageSliderProvider.DataItem("Page C"));
        dataItems.add(new VideoPlayerPageSliderProvider.DataItem("Page d"));
        dataItems.add(new VideoPlayerPageSliderProvider.DataItem("Page e"));
        dataItems.add(new VideoPlayerPageSliderProvider.DataItem("Page f"));
        return dataItems;
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
        players[index].getLifecycle().onForeground();
        super.onForeground(intent);
    }

    @Override
    protected void onBackground() {
        LogUtil.info(TAG, "onBackground is called");
        players[index].getLifecycle().onBackground();
        super.onBackground();
    }

    @Override
    protected void onStop() {
        LogUtil.info(TAG, "onStop is called");
        loadingView.unbind();
        controllerView.unbind();
        playerView.unbind();
        players[index].getLifecycle().onStop();
        super.onStop();
    }
}
