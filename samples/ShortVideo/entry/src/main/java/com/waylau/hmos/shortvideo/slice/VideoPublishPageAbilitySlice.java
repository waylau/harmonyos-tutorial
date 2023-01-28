package com.waylau.hmos.shortvideo.slice;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.api.IVideoPlayer;
import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.player.VideoPlayer;
import com.waylau.hmos.shortvideo.util.CommonUtil;
import com.waylau.hmos.shortvideo.util.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.agp.components.TextField;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.utils.zson.ZSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频发布页面
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-26
 */
public class VideoPublishPageAbilitySlice extends AbilitySlice {
    private static final String TAG = VideoPublishPageAbilitySlice.class.getSimpleName();

    // 视频信息列表
    private final List<VideoInfo> videoInfoList = new ArrayList<>();

    private final VideoInfo videoInfo = new VideoInfo();

    private Image imageVideoCover = null; // 封面
    private TextField textVideoContent = null; // 视频内容
    private Button buttonPublish = null; // 发布

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_video_publish_layout);
        // 初始化数据
        initData();

        // 初始化UI组件
        initUi();

        // 初始化事件监听
        initListener();
    }

    private void initData() {
        String resourcePath = "resources/rawfile/videoinfo.json";
        String videosJson = CommonUtil.getJsonFileToString(this, resourcePath);

        // json字符串转成对象集合
        List<VideoInfo> videoInfos = ZSONArray.stringToClassList(videosJson, VideoInfo.class);
        videoInfoList.clear();
        videoInfoList.addAll(videoInfos);

        // 处理视频对象
        for (VideoInfo bean : videoInfos) {
            IVideoPlayer player = new VideoPlayer.Builder(getContext()).setFilePath(bean.getVideoPath()).create();
            bean.setVideoPlayer(player);
        }
    }

    private void initUi() {
        imageVideoCover = (Image)findComponentById(ResourceTable.Id_image_video_cover);
        textVideoContent = (TextField)findComponentById(ResourceTable.Id_textfield_video_content);
        buttonPublish = (Button)findComponentById(ResourceTable.Id_button_video_publish);
    }

    private void initListener() {
        imageVideoCover.setClickedListener(component -> {
            presentForResult(new VideoSelectionAbilitySlice(), new Intent(), 0);

        });

        buttonPublish.setClickedListener(component -> {
            String videoContent = textVideoContent.getText();
            videoInfo.setContent(videoContent);

            checkPublish(videoInfo);
        });

    }

    private void checkPublish(VideoInfo video) {
        if (video.getCoverPath() == null || video.getVideoPath() == null || video.getCoverPath().isEmpty()
            || video.getVideoPath().isEmpty()) {
            new ToastDialog(getContext()).setText("请选择要发布的视频！").setAlignment(LayoutAlignment.CENTER).show();
        } else if (video.getContent() == null || video.getContent().isEmpty()) {
            new ToastDialog(getContext()).setText("请输入视频的内容！").setAlignment(LayoutAlignment.CENTER).show();

        }

        // TODO 发布
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    protected void onResult(int requestCode, Intent resultIntent) {
        LogUtil.info(TAG, "onResult requestCode:" + requestCode + "; resultIntent:" + resultIntent);
        if (requestCode == 0) {
            videoInfo.setCoverPath(resultIntent.getStringParam(Constants.IMAGE_SELECTION));
            videoInfo.setVideoPath(resultIntent.getStringParam(Constants.VIDEO_SELECTION));

            // 刷新视频封面
            imageVideoCover.setPixelMap(CommonUtil.getImageSource(this.getContext(), videoInfo.getCoverPath()));

        } else {
            terminate();
        }
    }
}
