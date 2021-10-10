package com.waylau.hmos.douyin.components;

import com.waylau.hmos.douyin.constant.ControlCode;
import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.constant.Constants;
import com.waylau.hmos.douyin.data.VideoInfo;
import com.waylau.hmos.douyin.data.VideoInfoService;
import com.waylau.hmos.douyin.model.ResolutionModel;
import com.waylau.hmos.douyin.provider.ResolutionItemProvider;
import com.waylau.hmos.douyin.utils.AppUtil;
import com.waylau.hmos.douyin.utils.DateUtils;

import com.waylau.hmos.douyin.utils.ElementUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.AttrHelper;
import ohos.agp.components.Component;
import ohos.agp.components.DependentLayout;
import ohos.agp.components.DragInfo;
import ohos.agp.components.Image;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Slider;
import ohos.agp.components.TableLayout;
import ohos.agp.components.Text;
import ohos.bundle.AbilityInfo;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.List;

/**
 * Remote Control Page
 */
public class RemoteController extends DependentLayout
        implements Component.ClickedListener, Slider.ValueChangedListener {
    private static final String TAG = RemoteController.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private static final int EACH_ROW_VIDEO_ITEM_NUM = 6;
    private final AbilitySlice slice;
    private final VideoInfoService videoService;

    private Component controllerView;
    private RemoteControllerListener remoteControllerListener;
    private Component episodesDialog;
    private int currentPlayingIndex;
    private String deviceName;
    private int startTime;
    private int totalTime;
    private boolean isShown = false;
    private boolean isPlaying = false;
    private boolean isSliderTouching = false;

    public RemoteController(AbilitySlice slice) {
        super(slice);
        this.slice = slice;
        videoService = new VideoInfoService(slice);
        // Avoid triggering player drag events
        setDraggedListener(DRAG_HORIZONTAL_VERTICAL, new DraggedListener() {
            @Override
            public void onDragDown(Component component, DragInfo dragInfo) {
            }

            @Override
            public void onDragStart(Component component, DragInfo dragInfo) {
            }

            @Override
            public void onDragUpdate(Component component, DragInfo dragInfo) {
            }

            @Override
            public void onDragEnd(Component component, DragInfo dragInfo) {
            }

            @Override
            public void onDragCancel(Component component, DragInfo dragInfo) {
            }
        });
    }

    /**
     * Setting the Projection Information
     *
     * @param deviceName          deviceName
     * @param currentPlayingIndex currentPlayingIndex
     * @param startTime           startTime
     * @param totalTime           totalTime
     */
    public void setVideoInfo(String deviceName, int currentPlayingIndex, int startTime, int totalTime) {
        this.deviceName = deviceName;
        this.currentPlayingIndex = currentPlayingIndex;
        this.startTime = startTime;
        this.totalTime = totalTime;
    }

    @Override
    public void onClick(Component component) {
        switch (component.getId()) {
            case ResourceTable.Id_app_bar_back:
                hide(true);
                break;
            case ResourceTable.Id_control_episodes_num:
            case ResourceTable.Id_control_all_episodes:
                episodesDialog.setVisibility(VISIBLE);
                break;
            case ResourceTable.Id_control_play:
                remoteControllerListener.sendControl(ControlCode.PLAY.getCode(), "");
                break;
            case ResourceTable.Id_control_backword:
                remoteControllerListener.sendControl(ControlCode.BACKWARD.getCode(), "");
                break;
            case ResourceTable.Id_control_forward:
                remoteControllerListener.sendControl(ControlCode.FORWARD.getCode(), "");
                break;
            case ResourceTable.Id_control_voice_up:
                remoteControllerListener.sendControl(ControlCode.VOLUME_ADD.getCode(), "");
                break;
            case ResourceTable.Id_control_voice_down:
                if (getDialogVisibility()) {
                    remoteControllerListener.sendControl(ControlCode.VOLUME_REDUCED.getCode(), "");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onProgressUpdated(Slider slider, int value, boolean fromUser) {
        slice.getUITaskDispatcher()
                .delayDispatch(
                        () -> {
                            Text currentTime =
                                    (Text) controllerView.findComponentById(ResourceTable.Id_control_current_time);
                            currentTime.setText(
                                    DateUtils.msToString(totalTime * value / Constants.ONE_HUNDRED_PERCENT));
                        },
                        0);
    }

    @Override
    public void onTouchStart(Slider slider) {
        isSliderTouching = true;

        HiLog.info(LABEL_LOG, "after onTouchStart");
    }

    @Override
    public void onTouchEnd(Slider slider) {
        // The pop-up box cannot block the slider touch event.
        // This event is not processed when a dialog box is displayed.
        if (getDialogVisibility()) {
            remoteControllerListener.sendControl(ControlCode.SEEK.getCode(), String.valueOf(slider.getProgress()));
        }
        isSliderTouching = false;

        HiLog.info(LABEL_LOG, "after onTouchEnd");
    }

    /**
     * Synchronize the video playback progress.
     *
     * @param totalTime totalTime
     * @param process   process
     */
    public void syncVideoPlayProcess(int totalTime, int process) {
        this.totalTime = totalTime;
        Text endTimeBtn = (Text) controllerView.findComponentById(ResourceTable.Id_control_end_time);
        endTimeBtn.setText(DateUtils.msToString(totalTime));

        if (!isSliderTouching) {
            Slider progressSlider = (Slider) controllerView.findComponentById(ResourceTable.Id_control_progress);
            progressSlider.setProgressValue(process);
            if (process == Constants.ONE_HUNDRED_PERCENT) {
                changePlayingStatus();
            }
        }
    }

    /**
     * setRemoteControllerCallback
     *
     * @param listener listener
     */
    public void setRemoteControllerCallback(RemoteControllerListener listener) {
        remoteControllerListener = listener;
    }

    /**
     * show method
     */
    public void show() {
        if (!isShown) {
            this.initView();
            setVisibility(VISIBLE);
            isShown = true;
        }
    }

    /**
     * hide method
     *
     * @param isPressBack Whether to press the back button
     */
    public void hide(boolean isPressBack) {
        if (!isShown) {
            return;
        }
        if (episodesDialog != null && episodesDialog.getVisibility() == VISIBLE) {
            episodesDialog.setVisibility(INVISIBLE);
            // If the control message fails to be sent, the system returns to the playback page.
            if (isPressBack) {
                return;
            }
        }
        isShown = false;
        removeAllComponents();
        slice.setDisplayOrientation(AbilityInfo.DisplayOrientation.UNSPECIFIED);
        setVisibility(INVISIBLE);
        if (isPressBack) {
            remoteControllerListener.sendControl(ControlCode.STOP_CONNECTION.getCode(), "");
        }
    }

    /**
     * isShown
     *
     * @return boolean isShown
     */
    public boolean isShown() {
        return isShown;
    }

    /**
     * changeVolumeIcon
     *
     * @param currentVolume Current volume
     */
    public void changeVolumeIcon(int currentVolume) {
        Image voiceDownImg = (Image) controllerView.findComponentById(ResourceTable.Id_control_voice_down);

        if (currentVolume == 0) {
            voiceDownImg.setForeground(ElementUtils.getElementByResId(slice, ResourceTable.Media_ic_mute));
        } else {
            voiceDownImg.setForeground(ElementUtils.getElementByResId(slice, ResourceTable.Media_ic_voice));
        }
    }

    private void initView() {
        setVisibility(INVISIBLE);
        if (controllerView == null) {
            controllerView =
                    LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_remote_ability_control, this, false);
        }

        initItemText();
        initItemSize();
        initItemImage();
        initProgressSlider();
        initButton(ResourceTable.Id_app_bar_back);
        initButton(ResourceTable.Id_control_episodes_num);
        initButton(ResourceTable.Id_control_all_episodes);
        initButton(ResourceTable.Id_control_play);
        initButton(ResourceTable.Id_control_backword);
        initButton(ResourceTable.Id_control_forward);
        initButton(ResourceTable.Id_control_voice_down);
        initButton(ResourceTable.Id_control_voice_up);
        initBottomComponent();
        addComponent(controllerView);

        initEpisodesDialog();

        isPlaying = true;
    }

    private void initItemText() {
        Text deviceNameBtn = (Text) controllerView.findComponentById(ResourceTable.Id_app_bar_device_name);
        deviceNameBtn.setText(deviceName);

        Text currentTimeBtn = (Text) controllerView.findComponentById(ResourceTable.Id_control_current_time);
        currentTimeBtn.setText(DateUtils.msToString(startTime));
        Text endTimeBtn = (Text) controllerView.findComponentById(ResourceTable.Id_control_end_time);
        endTimeBtn.setText(DateUtils.msToString(totalTime));

        setPlayingVideoDesc();

        Text episodesNum = (Text) controllerView.findComponentById(ResourceTable.Id_control_episodes_num);
        episodesNum.setText(
                AppUtil.getStringResource(slice, ResourceTable.String_control_all_episodes)
                        .replaceAll("\\?", String.valueOf(videoService.getAllVideoInfo().getTotalEpisodes())));
    }

    private void setPlayingVideoDesc() {
        VideoInfo videoInfo = videoService.getVideoInfoByIndex(currentPlayingIndex);
        Text videoDesc = (Text) controllerView.findComponentById(ResourceTable.Id_device_video_desc);
        String playingEpisodes =
                AppUtil.getStringResource(slice, ResourceTable.String_control_playing_episodes)
                        .replaceAll("\\?", String.valueOf(videoInfo.getCurrentAnthology()));
        videoDesc.setText(videoInfo.getVideoName() + " " + playingEpisodes + " " + videoInfo.getVideoDesc());
        videoDesc.startAutoScrolling();
    }

    private void initItemSize() {
        int screenWidth = AppUtil.getScreenInfo(slice).getPointXToInt();

        int icSize = (screenWidth - AttrHelper.vp2px(24, slice) * 2 - AttrHelper.vp2px(48, slice) * 3) / 4;

        int controlSize = screenWidth - icSize * 2 - AttrHelper.vp2px(24, slice) * 2;
        setComponentSize(ResourceTable.Id_control_middle_panel, controlSize, controlSize);
        setComponentSize(ResourceTable.Id_control_middle_panel_top, controlSize, controlSize / 3);
        setComponentSize(ResourceTable.Id_control_middle_panel_center, controlSize, controlSize / 3);
        setComponentSize(ResourceTable.Id_control_middle_panel_bottom, controlSize, controlSize / 3);
        setComponentSize(ResourceTable.Id_control_backword_parent, controlSize / 3, controlSize / 3);
        setComponentSize(ResourceTable.Id_control_play_parent, controlSize / 3, controlSize / 3);
        setComponentSize(ResourceTable.Id_control_forward_parent, controlSize / 3, controlSize / 3);
    }

    private void initItemImage() {
        Image voiceDownImg = (Image) controllerView.findComponentById(ResourceTable.Id_control_voice_down);
        voiceDownImg.setForeground(ElementUtils.getElementByResId(slice, ResourceTable.Media_ic_voice));

        Image playBtn = (Image) controllerView.findComponentById(ResourceTable.Id_control_play);
        playBtn.setPixelMap(ResourceTable.Media_ic_pause_black);
    }

    private void setComponentSize(int res, int width, int height) {
        Component component = controllerView.findComponentById(res);
        component.setComponentSize(width, height);
    }

    private void initButton(int res) {
        Component button = controllerView.findComponentById(res);
        button.setClickedListener(this);
    }

    private void initProgressSlider() {
        Slider progressSlider = (Slider) controllerView.findComponentById(ResourceTable.Id_control_progress);
        progressSlider.setProgressValue(0);
        progressSlider.setValueChangedListener(this);
    }

    /**
     * Calculates the width of each element by subtracting the left margin and the spacing of each element
     * from the width of the screen.
     */
    private void initBottomComponent() {
        int itemSize =
                (AppUtil.getScreenInfo(slice).getPointXToInt()
                        - AttrHelper.vp2px(24, slice)
                        - AttrHelper.vp2px(8, slice) * EACH_ROW_VIDEO_ITEM_NUM)
                        / EACH_ROW_VIDEO_ITEM_NUM;
        TableLayout controlBottomItem =
                (TableLayout) controllerView.findComponentById(ResourceTable.Id_cotrol_bottom_item);
        controlBottomItem.removeAllComponents();
        for (int i = 0; i < EACH_ROW_VIDEO_ITEM_NUM; i++) {
            controlBottomItem.addComponent(getEpisodesItem(i, itemSize));
        }
    }

    private void initEpisodesDialog() {
        if (episodesDialog == null) {
            episodesDialog = new EpisodesSelectionDialog(getContext());
        }
        episodesDialog.findComponentById(ResourceTable.Id_video_quality_list_parent).setVisibility(INVISIBLE);

        int screenWidth = AppUtil.getScreenInfo(slice).getPointXToInt();
        int itemSize =
                (screenWidth
                        - AttrHelper.vp2px(16, slice) * 2
                        - AttrHelper.vp2px(8, slice) * (EACH_ROW_VIDEO_ITEM_NUM - 1))
                        / EACH_ROW_VIDEO_ITEM_NUM;
        TableLayout table = (TableLayout) episodesDialog.findComponentById(ResourceTable.Id_all_episodes_table);
        table.removeAllComponents();
        for (int i = 0; i < videoService.getAllVideoInfo().getDetail().size(); i++) {
            table.addComponent(getEpisodesItem(i, itemSize));
        }
        Text videoName = (Text) episodesDialog.findComponentById(ResourceTable.Id_episodes_video_name);
        videoName.setText(videoService.getAllVideoInfo().getVideoName());
        videoName.setWidth((int) (screenWidth * 0.6));
        // Initialize the resolution pop-up window.
        ListContainer qualityContainer =
                (ListContainer) episodesDialog.findComponentById(ResourceTable.Id_video_quality_list);
        refreshResolutionsList(qualityContainer);
        qualityContainer.setItemClickedListener(
                (listContainer, component, position, id) -> {
                    ResolutionModel item = (ResolutionModel) listContainer.getItemProvider().getItem(position);
                    remoteControllerListener.sendControl(ControlCode.SWITCH_RESOLUTION.getCode(),
                            String.valueOf(position));
                    Text qualityText = (Text) episodesDialog.findComponentById(ResourceTable.Id_episodes_quality);
                    qualityText.setText(item.getShortName());
                    episodesDialog
                            .findComponentById(ResourceTable.Id_video_quality_list_parent)
                            .setVisibility(INVISIBLE);
                });

        addComponent(episodesDialog);
    }

    private Component getEpisodesItem(int index, int componentSize) {
        Component episodesItem =
                LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_remote_episodes_item, null, false);
        episodesItem.setComponentSize(componentSize, componentSize);

        // Set icons, for example, in-play and trailer icons.
        if (index == currentPlayingIndex) {
            Component icPlaying = episodesItem.findComponentById(ResourceTable.Id_episodes_item_playing);
            icPlaying.setVisibility(VISIBLE);
        } else {
            Text itemText = (Text) episodesItem.findComponentById(ResourceTable.Id_episodes_item_num);
            itemText.setText(String.valueOf(index + 1));
        }
        episodesItem.setClickedListener(
                component -> {
                    if (index == currentPlayingIndex) {
                        return;
                    }
                    remoteControllerListener.sendControl(ControlCode.SWITCH_VIDEO.getCode(), String.valueOf(index));
                    // Sets the icon that is being played.
                    refreshSelectedEpisodes(index);
                    currentPlayingIndex = index;
                    setPlayingVideoDesc();
                    ListContainer qualityContainer =
                            (ListContainer) episodesDialog.findComponentById(ResourceTable.Id_video_quality_list);
                    refreshResolutionsList(qualityContainer);
                });
        if (videoService.getVideoInfoByIndex(index).isTrailer()) {
            Component trailerBtn = episodesItem.findComponentById(ResourceTable.Id_episodes_item_trailer);
            trailerBtn.setVisibility(VISIBLE);
        }
        return episodesItem;
    }

    /**
     * when switch Video or change video resolution, change playing status synchrony.
     */
    public void changePlayingStatus() {
        Image playBtn = (Image) findComponentById(ResourceTable.Id_control_play);
        if (isPlaying) {
            playBtn.setPixelMap(ResourceTable.Media_ic_play_black);
        } else {
            playBtn.setPixelMap(ResourceTable.Media_ic_pause_black);
        }
        isPlaying = !isPlaying;
    }

    /**
     * Obtains playing status.
     *
     * @return whether remote device isPlayingVideo.
     */
    public boolean getPlayingStatus() {
        return isPlaying;
    }

    private boolean getDialogVisibility() {
        return episodesDialog == null || episodesDialog.getVisibility() != VISIBLE;
    }

    /**
     * Synchronize the styles of the selected episodes
     * in the anthology pop-up window and the selected episodes in the bottom anthology window.
     *
     * @param selectedIndex selectedIndex
     */
    private void refreshSelectedEpisodes(int selectedIndex) {
        TableLayout controlBottom = (TableLayout) controllerView.findComponentById(ResourceTable.Id_cotrol_bottom_item);
        if (selectedIndex < EACH_ROW_VIDEO_ITEM_NUM) {
            Component selectedBottomChild = controlBottom.getComponentAt(selectedIndex);
            selectedBottomChild.findComponentById(ResourceTable.Id_episodes_item_playing).setVisibility(VISIBLE);
            ((Text) selectedBottomChild.findComponentById(ResourceTable.Id_episodes_item_num)).setText("");
        }
        if (currentPlayingIndex < EACH_ROW_VIDEO_ITEM_NUM) {
            Component bottomChild = controlBottom.getComponentAt(currentPlayingIndex);
            bottomChild.findComponentById(ResourceTable.Id_episodes_item_playing).setVisibility(INVISIBLE);
            ((Text) bottomChild.findComponentById(ResourceTable.Id_episodes_item_num))
                    .setText(String.valueOf(currentPlayingIndex + 1));
        }
        if (episodesDialog != null) {
            TableLayout dialogTable =
                    (TableLayout) episodesDialog.findComponentById(ResourceTable.Id_all_episodes_table);
            Component srcChild = dialogTable.getComponentAt(currentPlayingIndex);
            srcChild.findComponentById(ResourceTable.Id_episodes_item_playing).setVisibility(INVISIBLE);
            ((Text) srcChild.findComponentById(ResourceTable.Id_episodes_item_num))
                    .setText(String.valueOf(currentPlayingIndex + 1));
            Component selectedChild = dialogTable.getComponentAt(selectedIndex);
            selectedChild.findComponentById(ResourceTable.Id_episodes_item_playing).setVisibility(VISIBLE);
            ((Text) selectedChild.findComponentById(ResourceTable.Id_episodes_item_num)).setText("");
        }
    }

    private void refreshResolutionsList(ListContainer container) {
        List<ResolutionModel> resolutions = videoService.getVideoInfoByIndex(currentPlayingIndex).getResolutions();
        Text qualityText = (Text) episodesDialog.findComponentById(ResourceTable.Id_episodes_quality);
        qualityText.setText(resolutions.get(0).getShortName());
        ResolutionItemProvider provider = new ResolutionItemProvider(getContext(), resolutions);
        container.setItemProvider(provider);
    }

    /**
     * RemoteControllerListener
     */
    public interface RemoteControllerListener {
        /**
         * sendControl
         *
         * @param code  code
         * @param extra extra
         */
        void sendControl(int code, String extra);
    }
}
