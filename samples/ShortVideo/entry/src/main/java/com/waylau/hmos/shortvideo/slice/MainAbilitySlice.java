/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.slice;

import com.waylau.hmos.shortvideo.MePageAbility;
import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.VideoUploadPageAbility;
import com.waylau.hmos.shortvideo.bean.UserInfo;
import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.player.VideoPlayer;
import com.waylau.hmos.shortvideo.api.IVideoPlayer;
import com.waylau.hmos.shortvideo.util.CommonUtil;
import com.waylau.hmos.shortvideo.provider.VideoPlayerPageSliderProvider;
import com.waylau.hmos.shortvideo.util.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.PageSlider;
import ohos.agp.components.TabList;
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
    private UserInfo userInfo = new UserInfo();
    // 视频信息列表
    private final List<VideoInfo> videoInfoList = new ArrayList<>();
    private int index = 0;
    private TabList tabList;
    private TabList.Tab tabMain;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        userInfo.setUsername(intent.getStringParam(Constants.LOGIN_USERNAME));
        userInfo.setPortraitPath(intent.getStringParam(Constants.IMAGE_SELECTION));

        // 初始化数据
        initData();

        // 初始化UI组件
        initUi(intent);
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
            bean.setVideoPlayer(player);
        }
    }

    private void initUi(Intent intent) {
        // 初始化TabList标签栏
        initTabList(intent);

        // 初始化PageSlider
        initPageSlider();
    }

    private void initTabList(Intent intent) {
        tabList = (TabList)findComponentById(ResourceTable.Id_tab_list);
        tabMain = tabList.new Tab(getContext());
        tabMain.setText("首页");
        tabList.addTab(tabMain, true); // 默认选中
        TabList.Tab tab2 = tabList.new Tab(getContext());
        tab2.setText("✚");
        tabList.addTab(tab2);
        TabList.Tab tab3 = tabList.new Tab(getContext());
        tab3.setText("我");
        tabList.addTab(tab3);

        // 各个Tab的宽度也会根据TabList的宽度而平均分配
        tabList.setFixedMode(true);

        // 设置TabList选择事件
        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                int position = tab.getPosition();
                // 当某个Tab从未选中状态变为选中状态时的回调
                LogUtil.info(TAG, "TabList onSelected, position: " + position);

                if (position == 1) {
                    // 视频上传界面
                    startVideoUploadAbility();
                } else if (position == 2) {
                    // “我”界面
                    startMeAbility(intent);
                }
            }

            @Override
            public void onUnselected(TabList.Tab tab) {
                // 当某个Tab从选中状态变为未选中状态时的回调
                LogUtil.info(TAG, "TabList onUnselected, position:" + tab.getPosition());
            }

            @Override
            public void onReselected(TabList.Tab tab) {
                // 当某个Tab已处于选中状态，再次被点击时的状态回调
                LogUtil.info(TAG, "TabList onReselected, position:" + tab.getPosition());
            }
        });
    }

    private void startVideoUploadAbility() {
        LogUtil.info(TAG, "before startVideoUploadAbility");
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder().withAbilityName(VideoUploadPageAbility.class)
            .withBundleName("com.waylau.hmos.shortvideo").build();

        intent.setOperation(operation);

        // 启动Ability
        startAbility(intent);
    }

    private void startMeAbility(Intent intent) {
        LogUtil.info(TAG, "before startMeAbility");

        Operation operation = new Intent.OperationBuilder().withAbilityName(MePageAbility.class)
            .withBundleName("com.waylau.hmos.shortvideo").build();

        intent.setOperation(operation);

        // 启动Ability
        startAbility(intent);
    }

    private void initPageSlider() {
        LogUtil.info(TAG, "initPageSlider is called");
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
            }
        });

        pageSlider.setCurrentPage(index);
    }

    private IVideoPlayer getPlayer(int itemPos) {
        VideoInfo videoInfo = videoInfoList.get(itemPos);
        return videoInfo.getVideoPlayer();
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

        // 首页选中
        tabList.selectTab(tabMain);
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
        getPlayer(index).getLifecycle().onStop();
        super.onStop();
    }

}
