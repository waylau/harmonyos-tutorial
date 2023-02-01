package com.waylau.hmos.shortvideo.slice;

import com.waylau.hmos.shortvideo.MainAbility;
import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.VideoPublishPageAbility;
import com.waylau.hmos.shortvideo.bean.UserInfo;
import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.provider.VideoListFavoriteItemProvider;
import com.waylau.hmos.shortvideo.provider.VideoListItemProvider;
import com.waylau.hmos.shortvideo.provider.VideoListThumbsUpItemProvider;
import com.waylau.hmos.shortvideo.util.CommonUtil;
import com.waylau.hmos.shortvideo.util.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Image;
import ohos.agp.components.ListContainer;
import ohos.agp.components.TabList;
import ohos.agp.components.Text;
import ohos.utils.zson.ZSONArray;

import java.util.ArrayList;
import java.util.List;

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

    private UserInfo userInfo = new UserInfo();

    private TabList tabListMe;
    private TabList tabListMeVideo;
    private TabList.Tab tabMe;



    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_me_layout);

        userInfo.setUsername(intent.getStringParam(Constants.LOGIN_USERNAME));
        userInfo.setPortraitPath(intent.getStringParam(Constants.IMAGE_SELECTION));

        // 初始化数据
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
    }

    private void initUi() {
        // 初始化用户资料
        initUserInfo();

        // 初始化TabList标签栏
        initMeTabList();
        initMeVideoTabList();

        // 初始化ListContainer
        initListContainerForVideoListItemProvider();
    }

    private void initUserInfo() {
        Image imageMePortrait = (Image)findComponentById(ResourceTable.Id_image_me_portrait);
        Text textMeAuthor = (Text)findComponentById(ResourceTable.Id_text_me_author);

        imageMePortrait.setPixelMap(CommonUtil.getImageSource(this.getContext(), userInfo.getPortraitPath()));
        textMeAuthor.setText(userInfo.getUsername());
    }

    private void initMeTabList() {
        tabListMe = (TabList)findComponentById(ResourceTable.Id_tab_list_me);
        TabList.Tab tab = tabListMe.new Tab(getContext());
        tab.setText("首页");
        tabListMe.addTab(tab);
        TabList.Tab tab2 = tabListMe.new Tab(getContext());
        tab2.setText("✚");
        tabListMe.addTab(tab2);
        tabMe = tabListMe.new Tab(getContext());
        tabMe.setText("我");
        tabListMe.addTab(tabMe, true); // 默认选中

        // 各个Tab的宽度也会根据TabList的宽度而平均分配
        tabListMe.setFixedMode(true);

        // 设置TabList选择事件
        tabListMe.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                int position = tab.getPosition();
                // 当某个Tab从未选中状态变为选中状态时的回调
                LogUtil.info(TAG, "TabList onSelected, position: " + position);

                if (position == 1) {
                    // 视频上传界面
                    startVideoUploadAbility();
                } else if (position == 0) {
                    // “首页”界面
                    startMainAbility();
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

        // 设置TabList选择事件
        tabListMeVideo.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                int position = tab.getPosition();
                // 当某个Tab从未选中状态变为选中状态时的回调
                LogUtil.info(TAG, "TabList onSelected, position: " + position);

                if (position == 1) {
                    initListContainerForVideoListThumbsUpItemProvider();
                } else  if (position == 2) {
                    initListContainerForVideoListFavoriteItemProvider();
                } else {
                    initListContainerForVideoListItemProvider();
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
        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_list_container_video_list);
        VideoListItemProvider itemProvider = new VideoListItemProvider(videoInfoList, this);
        listContainer.setItemProvider(itemProvider);
    }

    private void initListContainerForVideoListFavoriteItemProvider() {
        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_list_container_video_list);
        VideoListFavoriteItemProvider itemProvider = new VideoListFavoriteItemProvider(videoInfoList, this);
        listContainer.setItemProvider(itemProvider);
    }

    private void initListContainerForVideoListThumbsUpItemProvider() {
        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_list_container_video_list);
        VideoListThumbsUpItemProvider itemProvider = new VideoListThumbsUpItemProvider(videoInfoList, this);
        listContainer.setItemProvider(itemProvider);
    }

    private void startVideoUploadAbility() {
        LogUtil.info(TAG, "before startVideoUploadAbility");
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withAbilityName(VideoPublishPageAbility.class)
                .withBundleName("com.waylau.hmos.shortvideo")
                .build();

        intent.setOperation(operation);

        // 启动Ability
        startAbility(intent);
    }

    private void startMainAbility() {
        LogUtil.info(TAG, "before startMainAbility");
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withAbilityName(MainAbility.class)
                .withBundleName("com.waylau.hmos.shortvideo")
                .build();

        intent.setOperation(operation);

        // 启动Ability
        startAbility(intent);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        // 首页选中
        tabListMe.selectTab(tabMe);
        super.onForeground(intent);
    }
}
