package com.waylau.hmos.videoplayer.slice;

import com.waylau.hmos.videoplayer.Video;
import com.waylau.hmos.videoplayer.PlayerStateEnum;
import com.waylau.hmos.videoplayer.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.components.surfaceprovider.SurfaceProvider;
import ohos.agp.graphics.Surface;
import ohos.agp.graphics.SurfaceOps;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.global.resource.RawFileDescriptor;

import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.common.Source;
import ohos.media.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private Text currentName; // 当前视频名称
    private int currentIndex = 0; // 当前视频索引
    private Slider playerProgress;
    private List<Video> videoList; // 视频列表
    private SurfaceProvider surfaceProvider;
    private Player player;
    private DirectionalLayout playerLayout;
    private TaskDispatcher dispatcher;
    private Surface surface;
    private Button buttonQuery;
    private Button buttonPlayPause;
    private Button buttonNext;
    private PlayerStateEnum playerState = PlayerStateEnum.STOP;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发访问数据
        buttonQuery = (Button) findComponentById(ResourceTable.Id_button_previous);
        buttonQuery.setClickedListener(listener -> {
            try {
                this.doPrevious();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        buttonPlayPause = (Button) findComponentById(ResourceTable.Id_button_play_pause);
        buttonPlayPause.setClickedListener(listener -> {
            try {
                this.doPlayPause();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        buttonNext = (Button) findComponentById(ResourceTable.Id_button_next);
        buttonNext.setClickedListener(listener -> {
            try {
                this.doNext();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        currentName = (Text) findComponentById(ResourceTable.Id_text_current_name);

        // 跑马灯效果
        currentName.setTruncationMode(Text.TruncationMode.AUTO_SCROLLING);
        currentName.setAutoScrollingCount(Text.AUTO_SCROLLING_FOREVER);
        currentName.startAutoScrolling();

        playerProgress = (Slider) findComponentById(ResourceTable.Id_player_progress);

        playerLayout
                = (DirectionalLayout) findComponentById(ResourceTable.Id_layout_player);

        player = new Player(this);

        dispatcher = getUITaskDispatcher();

        // 初始化SurfaceProvider
        initSurfaceProvider();

        // 初始化视频列表
        initData();
    }

    @Override
    public void onStop() {
        super.onStop();

        // 关闭、释放资源
        release();
    }

    private void release() {
        HiLog.info(LABEL_LOG, "release");

        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    private void initSurfaceProvider() {
        HiLog.info(LABEL_LOG, "before initSurfaceProvider");

        surfaceProvider = new SurfaceProvider(this);
        surfaceProvider.getSurfaceOps().get().addCallback(new VideoSurfaceCallback());
        surfaceProvider.pinToZTop(true);
        surfaceProvider.setWidth(ComponentContainer.LayoutConfig.MATCH_CONTENT);
        surfaceProvider.setHeight(ComponentContainer.LayoutConfig.MATCH_PARENT);
        surfaceProvider.setTop(0);

        playerLayout.addComponent(surfaceProvider);

        HiLog.info(LABEL_LOG, "end initSurfaceProvider");
    }

    private void initData() {
        HiLog.info(LABEL_LOG, "before initData");

        Video video1 = new Video("Trailer", "trailer.mp4");
        Video video2 = new Video("Captain Marvel", "captain_marvel.mp4");
        Video video3 = new Video("Big BuckBunny", "big_buck_bunny.mp4");

        videoList = new ArrayList<>(
                Arrays.asList(
                        video1,
                        video2,
                        video3));

        int size = videoList.size();

        HiLog.info(LABEL_LOG, "end initData, size: %{public}s", size);
    }

    private void doPlayPause() throws IOException {
        Video video = videoList.get(currentIndex);

        currentName.setText(video.getName());

        if (playerState == PlayerStateEnum.PLAY) {
            HiLog.info(LABEL_LOG, "before pause");
            player.pause();
            playerState = PlayerStateEnum.PAUSE;
            buttonPlayPause.setText("Play");
            HiLog.info(LABEL_LOG, "end pause");
        } else if (playerState == PlayerStateEnum.PAUSE) {
            player.play();
            playerState = PlayerStateEnum.PLAY;
            buttonPlayPause.setText("Pause");
        } else {
            start(video.getFilePath());
            playerState = PlayerStateEnum.PLAY;
            buttonPlayPause.setText("Pause");
        }
    }

    private void start(String fileName) throws IOException {
        HiLog.info(LABEL_LOG, "before start: %{public}s", fileName);

        RawFileDescriptor filDescriptor = getResourceManager()
                .getRawFileEntry("resources/rawfile/" + fileName).openRawFileDescriptor();

        Source source = new Source(filDescriptor.getFileDescriptor(),
                filDescriptor.getStartPosition(), filDescriptor.getFileSize());

        player.setSource(source);
        player.setVideoSurface(surface);
        player.setPlayerCallback(new PlayerCallback());
        player.prepare();
        player.play();

        HiLog.info(LABEL_LOG, "end start");
    }

    private void doPrevious() throws IOException {
        // 查询所有用户
        currentIndex--;

        // 取模
        currentIndex = Math.floorMod(currentIndex, videoList.size());

        player.stop();
        playerState = PlayerStateEnum.STOP;

        doPlayPause();
    }

    private void doNext() throws IOException {
        currentIndex++;

        // 取模
        currentIndex = Math.floorMod(currentIndex, videoList.size());

        player.stop();
        playerState = PlayerStateEnum.STOP;

        doPlayPause();
    }

    private class PlayerCallback implements Player.IPlayerCallback {
        @Override
        public void onPrepared() {
            HiLog.info(LABEL_LOG, "onPrepared");

            // 延迟1秒，再刷新进度
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    HiLog.info(LABEL_LOG, "before playerProgress");

                    int progressValue = player.getCurrentTime() * 100 / player.getDuration();
                    playerProgress.setProgressValue(progressValue);

                    HiLog.info(LABEL_LOG, "playerProgress: %{public}s", progressValue);

                    dispatcher.delayDispatch(this, 200);
                }
            };

            dispatcher.asyncDispatch(runnable);
        }

        @Override
        public void onMessage(int i, int i1) {
            HiLog.info(LABEL_LOG, "onMessage");
        }

        @Override
        public void onError(int i, int i1) {
            HiLog.info(LABEL_LOG, "onError, i: %{public}s, i1: %{public}s", i, i1);
        }

        @Override
        public void onResolutionChanged(int i, int i1) {
            HiLog.info(LABEL_LOG, "onResolutionChanged");
        }

        @Override
        public void onPlayBackComplete() {
            HiLog.info(LABEL_LOG, "onPlayBackComplete");
        }

        @Override
        public void onRewindToComplete() {
            HiLog.info(LABEL_LOG, "onRewindToComplete");
        }

        @Override
        public void onBufferingChange(int i) {
            HiLog.info(LABEL_LOG, "onBufferingChange");
        }

        @Override
        public void onNewTimedMetaData(Player.MediaTimedMetaData mediaTimedMetaData) {
            HiLog.info(LABEL_LOG, "onNewTimedMetaData");
        }

        @Override
        public void onMediaTimeIncontinuity(Player.MediaTimeInfo mediaTimeInfo) {
            HiLog.info(LABEL_LOG, "onMediaTimeIncontinuity");
        }
    }

    private class VideoSurfaceCallback implements SurfaceOps.Callback {
        @Override
        public void surfaceCreated(SurfaceOps surfaceOps) {
            if (surfaceProvider.getSurfaceOps().isPresent()) {
                surface = surfaceProvider.getSurfaceOps().get().getSurface();
            }

            HiLog.info(LABEL_LOG, "surfaceCreated");
        }

        @Override
        public void surfaceChanged(SurfaceOps surfaceOps, int i, int i1, int i2) {
            HiLog.info(LABEL_LOG, "surfaceChanged, %{public}s, %{public}s, %{public}s",
                    i, i1, i2);
        }

        @Override
        public void surfaceDestroyed(SurfaceOps surfaceOps) {
            HiLog.info(LABEL_LOG, "surfaceDestroyed");
        }
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
