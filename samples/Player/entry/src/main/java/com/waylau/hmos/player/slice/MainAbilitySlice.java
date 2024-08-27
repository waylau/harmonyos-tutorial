package com.waylau.hmos.player.slice;

import com.waylau.hmos.player.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DependentLayout;
import ohos.agp.components.surfaceprovider.SurfaceProvider;
import ohos.agp.graphics.Surface;
import ohos.agp.graphics.SurfaceOps;
import ohos.global.resource.RawFileDescriptor;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.common.Source;
import ohos.media.player.Player;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private static Player player;

    private SurfaceProvider surfaceProvider;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button buttonPlay =
                (Button) findComponentById(ResourceTable.Id_button_play);

        // 为按钮设置点击事件回调
        buttonPlay.setClickedListener(listener -> {
            player.play();

            HiLog.info(LABEL_LOG, "getCurrentTime: %{public}s", player.getCurrentTime() );
            HiLog.info(LABEL_LOG, "getDuration: %{public}s", player.getDuration() );
        });

        Button buttonPause =
                (Button) findComponentById(ResourceTable.Id_button_pause);

        // 为按钮设置点击事件回调
        buttonPause.setClickedListener(listener -> {
            player.pause();
            HiLog.info(LABEL_LOG, "getCurrentTime: %{public}s", player.getCurrentTime() );
            HiLog.info(LABEL_LOG, "getDuration: %{public}s", player.getDuration() );
        });

        Button buttonStop =
                (Button) findComponentById(ResourceTable.Id_button_stop);

        // 为按钮设置点击事件回调
        buttonStop.setClickedListener(listener -> player.stop());
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }


    @Override
    protected void onActive() {
        super.onActive();
        initSurfaceProvider();
    }

    private void initSurfaceProvider() {
        HiLog.info(LABEL_LOG, "before initSurfaceProvider");

        player = new Player(this);

        surfaceProvider = new SurfaceProvider(this);
        surfaceProvider.getSurfaceOps().get().addCallback(new VideoSurfaceCallback());
        surfaceProvider.pinToZTop(true);
        surfaceProvider.setWidth(ComponentContainer.LayoutConfig.MATCH_CONTENT);
        surfaceProvider.setHeight(ComponentContainer.LayoutConfig.MATCH_PARENT);

        DependentLayout layout
                = (DependentLayout) findComponentById(ResourceTable.Id_layout_surface_provider);
        layout.addComponent(surfaceProvider);

        HiLog.info(LABEL_LOG, "end initSurfaceProvider");
    }

    class VideoSurfaceCallback implements SurfaceOps.Callback {
        @Override
        public void surfaceCreated(SurfaceOps surfaceOps) {
            if (surfaceProvider.getSurfaceOps().isPresent()) {
                Surface surface = surfaceProvider.getSurfaceOps().get().getSurface();
                playLocalFile(surface);
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

    private void playLocalFile(Surface surface) {
        HiLog.info(LABEL_LOG, "before playLocalFile");

        try {
            RawFileDescriptor filDescriptor =
                    getResourceManager()
                            .getRawFileEntry("resources/rawfile/video_00.mp4")
                            .openRawFileDescriptor();

            Source source = new Source(filDescriptor.getFileDescriptor(),
                    filDescriptor.getStartPosition(), filDescriptor.getFileSize());
            player.setSource(source);
            player.setVideoSurface(surface);
            player.setPlayerCallback(new VideoPlayerCallback());
            player.prepare();

            surfaceProvider.setTop(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HiLog.info(LABEL_LOG, "before playLocalFile");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.stop();
        }
        surfaceProvider.removeFromWindow();
    }

    private class VideoPlayerCallback implements Player.IPlayerCallback {
        @Override
        public void onPrepared() {
            HiLog.info(LABEL_LOG, "onPrepared");
        }

        @Override
        public void onMessage(int i, int i1) {
            HiLog.info(LABEL_LOG, "onMessage, %{public}s, %{public}s", i, i1);
        }

        @Override
        public void onError(int i, int i1) {
            HiLog.info(LABEL_LOG, "onError, %{public}s, %{public}s", i, i1);
        }

        @Override
        public void onResolutionChanged(int i, int i1) {
            HiLog.info(LABEL_LOG, "onResolutionChanged, %{public}s, %{public}s", i, i1);
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
            HiLog.info(LABEL_LOG, "onBufferingChange, %{public}s", i);
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
}
