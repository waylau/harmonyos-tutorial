/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.slice;

import java.util.ArrayList;
import java.util.List;

import com.waylau.hmos.shortvideo.MePageAbility;
import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.VideoPublishPageAbility;
import com.waylau.hmos.shortvideo.api.IVideoPlayer;
import com.waylau.hmos.shortvideo.bean.*;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.player.VideoPlayer;
import com.waylau.hmos.shortvideo.provider.VideoPlayerPageSliderProvider;
import com.waylau.hmos.shortvideo.store.MeFavoriteVideoInfoRepository;
import com.waylau.hmos.shortvideo.store.MeThumbsupVideoInfoRepository;
import com.waylau.hmos.shortvideo.store.UserFollowInfoRepository;
import com.waylau.hmos.shortvideo.store.VideoInfoRepository;
import com.waylau.hmos.shortvideo.util.LogUtil;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.PageSlider;
import ohos.agp.text.Font;
import ohos.agp.utils.Color;

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
    private final List<ViderPlayerInfo> videoInfoList = new ArrayList<>();
    private int index = 0;
    private PageSlider pageSlider;
    private VideoPlayerPageSliderProvider videoPlayerPageSliderProvider;
    private Button buttonMain;

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
        LogUtil.info(TAG, "initData");

        index = 0;

        // 获取视频列表
        List<VideoInfo> videoInfos = VideoInfoRepository.queryAll();
        videoInfoList.clear();

        // 获取关注列表
        List<UserFollowInfo> userFollowInfos =  UserFollowInfoRepository.queryByUsername(userInfo.getUsername());

        // 获取点赞列表
        List<MeThumbsupVideoInfo> meThumbsupVideoInfos =  MeThumbsupVideoInfoRepository.queryByUsername(userInfo.getUsername());

        // 获取收藏列表
        List<MeFavoriteVideoInfo> meFavoriteVideoInfos =  MeFavoriteVideoInfoRepository.queryByUsername(userInfo.getUsername());

        // 处理视频对象
        for (VideoInfo videoInfo : videoInfos) {
            LogUtil.info(TAG, "VideoInfo : " + videoInfo.toString());

            IVideoPlayer player = new VideoPlayer.Builder(getContext()).setFilePath(videoInfo.getVideoPath()).create();
            ViderPlayerInfo viderPlayerInfo = new ViderPlayerInfo(videoInfo, player, userInfo);

            // 处理关注
            for (UserFollowInfo followInfo: userFollowInfos) {
                if (followInfo.getAuthor().equals(videoInfo.getAuthor())) {
                    videoInfo.setFollow(Boolean.TRUE);
                }
            }

            // 处理点赞
            for (MeThumbsupVideoInfo thumbsupVideoInfo : meThumbsupVideoInfos) {
                if (thumbsupVideoInfo.getVideoId().equals(videoInfo.getVideoId())) {
                    videoInfo.setThumbsUp(Boolean.TRUE);
                }
            }

            // 处理收藏
            for (MeFavoriteVideoInfo favoriteVideoInfo: meFavoriteVideoInfos) {
                if (favoriteVideoInfo.getVideoId().equals(videoInfo.getVideoId())) {
                    videoInfo.setFavorite(Boolean.TRUE);
                }
            }

            videoInfoList.add(viderPlayerInfo);
        }


    }

    private void initUi(Intent intent) {
        // 初始化TabList标签栏
        initTabList(intent);

        // 初始化PageSlider
        initPageSlider();
    }

    private void initTabList(Intent intent) {
        buttonMain = (Button) findComponentById(ResourceTable.Id_button_main);
        Button buttonFriend = (Button) findComponentById(ResourceTable.Id_button_friend);
        Button iamgeAdd = (Button) findComponentById(ResourceTable.Id_button_add);
        Button buttonMsg = (Button) findComponentById(ResourceTable.Id_button_msg);
        Button buttonMe = (Button) findComponentById(ResourceTable.Id_button_me);

        iamgeAdd.setClickedListener(component -> {
            LogUtil.info(TAG, "buttonAdd Clicked");

            // 视频上传界面
            startVideoUploadAbility(intent);
        });

        buttonMe.setClickedListener(component -> {
            LogUtil.info(TAG, "buttonMe Clicked");

            // “我”界面
            startMeAbility(intent);
        });

    }

    private void startVideoUploadAbility(Intent intent) {
        LogUtil.info(TAG, "before startVideoUploadAbility");

        Operation operation = new Intent.OperationBuilder().withAbilityName(VideoPublishPageAbility.class)
            .withBundleName("com.waylau.hmos.shortvideo").build();

        intent.setOperation(operation);

        // 启动Ability
        startAbility(intent);

        terminate();
    }

    private void startMeAbility(Intent intent) {
        LogUtil.info(TAG, "before startMeAbility");

        Operation operation = new Intent.OperationBuilder().withAbilityName(MePageAbility.class)
            .withBundleName("com.waylau.hmos.shortvideo").build();

        intent.setOperation(operation);

        // 启动Ability
        startAbility(intent);

        terminate();
    }

    private void initPageSlider() {
        LogUtil.info(TAG, "initPageSlider is called");
        pageSlider = (PageSlider)findComponentById(ResourceTable.Id_page_slider);

        videoPlayerPageSliderProvider = new VideoPlayerPageSliderProvider(videoInfoList, this);

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
        ViderPlayerInfo videoInfo = videoInfoList.get(itemPos);
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
        //进入首页默认按钮加粗
        buttonMain.setFont(Font.DEFAULT_BOLD);
        buttonMain.setTextColor(Color.WHITE);
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
        getPlayer(index).getLifecycle().onBackground();
        super.onStop();
    }

}
