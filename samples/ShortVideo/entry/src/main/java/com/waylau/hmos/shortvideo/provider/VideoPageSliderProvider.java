package com.waylau.hmos.shortvideo.provider;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.VideoInfoBean;
import com.waylau.hmos.shortvideo.util.VideoPlayerUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;
import ohos.agp.components.surfaceprovider.SurfaceProvider;
import ohos.agp.graphics.SurfaceOps;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayManager;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideoPageSliderProvider extends PageSliderProvider {
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, VideoPageSliderProvider.class.getSimpleName());

    private AbilitySlice abilitySlice;

    // 滑动页面
    private PageSlider pageSlider;

    // 视频信息列表
    private List<VideoInfoBean> listVideoInfo = new ArrayList<>();

    // 封面
    private Image imageCover;

    public VideoPageSliderProvider(AbilitySlice context, PageSlider pageSlider, List<VideoInfoBean> listVideoInfo) {
        this.abilitySlice = context;
        this.pageSlider = pageSlider;
        this.listVideoInfo = listVideoInfo;
    }


    @Override
    public int getCount() {
        return listVideoInfo.size();
    }

    @Override
    public Object createPageInContainer(ComponentContainer componentContainer, int position) {
        HiLog.info(LABEL_LOG, "createPageInContainer: position: %{public}s", position);

        VideoInfoBean videoInfoBean = listVideoInfo.get(position);

        ComponentContainer layout = (ComponentContainer) LayoutScatter.getInstance(abilitySlice).parse(ResourceTable.Layout_video_page, null, true);

        Text textAuthor = (Text) layout.findComponentById(ResourceTable.Id_text_author);
        Text textContent = (Text) layout.findComponentById(ResourceTable.Id_text_content);
        Image imageComment = (Image) layout.findComponentById(ResourceTable.Id_image_comment);
        Image imageShare = (Image) layout.findComponentById(ResourceTable.Id_image_share);
        imageCover = (Image) layout.findComponentById(ResourceTable.Id_image_cover);

        // 设置窗口
        SurfaceProvider surfaceProvider = (SurfaceProvider) layout.findComponentById(ResourceTable.Id_surfaceProvider);

        setSurfaceProvider(surfaceProvider, position, videoInfoBean);

        // 设置作者
        textAuthor.setText(videoInfoBean.getAuthor());
        // 设置内容
        textContent.setText(videoInfoBean.getContent());

        // 请传入resources/media下的图片id
        imageCover.setPixelMap(ResourceTable.Media_pic01);

        // 隐藏封面
        imageCover.setVisibility(Component.VISIBLE);

        // 全屏点击，播放或者暂停
        componentContainer.setClickedListener(component -> {
            if (VideoPlayerUtil.getInstance().isPlaying()) {
                VideoPlayerUtil.getInstance().pauseVideo();
            } else {
                VideoPlayerUtil.getInstance().playerVideo();
            }
        });

        componentContainer.addComponent(layout);

        return layout;
    }

    @Override
    public void destroyPageFromContainer(ComponentContainer componentContainer, int position, Object object) {
        componentContainer.removeComponent((Component) object);
    }

    @Override
    public boolean isPageMatchToObject(Component component, Object o) {
        return true;
    }

    /**
     * 设置窗口信息
     */
    private void setSurfaceProvider(SurfaceProvider surfaceProvider, int position, VideoInfoBean videoInfoBean) {
        // 设置播放窗口在顶层
        surfaceProvider.pinToZTop(true);

        // 设置播放窗口操作监听
        if (surfaceProvider.getSurfaceOps().isPresent()) {
            surfaceProvider.getSurfaceOps().get().addCallback(new SurfaceOps.Callback() {
                // 窗口创建成功
                @Override
                public void surfaceCreated(SurfaceOps surfaceOps) {
                    // 设置窗口
                    videoInfoBean.setSurface(surfaceOps.getSurface());
                    listVideoInfo.set(position, videoInfoBean);
                    if (position == pageSlider.getCurrentPage()) {
                        // 设置播放配置
                        VideoPlayerUtil.getInstance().setPlayerConfig(abilitySlice, videoInfoBean.getVideoPath(), surfaceOps.getSurface());

                        // 开始播放
                        VideoPlayerUtil.getInstance().playerVideo();

                        // 隐藏封面
                        imageCover.setVisibility(Component.INVISIBLE);
                    }
                }

                @Override
                public void surfaceChanged(SurfaceOps callbackSurfaceOps, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceOps surfaceOps) {
                }
            });
        }
        // 设置播放窗口的宽高
        /*
        Optional<Display> defaultDisplay = DisplayManager.getInstance().getDefaultDisplay(abilitySlice);
        if (defaultDisplay.isPresent()) {
            int screenWidth = defaultDisplay.get().getAttributes().width;
            int videoHeight = screenWidth * 1080 / 1920;
            StackLayout.LayoutConfig layoutConfig = new StackLayout.LayoutConfig(screenWidth, videoHeight);
            layoutConfig.alignment = LayoutAlignment.CENTER;
            surfaceProvider.setLayoutConfig(layoutConfig);
        }

         */

    }


}
