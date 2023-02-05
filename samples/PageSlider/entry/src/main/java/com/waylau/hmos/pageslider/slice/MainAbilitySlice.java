package com.waylau.hmos.pageslider.slice;

import com.waylau.hmos.pageslider.ResourceTable;
import com.waylau.hmos.pageslider.bean.VideoInfo;
import com.waylau.hmos.pageslider.provider.VideoPlayerPageSliderProvider;
import com.waylau.hmos.pageslider.util.CommonUtil;
import com.waylau.hmos.pageslider.util.LogUtil;
import com.waylau.hmos.pageslider.util.VideoPlayerPlugin;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.components.surfaceprovider.SurfaceProvider;
import ohos.agp.graphics.Surface;
import ohos.agp.graphics.SurfaceOps;
import ohos.agp.window.service.WindowManager;
import ohos.utils.zson.ZSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = VideoPlayerPageSliderProvider.class.getSimpleName();

    private final List<VideoInfo> videoInfoList = new ArrayList<>();
    private int index = 0;
    private PageSlider pageSlider;
    private SurfaceProvider surfaceProvider;
    private VideoPlayerPlugin videoPlayerPlugin;
    private Surface surface;
    private DirectionalLayout layoutVideoPlayer;

    VideoPlayerPageSliderProvider videoPlayerPageSliderProvider;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        // 初始化数据
        initData();

        // 初始化UI组件
        initUi(intent);

        addSurfaceProvider();

        initPlayer();

    }

    private void addSurfaceProvider() {
        surfaceProvider = new SurfaceProvider(this);

        if (surfaceProvider.getSurfaceOps().isPresent()) {
            surfaceProvider.getSurfaceOps().get().addCallback(new SurfaceCallBack());
            surfaceProvider.pinToZTop(true);
        }

        layoutVideoPlayer = (DirectionalLayout) findComponentById(ResourceTable.Id_layout_video_player);
        layoutVideoPlayer.addComponent(surfaceProvider);
    }

    private void initPlayer() {
        videoPlayerPlugin = new VideoPlayerPlugin(getApplicationContext());
    }


    private void initData() {
        String resourcePath = "resources/rawfile/videoinfo.json";
        String videosJson = CommonUtil.getJsonFileToString(this, resourcePath);

        // json字符串转成对象集合
        List<VideoInfo> videoInfos = ZSONArray.stringToClassList(videosJson, VideoInfo.class);

        // 处理视频对象
        for (VideoInfo bean : videoInfos) {
            videoInfoList.add(bean);
        }
    }

    private void initUi(Intent intent) {
        // 初始化PageSlider
        initPageSlider();
    }


    private void initPageSlider() {
        LogUtil.info(TAG, "initPageSlider is called");
        pageSlider = (PageSlider) findComponentById(ResourceTable.Id_page_slider);

        videoPlayerPageSliderProvider =
                new VideoPlayerPageSliderProvider(videoInfoList, this);

        pageSlider.setProvider(videoPlayerPageSliderProvider);
        videoPlayerPageSliderProvider.notifyDataChanged();
        pageSlider.setReboundEffect(true);
        pageSlider.addPageChangedListener(new PageSlider.PageChangedListener() {
            @Override
            public void onPageSliding(int itemPos, float itemPosOffset, int itemPosPixles) {
                LogUtil.info(TAG, "onPageSliding itemPos:" + itemPos);
            }

            @Override
            public void onPageSlideStateChanged(int state) {
                LogUtil.info(TAG, "onPageSlideStateChanged state:" + state);
            }

            @Override
            public void onPageChosen(int itemPos) {
                LogUtil.info(TAG, "onPageChosen itemPos:" + itemPos);

                index = itemPos;
                videoPlayerPlugin.startPlay(videoInfoList.get(index), surface);
            }
        });

        pageSlider.setCurrentPage(index);

    }

    /**
     * SurfaceCallBack
     */
    class SurfaceCallBack implements SurfaceOps.Callback {
        @Override
        public void surfaceCreated(SurfaceOps callbackSurfaceOps) {
            if (surfaceProvider.getSurfaceOps().isPresent()) {
                surface = surfaceProvider.getSurfaceOps().get().getSurface();
                LogUtil.info(TAG, "surface set");

                if(index ==0) {
                    videoPlayerPlugin.startPlay(videoInfoList.get(index), surface);
                }
            }
        }

        @Override
        public void surfaceChanged(SurfaceOps callbackSurfaceOps, int format, int width, int height) {
            LogUtil.info(TAG, "surface changed");
        }

        @Override
        public void surfaceDestroyed(SurfaceOps callbackSurfaceOps) {
            LogUtil.info(TAG, "surface destroyed");
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
