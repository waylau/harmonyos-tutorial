package com.waylau.hmos.shortvideo.slice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.waylau.hmos.shortvideo.MainAbility;
import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.VideoPublishPageAbility;
import com.waylau.hmos.shortvideo.bean.*;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.provider.UserFollowItemProvider;
import com.waylau.hmos.shortvideo.provider.VideoListFavoriteItemProvider;
import com.waylau.hmos.shortvideo.provider.VideoListItemProvider;
import com.waylau.hmos.shortvideo.provider.VideoListThumbsUpItemProvider;
import com.waylau.hmos.shortvideo.store.MeFavoriteVideoInfoRepository;
import com.waylau.hmos.shortvideo.store.MeThumbsupVideoInfoRepository;
import com.waylau.hmos.shortvideo.store.UserFollowInfoRepository;
import com.waylau.hmos.shortvideo.store.VideoInfoRepository;
import com.waylau.hmos.shortvideo.util.CommonUtil;
import com.waylau.hmos.shortvideo.util.LogUtil;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.components.element.PixelMapElement;
import ohos.global.resource.NotExistException;

/**
 * “我”页面
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-26
 */
public class MePageAbilitySlice extends AbilitySlice {
    private static final String TAG = MePageAbilitySlice.class.getSimpleName();

    // 视频信息列表
    private final List<VideoInfo> videoInfoList = new ArrayList<>();
    private final List<MeThumbsupVideoInfo> meThumbsupVideoInfoList = new ArrayList<>();
    private final List<MeFavoriteVideoInfo> meFavoriteVideoInfoList = new ArrayList<>();
    private final List<UserFollowInfo> userFollowInfoList = new ArrayList<>();
    private UserInfo userInfo = new UserInfo();

    private VideoListItemProvider videoListItemProvider;
    private VideoListThumbsUpItemProvider videoListThumbsUpItemProvider;
    private VideoListFavoriteItemProvider videoListFavoriteItemProvider;
    private UserFollowItemProvider userFollowItemProvider;

    private TabList tabListMeVideo;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_me_layout);

        userInfo.setUsername(intent.getStringParam(Constants.LOGIN_USERNAME));
        userInfo.setPortraitPath(intent.getStringParam(Constants.IMAGE_SELECTION));

        // 初始化数据
        initData();

        // 初始化UI组件
        initUi(intent);
    }

    private void initData() {
        List<VideoInfo> videoInfos = VideoInfoRepository.queryByUsername(userInfo.getUsername());
        videoInfoList.clear();
        videoInfoList.addAll(videoInfos);

        List<MeThumbsupVideoInfo> meThumbsupVideoInfos =
            MeThumbsupVideoInfoRepository.queryByUsername(userInfo.getUsername());
        meThumbsupVideoInfoList.clear();
        meThumbsupVideoInfoList.addAll(meThumbsupVideoInfos);

        List<MeFavoriteVideoInfo> meFavoriteVideoInfos =
            MeFavoriteVideoInfoRepository.queryByUsername(userInfo.getUsername());
        meFavoriteVideoInfoList.clear();
        meFavoriteVideoInfoList.addAll(meFavoriteVideoInfos);

        List<UserFollowInfo> userFollowInfos = UserFollowInfoRepository.queryByUsername(userInfo.getUsername());
        userFollowInfoList.clear();
        userFollowInfoList.addAll(userFollowInfos);
    }

    private void initUi(Intent intent) {
        // 初始化背景图
        initBackground();

        // 初始化用户资料
        initUserInfo();

        // 初始化导航栏
        initMeNavigation(intent);

        initMeVideoTabList();

        // 初始化ListContainer
        initListContainerForVideoListItemProvider();
    }

    private void initBackground() {
        // 根据资源生成PixelMapElement实例
        PixelMapElement pixBg = null;
        try {
            pixBg = new PixelMapElement(getResourceManager().getResource(ResourceTable.Media_background));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }
        DependentLayout directLayout = (DependentLayout)findComponentById(ResourceTable.Id_layout_me);
        // 设置DependentLayout背景
        directLayout.setBackground(pixBg);
    }

    private void initUserInfo() {
        Image imageMePortrait = (Image)findComponentById(ResourceTable.Id_image_me_portrait);
        Text textMeAuthor = (Text)findComponentById(ResourceTable.Id_text_me_author);

        imageMePortrait.setPixelMap(CommonUtil.getImageSource(this.getContext(), userInfo.getPortraitPath()));
        // 设置圆角
        imageMePortrait.setCornerRadius(Constants.NUMBER_FLOAT_100);
        textMeAuthor.setText(userInfo.getUsername());
    }

    private void initMeNavigation(Intent intent) {
        Button buttonMain = (Button)findComponentById(ResourceTable.Id_button_main);
        Button iamgeAdd = (Button)findComponentById(ResourceTable.Id_button_add);

        buttonMain.setClickedListener(component -> {
            LogUtil.info(TAG, "buttonMain Clicked");

            // “首页”界面
            startMainAbility(intent);
        });

        iamgeAdd.setClickedListener(component -> {
            LogUtil.info(TAG, "buttonAdd Clicked");

            // 视频上传界面
            startVideoUploadAbility(intent);
        });
    }

    private void initMeVideoTabList() {
        tabListMeVideo = (TabList)findComponentById(ResourceTable.Id_tab_me_video_list);
        TabList.Tab tab = tabListMeVideo.new Tab(getContext());
        tab.setText("作品");
        tabListMeVideo.addTab(tab, true); // 默认选中
        TabList.Tab tab2 = tabListMeVideo.new Tab(getContext());
        tab2.setText("喜欢");
        tabListMeVideo.addTab(tab2);
        TabList.Tab tab3 = tabListMeVideo.new Tab(getContext());
        tab3.setText("收藏");
        tabListMeVideo.addTab(tab3);
        TabList.Tab tab4 = tabListMeVideo.new Tab(getContext());
        tab4.setText("关注");
        tabListMeVideo.addTab(tab4);

        // 设置TabList选择事件
        tabListMeVideo.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                int position = tab.getPosition();
                // 当某个Tab从未选中状态变为选中状态时的回调
                LogUtil.info(TAG, "TabList onSelected, position: " + position);

                if (position == 0) {
                    initListContainerForVideoListItemProvider();
                } else if (position == 1) {
                    initListContainerForVideoListThumbsUpItemProvider();
                } else if (position == 2) {
                    initListContainerForVideoListFavoriteItemProvider();
                } else {
                    initListContainerForFollowItemProvider();
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

    private void initListContainerForVideoListItemProvider() {
        ListContainer listContainer = (ListContainer)findComponentById(ResourceTable.Id_list_container_video_list);
        videoListItemProvider = new VideoListItemProvider(videoInfoList, this, () -> {
            videoListItemProvider.notifyDataChanged();
        });
        listContainer.setItemProvider(videoListItemProvider);
    }

    private void initListContainerForVideoListFavoriteItemProvider() {

        ListContainer listContainer = (ListContainer)findComponentById(ResourceTable.Id_list_container_video_list);
        videoListFavoriteItemProvider = new VideoListFavoriteItemProvider(meFavoriteVideoInfoList, this, () -> {
            videoListFavoriteItemProvider.notifyDataChanged();
        });
        listContainer.setItemProvider(videoListFavoriteItemProvider);
    }

    private void initListContainerForVideoListThumbsUpItemProvider() {
        ListContainer listContainer = (ListContainer)findComponentById(ResourceTable.Id_list_container_video_list);
        videoListThumbsUpItemProvider = new VideoListThumbsUpItemProvider(meThumbsupVideoInfoList, this, () -> {
            videoListThumbsUpItemProvider.notifyDataChanged();
        });
        listContainer.setItemProvider(videoListThumbsUpItemProvider);
    }

    private void initListContainerForFollowItemProvider() {
        ListContainer listContainer = (ListContainer)findComponentById(ResourceTable.Id_list_container_video_list);
        userFollowItemProvider = new UserFollowItemProvider(userFollowInfoList, this, () -> {
            userFollowItemProvider.notifyDataChanged();
        });
        listContainer.setItemProvider(userFollowItemProvider);
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

    private void startMainAbility(Intent intent) {
        LogUtil.info(TAG, "before startMainAbility");

        Operation operation = new Intent.OperationBuilder().withAbilityName(MainAbility.class)
            .withBundleName("com.waylau.hmos.shortvideo").build();

        intent.setOperation(operation);

        // 启动Ability
        startAbility(intent);
        terminate();
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
