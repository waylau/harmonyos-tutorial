/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.view;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.api.IVideoInfoBinding;
import com.waylau.hmos.shortvideo.api.IVideoPlayer;
import com.waylau.hmos.shortvideo.listener.StatuChangeListener;
import com.waylau.hmos.shortvideo.bean.*;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.constant.PlayerStatusEnum;
import com.waylau.hmos.shortvideo.store.MeFavoriteVideoInfoRepository;
import com.waylau.hmos.shortvideo.store.MeThumbsupVideoInfoRepository;
import com.waylau.hmos.shortvideo.store.UserFollowInfoRepository;
import com.waylau.hmos.shortvideo.util.CommonUtil;
import com.waylau.hmos.shortvideo.util.DateUtil;
import com.waylau.hmos.shortvideo.util.LogUtil;

import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.app.Context;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;

/**
 * PlayerController
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class PlayerController extends ComponentContainer implements IVideoInfoBinding {
    private static final String TAG = PlayerController.class.getSimpleName();
    private static final int THUMB_RED = 255;
    private static final int THUMB_GREEN = 255;
    private static final int THUMB_BLUE = 240;
    private static final int THUMB_WIDTH = 40;
    private static final int THUMB_HEIGHT = 40;
    private static final int THUMB_RADIUS = 20;
    private static final int CONTROLLER_HIDE_DLEY_TIME = 5000;
    private static final int PROGRESS_RUNNING_TIME = 1000;
    private Context context;
    private boolean isDragMode = false;
    private IVideoPlayer videoPlayer;
    private VideoInfo videoInfo;
    private UserInfo userInfo;
    private DirectionalLayout bottomLayout;
    private Image imagePlayToogle;
    private Slider sliderProgress;
    private Text textCurrentTime;
    private Text textTotleTime;
    private Image imagePortrait;
    private Text textAuthor;
    private Text textContent;
    private Button buttonFollow;
    private Image imageThumbsup;
    private Image imageComment;
    private Image imageFavorite;
    private Text textCommentCount;
    private Text textThumbsUpCount;
    private Text textFavoriteCount;

    private ControllerHandler controllerHandler;
    private StatuChangeListener mStatuChangeListener = new StatuChangeListener() {
        @Override
        public void statuCallback(PlayerStatusEnum statu) {
            mContext.getUITaskDispatcher().asyncDispatch(() -> {
                switch (statu) {
                    case PREPARING:
                        imagePlayToogle.setClickable(false);
                        sliderProgress.setEnabled(false);
                        sliderProgress.setProgressValue(0);
                        break;
                    case PREPARED:
                        sliderProgress.setMaxValue(videoPlayer.getDuration());
                        textTotleTime.setText(DateUtil.msToString(videoPlayer.getDuration()));
                        break;
                    case PLAY:
                        showController(false);
                        imagePlayToogle.setPixelMap(ResourceTable.Media_ic_public_pause_norm);
                        imagePlayToogle.setClickable(true);
                        sliderProgress.setEnabled(true);
                        break;
                    case PAUSE:
                        imagePlayToogle.setPixelMap(ResourceTable.Media_ic_public_play);
                        break;
                    case STOP:
                    case COMPLETE:
                        imagePlayToogle.setPixelMap(ResourceTable.Media_ic_public_refresh);
                        sliderProgress.setEnabled(false);
                        break;
                    default:
                        break;
                }
            });
        }
    };

    /**
     * constructor of SimplePlayerController
     *
     * @param context context
     */
    public PlayerController(Context context) {
        this(context, null);
    }

    /**
     * constructor of SimplePlayerController
     *
     * @param context context
     * @param attrSet attSet
     */
    public PlayerController(Context context, AttrSet attrSet) {
        this(context, attrSet, null);
    }

    /**
     * constructor of SimplePlayerController
     *
     * @param context context
     * @param attrSet attSet
     * @param styleName styleName
     */
    public PlayerController(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        this.context = context;
        createHandler();
        initView();
        initListener();
    }

    private void createHandler() {
        EventRunner runner = EventRunner.create(true);
        if (runner == null) {
            return;
        }
        controllerHandler = new ControllerHandler(runner);
    }

    private void initView() {
        Component playerController =
            LayoutScatter.getInstance(mContext).parse(ResourceTable.Layout_video_player_controller_layout, null, false);
        addComponent(playerController);

        bottomLayout = (DirectionalLayout)playerController.findComponentById(ResourceTable.Id_controller_bottom_layout);

        imagePlayToogle = (Image)playerController.findComponentById(ResourceTable.Id_play_controller);

        sliderProgress = (Slider)playerController.findComponentById(ResourceTable.Id_progress);

        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setRgbColor(new RgbColor(THUMB_RED, THUMB_GREEN, THUMB_BLUE));
        shapeElement.setBounds(0, 0, THUMB_WIDTH, THUMB_HEIGHT);
        shapeElement.setCornerRadius(THUMB_RADIUS);
        sliderProgress.setThumbElement(shapeElement);

        textCurrentTime = (Text)playerController.findComponentById(ResourceTable.Id_current_time);
        textTotleTime = (Text)playerController.findComponentById(ResourceTable.Id_end_time);

        imagePortrait = (Image)playerController.findComponentById(ResourceTable.Id_image_portrait);
        textAuthor = (Text)playerController.findComponentById(ResourceTable.Id_text_author);
        textContent = (Text)playerController.findComponentById(ResourceTable.Id_text_content);
        buttonFollow = (Button) playerController.findComponentById(ResourceTable.Id_button_follow);
        imageThumbsup = (Image)playerController.findComponentById(ResourceTable.Id_image_thumbsup);
        imageComment = (Image)playerController.findComponentById(ResourceTable.Id_image_comment);
        imageFavorite = (Image)playerController.findComponentById(ResourceTable.Id_image_favorit);
        textThumbsUpCount = (Text)playerController.findComponentById(ResourceTable.Id_text_thumbs_up_count);
        textCommentCount = (Text)playerController.findComponentById(ResourceTable.Id_text_comment_count);
        textFavoriteCount = (Text)playerController.findComponentById(ResourceTable.Id_text_favorit_count);
    }

    private void initListener() {
        // 设置可拖动
        bottomLayout.setTouchEventListener((component, touchEvent) -> true);
    }

    private void initPlayListener() {
        videoPlayer.addPlayerStatuCallback(mStatuChangeListener);
        imagePlayToogle.setClickedListener(component -> {
            if (videoPlayer.isPlaying()) {
                videoPlayer.pause();
            } else {
                if (videoPlayer.getPlayerStatus() == PlayerStatusEnum.STOP) {
                    videoPlayer.replay();
                } else {
                    videoPlayer.resume();
                }
            }
        });
        sliderProgress.setValueChangedListener(new Slider.ValueChangedListener() {
            @Override
            public void onProgressUpdated(Slider slider, int value, boolean isB) {
                mContext.getUITaskDispatcher().asyncDispatch(() -> textCurrentTime.setText(DateUtil.msToString(value)));
            }

            @Override
            public void onTouchStart(Slider slider) {
                isDragMode = true;
                controllerHandler.removeEvent(Constants.PLAYER_PROGRESS_RUNNING, EventHandler.Priority.IMMEDIATE);
            }

            @Override
            public void onTouchEnd(Slider slider) {
                isDragMode = false;
                if (slider.getProgress() == videoPlayer.getDuration()) {
                    videoPlayer.stop();
                } else {
                    videoPlayer.rewindTo(getBasicTransTime(slider.getProgress()));
                }
            }
        });

        buttonFollow.setClickedListener(component -> {
            LogUtil.info(TAG, "imageFollow Clicked");

            // 更新值
            if (videoInfo.isFollow()) {
                videoInfo.setFollow(Boolean.FALSE);
            } else {
                videoInfo.setFollow(Boolean.TRUE);
            }
            
            // 更新"关注"视图
            setFollowButtonStyle(buttonFollow, videoInfo.isFollow());

            // 更新存储
            setFollowRepository(videoInfo, videoInfo.isFollow());
        });

        imageThumbsup.setClickedListener(component -> {
            LogUtil.info(TAG, "imageThumbsup Clicked");

            // 更新值
            if (videoInfo.isThumbsUp()) {
                videoInfo.setThumbsUp(Boolean.FALSE);
                videoInfo.setThumbsUpCount(videoInfo.getThumbsUpCount() - 1);
            } else {
                videoInfo.setThumbsUp(Boolean.TRUE);
                videoInfo.setThumbsUpCount(videoInfo.getThumbsUpCount() + 1);
            }

            // 更新视图
            setThumbsupImageStyle(imageThumbsup, videoInfo.isThumbsUp());

            textThumbsUpCount.setText(videoInfo.getThumbsUpCount() + "");

            // 更新存储
            setThumbsupRepository(videoInfo, videoInfo.isThumbsUp());
        });

        imageFavorite.setClickedListener(component -> {
            LogUtil.info(TAG, "imageFavorit Clicked");

            // 更新值
            if (videoInfo.isFavorite()) {
                videoInfo.setFavorite(Boolean.FALSE);
                videoInfo.setFavoriteCount(videoInfo.getFavoriteCount() - 1);
            } else {
                videoInfo.setFavorite(Boolean.TRUE);
                videoInfo.setFavoriteCount(videoInfo.getFavoriteCount() + 1);
            }

            // 更新视图
            setFavoriteImageStyle(imageFavorite, videoInfo.isFavorite());

            textFavoriteCount.setText(videoInfo.getFavoriteCount() + "");

            // 更新存储
            setFavoriteRepository(videoInfo, videoInfo.isThumbsUp());
        });
    }

    private void setFollowButtonStyle(Button button, boolean isFollow) {
        if (isFollow) {
            button.setText("取关");
            button.setBackground(new ShapeElement(mContext, ResourceTable.Graphic_background_button_gray));
        } else {
            button.setText("关注");
            button.setBackground(new ShapeElement(mContext, ResourceTable.Graphic_background_button_red));
        }
    }

    private void setFavoriteImageStyle(Image image, boolean isFavorite) {
        if (isFavorite) {
            image.setPixelMap(ResourceTable.Media_ic_public_highlightsed);
        } else {
            image.setPixelMap(ResourceTable.Media_ic_public_highlights);
        }
    }

    private void setThumbsupImageStyle(Image image, boolean isThumbsUp) {
        if (isThumbsUp) {
            image.setPixelMap(ResourceTable.Media_ic_public_favor_filled);
        } else {
            image.setPixelMap(ResourceTable.Media_ic_public_favor);
        }
    }

    private void setThumbsupRepository(VideoInfo videoInfo, boolean isThumbsUp) {
        MeThumbsupVideoInfo meThumbsupVideoInfo = new MeThumbsupVideoInfo();
        meThumbsupVideoInfo.copy(videoInfo);
        meThumbsupVideoInfo.setUsername(userInfo.getUsername());

        if (isThumbsUp) {
            MeThumbsupVideoInfoRepository.insert(meThumbsupVideoInfo);
        } else {
            MeThumbsupVideoInfoRepository.delete(meThumbsupVideoInfo);
        }
    }

    private void setFavoriteRepository(VideoInfo videoInfo, boolean isFavorite) {
        MeFavoriteVideoInfo meFavoriteVideoInfo = new MeFavoriteVideoInfo();
        meFavoriteVideoInfo.copy(videoInfo);
        meFavoriteVideoInfo.setUsername(userInfo.getUsername());

        if (isFavorite) {
            MeFavoriteVideoInfoRepository.insert(meFavoriteVideoInfo);
        } else {
            MeFavoriteVideoInfoRepository.delete(meFavoriteVideoInfo);
        }
    }

    private void setFollowRepository(VideoInfo videoInfo, boolean isFollow) {
        UserFollowInfo userFollowInfo = new  UserFollowInfo();
        userFollowInfo.copy(videoInfo);
        userFollowInfo.setUsername(userInfo.getUsername());

        if (isFollow) {
            UserFollowInfoRepository.insert(userFollowInfo);
        } else {
            UserFollowInfoRepository.delete(userFollowInfo);
        }
    }

    private int getBasicTransTime(int currentTime) {
        return currentTime / PROGRESS_RUNNING_TIME * PROGRESS_RUNNING_TIME;
    }

    /**
     * 进度条跟随播放进度移动
     *
     * @param isAutoHide isAutoHide
     */
    public void showController(boolean isAutoHide) {
        controllerHandler.sendEvent(Constants.PLAYER_CONTROLLER_SHOW, EventHandler.Priority.HIGH);
        if (isAutoHide) {
            hideController(CONTROLLER_HIDE_DLEY_TIME);
        } else {
            controllerHandler.removeEvent(Constants.PLAYER_CONTROLLER_HIDE);
        }
    }

    /**
     * hideController of PlayerController
     *
     * @param delay delay
     */
    public void hideController(int delay) {
        controllerHandler.removeEvent(Constants.PLAYER_CONTROLLER_HIDE);
        controllerHandler.sendEvent(Constants.PLAYER_CONTROLLER_HIDE, delay, EventHandler.Priority.HIGH);
    }

    @Override
    public void bind(ViderPlayerInfo viderPlayerInfo) {
        videoInfo = viderPlayerInfo.getVideoInfo();
        videoPlayer = viderPlayerInfo.getVideoPlayer();
        userInfo = viderPlayerInfo.getUserInfo();

        initPlayListener();

        initData();
    }

    private void initData() {
        // 更改显示
        imagePortrait.setPixelMap(CommonUtil.getImageSource(this.context, videoInfo.getPortraitPath()));
        // 设置圆角
        imagePortrait.setCornerRadius(Constants.NUMBER_FLOAT_100);

        textAuthor.setText(videoInfo.getAuthor());
        textContent.setText(videoInfo.getContent());
        textThumbsUpCount.setText(videoInfo.getThumbsUpCount() + "");
        textCommentCount.setText(videoInfo.getCommentCount() + "");
        textFavoriteCount.setText(videoInfo.getFavoriteCount() + "");

        // 更新视图
        setThumbsupImageStyle(imageThumbsup, videoInfo.isThumbsUp());

        // 更新视图
        setFavoriteImageStyle(imageFavorite, videoInfo.isFavorite());

        // 更新"关注"视图
        setFollowButtonStyle(buttonFollow, videoInfo.isFollow());
    }

    @Override
    public void unbind() {
        controllerHandler.removeAllEvent();
        controllerHandler = null;
    }

    /**
     * ControllerHandler
     */
    private class ControllerHandler extends EventHandler {
        private int currentPosition;

        private ControllerHandler(EventRunner runner) {
            super(runner);
        }

        @Override
        public void processEvent(InnerEvent event) {
            super.processEvent(event);
            if (event == null) {
                return;
            }
            switch (event.eventId) {
                case Constants.PLAYER_PROGRESS_RUNNING:
                    if (videoPlayer != null && videoPlayer.isPlaying() && !isDragMode) {
                        currentPosition = videoPlayer.getCurrentPosition();
                        while (currentPosition < PROGRESS_RUNNING_TIME) {
                            currentPosition = videoPlayer.getCurrentPosition();
                        }
                        mContext.getUITaskDispatcher().asyncDispatch(() -> {
                            sliderProgress.setProgressValue(currentPosition);
                            textCurrentTime.setText(DateUtil.msToString(currentPosition));
                        });
                        controllerHandler.sendEvent(Constants.PLAYER_PROGRESS_RUNNING, PROGRESS_RUNNING_TIME,
                            Priority.HIGH);
                    }
                    break;
                case Constants.PLAYER_CONTROLLER_HIDE:
                    mContext.getUITaskDispatcher().asyncDispatch(() -> setVisibility(INVISIBLE));
                    controllerHandler.removeEvent(Constants.PLAYER_PROGRESS_RUNNING);
                    break;
                case Constants.PLAYER_CONTROLLER_SHOW:
                    controllerHandler.removeEvent(Constants.PLAYER_PROGRESS_RUNNING);
                    controllerHandler.sendEvent(Constants.PLAYER_PROGRESS_RUNNING, Priority.IMMEDIATE);
                    mContext.getUITaskDispatcher().asyncDispatch(() -> {
                        if (getVisibility() != VISIBLE) {
                            setVisibility(VISIBLE);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }
}