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
import ohos.media.common.Source;
import ohos.media.player.Player;

public class MainAbilitySlice extends AbilitySlice {
    private static Player player;

    private SurfaceProvider surfaceProvider;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button buttonPlay =
                (Button) findComponentById(ResourceTable.Id_button_play);

        // 为按钮设置点击事件回调
        buttonPlay.setClickedListener(listener -> player.play());

        Button buttonPause =
                (Button) findComponentById(ResourceTable.Id_button_pause);

        // 为按钮设置点击事件回调
        buttonPause.setClickedListener(listener -> player.pause());

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
        player = new Player(this);

        surfaceProvider = new SurfaceProvider(this);
        surfaceProvider.getSurfaceOps().get().addCallback(new VideoSurfaceCallback());
        surfaceProvider.pinToZTop(true);
        surfaceProvider.setWidth(ComponentContainer.LayoutConfig.MATCH_CONTENT);
        surfaceProvider.setHeight(ComponentContainer.LayoutConfig.MATCH_PARENT);

        DependentLayout layout
                = (DependentLayout) findComponentById(ResourceTable.Id_layout_surface_provider);
        layout.addComponent(surfaceProvider);
    }

    class VideoSurfaceCallback implements SurfaceOps.Callback {
        @Override
        public void surfaceCreated(SurfaceOps surfaceOps) {
            if (surfaceProvider.getSurfaceOps().isPresent()) {
                Surface surface = surfaceProvider.getSurfaceOps().get().getSurface();
                playLocalFile(surface);
            }
        }

        @Override
        public void surfaceChanged(SurfaceOps surfaceOps, int i, int i1, int i2) {
        }

        @Override
        public void surfaceDestroyed(SurfaceOps surfaceOps) {
        }
    }

    private void playLocalFile(Surface surface) {
        try {
            RawFileDescriptor filDescriptor =
                    getResourceManager().getRawFileEntry("resources/rawfile/video.mp4").openRawFileDescriptor();

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
        }

        @Override
        public void onMessage(int i, int i1) {
        }

        @Override
        public void onError(int i, int i1) {
        }

        @Override
        public void onResolutionChanged(int i, int i1) {
        }

        @Override
        public void onPlayBackComplete() {
        }

        @Override
        public void onRewindToComplete() {
        }

        @Override
        public void onBufferingChange(int i) {
        }

        @Override
        public void onNewTimedMetaData(Player.MediaTimedMetaData mediaTimedMetaData) {
        }

        @Override
        public void onMediaTimeIncontinuity(Player.MediaTimeInfo mediaTimeInfo) {
        }
    }
}
