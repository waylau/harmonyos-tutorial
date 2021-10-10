package com.waylau.hmos.douyin.ability;

import com.waylau.hmos.douyin.constant.ControlCode;
import com.waylau.hmos.douyin.constant.RemoteConstant;
import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.components.RemoteController;
import com.waylau.hmos.douyin.components.VideoPlayerPlaybackButton;
import com.waylau.hmos.douyin.components.VideoPlayerSlider;
import com.waylau.hmos.douyin.constant.Constants;
import com.waylau.hmos.douyin.data.VideoInfo;
import com.waylau.hmos.douyin.data.VideoInfoService;
import com.waylau.hmos.douyin.model.ResolutionModel;
import com.waylau.hmos.douyin.utils.AppUtil;
import com.waylau.hmos.douyin.player.core.VideoPlayer.HmPlayerAdapter;
import com.waylau.hmos.douyin.player.ui.widget.media.VideoPlayerView;
import com.waylau.hmos.douyin.player.view.IBaseComponentAdapter;
import com.waylau.hmos.douyin.player.view.ITitleAdapter;
import com.waylau.hmos.douyin.player.view.VideoBoxArea;
import com.waylau.hmos.douyin.remote.MyRemoteProxy;
import com.waylau.hmos.douyin.utils.ElementUtils;
import com.waylau.hmos.douyin.utils.ScreenUtils;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityConnection;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.PixelMapElement;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.dialog.PopupDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.agp.window.service.WindowManager;
import ohos.app.AbilityContext;
import ohos.app.dispatcher.task.Revocable;
import ohos.bundle.AbilityInfo;
import ohos.bundle.ElementName;
import ohos.data.distributed.common.KvManagerConfig;
import ohos.data.distributed.common.KvManagerFactory;
import ohos.event.commonevent.CommonEventData;
import ohos.event.commonevent.CommonEventManager;
import ohos.event.commonevent.CommonEventSubscribeInfo;
import ohos.event.commonevent.CommonEventSubscriber;
import ohos.event.commonevent.CommonEventSupport;
import ohos.event.commonevent.MatchingSkills;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.IRemoteObject;
import ohos.rpc.RemoteException;

import java.util.List;

/**
 * VideoPlayerView Example
 */
public class VideoPlayAbilitySlice extends AbilitySlice {
    private static final String TAG = VideoPlayAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    /**
     * Value of UNSPECIFIED{@link AbilityContext#getDisplayOrientation()}
     */
    public static final int VALUE_UNSPECIFIED = -1;

    /**
     * Value of LANDSCAPE{@link AbilityContext#getDisplayOrientation()}
     */
    public static final int VALUE_LANDSCAPE = 0;

    /**
     * Value of PORTRAIT{@link AbilityContext#getDisplayOrientation()}
     */
    public static final int VALUE_PORTRAIT = 1;

    /**
     * Value of FOLLOWRECENT{@link AbilityContext#getDisplayOrientation()}
     */
    public static final int VALUE_FOLLOWRECENT = 3;

    /**
     * Step of playback speed
     */
    public static final float STEP_PLAYBACK_SPEED = 0.25f;

    /**
     * Playback speed options:0.75 1.0 1.25 1.5
     */
    public static final float MIN_PLAYBACK_SPEED = 0.75f;

    /**
     * Delay Recovery Screen Orientation
     */
    public static final int DELAY_RECOVERY_ORIENTATION = 3000;

    /**
     * Used for {@link PopupDialog#showOnCertainPosition(int, int, int)} methods
     */
    public static final int INVALID_ALIGNMENT_VALUE = -1;

    /**
     * Provides the ability to manage the connected PA when the connection is complete.
     */
    private static MyRemoteProxy mProxy;
    /**
     * Creating a Connection Callback Instance
     */
    private final IAbilityConnection connection =
            new IAbilityConnection() {
                // Callback for connecting to a service
                @Override
                public void onAbilityConnectDone(ElementName elementName, IRemoteObject iRemoteObject, int resultCode) {
                    mProxy = new MyRemoteProxy(iRemoteObject);
                }

                // Callback for disconnecting from the service
                @Override
                public void onAbilityDisconnectDone(ElementName elementName, int resultCode) {
                    disconnectAbility(this);
                }
            };
    /**
     * Task of recovery orientation
     */
    private final Runnable recoveryOrientationTask =
            () -> {
                setDisplayOrientation(getAbilityInfo().getOrientation());
                HiLog.info(LABEL, "Recovery display orientation");
            };
    /**
     * VideoPlayerView instance
     */
    private VideoPlayerView player;
    private boolean isVideoPlaying;
    /**
     * RemoteController instance
     */
    private RemoteController remoteController;
    private int sourceDisplayOrientation;
    /**
     * MyCommonEventSubscriber instance
     */
    private MyCommonEventSubscriber subscriber;
    /**
     * Video info from parses json(src/main/resources/rawfile/videos.json)
     */
    private VideoInfoService videoService;
    /**
     * Index of playlist,default value is 0
     */
    private int currentPlayingIndex = 0;
    /**
     * Index of resolution list,default value is 0
     */
    private int currentPlayingResolutionIndex = 0;
    /**
     * Whether to resume playback
     */
    private boolean needResumeStatus;
    /**
     * Dispatch of recovery orientation
     */
    private Revocable recoveryOrientationDispatch;

    @Override
    protected void onStart(Intent intent) {
        if (!ScreenUtils.isPortrait(getContext())) {
            hideStatusBar();
        } else {
            showStatusBar();
        }
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_hm_sample_ability_video_box);
        player = (VideoPlayerView) findComponentById(ResourceTable.Id_video_view);
        if (player != null) {
            videoService = new VideoInfoService(getContext());
            String path =
                    videoService
                            .getVideoInfoByIndex(currentPlayingIndex)
                            .getResolutions()
                            .get(currentPlayingResolutionIndex)
                            .getUrl();
            String name = videoService.getVideoInfoByIndex(currentPlayingIndex).getVideoDesc();
            HiLog.debug(LABEL, "Video name = " + name + " path = " + path);
            player.setVideoPathAndTitle(path, name);
            // Double-click control player pause or play
            player.setDoubleClickedListener(
                    component -> {
                        if (remoteController != null && remoteController.isShown()) {
                            return;
                        }
                        HiLog.debug(LABEL, "VideoPlayView double-click event");
                        if (player.isPlaying()) {
                            player.pause();
                        } else {
                            player.start();
                        }
                    });
            player.setErrorListener(
                    (errorType, errorCode) -> {
                        ToastDialog toast = new ToastDialog(getContext());
                        switch (errorType) {
                            case HmPlayerAdapter.ERROR_LOADING_RESOURCE:
                                toast.setText(
                                        AppUtil.getStringResource(
                                                getContext(), ResourceTable.String_media_file_loading_error));
                                break;
                            case HmPlayerAdapter.ERROR_INVALID_OPERATION:
                                toast.setText(
                                        AppUtil.getStringResource(
                                                getContext(), ResourceTable.String_invalid_operation));
                                break;
                            default:
                                toast.setText(
                                        AppUtil.getStringResource(
                                                getContext(), ResourceTable.String_undefined_error_type));
                                break;
                        }
                        getUITaskDispatcher().asyncDispatch(toast::show);
                    });


            // 增加拖动事件
            player.setDraggedListener(
                    Component.DRAG_HORIZONTAL_VERTICAL,
                    new Component.DraggedListener() {
                        @Override
                        public void onDragDown(Component component, DragInfo dragInfo) {
                        }

                        @Override
                        public void onDragStart(Component component, DragInfo dragInfo) {
                        }

                        @Override
                        public void onDragUpdate(Component component, DragInfo dragInfo) {
                            int size = videoService.getAllVideoInfo().getDetail().size();
                            HiLog.info(LABEL, "size:%{public}s, currentPlayingIndex:%{public}s", size, currentPlayingIndex);
                            currentPlayingIndex = (++currentPlayingIndex) % size;

                            HiLog.info(LABEL, "size:%{public}s, currentPlayingIndex:%{public}s", size, currentPlayingIndex);
                            playbackNext(videoService.getVideoInfoByIndex(currentPlayingIndex));
                        }

                        @Override
                        public void onDragEnd(Component component, DragInfo dragInfo) {
                        }

                        @Override
                        public void onDragCancel(Component component, DragInfo dragInfo) {
                        }
                    });
            addCoreComponent();
            addCustomComponent();
        }
        this.subscribe();

        // 评论的内容，以跑马灯的方式展现
        Text textAutoScrolling =
                (Text) findComponentById(ResourceTable.Id_text_auto_scrolling);

        // 跑马灯效果
        textAutoScrolling.setTruncationMode(Text.TruncationMode.AUTO_SCROLLING);

        // 始终处于自动滚动状态
        textAutoScrolling.setAutoScrollingCount(Text.AUTO_SCROLLING_FOREVER);

        // 启动跑马灯效果
        textAutoScrolling.startAutoScrolling();

        Button button =
                (Button) findComponentById(ResourceTable.Id_comment_button);

        TextField commentTextField =
                (TextField) findComponentById(ResourceTable.Id_comment_text_field);

        // 为评论按钮设置点击事件回调
        button.setClickedListener(listener -> {
                    textAutoScrolling.setText(commentTextField.getText());
                    commentTextField.setText("");

                    // 重新启动跑马灯效果
                    textAutoScrolling.startAutoScrolling();
                }
        );

    }

    /**
     * Adding core component like playback button and seek bar
     */
    private void addCoreComponent() {
        // add playback button
        player.addPlaybackButton(new VideoPlayerPlaybackButton(getContext()), VideoBoxArea.BOTTOM);
        // add select next episode button
        player.addComponent(
                new IBaseComponentAdapter() {
                    private Image iamge;

                    @Override
                    public Component initComponent() {
                        iamge = new Image(getContext());
                        iamge.setImageElement(
                                ElementUtils.getElementByResId(getContext(), ResourceTable.Media_ic_next_anthology));
                        return iamge;
                    }

                    @Override
                    public void onClick(Component component) {
                        HiLog.info(LABEL, "select next episode");
                        if (currentPlayingIndex < videoService.getAllVideoInfo().getDetail().size() - 2) {
                            currentPlayingIndex = currentPlayingIndex + 1;
                            playbackNext(videoService.getVideoInfoByIndex(currentPlayingIndex));
                        }
                    }

                    @Override
                    public DirectionalLayout.LayoutConfig initLayoutConfig() {
                        return null;
                    }

                    @Override
                    public void onOrientationChanged(
                            AbilityInfo.DisplayOrientation displayOrientation,
                            ComponentContainer from,
                            ComponentContainer to) {
                        if (iamge != null) {
                            switch (displayOrientation) {
                                case PORTRAIT:
                                    iamge.setVisibility(Component.HIDE);
                                    break;
                                default:
                                    iamge.setVisibility(Component.VISIBLE);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onVideoSourceChanged() {
                    }
                },
                VideoBoxArea.BOTTOM);
        // add progress bar
        player.addSeekBar(
                new VideoPlayerSlider(getContext()),
                VideoBoxArea.BOTTOM,
                (int) AppUtil.getFloatResource(getContext(), ResourceTable.Float_normal_margin_16));
    }

    /**
     * Adding no-core component like share button and title……
     */
    private void addCustomComponent() {
        // add title and back button
        player.addTitle(
                new ITitleAdapter() {
                    private Text title;

                    @Override
                    public Text initComponent() {
                        title = new Text(getContext());
                        PixelMapElement element =
                                ElementUtils.getElementByResId(getContext(), ResourceTable.Media_ic_video_back);
                        title.setAroundElements(element, null, null, null);
                        title.setAroundElementsPadding(AttrHelper.vp2px(4, getContext()));
                        title.setMaxTextLines(1);
                        title.setTextColor(Color.WHITE);
                        title.setAutoFontSize(true);
                        title.setAutoFontSizeRule(
                                AppUtil.getDimension(getContext(), ResourceTable.Float_little_text_size_10),
                                AppUtil.getDimension(getContext(), ResourceTable.Float_normal_text_size_20),
                                AppUtil.getDimension(getContext(), ResourceTable.Float_normal_text_step_2));
                        int textSize = title.getTextSize();
                        HiLog.debug(LABEL, "Video title text size = " + textSize);

                        String name = videoService.getVideoInfoByIndex(currentPlayingIndex).getVideoDesc();
                        title.setText(name);
                        HiLog.debug(LABEL, "Set title = " + name);
                        return title;
                    }

                    @Override
                    public void onTitleChange(String str) {
                        if (title != null) {
                            title.setText(str);
                        }
                    }

                    @Override
                    public void onOrientationChanged(
                            AbilityInfo.DisplayOrientation displayOrientation,
                            ComponentContainer from,
                            ComponentContainer to) {
                        if (title != null) {
                            switch (displayOrientation) {
                                case PORTRAIT:
                                    // Title placeholder required,cannot be hidden.
                                    title.setVisibility(Component.INVISIBLE);
                                    break;
                                default:
                                    title.setVisibility(Component.VISIBLE);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onVideoSourceChanged() {
                    }

                    @Override
                    public void onClick(Component component) {
                        onBackPressed();
                    }

                    @Override
                    public DirectionalLayout.LayoutConfig initLayoutConfig() {
                        return null;
                    }
                },
                VideoBoxArea.TOP);

        // add toggle tv button
        player.addComponent(
                new IBaseComponentAdapter() {
                    @Override
                    public Component initComponent() {
                        Image image = new Image(getContext());
                        image.setImageElement(
                                ElementUtils.getElementByResId(getContext(), ResourceTable.Media_ic_transfer));
                        return image;
                    }

                    @Override
                    public void onClick(Component component) {
                        HiLog.info(LABEL, "Toggle tv button onclick");
                        // Must pause before starting ability
                        player.pause();

                        Intent intent = new Intent();
                        intent.setParam(Constants.PARAM_VIDEO_INDEX, currentPlayingIndex);

                        Operation operation =
                                new Intent.OperationBuilder()
                                        .withDeviceId("")
                                        .withBundleName(getBundleName())
                                        .withAbilityName(DevicesSelectAbility.class)
                                        .build();
                        intent.setOperation(operation);
                        startAbilityForResult(intent, Constants.PRESENT_SELECT_DEVICES_REQUEST_CODE);
                        isVideoPlaying = player.isPlaying();
                        sourceDisplayOrientation = getDisplayOrientation();
                        setDisplayOrientation(AbilityInfo.DisplayOrientation.PORTRAIT);
                    }

                    @Override
                    public DirectionalLayout.LayoutConfig initLayoutConfig() {
                        return null;
                    }

                    @Override
                    public void onOrientationChanged(
                            AbilityInfo.DisplayOrientation displayOrientation,
                            ComponentContainer from,
                            ComponentContainer to) {
                    }

                    @Override
                    public void onVideoSourceChanged() {
                    }
                },
                VideoBoxArea.TOP,
                AttrHelper.vp2px(24, getContext()));
        // add device switch button
        player.addComponent(
                new IBaseComponentAdapter() {
                    private Image button;

                    @Override
                    public Component initComponent() {
                        button = new Image(getContext());
                        button.setImageElement(
                                ElementUtils.getElementByResId(
                                        getContext(), ResourceTable.Media_ic_toggle_window_playback));
                        return button;
                    }

                    @Override
                    public void onClick(Component component) {
                        HiLog.info(LABEL, "Device switchover button onClick");
                    }

                    @Override
                    public DirectionalLayout.LayoutConfig initLayoutConfig() {
                        return null;
                    }

                    @Override
                    public void onOrientationChanged(
                            AbilityInfo.DisplayOrientation displayOrientation,
                            ComponentContainer from,
                            ComponentContainer to) {
                        if (button != null) {
                            switch (displayOrientation) {
                                case PORTRAIT:
                                    button.setVisibility(Component.VISIBLE);
                                    break;
                                default:
                                    button.setVisibility(Component.HIDE);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onVideoSourceChanged() {
                    }
                },
                VideoBoxArea.BOTTOM);
        // add full screen playback button
        player.addComponent(
                new IBaseComponentAdapter() {
                    private Image button;

                    @Override
                    public Component initComponent() {
                        button = new Image(getContext());
                        button.setImageElement(
                                ElementUtils.getElementByResId(
                                        getContext(), ResourceTable.Media_ic_orientation_switchover));
                        return button;
                    }

                    @Override
                    public void onClick(Component component) {
                        HiLog.debug(LABEL, "Current orientation = " + getDisplayOrientation());
                        int displayOrientation = getDisplayOrientation();
                        switch (displayOrientation) {
                            case VALUE_LANDSCAPE:
                                // Normally, this case is not entered
                                // This button is gone while orientation is landscape
                                setDisplayOrientation(getAbilityInfo().getOrientation());
                                break;
                            default:
                                setDisplayOrientation(AbilityInfo.DisplayOrientation.LANDSCAPE);
                                break;
                        }
                    }

                    @Override
                    public DirectionalLayout.LayoutConfig initLayoutConfig() {
                        return null;
                    }

                    @Override
                    public void onOrientationChanged(
                            AbilityInfo.DisplayOrientation displayOrientation,
                            ComponentContainer from,
                            ComponentContainer to) {
                        if (button != null) {
                            switch (displayOrientation) {
                                case PORTRAIT:
                                    button.setVisibility(Component.VISIBLE);
                                    break;
                                default:
                                    button.setVisibility(Component.HIDE);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onVideoSourceChanged() {
                    }
                },
                VideoBoxArea.BOTTOM);
        // add clipping button
        player.addComponent(
                new IBaseComponentAdapter() {
                    private Image button;

                    @Override
                    public Component initComponent() {
                        button = new Image(getContext());
                        PixelMapElement element =
                                ElementUtils.getElementByResId(getContext(), ResourceTable.Media_ic_clipping);
                        button.setImageElement(element);
                        return button;
                    }

                    @Override
                    public void onClick(Component component) {
                        HiLog.info(LABEL, "Clipping button onclick");
                    }

                    @Override
                    public DirectionalLayout.LayoutConfig initLayoutConfig() {
                        return null;
                    }

                    @Override
                    public void onOrientationChanged(
                            AbilityInfo.DisplayOrientation displayOrientation,
                            ComponentContainer from,
                            ComponentContainer to) {
                        if (button != null) {
                            switch (displayOrientation) {
                                case PORTRAIT:
                                    button.setVisibility(Component.HIDE);
                                    break;
                                default:
                                    button.setVisibility(Component.VISIBLE);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onVideoSourceChanged() {
                    }
                },
                VideoBoxArea.RIGHT);
        // add GIF button
        player.addComponent(
                new IBaseComponentAdapter() {
                    private Image button;

                    @Override
                    public Component initComponent() {
                        button = new Image(getContext());
                        PixelMapElement element =
                                ElementUtils.getElementByResId(getContext(), ResourceTable.Media_ic_gif);
                        button.setImageElement(element);
                        return button;
                    }

                    @Override
                    public void onClick(Component component) {
                        HiLog.info(LABEL, "GIF button onClick");
                    }

                    @Override
                    public DirectionalLayout.LayoutConfig initLayoutConfig() {
                        return null;
                    }

                    @Override
                    public void onOrientationChanged(
                            AbilityInfo.DisplayOrientation displayOrientation,
                            ComponentContainer from,
                            ComponentContainer to) {
                        if (button != null) {
                            switch (displayOrientation) {
                                case PORTRAIT:
                                    button.setVisibility(Component.HIDE);
                                    break;
                                default:
                                    button.setVisibility(Component.VISIBLE);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onVideoSourceChanged() {
                    }
                },
                VideoBoxArea.RIGHT,
                AttrHelper.vp2px(24, getContext()));
        // add lock button
        player.addComponent(
                new IBaseComponentAdapter() {
                    private Image image;

                    @Override
                    public Component initComponent() {
                        image = new Image(getContext());
                        image.setImageElement(
                                ElementUtils.getElementByResId(getContext(), ResourceTable.Media_ic_locked));
                        return image;
                    }

                    @Override
                    public void onClick(Component component) {
                        HiLog.info(LABEL, "Lock button onclick");
                    }

                    @Override
                    public DirectionalLayout.LayoutConfig initLayoutConfig() {
                        return null;
                    }

                    @Override
                    public void onOrientationChanged(
                            AbilityInfo.DisplayOrientation displayOrientation,
                            ComponentContainer from,
                            ComponentContainer to) {
                        if (image != null) {
                            switch (displayOrientation) {
                                case PORTRAIT:
                                    image.setVisibility(Component.HIDE);
                                    break;
                                default:
                                    image.setVisibility(Component.VISIBLE);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onVideoSourceChanged() {
                    }
                },
                VideoBoxArea.LEFT);
        // add timing button
        player.addComponent(
                new IBaseComponentAdapter() {
                    private Image image;

                    @Override
                    public Component initComponent() {
                        image = new Image(getContext());
                        image.setImageElement(
                                ElementUtils.getElementByResId(getContext(), ResourceTable.Media_ic_timing));
                        return image;
                    }

                    @Override
                    public void onClick(Component component) {
                        HiLog.info(LABEL, "Timing button onclick");
                    }

                    @Override
                    public DirectionalLayout.LayoutConfig initLayoutConfig() {
                        return null;
                    }

                    @Override
                    public void onOrientationChanged(
                            AbilityInfo.DisplayOrientation displayOrientation,
                            ComponentContainer from,
                            ComponentContainer to) {
                        if (image != null) {
                            switch (displayOrientation) {
                                case PORTRAIT:
                                    image.setVisibility(Component.HIDE);
                                    break;
                                default:
                                    image.setVisibility(Component.VISIBLE);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onVideoSourceChanged() {
                    }
                },
                VideoBoxArea.LEFT,
                AttrHelper.vp2px(24, getContext()));
        // add playback speed button
        player.addComponent(
                new IBaseComponentAdapter() {
                    private Text text;
                    private PopupDialog playbackSpeedDialog;

                    @Override
                    public Component initComponent() {
                        text = new Text(getContext());
                        text.setText(ResourceTable.String_playback_speed);
                        text.setTextSize(AppUtil.getDimension(getContext(), ResourceTable.Float_normal_text_size_14));
                        text.setTextColor(Color.WHITE);
                        return text;
                    }

                    @Override
                    public void onClick(Component component) {
                        float playbackSpeed = player.getPlaybackSpeed();
                        HiLog.debug(LABEL, "getPlaybackSpeed = " + playbackSpeed);
                        float epsilon = 0.001f;

                        // Playback speed = 0 while playback complete
                        if (Math.abs(playbackSpeed) < epsilon) {
                            playbackSpeed = player.getLastPlaybackSpeed();
                            HiLog.error(
                                    LABEL, "The playback speed is 0,display last playback speed = " + playbackSpeed);
                        }

                        int dlgWidth =
                                (int)
                                        AppUtil.getFloatResource(
                                                getContext(), ResourceTable.Float_video_comp_dialog_width);
                        int dlgHeight = player.getHeight();
                        playbackSpeedDialog = new PopupDialog(getContext(), null, dlgWidth, dlgHeight);
                        Component parentComp =
                                LayoutScatter.getInstance(getContext())
                                        .parse(ResourceTable.Layout_dialog_table_layout, null, true);
                        TableLayout resolutionTable =
                                (TableLayout) parentComp.findComponentById(ResourceTable.Id_table);

                        String[] items =
                                AppUtil.getStringArray(getContext(), ResourceTable.Strarray_playback_speed_array);
                        for (int i = 0; i < items.length; i++) {
                            Text item = new Text(getContext());
                            item.setText(items[i]);
                            int width = ComponentContainer.LayoutConfig.MATCH_PARENT;
                            int height =
                                    (int)
                                            AppUtil.getFloatResource(
                                                    getContext(), ResourceTable.Float_item_resolution_selection_height);
                            ComponentContainer.LayoutConfig layoutConfig =
                                    new ComponentContainer.LayoutConfig(width, height);
                            item.setLayoutConfig(layoutConfig);
                            item.setTextAlignment(TextAlignment.CENTER);
                            item.setTextSize(
                                    AppUtil.getDimension(getContext(), ResourceTable.Float_normal_text_size_16));
                            if (Math.abs(playbackSpeed - MIN_PLAYBACK_SPEED - STEP_PLAYBACK_SPEED * i) < epsilon) {
                                item.setTextColor(
                                        AppUtil.getColor(
                                                getContext(), ResourceTable.Color_video_comp_selected_text_color));
                            } else {
                                item.setTextColor(Color.WHITE);
                            }
                            item.setTag(i);
                            item.setClickedListener(
                                    new Component.ClickedListener() {
                                        @Override
                                        public void onClick(Component component) {
                                            playbackSpeedDialog.destroy();
                                            int position = (int) component.getTag();
                                            if (player != null) {
                                                player.setPlaybackSpeed(
                                                        MIN_PLAYBACK_SPEED + position * STEP_PLAYBACK_SPEED);
                                            }
                                        }
                                    });

                            resolutionTable.addComponent(item);
                        }

                        playbackSpeedDialog.setCustomComponent(parentComp);
                        playbackSpeedDialog.setAutoClosable(true);
                        playbackSpeedDialog.setAlignment(LayoutAlignment.BOTTOM | LayoutAlignment.START);
                        playbackSpeedDialog.showOnCertainPosition(
                                INVALID_ALIGNMENT_VALUE, player.getWidth() - dlgWidth, 0);
                    }

                    @Override
                    public DirectionalLayout.LayoutConfig initLayoutConfig() {
                        return new DirectionalLayout.LayoutConfig(
                                ComponentContainer.LayoutConfig.MATCH_CONTENT,
                                ComponentContainer.LayoutConfig.MATCH_CONTENT);
                    }

                    @Override
                    public void onOrientationChanged(
                            AbilityInfo.DisplayOrientation displayOrientation,
                            ComponentContainer from,
                            ComponentContainer to) {
                        if (text != null) {
                            switch (displayOrientation) {
                                case PORTRAIT:
                                    text.setVisibility(Component.HIDE);
                                    break;
                                default:
                                    text.setVisibility(Component.VISIBLE);
                                    break;
                            }
                        }
                        if (playbackSpeedDialog != null && playbackSpeedDialog.isShowing()) {
                            playbackSpeedDialog.hide();
                        }
                    }

                    @Override
                    public void onVideoSourceChanged() {
                    }
                },
                VideoBoxArea.BOTTOM);
        // add select playlist button
        player.addComponent(
                new IBaseComponentAdapter() {
                    private Text text;
                    private PopupDialog playlistDialog;

                    @Override
                    public Component initComponent() {
                        text = new Text(getContext());
                        text.setText(ResourceTable.String_select_playlist);
                        text.setTextSize(AppUtil.getDimension(getContext(), ResourceTable.Float_normal_text_size_14));
                        text.setTextColor(Color.WHITE);
                        return text;
                    }

                    @Override
                    public void onClick(Component component) {
                        HiLog.info(LABEL, "Show playlist dialog");
                        Component parentComp =
                                LayoutScatter.getInstance(getContext())
                                        .parse(ResourceTable.Layout_dialog_playlist, null, true);

                        Text resolutionSelection =
                                (Text) parentComp.findComponentById(ResourceTable.Id_resolution_selection);
                        Text videoNameComp = (Text) parentComp.findComponentById(ResourceTable.Id_video_name);
                        String videoName = videoService.getAllVideoInfo().getVideoName();
                        HiLog.debug(LABEL, "Get playlist name = " + videoName);
                        ResolutionModel resolution =
                                videoService
                                        .getVideoInfoByIndex(currentPlayingIndex)
                                        .getResolutions()
                                        .get(currentPlayingResolutionIndex);
                        videoNameComp.setAutoFontSizeRule(
                                AppUtil.getDimension(getContext(), ResourceTable.Float_little_text_size_8),
                                AppUtil.getDimension(getContext(), ResourceTable.Float_normal_text_size_18),
                                AppUtil.getDimension(getContext(), ResourceTable.Float_normal_text_step_2));
                        videoNameComp.setText(videoName);
                        int textSize = videoNameComp.getTextSize();
                        HiLog.debug(LABEL, "Playlist title text size = " + textSize);
                        resolutionSelection.setText(resolution.getShortName());
                        resolutionSelection.setClickedListener(
                                resolutionText -> {
                                    HiLog.info(LABEL, "Show resolution selection dialog");
                                    List<ResolutionModel> resolutions =
                                            videoService.getVideoInfoByIndex(currentPlayingIndex).getResolutions();
                                    HiLog.debug(LABEL, "Get resolution list  = " + resolutions);
                                    PopupDialog resolutionSelectionDialog = new PopupDialog(getContext(), null);
                                    Component dialogParent =
                                            LayoutScatter.getInstance(getContext())
                                                    .parse(ResourceTable.Layout_dialog_resolution_list, null, true);
                                    TableLayout resolutionTable =
                                            (TableLayout) dialogParent.findComponentById(ResourceTable.Id_table);
                                    for (int i = 0; i < resolutions.size(); i++) {
                                        Text item = new Text(getContext());
                                        item.setText(resolutions.get(i).getName());
                                        int height =
                                                (int)
                                                        AppUtil.getFloatResource(
                                                                getContext(),
                                                                ResourceTable.Float_item_resolution_selection_height);
                                        ComponentContainer.LayoutConfig layoutConfig =
                                                new ComponentContainer.LayoutConfig(
                                                        ComponentContainer.LayoutConfig.MATCH_PARENT, height);
                                        item.setLayoutConfig(layoutConfig);
                                        item.setTextAlignment(TextAlignment.CENTER);
                                        item.setTextSize(
                                                AppUtil.getDimension(
                                                        getContext(), ResourceTable.Float_normal_text_size_16));
                                        item.setTruncationMode(Text.TruncationMode.ELLIPSIS_AT_END);
                                        if (i == currentPlayingResolutionIndex) {
                                            item.setTextColor(
                                                    AppUtil.getColor(
                                                            getContext(),
                                                            ResourceTable.Color_video_comp_selected_text_color));
                                        } else {
                                            item.setTextColor(Color.BLACK);
                                        }
                                        item.setTag(i);
                                        item.setClickedListener(
                                                component12 -> {
                                                    resolutionSelectionDialog.destroy();
                                                    currentPlayingResolutionIndex = (int) component12.getTag();
                                                    ResolutionModel model =
                                                            videoService
                                                                    .getVideoInfoByIndex(currentPlayingIndex)
                                                                    .getResolutions()
                                                                    .get(currentPlayingResolutionIndex);
                                                    resolutionSelection.setText(model.getShortName());
                                                    HiLog.info(LABEL, "Select No." + currentPlayingResolutionIndex);
                                                    updateResolution(resolutions.get(currentPlayingResolutionIndex));
                                                });
                                        resolutionTable.addComponent(item);
                                    }
                                    resolutionSelectionDialog.setCustomComponent(dialogParent);
                                    resolutionSelectionDialog.setAutoClosable(true);
                                    resolutionSelectionDialog.setCornerRadius(
                                            AppUtil.getFloatResource(
                                                    getContext(), ResourceTable.Float_normal_corner_radius_8));
                                    resolutionSelectionDialog.setAlignment(LayoutAlignment.END | LayoutAlignment.TOP);
                                    // Orientation:X-axis left-->+  right-->-   Y-axis:top-->-  bottom-->+
                                    resolutionSelectionDialog.showOnCertainPosition(-1, 70, 288);
                                });

                        for (int i = 0; i < videoService.getAllVideoInfo().getDetail().size(); i++) {
                            int width =
                                    (int)
                                            AppUtil.getFloatResource(
                                                    getContext(), ResourceTable.Float_item_playlist_width);
                            int height =
                                    (int)
                                            AppUtil.getFloatResource(
                                                    getContext(), ResourceTable.Float_item_playlist_height);
                            Component item = getPlaylistItem(i, width, height);
                            TableLayout playlistTable =
                                    (TableLayout) parentComp.findComponentById(ResourceTable.Id_play_set_table);
                            playlistTable.addComponent(item);
                        }

                        playlistDialog = new PopupDialog(getContext(), null, parentComp.getWidth(), player.getHeight());
                        playlistDialog.setCustomComponent(parentComp);
                        playlistDialog.setAlignment(LayoutAlignment.BOTTOM | LayoutAlignment.START);
                        playlistDialog.setAutoClosable(true);
                        playlistDialog.showOnCertainPosition(
                                INVALID_ALIGNMENT_VALUE, player.getWidth() - parentComp.getWidth(), 0);
                    }

                    @Override
                    public DirectionalLayout.LayoutConfig initLayoutConfig() {
                        return new DirectionalLayout.LayoutConfig(
                                ComponentContainer.LayoutConfig.MATCH_CONTENT,
                                ComponentContainer.LayoutConfig.MATCH_CONTENT);
                    }

                    @Override
                    public void onOrientationChanged(
                            AbilityInfo.DisplayOrientation displayOrientation,
                            ComponentContainer from,
                            ComponentContainer to) {
                        if (text != null) {
                            switch (displayOrientation) {
                                case PORTRAIT:
                                    text.setVisibility(Component.HIDE);
                                    break;
                                default:
                                    text.setVisibility(Component.VISIBLE);
                                    break;
                            }
                        }
                        if (playlistDialog != null && playlistDialog.isShowing()) {
                            playlistDialog.hide();
                        }
                    }

                    @Override
                    public void onVideoSourceChanged() {
                    }
                },
                VideoBoxArea.BOTTOM);
        // add switch HD button
        player.addComponent(
                new IBaseComponentAdapter() {
                    private Text text;
                    private PopupDialog resolutionSelectionDialog;

                    @Override
                    public Component initComponent() {
                        text = new Text(getContext());
                        ResolutionModel resolution =
                                videoService
                                        .getVideoInfoByIndex(currentPlayingIndex)
                                        .getResolutions()
                                        .get(currentPlayingResolutionIndex);
                        text.setText(resolution.getShortName());
                        text.setTextSize(AppUtil.getDimension(getContext(), ResourceTable.Float_normal_text_size_14));
                        text.setTextColor(Color.WHITE);
                        return text;
                    }

                    @Override
                    public void onClick(Component component) {
                        HiLog.info(LABEL, "Show resolution selection dialog from video view");
                        List<ResolutionModel> resolutions =
                                videoService.getVideoInfoByIndex(currentPlayingIndex).getResolutions();
                        HiLog.debug(LABEL, "Get resolution list  = " + resolutions + " from video view");
                        int dlgWidth =
                                (int)
                                        AppUtil.getFloatResource(
                                                getContext(), ResourceTable.Float_video_comp_dialog_width);

                        int dlgHeight = player.getHeight();
                        resolutionSelectionDialog = new PopupDialog(getContext(), null, dlgWidth, dlgHeight);

                        Component parentComp =
                                LayoutScatter.getInstance(getContext())
                                        .parse(ResourceTable.Layout_dialog_table_layout, null, true);
                        TableLayout resolutionTable =
                                (TableLayout) parentComp.findComponentById(ResourceTable.Id_table);
                        for (int i = 0; i < resolutions.size(); i++) {
                            Text item = new Text(getContext());
                            item.setText(resolutions.get(i).getName());
                            int width = ComponentContainer.LayoutConfig.MATCH_PARENT;
                            int height =
                                    (int)
                                            AppUtil.getFloatResource(
                                                    getContext(), ResourceTable.Float_item_resolution_selection_height);
                            ComponentContainer.LayoutConfig layoutConfig =
                                    new ComponentContainer.LayoutConfig(width, height);
                            item.setLayoutConfig(layoutConfig);
                            item.setTextAlignment(TextAlignment.CENTER);
                            item.setTextSize(
                                    AppUtil.getDimension(getContext(), ResourceTable.Float_normal_text_size_16));
                            if (i == currentPlayingResolutionIndex) {
                                item.setTextColor(
                                        AppUtil.getColor(
                                                getContext(), ResourceTable.Color_video_comp_selected_text_color));
                            } else {
                                item.setTextColor(Color.WHITE);
                            }
                            item.setTag(i);
                            item.setClickedListener(
                                    component1 -> {
                                        resolutionSelectionDialog.destroy();
                                        currentPlayingResolutionIndex = (int) component1.getTag();
                                        HiLog.info(
                                                LABEL,
                                                "Select No." + currentPlayingResolutionIndex + " from video component");
                                        ResolutionModel model =
                                                videoService
                                                        .getVideoInfoByIndex(currentPlayingIndex)
                                                        .getResolutions()
                                                        .get(currentPlayingResolutionIndex);
                                        updateResolution(model);
                                    });

                            resolutionTable.addComponent(item);
                        }

                        resolutionSelectionDialog.setCustomComponent(parentComp);
                        resolutionSelectionDialog.setAutoClosable(true);
                        resolutionSelectionDialog.setAlignment(LayoutAlignment.BOTTOM | LayoutAlignment.START);
                        resolutionSelectionDialog.showOnCertainPosition(
                                INVALID_ALIGNMENT_VALUE, player.getWidth() - dlgWidth, 0);
                    }

                    @Override
                    public DirectionalLayout.LayoutConfig initLayoutConfig() {
                        return new DirectionalLayout.LayoutConfig(
                                ComponentContainer.LayoutConfig.MATCH_CONTENT,
                                ComponentContainer.LayoutConfig.MATCH_CONTENT);
                    }

                    @Override
                    public void onOrientationChanged(
                            AbilityInfo.DisplayOrientation displayOrientation,
                            ComponentContainer from,
                            ComponentContainer to) {
                        if (text != null) {
                            switch (displayOrientation) {
                                case PORTRAIT:
                                    text.setVisibility(Component.HIDE);
                                    break;
                                default:
                                    text.setVisibility(Component.VISIBLE);
                                    break;
                            }
                        }
                        if (resolutionSelectionDialog != null && resolutionSelectionDialog.isShowing()) {
                            resolutionSelectionDialog.hide();
                        }
                    }

                    @Override
                    public void onVideoSourceChanged() {
                        if (text != null) {
                            ResolutionModel resolution =
                                    videoService
                                            .getVideoInfoByIndex(currentPlayingIndex)
                                            .getResolutions()
                                            .get(currentPlayingResolutionIndex);
                            text.setText(resolution.getShortName());
                        }
                    }
                },
                VideoBoxArea.BOTTOM);
    }

    @Override
    protected void onAbilityResult(int requestCode, int resultCode, Intent resultIntent) {
        if (requestCode == Constants.PRESENT_SELECT_DEVICES_REQUEST_CODE && resultIntent != null) {
            startRemoteAbilityPa(resultIntent);
            return;
        }
        setDisplayOrientation(AbilityInfo.DisplayOrientation.values()[sourceDisplayOrientation + 1]);
        if (isVideoPlaying) {
            player.start();
        }
    }

    @Override
    protected void onOrientationChanged(AbilityInfo.DisplayOrientation displayOrientation) {
        super.onOrientationChanged(displayOrientation);
        if (player != null) {
            HiLog.info(LABEL, "onOrientationChanged = " + displayOrientation);
            player.onOrientationChanged(displayOrientation);
        }
        if (displayOrientation == AbilityInfo.DisplayOrientation.LANDSCAPE) {
            hideStatusBar();
        } else {
            showStatusBar();
        }
    }

    private void hideStatusBar() {
        getWindow().addFlags(WindowManager.LayoutConfig.MARK_FULL_SCREEN);
    }

    private void showStatusBar() {
        getWindow().clearFlags(WindowManager.LayoutConfig.MARK_FULL_SCREEN);
    }

    @Override
    protected void onBackground() {
        super.onBackground();
        if (player != null) {
            if (player.isPlaying()) {
                needResumeStatus = true;
                player.pause();
            } else {
                // Recreate the VideoPlayerView instance to resolve the black screen issue.
                player.setSeekWhenPrepared(player.getCurrentPosition());
                player.stopPlayback();
            }
        }
    }

    @Override
    protected void onForeground(Intent intent) {
        super.onForeground(intent);
        if (player != null && needResumeStatus) {
            player.start();
            needResumeStatus = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release(true);
        }
        unSubscribe();
    }

    /**
     * On back button pressed.
     */
    protected void onBackPressed() {
        if (remoteController != null && remoteController.isShown()) {
            remoteController.hide(true);
        } else if (!ScreenUtils.isPortrait(getContext())) {
            HiLog.info(LABEL, "Exit full-screen playback");
            setDisplayOrientation(AbilityInfo.DisplayOrientation.PORTRAIT);
            if (recoveryOrientationDispatch != null) {
                recoveryOrientationDispatch.revoke();
            }
            recoveryOrientationDispatch =
                    getUITaskDispatcher().delayDispatch(recoveryOrientationTask, DELAY_RECOVERY_ORIENTATION);
        } else {
            HiLog.info(LABEL, "onBackPressed");
            super.onBackPressed();
        }
    }

    private void startRemoteAbilityPa(Intent resultIntent) {
        String devicesId = resultIntent.getStringParam(Constants.PARAM_DEVICE_ID);
        Intent intent = new Intent();
        Operation operation =
                new Intent.OperationBuilder()
                        .withDeviceId(devicesId)
                        .withBundleName(getBundleName())
                        .withAbilityName("com.waylau.hmos.douyin.MainAbility")
                        .withAction("action.video.play")
                        .withFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE)
                        .build();
        String localDeviceId =
                KvManagerFactory.getInstance().createKvManager(new KvManagerConfig(this)).getLocalDeviceInfo().getId();
        intent.setParam(RemoteConstant.INTENT_PARAM_REMOTE_DEVICE_ID, localDeviceId);
        String path =
                videoService
                        .getVideoInfoByIndex(currentPlayingIndex)
                        .getResolutions()
                        .get(currentPlayingResolutionIndex)
                        .getUrl();
        intent.setParam(RemoteConstant.INTENT_PARAM_REMOTE_VIDEO_PATH, path);
        intent.setParam(RemoteConstant.INTENT_PARAM_REMOTE_VIDEO_INDEX, currentPlayingIndex);
        intent.setParam(RemoteConstant.INTENT_PARAM_REMOTE_START_POSITION, (int) player.getSeekWhenPrepared());
        intent.setOperation(operation);
        startAbility(intent);

        Intent remotePaIntent = new Intent();
        Operation paOperation =
                new Intent.OperationBuilder()
                        .withDeviceId(devicesId)
                        .withBundleName(getBundleName())
                        .withAbilityName("com.waylau.hmos.douyin.VideoControlServiceAbility")
                        .withFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE)
                        .build();
        remotePaIntent.setOperation(paOperation);
        boolean connectFlag = connectAbility(remotePaIntent, connection);
        if (connectFlag) {
            HiLog.info(LABEL, "start remote ability PA success");
            setDisplayOrientation(AbilityInfo.DisplayOrientation.PORTRAIT);
            initRemoteController();
            remoteController.setVideoInfo(
                    resultIntent.getStringParam(Constants.PARAM_DEVICE_NAME),
                    currentPlayingIndex,
                    (int) player.getCurrentPosition(),
                    (int) player.getDuration());
            remoteController.show();
        } else {
            HiLog.error(LABEL, "start remote ability PA failed");
            stopAbility(intent);
        }
    }

    private void initRemoteController() {
        if (remoteController == null) {
            remoteController = new RemoteController(this);
            remoteController.setRemoteControllerCallback(
                    (code, extra) -> {
                        if (mProxy == null) {
                            return;
                        }
                        boolean result =
                                mProxy.sendDataToRemote(RemoteConstant.REQUEST_CONTROL_REMOTE_DEVICE, code, extra);
                        if (!result) {
                            new ToastDialog(getContext())
                                    .setText(
                                            AppUtil.getStringResource(
                                                    getContext(), ResourceTable.String_send_failed_tips))
                                    .show();
                            remoteController.hide(false);
                        }
                    });

            StackLayout rootLayout = (StackLayout) findComponentById(ResourceTable.Id_root_layout);
            rootLayout.addComponent(remoteController);
        }
    }

    private void subscribe() {
        MatchingSkills matchingSkills = new MatchingSkills();
        matchingSkills.addEvent(Constants.PHONE_CONTROL_EVENT);
        matchingSkills.addEvent(CommonEventSupport.COMMON_EVENT_SCREEN_ON);
        CommonEventSubscribeInfo subscribeInfo = new CommonEventSubscribeInfo(matchingSkills);
        subscriber = new MyCommonEventSubscriber(subscribeInfo);
        try {
            CommonEventManager.subscribeCommonEvent(subscriber);
        } catch (RemoteException e) {
            HiLog.error(LABEL, "subscribeCommonEvent occur exception.");
        }
    }

    private void unSubscribe() {
        try {
            CommonEventManager.unsubscribeCommonEvent(subscriber);
        } catch (RemoteException e) {
            HiLog.error(LABEL, "unsubscribecommonevent occur exception.");
        }
    }

    /**
     * Obtains the play set item
     *
     * @param index  index of play set
     * @param width  component's width
     * @param height component's height
     * @return play set item
     */
    private Component getPlaylistItem(int index, int width, int height) {
        Component anthologyItem =
                LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_remote_episodes_item, null, false);
        anthologyItem.setComponentSize(width, height);
        HiLog.debug(LABEL, "Set playlist item size  = [" + width + "," + height + "]");
        Text numberText = (Text) anthologyItem.findComponentById(ResourceTable.Id_episodes_item_num);
        numberText.setTextColor(Color.WHITE);
        // Set background
        ShapeElement background = new ShapeElement();
        background.setRgbColor(new RgbColor(0x58, 0x58, 0x5D));
        background.setCornerRadius(AttrHelper.vp2px(8, getContext()));
        anthologyItem.setBackground(background);

        // Set icons, for example, in-play and trailer icons.
        if (index == currentPlayingIndex) {
            Component icPlaying = anthologyItem.findComponentById(ResourceTable.Id_episodes_item_playing);
            icPlaying.setVisibility(Component.VISIBLE);
        } else {
            numberText.setText(String.valueOf(index + 1));
        }
        anthologyItem.setClickedListener(
                component -> {
                    if (index == currentPlayingIndex) {
                        return;
                    }
                    // Current button show number of play set
                    TableLayout table = (TableLayout) component.getComponentParent();
                    Component srcChild = table.getComponentAt(currentPlayingIndex);
                    srcChild.findComponentById(ResourceTable.Id_episodes_item_playing)
                            .setVisibility(Component.INVISIBLE);
                    Text numText = (Text) srcChild.findComponentById(ResourceTable.Id_episodes_item_num);
                    numText.setText(String.valueOf(currentPlayingIndex + 1));
                    // Newly selected button display icon
                    component
                            .findComponentById(ResourceTable.Id_episodes_item_playing)
                            .setVisibility(Component.VISIBLE);
                    ((Text) component.findComponentById(ResourceTable.Id_episodes_item_num)).setText("");

                    currentPlayingIndex = index;
                    HiLog.info(LABEL, "Select index of playlist = " + currentPlayingIndex);
                    playbackNext(videoService.getVideoInfoByIndex(currentPlayingIndex));
                });
        if (videoService.getVideoInfoByIndex(index).isTrailer()) {
            Component trailerBtn = anthologyItem.findComponentById(ResourceTable.Id_episodes_item_trailer);
            trailerBtn.setVisibility(Component.VISIBLE);
        }
        return anthologyItem;
    }

    /**
     * Playback next video
     *
     * @param nextVideoInfo next video info from play set
     */
    private void playbackNext(VideoInfo nextVideoInfo) {
        player.pause();
        String path = nextVideoInfo.getResolutions().get(currentPlayingResolutionIndex).getUrl();
        String title = nextVideoInfo.getVideoDesc();
        player.setVideoPathAndTitle(path, title);
        player.setPlayerOnPreparedListener(() -> player.start());
    }

    /**
     * Switch playback sources when update resolution
     *
     * @param model Selected resolution information
     */
    private void updateResolution(ResolutionModel model) {
        boolean isPlayingNow = player.isPlaying();
        player.pause();
        long currentPosition = player.getCurrentPosition();
        player.setVideoPath(model.getUrl());
        player.setPlayerOnPreparedListener(
                () -> {
                    player.seekTo(currentPosition);
                    if (isPlayingNow) {
                        player.start();
                    }
                });
    }

    class MyCommonEventSubscriber extends CommonEventSubscriber {
        MyCommonEventSubscriber(CommonEventSubscribeInfo info) {
            super(info);
        }

        @Override
        public void onReceiveEvent(CommonEventData commonEventData) {
            Intent intent = commonEventData.getIntent();
            int controlCode = intent.getIntParam(Constants.KEY_CONTROL_CODE, 0);
            if (remoteController == null || !remoteController.isShown()) {
                HiLog.debug(LABEL, "remote controller is hidden now");
                return;
            }
            if (controlCode == ControlCode.SYNC_VIDEO_PROCESS.getCode()) {
                int totalTime = Integer.parseInt(intent.getStringParam(Constants.KEY_CONTROL_VIDEO_TIME));
                int progress = Integer.parseInt(intent.getStringParam(Constants.KEY_CONTROL_VIDEO_PROGRESS));
                remoteController.syncVideoPlayProcess(totalTime, progress);
            } else if (controlCode == ControlCode.SYNC_VIDEO_STATUS.getCode()) {
                boolean isPlaying =
                        Boolean.parseBoolean(intent.getStringParam(Constants.KEY_CONTROL_VIDEO_PLAYBACK_STATUS));
                if (remoteController.getPlayingStatus() != isPlaying) {
                    remoteController.changePlayingStatus();
                }
            } else {
                int currentVolume = Integer.parseInt(intent.getStringParam(Constants.KEY_CONTROL_VIDEO_VOLUME));
                remoteController.changeVolumeIcon(currentVolume);
            }
        }
    }
}
