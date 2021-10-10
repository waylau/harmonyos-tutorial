package com.waylau.hmos.douyin.player.ui.widget.media;

import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.player.core.PlayerStatus;
import com.waylau.hmos.douyin.player.core.SourceFactory;
import com.waylau.hmos.douyin.player.core.VideoPlayer.HmPlayerAdapter;
import com.waylau.hmos.douyin.player.core.VideoPlayer.IVideoPlayer;
import com.waylau.hmos.douyin.player.view.IBaseComponentAdapter;
import com.waylau.hmos.douyin.player.view.IPlaybackButtonAdapter;
import com.waylau.hmos.douyin.player.view.ISliderAdapter;
import com.waylau.hmos.douyin.player.view.ITitleAdapter;
import com.waylau.hmos.douyin.player.view.VideoBoxArea;
import com.waylau.hmos.douyin.utils.ElementUtils;
import com.waylau.hmos.douyin.utils.ScreenUtils;
import com.waylau.hmos.douyin.utils.StringUtils;

import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorGroup;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.AttrHelper;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DependentLayout;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.DragInfo;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.RoundProgressBar;
import ohos.agp.components.Slider;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.components.surfaceprovider.SurfaceProvider;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.utils.Point;
import ohos.agp.window.service.Window;
import ohos.agp.window.service.WindowManager;
import ohos.app.Context;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.bundle.AbilityInfo;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.audio.AudioInterrupt;
import ohos.media.audio.AudioManager;
import ohos.media.audio.AudioRemoteException;
import ohos.media.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A customized view to provide video playback ability.
 */
public class VideoPlayerView extends DependentLayout {
    private static final String TAG = VideoPlayerView.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    /**
     * log label.
     */
    public static final HiLogLabel VIDEO_BOX_LABEL = new HiLogLabel(0, 0, "VideoPlayerView");

    /**
     * transparent animation duration microseconds.
     */
    public static final int ALPHA_ANIMATION_DURATION = 500;

    /**
     * transparent parameter for alpha value
     */
    public static final float VALUE_TRANSPARENT = 0;

    /**
     * transparent parameter alpha value
     */
    public static final float VALUE_OPAQUE = 1;

    /**
     * controller component displaying duration seconds.
     * <p>Click the control component, and the timing will be re-timed.
     */
    public static final int COMPONENT_DISPLAY_TIME = 5;

    /**
     * volume adjustment step.
     */
    public static final int VOLUME_STEP = 1;

    /**
     * Minimum threshold offset to trigger volume adjustment, volume+-{@link VideoPlayerView#VOLUME_STEP}
     */
    public static final int MIN_OFFSET_FOR_VOLUME_ADJUSTMENT = 10;

    /**
     * Minimum threshold offset to trigger progress adjustment
     */
    public static final int MIN_OFFSET_FOR_PROGRESS_ADJUSTMENT = 8;

    /**
     * default controller component margin.
     */
    public static final float DEFAULT_MARGIN_VALUE_UNIT_VP = 16f;

    /**
     * Default playback speed is 1.0
     */
    public static final float DEFAULT_PLAYBACK_SPEED = 1.0f;

    /**
     * Maximum progress bar
     */
    public static final int MAX_PROGRESS_VALUE = 100;

    /**
     * Modify progress
     */
    public static final int GESTURE_MODIFY_PROGRESS = 1;

    /**
     * Modify volume
     */
    public static final int GESTURE_MODIFY_VOLUME = 2;

    /**
     * Control button icon width, unit:px
     */
    public static final int ICON_WIDTH = 72;

    /**
     * Invalid value. This value is not updated when the time or progress is transferred.
     */
    public static final int INVALID_VALUE = -1;
    private static final HiLogLabel LABEL = new HiLogLabel(0, 0, "VideoPlayerView");
    /**
     * Gesture flag
     */
    private static int GESTURE_FLAG = 0;
    private final int HmPlayer = 1;
    /**
     * All component containers of the VideoPlayerView
     */
    private final List<ComponentContainer> componentContainerList = new ArrayList<>();
    /**
     * Animator group
     */
    private final AnimatorGroup animatorGroup = new AnimatorGroup();
    /**
     * All components of the VideoPlayerView
     */
    private final List<IBaseComponentAdapter> comAdapterList = new ArrayList<>();
    private final AttrSet attrSet;
    /**
     * Progress adjustment step,unit milliseconds.
     * <p>Change based on the video duration{@link VideoPlayerView#playerOnPreparedListener}.
     */
    private int PROGRESS_STEP = 3000;
    // currentState is a VideoPlayerView instance's current state.
    // targetState is the state that a method caller intends to reach.
    private PlayerStatus currentState = PlayerStatus.IDLE;
    private PlayerStatus targetState = PlayerStatus.IDLE;
    // settable by the client
    private String videoPath;
    // All the stuff we need for playing and showing a video
    private IRenderView.ISurfaceHolder surfaceHolder = null;
    private IVideoPlayer videoPlayer = null;
    // VideoView attribute
    private int videoWidth;
    private int videoHeight;
    private int surfaceWidth;
    private int surfaceHeight;
    private int currentBufferPercentage;
    private long seekWhenPrepared; // recording the seek position while preparing
    // Callback
    private IVideoPlayer.PlaybackCompleteListener playbackCompleteListener;
    private IVideoPlayer.PlayerPreparedListener playerPreparedListener;
    private IVideoPlayer.ErrorListener onErrorListener;
    // RenderView instance
    private IRenderView renderView;
    IVideoPlayer.VideoSizeChangedListener videoSizeChangedListener =
            new IVideoPlayer.VideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(int width, int height) {
                    HiLog.debug(VIDEO_BOX_LABEL, "onVideoSizeChanged width = " + width + " height = " + height);
                    if (width != 0 && height != 0) {
                        videoWidth = width;
                        videoHeight = height;
                        if (renderView != null) {
                            renderView.setVideoSize(videoWidth, videoHeight);
                        }
                    }
                }
            };
    // Audio-related
    private AudioManager audioManager;
    private AudioInterrupt audioInterrupt;
    private DependentLayout rootComp;
    private DirectionalLayout bottomArea;
    private DirectionalLayout topArea;
    private DirectionalLayout leftArea;
    private DirectionalLayout rightArea;
    private DirectionalLayout aboveBottomArea;
    /**
     * Counting variables
     */
    private int count;
    /**
     * Timer for component status refresh.
     */
    private Timer timer;
    /**
     * Playback Button Adapter
     */
    private Optional<IPlaybackButtonAdapter> iPlaybackButtonAdapter = Optional.empty();
    /**
     * SeekBar
     */
    private Optional<ISliderAdapter> iSliderAdapter = Optional.empty();
    private final IVideoPlayer.BufferChangedListener bufferChangedListener1 =
            new IVideoPlayer.BufferChangedListener() {
                @Override
                public void onBufferChangedListener(int percent) {
                    currentBufferPercentage = percent;
                    iSliderAdapter.ifPresent(adapter -> adapter.onBufferProgressChanged(percent));
                }
            };
    private final IVideoPlayer.RewindToCompleteListener rewindToCompleteListener =
            () -> {
                HiLog.error(LABEL, " onSeekComplete");
                iSliderAdapter.ifPresent(
                        slider -> slider.onMediaProgressChanged(videoPlayer.getCurrentPosition(), INVALID_VALUE));
            };
    /**
     * VideoPlayerView title adapter's impl
     */
    private Optional<ITitleAdapter> iTitleAdapters = Optional.empty();
    /**
     * judge whether user is dragging slider.
     */
    private boolean isDraggingSlider;
    /**
     * judge whether user is dragging VideoPlayer.
     */
    private boolean isDraggingPlayer;
    /**
     * Whether to resume playback
     */
    private boolean needResumeStatus;
    /**
     * progress callback instance
     */
    private RemoteControlCallback remoteControlCallback;
    private final IVideoPlayer.PlaybackCompleteListener playbackCompleteListener1 =
            new IVideoPlayer.PlaybackCompleteListener() {
                @Override
                public void onPlaybackComplete() {
                    currentState = PlayerStatus.COMPLETE;
                    targetState = PlayerStatus.COMPLETE;

                    // you can add your own control logic here when playback is complete.
                    long duration = videoPlayer.getDuration();
                    HiLog.error(LABEL, "playbackComplete" + duration);
                    if (playbackCompleteListener != null) {
                        playbackCompleteListener.onPlaybackComplete();
                    }

                    iPlaybackButtonAdapter.ifPresent(button -> button.onPlayStatusChange(PlayerStatus.COMPLETE));

                    iSliderAdapter.ifPresent(slider -> slider.onMediaProgressChanged(duration, MAX_PROGRESS_VALUE));

                    if (remoteControlCallback != null) {
                        remoteControlCallback.onProgressChanged(duration, MAX_PROGRESS_VALUE);
                    }
                }
            };
    /**
     * Custom click listener,
     * <p>Do not use{@link Component#setClickedListener(ClickedListener)},the fade-in and fade-out animation will fail.
     */
    private VideoPlayerViewClickedListener clickedListener;
    /**
     * Record the last playback speed
     */
    private float lastPlaybackSpeed;
    /**
     * Video current playing time
     */
    private long curDownPlayingTime;
    /**
     * Default value is true.If equal to false,click outside can not hide the control area.
     */
    private boolean isClickToHideControlArea = true;
    /**
     * Loading component.
     */
    private RoundProgressBar loadingComp;
    IVideoPlayer.PlayerPreparedListener playerOnPreparedListener =
            new IVideoPlayer.PlayerPreparedListener() {
                @Override
                public void onPrepared() {
                    HiLog.error(LABEL, "IVideoView preparedListener callback invoke");
                    hideLoadingComponent();
                    currentState = PlayerStatus.PREPARED;
                    targetState = PlayerStatus.PREPARED;

                    if (playerPreparedListener != null) {
                        playerPreparedListener.onPrepared();
                    }

                    long seekToPosition = seekWhenPrepared; // mSeekWhenPrepared may be changed after seekTo() call
                    if (seekToPosition != 0) {
                        seekTo(seekToPosition);
                    }

                    startUpdateProgressTask();
                    // pass the video duration.
                    iSliderAdapter.ifPresent(slider -> slider.initMediaEndTime(videoPlayer.getDuration()));
                    // One percent adjustment at a time
                    PROGRESS_STEP = (int) (videoPlayer.getDuration() / MAX_PROGRESS_VALUE);
                }
            };
    private final IVideoPlayer.MessageListener messageListener =
            (type, info) -> {
                // currently this callback just directly pass error code and info from player instance.
                HiLog.info(LABEL, "message type: " + type + " extra info: " + info);
                switch (type) {
                    case Player.PLAYER_INFO_AUDIO_NOT_PLAYING:
                        HiLog.info(LABEL, "Audio playback has stopped, but the video is still playing");
                        break;
                    case Player.PLAYER_INFO_BAD_INTERLEAVING:
                        HiLog.info(LABEL, "Media is incorrectly interleaved or not interleaved");
                        break;
                    case Player.PLAYER_INFO_EXTERNAL_METADATA_UPDATE:
                        HiLog.info(LABEL, "A new set of metadata is only externally available");
                        break;
                    case Player.PLAYER_INFO_METADATA_UPDATE:
                        HiLog.info(LABEL, "A new set of metadata is available");
                        break;
                    case Player.PLAYER_INFO_NETWORK_BANDWIDTH:
                        HiLog.info(LABEL, "Estimated network bandwidth information is available");
                        break;
                    case Player.PLAYER_INFO_NOT_SEEKABLE:
                        HiLog.info(LABEL, "Media is not seekable");
                        break;
                    case Player.PLAYER_INFO_STARTED_AS_NEXT:
                        HiLog.info(
                                LABEL,
                                "Current player has just finished the playback and the next player starts the"
                                        + " playback");
                        break;
                    case Player.PLAYER_INFO_SUBTITLE_TIMED_OUT:
                        HiLog.info(LABEL, "Parsing the subtitle track is time-consuming");
                        break;
                    case Player.PLAYER_INFO_TIMED_TEXT_ERROR:
                        HiLog.info(LABEL, "An error occurs when processing timed text");
                        break;
                    case Player.PLAYER_INFO_UNKNOWN:
                        HiLog.info(LABEL, "An unknown the information type");
                        break;
                    case Player.PLAYER_INFO_UNSUPPORTED_SUBTITLE:
                        HiLog.info(LABEL, "Media system does not support subtitle tracks");
                        break;
                    case Player.PLAYER_INFO_VIDEO_NOT_PLAYING:
                        HiLog.info(LABEL, "Video playback has stopped, but the audio is still playing");
                        break;
                    case Player.PLAYER_INFO_VIDEO_TRACK_LAGGING:
                        HiLog.info(LABEL, "Video frames cannot be decoded quickly");
                        break;
                    case Player.PLAYER_INFO_VIDEO_RENDERING_START:
                        HiLog.info(LABEL, "Rendering of the first frame in the media file starts");
                        break;
                    case Player.PLAYER_INFO_BUFFERING_START:
                        HiLog.info(LABEL, "Player pauses to buffer more data");
                        currentState = PlayerStatus.BUFFERING;
                        targetState = PlayerStatus.BUFFERING;
                        showLoadingComponent();
                        break;
                    case Player.PLAYER_INFO_BUFFERING_END:
                        HiLog.info(LABEL, "Player resumes after the buffer is filled");
                        currentState = PlayerStatus.BUFFERED;
                        targetState = PlayerStatus.BUFFERED;
                        hideLoadingComponent();
                        break;
                    default:
                        HiLog.error(LABEL, "Undefined message type: " + type + " extra info: " + info);
                        break;
                }
            };
    private final IVideoPlayer.ErrorListener errorListener1 =
            (errorType, errorCode) -> {
                HiLog.error(LABEL, "player framework_err: " + errorType + " err_message: " + errorCode);
                hideLoadingComponent();
                currentState = PlayerStatus.ERROR;
                targetState = PlayerStatus.ERROR;
                switch (errorType) {
                    case HmPlayerAdapter.ERROR_LOADING_RESOURCE:
                        HiLog.error(LABEL, "Media file loading error");
                        break;
                    case HmPlayerAdapter.ERROR_INVALID_OPERATION:
                        HiLog.error(LABEL, "Invalid operation");
                        break;
                    default:
                        HiLog.error(LABEL, "Undefined error type: " + errorType + " extra info: " + errorCode);
                        break;
                }
                onErrorListener.onError(errorType, errorCode);
            };
    IRenderView.IRenderCallback surfaceHolderCallback =
            new IRenderView.IRenderCallback() {
                /**
                 * callback when surface changed.
                 *
                 * @param holder holder
                 * @param format could be 0
                 * @param width willing width.
                 * @param height willing height.
                 */
                @Override
                public void onSurfaceChanged(IRenderView.ISurfaceHolder holder, int format, int width, int height) {
                    if (holder.getRenderView() != renderView) {
                        HiLog.error(LABEL, "onSurfaceChanged: unmatched render callback\n");
                    }
                }

                /**
                 * callback when surface changed.
                 *
                 * @param holder holder
                 * @param width  could be 0
                 * @param height could be 0
                 */
                @Override
                public void onSurfaceCreated(IRenderView.ISurfaceHolder holder, int width, int height) {
                    if (holder.getRenderView() != renderView) {
                        HiLog.error(LABEL, "onSurfaceCreated: unmatched render callback\n");
                        return;
                    }
                    surfaceHolder = holder;

                    if (currentState == PlayerStatus.IDLE && targetState == PlayerStatus.IDLE) {
                        HiLog.debug(LABEL, "openVideo");
                        openVideo();
                    } else {
                        HiLog.debug(LABEL, "bindSurfaceHolder");
                        bindSurfaceHolder(videoPlayer, holder);
                    }

                    setComponentSize(surfaceWidth, surfaceHeight);

                    renderView.setPlaybackWindowSize(surfaceWidth, surfaceHeight);
                }

                /**
                 * callback when surface destroyed.
                 *
                 * @param holder holder
                 */
                @Override
                public void onSurfaceDestroyed(IRenderView.ISurfaceHolder holder) {
                    if (holder.getRenderView() != renderView) {
                        HiLog.error(LABEL, "onSurfaceDestroyed: unmatched render callback\n");
                        return;
                    }

                    // after we return from this we can't use the surface any more
                    surfaceHolder = null;
                    releaseWithoutStop();
                }
            };

    public VideoPlayerView(Context context) {
        this(context, null);
    }

    public VideoPlayerView(Context context, AttrSet attrSet) {
        this(context, attrSet, null);
    }

    public VideoPlayerView(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        this.attrSet = attrSet;
        measurePlaybackWindow();
        initVideoView(context);
    }

    /**
     * Measure playback window
     */
    public void measurePlaybackWindow() {
        Point screenSize = ScreenUtils.getScreenSize(getContext());
        attrSet.getAttr("height")
                .ifPresent(
                        attr -> {
                            int dimensionValue = attr.getDimensionValue();
                            switch (dimensionValue) {
                                case ComponentContainer.LayoutConfig.MATCH_PARENT:
                                case ComponentContainer.LayoutConfig.MATCH_CONTENT:
                                    surfaceHeight = ComponentContainer.LayoutConfig.MATCH_PARENT;
                                    break;
                                default:
                                    if (screenSize.getPointYToInt() >= screenSize.getPointXToInt()) {
                                        surfaceHeight = dimensionValue;
                                    } else {
                                        surfaceHeight = ComponentContainer.LayoutConfig.MATCH_PARENT;
                                    }
                                    break;
                            }
                        });
        attrSet.getAttr("width")
                .ifPresent(
                        attr -> {
                            int dimensionValue = attr.getDimensionValue();
                            switch (dimensionValue) {
                                case ComponentContainer.LayoutConfig.MATCH_PARENT:
                                case ComponentContainer.LayoutConfig.MATCH_CONTENT:
                                    surfaceWidth = ComponentContainer.LayoutConfig.MATCH_PARENT;
                                    break;
                                default:
                                    if (screenSize.getPointYToInt() >= screenSize.getPointXToInt()) {
                                        surfaceWidth = dimensionValue;
                                    } else {
                                        surfaceWidth = ComponentContainer.LayoutConfig.MATCH_PARENT;
                                    }
                                    break;
                            }
                        });
    }

    private void initVideoView(Context context) {
        Component parentComponent =
                LayoutScatter.getInstance(context).parse(ResourceTable.Layout_view_videoplayer, this, true);
        rootComp = (DependentLayout) parentComponent.findComponentById(ResourceTable.Id_root_comp);
        loadingComp = (RoundProgressBar) parentComponent.findComponentById(ResourceTable.Id_loading);
        setRender();

        videoWidth = 0;
        videoHeight = 0;
        requestFocus();
        currentState = PlayerStatus.IDLE;
        targetState = PlayerStatus.IDLE;

        // Bottom area
        bottomArea = (DirectionalLayout) findComponentById(ResourceTable.Id_bottom_area);
        // Dark middle, light edge
        bottomArea.setBackground(
                ElementUtils.getGradientColor(0x00000040, 0x00001230, ShapeElement.Orientation.BOTTOM_TO_TOP));
        /*
        bottomArea.setBackground(
                ElementUtils.getGradientColor(0x00000080, 0x00000000, ShapeElement.Orientation.BOTTOM_TO_TOP));
        */
        // Above bottom area
        aboveBottomArea = (DirectionalLayout) findComponentById(ResourceTable.Id_above_bottom_area);
        aboveBottomArea.setBackground(
                ElementUtils.getGradientColor(0x00000040, 0x00000000, ShapeElement.Orientation.BOTTOM_TO_TOP));
        // Top area
        topArea = (DirectionalLayout) findComponentById(ResourceTable.Id_top_area);
        topArea.setBackground(
                ElementUtils.getGradientColor(0x00000080, 0x00000000, ShapeElement.Orientation.TOP_TO_BOTTOM));
        // Left area
        leftArea = (DirectionalLayout) findComponentById(ResourceTable.Id_left_area);
        // Left area parent
        DependentLayout leftAreaParent = (DependentLayout) findComponentById(ResourceTable.Id_left_area_parent);
        // Right area
        rightArea = (DirectionalLayout) findComponentById(ResourceTable.Id_right_area);
        // Right area parent
        DependentLayout rightAreaParent = (DependentLayout) findComponentById(ResourceTable.Id_right_area_parent);

        componentContainerList.add(bottomArea);
        componentContainerList.add(topArea);
        componentContainerList.add(leftAreaParent);
        componentContainerList.add(rightAreaParent);
        componentContainerList.add(aboveBottomArea);

        // Gesture callback
        // 禁用拖动
        /*
        setDraggedListener(
                DRAG_HORIZONTAL_VERTICAL,
                new DraggedListener() {
                    @Override
                    public void onDragDown(Component component, DragInfo dragInfo) {
                        HiLog.info(VIDEO_BOX_LABEL, "VideoView onDragDown");
                    }

                    @Override
                    public void onDragStart(Component component, DragInfo dragInfo) {
                    }

                    @Override
                    public void onDragUpdate(Component component, DragInfo dragInfo) {
                        if (!isDraggingPlayer) {
                            if (Math.abs(dragInfo.xOffset) >= Math.abs(dragInfo.yOffset)) {
                                // Adjusting the progress
                                GESTURE_FLAG = GESTURE_MODIFY_PROGRESS;
                                // Hold pause while dragging the progress bar
                                if (isPlaying()) {
                                    videoPlayer.pause();
                                    needResumeStatus = true;
                                }
                                // Keep control area displayed
                                executeAnim(true);
                            } else {
                                // Adjusting the volume
                                HiLog.info(VIDEO_BOX_LABEL, "Adjusting the volume");
                                GESTURE_FLAG = GESTURE_MODIFY_VOLUME;
                            }
                            curDownPlayingTime = getCurrentPosition();
                            isDraggingPlayer = true;
                        }
                        if (GESTURE_FLAG == GESTURE_MODIFY_PROGRESS) {
                            // The control area does not disappear when the progress bar is being adjusted.
                            resetFadedOutTime();
                            if (Math.abs(dragInfo.xOffset) > MIN_OFFSET_FOR_PROGRESS_ADJUSTMENT) {
                                if (dragInfo.xOffset > 0) {
                                    if (curDownPlayingTime + PROGRESS_STEP < getDuration()) {
                                        curDownPlayingTime += PROGRESS_STEP;
                                    } else {
                                        curDownPlayingTime = getDuration();
                                    }
                                } else {
                                    if (curDownPlayingTime > PROGRESS_STEP) {
                                        curDownPlayingTime -= PROGRESS_STEP;
                                    } else {
                                        curDownPlayingTime = 0;
                                    }
                                }

                                if (curDownPlayingTime < 0) {
                                    curDownPlayingTime = 0;
                                    HiLog.error(VIDEO_BOX_LABEL, "Error curDownPlayingTime");
                                }

                                HiLog.debug(VIDEO_BOX_LABEL, "curDownPlayingTime = " + curDownPlayingTime);
                                seekTo(curDownPlayingTime);
                            }
                        } else if (GESTURE_FLAG == GESTURE_MODIFY_VOLUME) {
                            if (Math.abs(dragInfo.yOffset) > MIN_OFFSET_FOR_VOLUME_ADJUSTMENT) {
                                setVolume(dragInfo.yOffset > 0 ? -VOLUME_STEP : VOLUME_STEP);
                            }
                        } else {
                            HiLog.error(VIDEO_BOX_LABEL, "Error gesture flag");
                        }
                    }

                    @Override
                    public void onDragEnd(Component component, DragInfo dragInfo) {
                        // Resume Dragging flag
                        isDraggingPlayer = false;

                        if (GESTURE_FLAG == GESTURE_MODIFY_PROGRESS) {
                            if (needResumeStatus) {
                                start();
                            }
                            needResumeStatus = false;
                        }
                        // Resume gesture flag
                        GESTURE_FLAG = 0;
                    }

                    @Override
                    public void onDragCancel(Component component, DragInfo dragInfo) {
                    }
                });
         */
        setClickedListener(
                component -> {
                    HiLog.info(VIDEO_BOX_LABEL, "VideoPlayerView onClick");
                    // the control component shown when clicked.
                    if (topArea.getAlpha() == 0) {
                        executeAnim(true);
                        resetFadedOutTime();
                    } else {
                        if (isClickToHideControlArea) {
                            // Faded out while click again
                            executeAnim(false);
                            resetFadedInTime();
                        }
                    }

                    if (clickedListener != null) {
                        clickedListener.onClick(component);
                    }
                });
    }

    /**
     * set playback volume.
     *
     * @param volume volume offset
     */
    public void setVolume(int volume) {
        AudioManager am = new AudioManager(getContext());
        try {
            int currentVolume = am.getVolume(AudioManager.AudioVolumeType.STREAM_MUSIC);
            int maxVolume = am.getMaxVolume(AudioManager.AudioVolumeType.STREAM_MUSIC);
            int minVolume = am.getMinVolume(AudioManager.AudioVolumeType.STREAM_MUSIC);
            int targetVolume = currentVolume + volume;
            if (targetVolume < minVolume || targetVolume > maxVolume) {
                HiLog.info(VIDEO_BOX_LABEL, "The volume is at the maximum or minimum");
                // invoke this method so when volume adjusting the pop-up dialog will be shown.
                am.setVolume(AudioManager.AudioVolumeType.STREAM_MUSIC, currentVolume);
            } else {
                am.setVolume(AudioManager.AudioVolumeType.STREAM_MUSIC, targetVolume);
            }
            if (remoteControlCallback != null) {
                remoteControlCallback.onVolumeChanged(am.getVolume(AudioManager.AudioVolumeType.STREAM_MUSIC));
            }
            HiLog.info(VIDEO_BOX_LABEL, "Set target volume = " + targetVolume);
        } catch (AudioRemoteException e) {
            HiLog.error(LABEL, "setVolume error");
        }
    }

    /**
     * To assign renderView.
     *
     * @param renderView the SurfaceRenderView to bind with player
     */
    public void setRenderView(IRenderView renderView) {
        if (this.renderView != null) {
            if (videoPlayer != null) {
                videoPlayer.setSurface(null);
            }

            Component renderUIView = this.renderView.getView();
            this.renderView.removeRenderCallback(surfaceHolderCallback);
            this.renderView = null;
            removeComponent(renderUIView);
        }

        if (renderView == null) {
            return;
        }

        this.renderView = renderView;
        if (videoWidth > 0 && videoHeight > 0) {
            renderView.setVideoSize(videoWidth, videoHeight);
        }

        SurfaceProvider renderUIView = (SurfaceProvider) this.renderView.getView();

        // Resolve conflicts that do not display
        renderUIView.pinToZTop(false);
        WindowManager windowManager = WindowManager.getInstance();
        Window window = windowManager.getTopWindow().get();
        window.setTransparent(true);

        LayoutConfig config = new LayoutConfig(LayoutConfig.MATCH_PARENT, LayoutConfig.MATCH_PARENT);
        config.addRule(LayoutConfig.CENTER_IN_PARENT);

        renderUIView.setLayoutConfig(config);
        rootComp.addComponent(renderUIView, 0);
        this.renderView.addRenderCallback(surfaceHolderCallback);
    }

    /**
     * set renderView.
     */
    public void setRender() {
        SurfaceRenderView render = new SurfaceRenderView(getContext());
        setRenderView(render);
    }

    /**
     * Execute faded in or faded out animation.
     * <p>Scope: all component container in mCompContainerList
     *
     * @param isVisible true --> faded in  false --> faded out
     */
    private synchronized void executeAnim(boolean isVisible) {
        HiLog.info(VIDEO_BOX_LABEL, "executeAnim isVisible = " + isVisible);
        animatorGroup.cancel();
        animatorGroup.clear();

        Animator[] animators = new Animator[componentContainerList.size()];
        for (int i = 0; i < componentContainerList.size(); i++) {
            AnimatorProperty property = componentContainerList.get(i).createAnimatorProperty();
            property.alpha(isVisible ? VALUE_OPAQUE : VALUE_TRANSPARENT).setDuration(ALPHA_ANIMATION_DURATION);
            animators[i] = property;
        }
        animatorGroup.runParallel(animators);
        animatorGroup.start();
    }

    /**
     * Sets video path.
     *
     * @param path the path of the video.
     */
    public void setVideoPath(String path) {
        if (StringUtils.isEmpty(path)) {
            HiLog.error(VIDEO_BOX_LABEL, "Path is null");
            return;
        }
        this.videoPath = path;
        openVideo();
        for (IBaseComponentAdapter adapter : comAdapterList) {
            adapter.onVideoSourceChanged();
        }
        iSliderAdapter.ifPresent(ISliderAdapter::clearEndTime);
    }

    /**
     * Sets video path and title.
     *
     * @param path  the path of the video.
     * @param title the title of the video.
     */
    public void setVideoPathAndTitle(String path, String title) {
        iTitleAdapters.ifPresent(adapter -> adapter.onTitleChange(title));
        setVideoPath(path);
    }

    /**
     * stop video playback.
     */
    public void stopPlayback() {
        if (videoPlayer != null) {
            videoPlayer.stop();
            videoPlayer.release();

            currentState = PlayerStatus.IDLE;
            targetState = PlayerStatus.IDLE;
            // Release audio focus.
            if (audioManager != null && audioInterrupt != null) {
                audioManager.deactivateAudioInterrupt(audioInterrupt);
            }
        }
    }

    private void openVideo() {
        if (videoPath == null || surfaceHolder == null) {
            // not ready for playback just yet, will try again later
            return;
        }
        // we shouldn't clear the target state, because somebody might have
        // called start() previously
        release(false);
        // request Audio focus，Type: STREAM_MUSIC
        audioManager = new AudioManager(getContext());
        audioInterrupt = new AudioInterrupt();
        audioInterrupt.setInterruptListener(
                (type, hint) -> {
                    HiLog.debug(LABEL, "InterruptListener type = " + type + " hint = " + hint);
                    if (type == AudioInterrupt.INTERRUPT_TYPE_BEGIN && hint == AudioInterrupt.INTERRUPT_HINT_PAUSE) {
                        if (isPlaying()) {
                            pause();
                            needResumeStatus = true;
                        }
                    } else if (type == AudioInterrupt.INTERRUPT_TYPE_END
                            && (hint == AudioInterrupt.INTERRUPT_HINT_NONE
                            || hint == AudioInterrupt.INTERRUPT_HINT_RESUME)) {
                        if (needResumeStatus) {
                            start();
                            needResumeStatus = false;
                        }
                    } else {
                        HiLog.error(LABEL, "Ignoring the case");
                    }
                });
        audioManager.activateAudioInterrupt(audioInterrupt);
        try {
            videoPlayer = createPlayer(HmPlayer); // choose player implementations.
            currentBufferPercentage = 0;
            SourceFactory sourceFactory = new SourceFactory(getContext(), videoPath);
            videoPlayer.setSource(sourceFactory.getSource());

            bindSurfaceHolder(videoPlayer, surfaceHolder);
            videoPlayer.setScreenOnWhilePlaying(true);
            showLoadingComponent();
            // Prepare() method may cause anr in UI thread.
            TaskDispatcher globalTaskDispatcher = getContext().getGlobalTaskDispatcher(TaskPriority.HIGH);
            globalTaskDispatcher.asyncDispatch(() -> videoPlayer.prepare());
            currentState = PlayerStatus.PREPARING;
            targetState = PlayerStatus.PREPARING;
            HiLog.error(LABEL, "openVideo CurrentState" + currentState);
        } catch (IllegalArgumentException ex) {
            currentState = PlayerStatus.ERROR;
            targetState = PlayerStatus.ERROR;
        }
    }

    /**
     * To start polling slider progress.
     */
    private void startUpdateProgressTask() {
        TimerTask timerTask =
                new TimerTask() {
                    @Override
                    public void run() {
                        updateProgress();
                    }
                };
        timer = new Timer();
        timer.schedule(timerTask, 300, 1000);
    }

    /**
     * update playing progress.
     */
    private void updateProgress() {
        if (videoPlayer != null && videoPlayer.isPlaying()) {
            long currentTime = videoPlayer.getCurrentPosition();
            // would not update progress when user is dragging.
            if (currentTime > 0 && !isDraggingSlider) {
                int progress = getProgressByCurTime(currentTime);
                iSliderAdapter.ifPresent(slider -> slider.onMediaProgressChanged(currentTime, progress));

                if (remoteControlCallback != null) {
                    remoteControlCallback.onProgressChanged(videoPlayer.getDuration(), progress);
                }
            }
        }

        // all controller component will disappeared in 5 seconds.
        float epsilon = 0.001f;
        if (Math.abs(bottomArea.getAlpha() - VALUE_OPAQUE) < epsilon) {
            count++;
            if (count > COMPONENT_DISPLAY_TIME) {
                executeAnim(false);
                resetFadedOutTime();
            }
        }
    }

    /**
     * Obtains progress by current time
     *
     * @param curTime unit:milliseconds
     * @return progress scope 0~100
     */
    private int getProgressByCurTime(long curTime) {
        return (int) (curTime * MAX_PROGRESS_VALUE / videoPlayer.getDuration());
    }

    /**
     * Reset fade out animation timing.
     */
    public void resetFadedOutTime() {
        count = 0;
    }

    /**
     * Reset fade in animation timing.
     */
    public void resetFadedInTime() {
        count = COMPONENT_DISPLAY_TIME;
    }

    /**
     * register callback in VideoPlayAbilitySlice
     *
     * @param listener callback logic
     */
    public void setPlayerOnPreparedListener(IVideoPlayer.PlayerPreparedListener listener) {
        playerPreparedListener = listener;
    }

    /**
     * Register a callback to be invoked when the end of a media file
     * has been reached during playback.
     *
     * @param listener The callback that will be run
     */
    public void setOnCompletionListener(IVideoPlayer.PlaybackCompleteListener listener) {
        playbackCompleteListener = listener;
    }

    /**
     * Register a callback to be invoked when an error occurs
     * during playback or setup.  If no listener is specified,
     * or if the listener returned false, VideoView will inform
     * the user of any errors.
     *
     * @param listener The callback that will be run
     */
    public void setErrorListener(IVideoPlayer.ErrorListener listener) {
        onErrorListener = listener;
    }

    private void bindSurfaceHolder(IVideoPlayer vp, IRenderView.ISurfaceHolder holder) {
        if (vp == null) {
            return;
        }

        if (holder == null) {
            return;
        }

        holder.bindToMediaPlayer(vp);
    }

    /**
     * invoked when surface onDestroy.
     */
    public void releaseWithoutStop() {
        if (videoPlayer != null) {
            videoPlayer.setSurface(null);
        }
    }

    /**
     * release the media player in any state
     *
     * @param clearTargetState true: clear state
     */
    public void release(boolean clearTargetState) {
        if (videoPlayer != null) {
            videoPlayer.setPlayerListeners(null, null, null, null, null, null, null);
            videoPlayer.reset();
            videoPlayer.release();
            videoPlayer = null;
            currentState = PlayerStatus.IDLE;
            if (clearTargetState) {
                targetState = PlayerStatus.IDLE;
            }
            // Release audio focus.
            if (audioManager != null && audioInterrupt != null) {
                audioInterrupt.setInterruptListener(null);
                audioManager.deactivateAudioInterrupt(audioInterrupt);
            }
        }

        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * start playing video.
     */
    public void start() {
        HiLog.error(LABEL, "click start playback");
        if (videoPlayer != null) {
            videoPlayer.start();
            currentState = PlayerStatus.PLAY;
        }
        iPlaybackButtonAdapter.ifPresent(button -> button.onPlayStatusChange(PlayerStatus.PLAY));
        targetState = PlayerStatus.PLAY;
        if (remoteControlCallback != null) {
            remoteControlCallback.onPlayingStatusChanged(true);
        }
    }

    /**
     * To obtain current video playback speed.
     *
     * @return current video playback speed.
     */
    public float getPlaybackSpeed() {
        return videoPlayer.getPlaybackSpeed();
    }

    /**
     * set playback speed.
     *
     * @param playbackSpeed playback speed
     * @return whether playback speed is successful set.
     */
    public void setPlaybackSpeed(float playbackSpeed) {
        lastPlaybackSpeed = playbackSpeed;
        videoPlayer.setPlaybackSpeed(playbackSpeed);
    }

    /**
     * To obtain previous video playback speed.
     *
     * @return previous video playback speed.
     */
    public float getLastPlaybackSpeed() {
        if (lastPlaybackSpeed == 0) {
            return DEFAULT_PLAYBACK_SPEED;
        }
        return lastPlaybackSpeed;
    }

    /**
     * Pause playback.
     */
    public void pause() {
        if (videoPlayer != null) {
            if (videoPlayer.isPlaying()) {
                videoPlayer.pause();
                currentState = PlayerStatus.PAUSE;
            }
            iPlaybackButtonAdapter.ifPresent(button -> button.onPlayStatusChange(PlayerStatus.PAUSE));
            targetState = PlayerStatus.PAUSE;
            if (remoteControlCallback != null) {
                remoteControlCallback.onPlayingStatusChanged(false);
            }
        }
    }

    /**
     * suspend playback, currently not use.
     */
    public void suspend() {
        release(false);
    }

    /**
     * resume playback.
     */
    public void resume() {
        openVideo();
    }

    /**
     * To obtain video duration.
     *
     * @return video duration.
     */
    public long getDuration() {
        if (videoPlayer != null) {
            return videoPlayer.getDuration();
        }
        return -1;
    }

    /**
     * To obtain current playback position.
     *
     * @return current playback position.
     */
    public long getCurrentPosition() {
        if (videoPlayer != null) {
            return videoPlayer.getCurrentPosition();
        }
        return 0;
    }

    /**
     * playback position seeking.
     *
     * @param milliseconds willing video position, unit: milliseconds.
     */
    public void seekTo(long milliseconds) {
        if (videoPlayer != null) {
            videoPlayer.rewindTo(milliseconds * 1000);
            if (!videoPlayer.isPlaying()) {
                iSliderAdapter.ifPresent(
                        slider -> slider.onMediaProgressChanged(milliseconds, getProgressByCurTime(milliseconds)));
            }
            seekWhenPrepared = 0;
        } else {
            seekWhenPrepared = milliseconds;
        }
    }

    /**
     * Obtains {@link VideoPlayerView#seekWhenPrepared} value.
     *
     * @return recording the seek position while preparing.
     */
    public long getSeekWhenPrepared() {
        return seekWhenPrepared;
    }

    /**
     * Set {@link VideoPlayerView#seekWhenPrepared} value.
     *
     * @param seekWhenPrepared unit: milliseconds.
     */
    public void setSeekWhenPrepared(long seekWhenPrepared) {
        this.seekWhenPrepared = seekWhenPrepared;
    }

    /**
     * judging whether video is playing.
     *
     * @return whether video is playing.
     */
    public boolean isPlaying() {
        return videoPlayer != null && videoPlayer.isPlaying();
    }

    /**
     * return current buffer percentage.
     *
     * @return current buffer percentage
     */
    public int getBufferPercentage() {
        if (videoPlayer != null) {
            return currentBufferPercentage;
        }
        return 0;
    }

    /**
     * Create player instance and set listener
     *
     * @param playerType HmPlayer or other player implementation.
     * @return IVideoPlayer instance
     */
    public IVideoPlayer createPlayer(int playerType) {
        IVideoPlayer iVideoPlayer = null;
        switch (playerType) {
            case HmPlayer:
                iVideoPlayer = new HmPlayerAdapter(getContext());
                break;
            default:
                // you can add your player implementation here in this way: videoPlayer = new IjkVideoPlayerAdapter();
                break;
        }
        if (iVideoPlayer != null) {
            iVideoPlayer.setPlayerListeners(
                    playerOnPreparedListener,
                    playbackCompleteListener1,
                    rewindToCompleteListener,
                    messageListener,
                    errorListener1,
                    videoSizeChangedListener,
                    bufferChangedListener1);
        } else {
            HiLog.error(VIDEO_BOX_LABEL, "videoPlayer is null when set player listeners");
        }

        return iVideoPlayer;
    }

    /**
     * Show loading component.
     */
    public void showLoadingComponent() {
        getContext().getUITaskDispatcher().asyncDispatch(() -> {
            loadingComp.setVisibility(VISIBLE);
        });
    }

    /**
     * Hide loading component.
     */
    public void hideLoadingComponent() {
        getContext().getUITaskDispatcher().asyncDispatch(() -> {
            loadingComp.setVisibility(HIDE);
        });
    }

    /**
     * To add component
     *
     * @param baseComponentAdapter basic component adapter.
     * @param area                 the area where component will be add to.
     */
    public void addComponent(IBaseComponentAdapter baseComponentAdapter, VideoBoxArea area) {
        this.addComponent(baseComponentAdapter, area, AttrHelper.vp2px(DEFAULT_MARGIN_VALUE_UNIT_VP, getContext()));
    }

    /**
     * 添加组件
     *
     * @param baseComponentAdapter basic component adapter.
     * @param area                 the area where component will be add to.
     * @param margin               margin.
     */
    public void addComponent(IBaseComponentAdapter baseComponentAdapter, VideoBoxArea area, int margin) {
        if (baseComponentAdapter != null) {
            comAdapterList.add(baseComponentAdapter);
            DirectionalLayout.LayoutConfig config =
                    baseComponentAdapter.initLayoutConfig() == null
                            ? new DirectionalLayout.LayoutConfig(ICON_WIDTH, ICON_WIDTH)
                            : baseComponentAdapter.initLayoutConfig();
            setMarginByArea(area, margin, config);
            Component targetComponent = baseComponentAdapter.initComponent();
            if (targetComponent != null) {
                baseComponentAdapter.onOrientationChanged(
                        ScreenUtils.isPortrait(getContext())
                                ? AbilityInfo.DisplayOrientation.PORTRAIT
                                : AbilityInfo.DisplayOrientation.LANDSCAPE,
                        bottomArea,
                        aboveBottomArea);
                getArea(area).addComponent(targetComponent, config);
                targetComponent.setClickedListener(
                        component -> {
                            resetFadedOutTime();
                            baseComponentAdapter.onClick(component);
                        });
            }
        }
    }

    /**
     * set margins of different direction according to areas:
     * left margin of top and bottom area will be set.
     * up margin of left and right area will be set.
     *
     * @param area   Area{@link VideoBoxArea}
     * @param margin Unit:px
     * @param config config which need to set margin
     */
    private void setMarginByArea(VideoBoxArea area, int margin, DirectionalLayout.LayoutConfig config) {
        switch (area) {
            case TOP:
            case BOTTOM:
                config.alignment = LayoutAlignment.VERTICAL_CENTER;
                config.setMarginLeft(margin);
                break;
            case LEFT:
            case RIGHT:
                config.alignment = LayoutAlignment.HORIZONTAL_CENTER;
                config.setMarginTop(margin);
                break;
            default:
                HiLog.error(VIDEO_BOX_LABEL, "Please check case in setMarginByArea()");
                break;
        }
    }

    /**
     * add playback button component
     *
     * @param playbackButtonAdapter implementation of IPlaybackButtonAdapter.
     * @param area                  the area that playback button will be add to.
     */
    public void addPlaybackButton(IPlaybackButtonAdapter playbackButtonAdapter, VideoBoxArea area) {
        iPlaybackButtonAdapter = Optional.ofNullable(playbackButtonAdapter);
        iPlaybackButtonAdapter.ifPresent(
                adapter -> {
                    comAdapterList.add(playbackButtonAdapter);
                    // add Component at certain area.
                    DirectionalLayout componentContainer = getArea(area);
                    Component component = adapter.initComponent();
                    if (component != null) {
                        component.setClickedListener(
                                comp -> {
                                    HiLog.info(VIDEO_BOX_LABEL, "Playback button onClick");
                                    resetFadedOutTime();
                                    if (videoPlayer.isPlaying()) {
                                        pause();
                                    } else {
                                        start();
                                    }
                                    adapter.onClick(comp);
                                });
                        DirectionalLayout.LayoutConfig config =
                                adapter.initLayoutConfig() == null
                                        ? new DirectionalLayout.LayoutConfig(
                                        ICON_WIDTH, ICON_WIDTH, LayoutAlignment.VERTICAL_CENTER, 0)
                                        : adapter.initLayoutConfig();
                        adapter.onOrientationChanged(
                                ScreenUtils.isPortrait(getContext())
                                        ? AbilityInfo.DisplayOrientation.PORTRAIT
                                        : AbilityInfo.DisplayOrientation.LANDSCAPE,
                                bottomArea,
                                aboveBottomArea);
                        componentContainer.addComponent(component, config);
                    } else {
                        HiLog.error(
                                VIDEO_BOX_LABEL,
                                "Playback button is null, please check the return value of the initComponent()");
                    }
                });
    }

    /**
     * To obtain certain area ComponentContainer
     *
     * @param area {@link VideoBoxArea}
     * @return the certain area ComponentContainer
     */
    private DirectionalLayout getArea(VideoBoxArea area) {
        switch (area) {
            case TOP:
                return topArea;
            case BOTTOM:
                return bottomArea;
            case LEFT:
                return leftArea;
            case RIGHT:
                return rightArea;
            case ABOVE_BOTTOM:
                return aboveBottomArea;
            default:
                HiLog.error(VIDEO_BOX_LABEL, "Error video box area");
                return topArea;
        }
    }

    /**
     * To add Slider Component.
     *
     * @param sliderAdapter SliderAdapter.
     * @param area          area that component will be added.
     * @param margin        set margins of different direction according to areas:
     *                      left margin of top and bottom area will be set.
     *                      up margin of left and right area will be set.
     */
    public void addSeekBar(ISliderAdapter sliderAdapter, VideoBoxArea area, int margin) {
        iSliderAdapter = Optional.ofNullable(sliderAdapter);
        iSliderAdapter.ifPresent(
                adapter -> {
                    comAdapterList.add(sliderAdapter);
                    DirectionalLayout componentContainer = getArea(area);
                    Component slider = adapter.initComponent();
                    if (slider != null) {
                        adapter.onValueChanged(
                                new Slider.ValueChangedListener() {
                                    @Override
                                    public void onProgressUpdated(Slider slider, int progress, boolean fromUser) {
                                        // The control area does not disappear when the progress bar is being adjusted.
                                        if (fromUser) {
                                            resetFadedOutTime();
                                        }
                                    }

                                    @Override
                                    public void onTouchStart(Slider slider) {
                                        isDraggingSlider = true;

                                        HiLog.info(LABEL_LOG, "after onTouchStart");
                                    }

                                    @Override
                                    public void onTouchEnd(Slider slider) {
                                        int percent = slider.getProgress();
                                        HiLog.debug(VIDEO_BOX_LABEL, "End touch while progress is " + percent);
                                        long duration = videoPlayer.getDuration();
                                        long newPosition = duration * percent * 10;
                                        videoPlayer.rewindTo(newPosition);
                                        isDraggingSlider = false;

                                        HiLog.info(LABEL_LOG, "after onTouchEnd");
                                    }
                                });
                        DirectionalLayout.LayoutConfig config =
                                adapter.initLayoutConfig() == null
                                        ? new DirectionalLayout.LayoutConfig(
                                        ComponentContainer.LayoutConfig.MATCH_PARENT,
                                        ComponentContainer.LayoutConfig.MATCH_CONTENT,
                                        LayoutAlignment.VERTICAL_CENTER,
                                        1)
                                        : adapter.initLayoutConfig();
                        setMarginByArea(area, margin, config);
                        componentContainer.addComponent(slider, config);
                        adapter.onOrientationChanged(
                                ScreenUtils.isPortrait(getContext())
                                        ? AbilityInfo.DisplayOrientation.PORTRAIT
                                        : AbilityInfo.DisplayOrientation.LANDSCAPE,
                                bottomArea,
                                aboveBottomArea);
                    }
                });
    }

    /**
     * Add title component
     *
     * @param titleAdapter ITitleAdapter{@link ITitleAdapter} impl
     * @param area         Area division of the VideoPlayerView
     */
    public void addTitle(ITitleAdapter titleAdapter, VideoBoxArea area) {
        iTitleAdapters = Optional.ofNullable(titleAdapter);
        iTitleAdapters.ifPresent(
                adapter -> {
                    comAdapterList.add(titleAdapter);
                    DirectionalLayout componentContainer = getArea(area);
                    Text title = adapter.initComponent();
                    if (title != null) {
                        title.setClickedListener(adapter::onClick);
                        DirectionalLayout.LayoutConfig config =
                                adapter.initLayoutConfig() == null
                                        ? new DirectionalLayout.LayoutConfig(
                                        0,
                                        ComponentContainer.LayoutConfig.MATCH_CONTENT,
                                        LayoutAlignment.VERTICAL_CENTER,
                                        1)
                                        : adapter.initLayoutConfig();
                        componentContainer.addComponent(title, config);
                        adapter.onOrientationChanged(
                                ScreenUtils.isPortrait(getContext())
                                        ? AbilityInfo.DisplayOrientation.PORTRAIT
                                        : AbilityInfo.DisplayOrientation.LANDSCAPE,
                                bottomArea,
                                aboveBottomArea);
                    }
                });
    }

    /**
     * Change Video size and playback window size when orientation changed.
     *
     * @param displayOrientation orientation
     */
    public void onOrientationChanged(AbilityInfo.DisplayOrientation displayOrientation) {
        if (renderView != null) {
            measurePlaybackWindow();
            HiLog.debug(VIDEO_BOX_LABEL, "After measure with = " + surfaceWidth + " height = " + surfaceHeight);
            setComponentSize(surfaceWidth, surfaceHeight);
            renderView.setPlaybackWindowSize(surfaceWidth, surfaceHeight);
            renderView.setVideoSize(videoWidth, videoHeight);
        }

        for (IBaseComponentAdapter adapter : comAdapterList) {
            adapter.onOrientationChanged(displayOrientation, bottomArea, aboveBottomArea);
        }
    }

    /**
     * Set custom click listener.
     *
     * @param listener custom click listener.
     */
    public void setVideoPlayerViewClickedListener(VideoPlayerViewClickedListener listener) {
        this.clickedListener = listener;
    }

    /**
     * Register progress changed callback
     *
     * @param callback progress callback which will be invoked when slider progress changed.
     */
    public void registerRemoteControlCallback(RemoteControlCallback callback) {
        this.remoteControlCallback = callback;
    }

    /**
     * Unregister progress changed callback
     */
    public void unregisterRemoteControlCallback() {
        this.remoteControlCallback = null;
    }

    /**
     * Obtains {@link VideoPlayerView#isClickToHideControlArea} status.
     *
     * @return Is click to hide the control area?
     */
    public boolean isClickToHideControlArea() {
        return isClickToHideControlArea;
    }

    /**
     * Set {@link VideoPlayerView#isClickToHideControlArea} status.
     *
     * @param clickToHideControlArea If equal to false,click outside can not hide the control area.
     */
    public void setClickToHideControlArea(boolean clickToHideControlArea) {
        this.isClickToHideControlArea = clickToHideControlArea;
    }

    /**
     * Interface of custom click listener.
     */
    public interface VideoPlayerViewClickedListener {
        void onClick(Component component);
    }

    /**
     * Interface of callback when progress updates.
     */
    public interface RemoteControlCallback {
        /**
         * This method is called when the progress changes.
         *
         * @param totalTime unit:milliseconds
         * @param progress  scope:[0,100]
         */
        void onProgressChanged(long totalTime, int progress);

        /**
         * This method is called when the playback status changes.
         *
         * @param isPlaying isPlaying
         */
        void onPlayingStatusChanged(boolean isPlaying);

        /**
         * This method is called when adjust the volume to mute.
         *
         * @param volume volume
         */
        void onVolumeChanged(int volume);
    }
}
