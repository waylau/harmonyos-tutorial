package com.waylau.hmos.douyin.component;

import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.constant.Constants;
import com.waylau.hmos.douyin.constant.ResolutionEnum;
import com.waylau.hmos.douyin.constant.SettingOptionEnum;
import com.waylau.hmos.douyin.constant.SpeedEnum;
import com.waylau.hmos.douyin.data.VideoInfo;
import com.waylau.hmos.douyin.data.VideoInfoService;
import com.waylau.hmos.douyin.data.Videos;
import com.waylau.hmos.douyin.model.SettingComponentTag;
import com.waylau.hmos.douyin.model.SettingModel;
import com.waylau.hmos.douyin.model.VideoModel;
import com.waylau.hmos.douyin.provider.SettingProvider;
import com.waylau.hmos.douyin.provider.VideoEpisodesSelectProvider;
import com.waylau.hmos.douyin.provider.VideoSettingProvider;
import com.waylau.hmos.douyin.utils.AppUtil;

import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.AttrHelper;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;
import ohos.multimodalinput.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Video Settings Page
 */
public class VideoSetting extends DirectionalLayout {
    private final AbilitySlice slice;
    // Video information acquisition service, which can implement its own services
    private final VideoInfoService videoInfoService;
    private Component settingView;
    private boolean isShow;
    private VideoSettingCallback videoSettingCallback;
    private int lastPos = Constants.INVALID_POSITION;
    private int currentPlayingIndex = 0;

    // Remote control keys listen to events.
    // Only the upper-level and lower-level confirmation keys need to be processed.
    private final Component.KeyEventListener itemOnKeyListener =
            ((component, keyEvent) -> {
                if (!keyEvent.isKeyDown()) {
                    return false;
                }
                ListContainer settingContainer =
                        (ListContainer) this.findComponentById(ResourceTable.Id_video_setting_container);
                SettingComponentTag tag = null;
                if (component.getTag() instanceof SettingComponentTag) {
                    tag = (SettingComponentTag) component.getTag();
                }
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.KEY_ENTER:
                    case KeyEvent.KEY_DPAD_CENTER:
                        if (tag == null
                                || videoSettingCallback == null
                                || !tag.getLever().equals(Constants.SETTING_OPTION_LEVER2)) {
                            break;
                        }
                        ListContainer child =
                                (ListContainer)
                                        settingContainer
                                                .getComponentAt(tag.getParentType())
                                                .findComponentById(ResourceTable.Id_video_setting_option_content);
                        if (tag.getParentType() == SettingOptionEnum.SPEED_SELECTION.ordinal()) {
                            float speed = SpeedEnum.values()[child.getIndexForComponent(component)].getValue();
                            videoSettingCallback.setVideoSpeed(speed);
                            return true;
                        }
                        if (tag.getParentType() == SettingOptionEnum.RESOLUTION.ordinal()) {
                            String url = getResolutionUrl(child, component);
                            if (!"".equals(url)) {
                                videoSettingCallback.refreshVideoPath(url, "");
                            }
                            return true;
                        }
                        if (tag.getParentType() == SettingOptionEnum.EPISODES.ordinal()) {
                            VideoInfo videoInfo = getEpisodesInfo(child, component);
                            if (videoInfo != null) {
                                videoSettingCallback.refreshVideoPath(videoInfo.getResolutions().get(0).getUrl(),
                                        videoInfo.getVideoDesc());
                            }
                            return true;
                        }
                        break;
                    case KeyEvent.KEY_DPAD_DOWN:
                    case KeyEvent.KEY_DPAD_UP:
                        if (tag != null && tag.getLever().equals(Constants.SETTING_OPTION_LEVER2)) {
                            // When the focus is on a subcomponent, press the up and down arrow keys
                            // to display the focus to the corresponding parent component.
                            int nextIndex =
                                    keyEvent.getKeyCode() == KeyEvent.KEY_DPAD_DOWN
                                            ? tag.getParentType() + 1
                                            : tag.getParentType() - 1;
                            if (nextIndex >= 0 && nextIndex < settingContainer.getChildCount()) {
                                settingContainer.getComponentAt(nextIndex).requestFocus();
                            }
                        }
                        return true;
                    default:
                        break;
                }
                return false;
            });

    public VideoSetting(AbilitySlice slice, VideoInfoService videoInfoService) {
        super(slice);
        this.slice = slice;
        this.videoInfoService = videoInfoService;

        if (settingView == null) {
            settingView =
                    LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_video_setting, null, false);
        }
        addComponent(settingView);
        setVisibility(INVISIBLE);
        initI18NResource();
    }

    /**
     * Control the display of the setting page.
     */
    public void show() {
        if (isShow) {
            return;
        }
        lastPos = Constants.INVALID_POSITION;
        isShow = true;
        initView();
        setVisibility(VISIBLE);
    }

    /**
     * Control settings page hide.
     */
    public void hide() {
        if (!isShow) {
            return;
        }
        isShow = false;
        clearView();
        setVisibility(INVISIBLE);
    }

    /**
     * Obtains the display status of the control page.
     *
     * @return isShow isShow
     */
    public boolean isShow() {
        return isShow;
    }

    /**
     * Register the callback event.
     *
     * @param videoSettingCallback callback event
     */
    public void registerVideoSettingCallback(VideoSettingCallback videoSettingCallback) {
        this.videoSettingCallback = videoSettingCallback;
    }

    private void initView() {
        ListContainer settingContainer =
                (ListContainer) settingView.findComponentById(ResourceTable.Id_video_setting_container);
        setSettingList(settingContainer);
        settingContainer.setItemClickedListener(
                (container, component, position, id) -> {
                    for (int i = 0; i < 3; i++) {
                        if (i != position) {
                            Text lastOptionName =
                                    (Text)
                                            container
                                                    .getComponentAt(i)
                                                    .findComponentById(ResourceTable.Id_video_setting_option_name);
                            lastOptionName.setAlpha(0.4f);
                            lastOptionName.setTextSize(AttrHelper.fp2px(18, slice));
                        }
                    }
                    if (lastPos == position) {
                        return;
                    }
                    // Style unselected options
                    if (lastPos != Constants.INVALID_POSITION) {
                        ListContainer lastComponent =
                                (ListContainer)
                                        container
                                                .getComponentAt(lastPos)
                                                .findComponentById(ResourceTable.Id_video_setting_option_content);
                        if (lastComponent != null && lastComponent.getItemProvider() != null) {
                            ((SettingProvider) lastComponent.getItemProvider()).removeAllItem();
                        }
                    }

                    SettingModel selectItem = (SettingModel) container.getItemProvider().getItem(position);
                    if (selectItem.getOptionName().equals(SettingOptionEnum.SPEED_SELECTION.getName())) {
                        setSpeedList(component);
                    }
                    if (selectItem.getOptionName().equals(SettingOptionEnum.RESOLUTION.getName())) {
                        setResolutionList(component);
                    }
                    if (selectItem.getOptionName().equals(SettingOptionEnum.EPISODES.getName())) {
                        setVideoEpisodesList(component);
                    }

                    container.getItemProvider().notifyDataSetItemChanged(position);
                    lastPos = position;
                    // Sets the style of the currently selected option
                    Text optionName =
                            (Text)
                                    container
                                            .getComponentAt(position)
                                            .findComponentById(ResourceTable.Id_video_setting_option_name);
                    optionName.setAlpha(0.9f);
                    optionName.setTextSize(AttrHelper.fp2px(30, slice));
                });
    }

    private void clearView() {
        ListContainer settingContainer =
                (ListContainer) settingView.findComponentById(ResourceTable.Id_video_setting_container);
        if (lastPos != Constants.INVALID_POSITION) {
            ListContainer lastComponent =
                    (ListContainer)
                            settingContainer
                                    .getComponentAt(lastPos)
                                    .findComponentById(ResourceTable.Id_video_setting_option_content);
            if (lastComponent != null && lastComponent.getItemProvider() != null) {
                ((SettingProvider) lastComponent.getItemProvider()).removeAllItem();
            }
        }
        ((SettingProvider) settingContainer.getItemProvider()).removeAllItem();
        clearFocus();
    }

    private void setSettingList(ListContainer settingContainer) {
        List<SettingModel> list = new ArrayList<>();

        for (int i = 0; i < SettingOptionEnum.values().length; i++) {
            SettingModel item = new SettingModel();
            item.setOptionName(SettingOptionEnum.values()[i].getName());
            item.setLever(Constants.SETTING_OPTION_LEVER1);
            list.add(item);
        }
        VideoSettingProvider provider = new VideoSettingProvider(slice, list, 0, null);
        settingContainer.setItemProvider(provider);
    }

    private void setResolutionList(Component parentComponent) {
        ListContainer selectComponent =
                (ListContainer) parentComponent.findComponentById(ResourceTable.Id_video_setting_option_content);
        List<SettingModel> list = new ArrayList<>();
        for (int i = 0; i < ResolutionEnum.values().length; i++) {
            SettingModel item = new SettingModel();
            item.setOptionName(ResolutionEnum.values()[i].getName());
            item.setLever(Constants.SETTING_OPTION_LEVER2);
            list.add(item);
        }
        VideoSettingProvider provider =
                new VideoSettingProvider(slice, list, SettingOptionEnum.RESOLUTION.ordinal(), itemOnKeyListener);
        selectComponent.setItemProvider(provider);
    }

    private void setSpeedList(Component parentComponent) {
        ListContainer selectComponent =
                (ListContainer) parentComponent.findComponentById(ResourceTable.Id_video_setting_option_content);
        List<SettingModel> list = new ArrayList<>();
        for (int i = 0; i < SpeedEnum.values().length; i++) {
            SettingModel item = new SettingModel();
            item.setOptionName(SpeedEnum.values()[i].getName());
            item.setLever(Constants.SETTING_OPTION_LEVER2);
            list.add(item);
        }
        VideoSettingProvider provider =
                new VideoSettingProvider(slice, list, SettingOptionEnum.SPEED_SELECTION.ordinal(), itemOnKeyListener);
        selectComponent.setItemProvider(provider);
    }

    private void setVideoEpisodesList(Component parentComponent) {
        ListContainer selectComponent =
                (ListContainer) parentComponent.findComponentById(ResourceTable.Id_video_setting_option_content);
        List<VideoModel> list = new ArrayList<>();
        Videos videos = videoInfoService.getAllVideoInfo();
        for (int i = 0; i < videos.getDetail().size(); i++) {
            VideoModel item = new VideoModel();
            item.setVideoDesc(videos.getDetail().get(i).getVideoDesc());
            item.setVideoName(videos.getVideoName());
            item.setVideoImage(i % 2 == 0
                    ? ResourceTable.Media_video_preview
                    : ResourceTable.Media_video_preview2);
            item.setVideoTitleTime(videos.getDetail().get(i).getTotalTime());
            item.setLever(Constants.SETTING_OPTION_LEVER2);
            list.add(item);
        }
        VideoEpisodesSelectProvider provider =
                new VideoEpisodesSelectProvider(slice, list, SettingOptionEnum.EPISODES.ordinal(), itemOnKeyListener);
        selectComponent.setItemProvider(provider);
    }

    private String getResolutionUrl(ListContainer srcContainer, Component component) {
        String url = "";
        if (srcContainer.getIndexForComponent(component) == ResolutionEnum.RESOLUTION_2K.ordinal()) {
            url = videoInfoService.getVideoInfoByIndex(currentPlayingIndex).getResolutions().get(1).getUrl();
        }
        if (srcContainer.getIndexForComponent(component) == ResolutionEnum.RESOLUTION_4K.ordinal()
                || srcContainer.getIndexForComponent(component) == ResolutionEnum.RESOLUTION_HDR.ordinal()
                || srcContainer.getIndexForComponent(component) == ResolutionEnum.RESOLUTION_BD.ordinal()) {
            url = videoInfoService.getVideoInfoByIndex(currentPlayingIndex).getResolutions().get(2).getUrl();
        }
        if (srcContainer.getIndexForComponent(component) == ResolutionEnum.RESOLUTION_HD.ordinal()
                || srcContainer.getIndexForComponent(component) == ResolutionEnum.RESOLUTION_AUTO.ordinal()) {
            url = videoInfoService.getVideoInfoByIndex(currentPlayingIndex).getResolutions().get(0).getUrl();
        }

        return url;
    }

    private VideoInfo getEpisodesInfo(ListContainer srcContainer, Component component) {
        // The SD video address is returned by default.
        currentPlayingIndex = srcContainer.getIndexForComponent(component);
        return currentPlayingIndex == Constants.INVALID_POSITION
                ? null
                : videoInfoService.getVideoInfoByIndex(currentPlayingIndex);
    }

    private void initI18NResource() {
        SettingOptionEnum.EPISODES.setName(
                AppUtil.getStringResource(getContext(), ResourceTable.String_setting_option_episodes));
        SettingOptionEnum.SPEED_SELECTION.setName(
                AppUtil.getStringResource(getContext(), ResourceTable.String_setting_option_speed));
        SettingOptionEnum.RESOLUTION.setName(
                AppUtil.getStringResource(getContext(), ResourceTable.String_setting_option_resolution));
        ResolutionEnum.RESOLUTION_AUTO.setName(
                AppUtil.getStringResource(getContext(), ResourceTable.String_resolution_auto));
        ResolutionEnum.RESOLUTION_BD.setName(
                AppUtil.getStringResource(getContext(), ResourceTable.String_resolution_bd));
        ResolutionEnum.RESOLUTION_HD.setName(
                AppUtil.getStringResource(getContext(), ResourceTable.String_resolution_hd));
    }

    /**
     * Setting operation callback event.
     */
    public interface VideoSettingCallback {
        /**
         * setVideoSpeed
         *
         * @param speed speed
         */
        void setVideoSpeed(float speed);

        /**
         * refreshVideoPath
         *
         * @param url  url
         * @param name name
         */
        void refreshVideoPath(String url, String name);
    }
}
