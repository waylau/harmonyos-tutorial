package com.waylau.hmos.douyin.slice;

import com.waylau.hmos.douyin.constant.ControlCode;
import com.waylau.hmos.douyin.constant.RemoteConstant;
import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.component.VideoSetting;
import com.waylau.hmos.douyin.constant.Constants;
import com.waylau.hmos.douyin.data.VideoInfo;
import com.waylau.hmos.douyin.data.VideoInfoService;
import com.waylau.hmos.douyin.utils.AppUtil;
import com.waylau.hmos.douyin.view.VideoPlayerSlider;
import com.waylau.hmos.douyin.player.core.VideoPlayer.HmPlayerAdapter;
import com.waylau.hmos.douyin.player.ui.widget.media.VideoPlayerView;
import com.waylau.hmos.douyin.player.view.IBaseComponentAdapter;
import com.waylau.hmos.douyin.player.view.ITitleAdapter;
import com.waylau.hmos.douyin.player.view.VideoBoxArea;
import com.waylau.hmos.douyin.remote.MyRemoteProxy;
import com.waylau.hmos.douyin.utils.ScreenUtils;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityConnection;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.AttrHelper;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Image;
import ohos.agp.components.StackLayout;
import ohos.agp.components.Text;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.agp.window.service.Window;
import ohos.agp.window.service.WindowManager;
import ohos.bundle.AbilityInfo;
import ohos.bundle.ElementName;
import ohos.event.commonevent.CommonEventData;
import ohos.event.commonevent.CommonEventManager;
import ohos.event.commonevent.CommonEventSubscribeInfo;
import ohos.event.commonevent.CommonEventSubscriber;
import ohos.event.commonevent.CommonEventSupport;
import ohos.event.commonevent.MatchingSkills;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.multimodalinput.event.KeyEvent;
import ohos.rpc.IRemoteObject;
import ohos.rpc.RemoteException;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Video Playback Page
 */
public class VideoPlayAbilitySlice extends AbilitySlice {
    private static final HiLogLabel LABEL = new HiLogLabel(0, 0, "VideoPlayAbilitySlice");
    /**
     * Demo File Path
     */
    private static final String DEFAULT_PATH = "entrytv/resources/base/media/hm_sample_pv.mp4";

    private static final String REMOTE_PHONE_ABILITY =
            "com.waylau.hmos.douyin.ability.SyncControlServiceAbility";
    private static MyRemoteProxy myProxy;
    // Creating a Connection Callback Instance
    private final IAbilityConnection connection =
            new IAbilityConnection() {
                // Callback for connecting to a service
                @Override
                public void onAbilityConnectDone(ElementName elementName, IRemoteObject iRemoteObject, int resultCode) {
                    myProxy = new MyRemoteProxy(iRemoteObject);
                }

                // Callback for disconnecting from the service
                @Override
                public void onAbilityDisconnectDone(ElementName elementName, int resultCode) {
                    disconnectAbility(this);
                }
            };
    private VideoInfoService videoInfoService;
    private VideoPlayerView videoBox;
    private VideoSetting videoSetting;
    private int currentPlayingIndex = 0;
    private MyCommonEventSubscriber tvSubscriber;
    /**
     * Timer for refresh the progress.
     */
    private Timer timer;
    private VideoPlayerView.RemoteControlCallback remoteControlCallback =
            new VideoPlayerView.RemoteControlCallback() {
                @Override
                public void onProgressChanged(long totalTime, int progress) {
                    if (myProxy != null) {
                        Map<String, String> progressValue = new HashMap<>();
                        progressValue.put(RemoteConstant.REMOTE_KEY_VIDEO_TOTAL_TIME, String.valueOf(totalTime));
                        progressValue.put(RemoteConstant.REMOTE_KEY_VIDEO_CURRENT_PROGRESS, String.valueOf(progress));
                        myProxy.sendDataToRemote(
                                RemoteConstant.REQUEST_SYNC_VIDEO_STATUS,
                                ControlCode.SYNC_VIDEO_PROCESS.getCode(),
                                progressValue);
                    }
                }

                @Override
                public void onPlayingStatusChanged(boolean isPlaying) {
                    if (myProxy != null) {
                        Map<String, String> videoStatusMap = new HashMap<>();
                        videoStatusMap.put(
                                RemoteConstant.REMOTE_KEY_VIDEO_CURRENT_PLAYBACK_STATUS, String.valueOf(isPlaying));
                        HiLog.debug(LABEL, "isPlaying = " + String.valueOf(isPlaying));
                        myProxy.sendDataToRemote(
                                RemoteConstant.REQUEST_SYNC_VIDEO_STATUS,
                                ControlCode.SYNC_VIDEO_STATUS.getCode(),
                                videoStatusMap);
                    }
                }

                @Override
                public void onVolumeChanged(int volume) {
                    if (myProxy != null) {
                        Map<String, String> volumeMap = new HashMap<>();
                        volumeMap.put(RemoteConstant.REMOTE_KEY_VIDEO_CURRENT_VOLUME, String.valueOf(volume));
                        myProxy.sendDataToRemote(
                                RemoteConstant.REQUEST_SYNC_VIDEO_STATUS,
                                ControlCode.SYNC_VIDEO_VOLUME.getCode(),
                                volumeMap);
                    }
                }
            };
    /**
     * Whether to resume playback
     */
    private boolean needResumeStatus;

    @Override
    protected void onStart(Intent intent) {
        if (!ScreenUtils.isPortrait(getContext())) {
            hideStatusBar();
        } else {
            showStatusBar();
        }
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_video_box);

        videoInfoService = new VideoInfoService(this);
        videoBox = (VideoPlayerView) findComponentById(ResourceTable.Id_video_view);

        if (videoBox != null) {
            currentPlayingIndex = intent.getIntParam(RemoteConstant.INTENT_PARAM_REMOTE_VIDEO_INDEX, 0);
            videoBox.setClickToHideControlArea(false);
            addCoreComponent();
            addCustomComponent();
            videoBox.registerRemoteControlCallback(remoteControlCallback);
            String path = intent.getStringParam(RemoteConstant.INTENT_PARAM_REMOTE_VIDEO_PATH);
            if (path != null) {
                videoBox.setVideoPath(
                        videoInfoService.getVideoInfoByIndex(currentPlayingIndex).getResolutions().get(0).getUrl());
                videoBox.setPlayerOnPreparedListener(
                        () -> {
                            videoBox.start();
                            videoBox.seekTo(intent.getIntParam(RemoteConstant.INTENT_PARAM_REMOTE_START_POSITION, 0));
                        });
            } else {
                videoBox.setVideoPath(DEFAULT_PATH);
            }
            videoBox.setErrorListener(
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
        }

        initSettingComponent();
        connectRemoteDevice(intent.getStringParam(RemoteConstant.INTENT_PARAM_REMOTE_DEVICE_ID));
        subscribe();
    }

    @Override
    protected void onOrientationChanged(AbilityInfo.DisplayOrientation displayOrientation) {
        super.onOrientationChanged(displayOrientation);
        // The length and width of the VideoBox must be recalculated when the landscape and portrait are switched.
        if (videoBox != null) {
            videoBox.onOrientationChanged(displayOrientation);
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
    protected void onForeground(Intent intent) {
        super.onForeground(intent);
        if (videoBox != null && needResumeStatus) {
            videoBox.registerRemoteControlCallback(remoteControlCallback);
            videoBox.start();
            needResumeStatus = false;
        }
    }

    @Override
    protected void onBackground() {
        super.onBackground();
        if (videoBox != null) {
            if (videoBox.isPlaying()) {
                needResumeStatus = true;
            }
            videoBox.unregisterRemoteControlCallback();
            videoBox.pause();
        }
    }

    @Override
    protected void onBackPressed() {
        if (videoSetting.isShow()) {
            videoSetting.hide();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (videoBox != null) {
            videoBox.release(true);
            videoBox.unregisterRemoteControlCallback();
        }
        if (timer != null) {
            timer.cancel();
        }
        unSubscribe();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        Window window = getWindow();
        if (window == null) {
            return super.onKeyDown(keyCode, keyEvent);
        }
        if (keyCode == KeyEvent.KEY_VOLUME_UP) {
            videoBox.setVolume(Constants.VOLUME_STEP);
            return true;
        }
        if (keyCode == KeyEvent.KEY_VOLUME_DOWN) {
            videoBox.setVolume(-Constants.VOLUME_STEP);
            return true;
        }
        if (videoSetting.isShow()) {
            return true;
        }
        videoBox.simulateDrag();
        if (keyCode == KeyEvent.KEY_ENTER || keyCode == KeyEvent.KEY_DPAD_CENTER) {
            if (videoBox.isPlaying()) {
                videoBox.pause();
            } else {
                videoBox.start();
            }
            return true;
        }
        if (keyCode == KeyEvent.KEY_DPAD_LEFT) {
            videoBox.seekTo(videoBox.getCurrentPosition() - Constants.REWIND_STEP);
            return true;
        }
        if (keyCode == KeyEvent.KEY_DPAD_RIGHT) {
            videoBox.seekTo(videoBox.getCurrentPosition() + Constants.REWIND_STEP);
            return true;
        }

        return super.onKeyDown(keyCode, keyEvent);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        if (timer != null) {
            timer.cancel();
        }
        Window window = getWindow();
        if (window == null) {
            return super.onKeyUp(keyCode, keyEvent);
        }
        if (keyCode == KeyEvent.KEY_DPAD_DOWN && videoSetting != null) {
            videoSetting.show();
            return true;
        }
        return super.onKeyUp(keyCode, keyEvent);
    }

    @Override
    public boolean onKeyPressAndHold(int keyCode, KeyEvent keyEvent) {
        Window window = getWindow();
        if (window == null || videoSetting.isShow()) {
            return super.onKeyPressAndHold(keyCode, keyEvent);
        }
        if (keyCode == KeyEvent.KEY_DPAD_LEFT || keyCode == KeyEvent.KEY_DPAD_RIGHT) {
            // When you press and hold the fast forward or rewind key,
            // the timer is started and the progress is refreshed every 500 ms.
            TimerTask timerTask =
                    new TimerTask() {
                        @Override
                        public void run() {
                            int rewindStep =
                                    keyCode == KeyEvent.KEY_DPAD_RIGHT ? Constants.REWIND_STEP : -Constants.REWIND_STEP;
                            videoBox.seekTo(videoBox.getCurrentPosition() + rewindStep);
                        }
                    };
            timer = new Timer();
            timer.schedule(timerTask, 100, 500);
            return true;
        }

        return super.onKeyPressAndHold(keyCode, keyEvent);
    }

    /**
     * Adding core component like playback button and seek bar
     */
    private void addCoreComponent() {
        // Add Progress Bar
        videoBox.addSeekBar(new VideoPlayerSlider(getContext()), VideoBoxArea.BOTTOM, 0);
    }

    /**
     * Adding no-core component like share button and title……
     */
    private void addCustomComponent() {
        // Add title and back button
        addTitle();

        // Add more settings component
        addMoreSetting();
    }

    private void addTitle() {
        videoBox.addTitle(
                new ITitleAdapter() {
                    private Text title;

                    @Override
                    public Text initComponent() {
                        title = new Text(getContext());
                        title.setMaxTextLines(1);
                        title.setTextColor(new Color(getColor(ResourceTable.Color_tv_video_title_color)));
                        title.setAutoFontSize(true);
                        title.setAutoFontSizeRule(
                                AttrHelper.fp2px(10, getContext()),
                                AttrHelper.fp2px(20, getContext()),
                                AttrHelper.fp2px(2, getContext()));
                        title.setText(videoInfoService.getVideoInfoByIndex(currentPlayingIndex).getVideoDesc());
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
                        if (title == null) {
                            return;
                        }
                        // Title placeholder required,cannot be hidden.
                        title.setVisibility(
                                displayOrientation == AbilityInfo.DisplayOrientation.PORTRAIT
                                        ? Component.INVISIBLE
                                        : Component.VISIBLE);
                    }

                    @Override
                    public void onVideoSourceChanged() {
                    }

                    @Override
                    public void onClick(Component component) {
                    }

                    @Override
                    public DirectionalLayout.LayoutConfig initLayoutConfig() {
                        return null;
                    }
                },
                VideoBoxArea.TOP);
    }

    private void addMoreSetting() {
        videoBox.addComponent(
                new IBaseComponentAdapter() {
                    private DirectionalLayout moreSetting;

                    @Override
                    public Component initComponent() {
                        moreSetting = new DirectionalLayout(getContext());
                        moreSetting.setOrientation(Component.HORIZONTAL);
                        moreSetting.setAlignment(LayoutAlignment.CENTER);
                        Text textBef = new Text(getContext());
                        textBef.setText(ResourceTable.String_videobox_title_tips_pre);
                        textBef.setTextColor(new Color(getColor(ResourceTable.Color_tv_more_setting_icon_color)));
                        textBef.setTextSize(AttrHelper.fp2px(18, getContext()));
                        Image image = new Image(getContext());
                        image.setPixelMap(ResourceTable.Media_ic_down);
                        image.setComponentSize(AttrHelper.fp2px(16, getContext()), AttrHelper.fp2px(18, getContext()));
                        image.setScaleMode(Image.ScaleMode.STRETCH);
                        Text textAft = new Text(getContext());
                        textAft.setText(ResourceTable.String_videobox_title_tips_after);
                        textAft.setTextColor(new Color(getColor(ResourceTable.Color_tv_more_setting_icon_color)));
                        textAft.setTextSize(AttrHelper.fp2px(18, getContext()));
                        moreSetting.addComponent(textBef);
                        moreSetting.addComponent(image);
                        moreSetting.addComponent(textAft);
                        return moreSetting;
                    }

                    @Override
                    public void onClick(Component component) {
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
                        if (moreSetting == null) {
                            return;
                        }
                        moreSetting.setVisibility(
                                displayOrientation == AbilityInfo.DisplayOrientation.PORTRAIT
                                        ? Component.INVISIBLE
                                        : Component.VISIBLE);
                    }

                    @Override
                    public void onVideoSourceChanged() {
                    }
                },
                VideoBoxArea.TOP,
                AttrHelper.vp2px(24, getContext()));
    }

    private void connectRemoteDevice(String deviceId) {
        Intent connectPaIntent = new Intent();
        Operation operation =
                new Intent.OperationBuilder()
                        .withDeviceId(deviceId)
                        .withBundleName(getBundleName())
                        .withAbilityName(REMOTE_PHONE_ABILITY)
                        .withFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE)
                        .build();
        connectPaIntent.setOperation(operation);

        connectAbility(connectPaIntent, connection);
    }

    private void subscribe() {
        MatchingSkills matchingSkills = new MatchingSkills();
        matchingSkills.addEvent(Constants.TV_CONTROL_EVENT);
        matchingSkills.addEvent(CommonEventSupport.COMMON_EVENT_SCREEN_ON);
        CommonEventSubscribeInfo subscribeInfo = new CommonEventSubscribeInfo(matchingSkills);
        tvSubscriber = new MyCommonEventSubscriber(subscribeInfo);
        try {
            CommonEventManager.subscribeCommonEvent(tvSubscriber);
        } catch (RemoteException e) {
            HiLog.error(LABEL, "subscribeCommonEvent occur exception.");
        }
    }

    private void unSubscribe() {
        try {
            CommonEventManager.unsubscribeCommonEvent(tvSubscriber);
        } catch (RemoteException e) {
            HiLog.error(LABEL, "unSubscribe Exception");
        }
    }

    private void initSettingComponent() {
        videoSetting = new VideoSetting(this, videoInfoService);
        videoSetting.registerVideoSettingCallback(
                new VideoSetting.VideoSettingCallback() {
                    @Override
                    public void setVideoSpeed(float speed) {
                        videoBox.setPlaybackSpeed(speed);
                        videoSetting.hide();
                    }

                    @Override
                    public void refreshVideoPath(String url, String name) {
                        videoBox.pause();
                        if (!"".equals(name)) {
                            videoBox.setVideoPathAndTitle(url, name);
                        } else {
                            videoBox.setVideoPath(url);
                        }
                        videoBox.setPlayerOnPreparedListener(() -> videoBox.start());
                        videoSetting.hide();
                    }
                });
        StackLayout rootLayout = (StackLayout) findComponentById(ResourceTable.Id_root_layout);
        rootLayout.addComponent(videoSetting);
    }

    class MyCommonEventSubscriber extends CommonEventSubscriber {
        MyCommonEventSubscriber(CommonEventSubscribeInfo info) {
            super(info);
        }

        @Override
        public void onReceiveEvent(CommonEventData commonEventData) {
            HiLog.info(LABEL, "onReceiveEvent.....");
            Intent intent = commonEventData.getIntent();
            int controlCode = intent.getIntParam(Constants.KEY_CONTROL_CODE, 0);
            String extras = intent.getStringParam(Constants.KEY_CONTROL_VALUE);
            if (controlCode == ControlCode.PLAY.getCode()) {
                if (videoBox.isPlaying()) {
                    videoBox.pause();
                } else if (!videoBox.isPlaying() && !needResumeStatus) {
                    videoBox.start();
                } else {
                    HiLog.error(LABEL, "Ignoring the case with player status");
                }
            } else if (controlCode == ControlCode.SEEK.getCode()) {
                videoBox.seekTo(videoBox.getDuration() * Integer.parseInt(extras) / 100);
            } else if (controlCode == ControlCode.FORWARD.getCode()) {
                videoBox.seekTo(videoBox.getCurrentPosition() + Constants.REWIND_STEP);
            } else if (controlCode == ControlCode.BACKWARD.getCode()) {
                videoBox.seekTo(videoBox.getCurrentPosition() - Constants.REWIND_STEP);
            } else if (controlCode == ControlCode.VOLUME_ADD.getCode()) {
                videoBox.setVolume(Constants.VOLUME_STEP);
            } else if (controlCode == ControlCode.VOLUME_REDUCED.getCode()) {
                videoBox.setVolume(-Constants.VOLUME_STEP);
            } else if (controlCode == ControlCode.SWITCH_SPEED.getCode()) {
                videoBox.setPlaybackSpeed(Float.parseFloat(extras));
            } else if (controlCode == ControlCode.SWITCH_RESOLUTION.getCode()) {
                long currentPosition = videoBox.getCurrentPosition();
                int resolutionIndex = Integer.parseInt(extras);
                VideoInfo videoInfo = videoInfoService.getVideoInfoByIndex(currentPlayingIndex);
                videoBox.pause();
                videoBox.setVideoPath(videoInfo.getResolutions().get(resolutionIndex).getUrl());
                videoBox.setPlayerOnPreparedListener(
                        () -> {
                            videoBox.seekTo(currentPosition);
                            videoBox.start();
                        });
            } else if (controlCode == ControlCode.SWITCH_VIDEO.getCode()) {
                videoBox.pause();
                currentPlayingIndex = Integer.parseInt(extras);
                VideoInfo videoInfo = videoInfoService.getVideoInfoByIndex(currentPlayingIndex);
                videoBox.setVideoPathAndTitle(videoInfo.getResolutions().get(0).getUrl(), videoInfo.getVideoDesc());
                videoBox.setPlayerOnPreparedListener(() -> videoBox.start());
            } else if (controlCode == ControlCode.STOP_CONNECTION.getCode()) {
                terminate();
            } else {
                HiLog.error(LABEL, "Ignoring the case with control code");
            }
        }
    }
}
