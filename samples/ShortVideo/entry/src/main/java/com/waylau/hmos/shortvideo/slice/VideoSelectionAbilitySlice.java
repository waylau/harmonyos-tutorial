package com.waylau.hmos.shortvideo.slice;

import java.util.ArrayList;
import java.util.List;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.util.CommonUtil;
import com.waylau.hmos.shortvideo.util.LogUtil;
import com.waylau.hmos.shortvideo.util.ScreenUtil;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.TableLayout;
import ohos.agp.components.Text;
import ohos.utils.zson.ZSONArray;

/**
 * 图片选择页面
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-27
 */
public class VideoSelectionAbilitySlice extends AbilitySlice {
    private static final String TAG = VideoSelectionAbilitySlice.class.getSimpleName();
    private TableLayout tableLayout;
    private Text textNoVideoTip, text;
    private List<VideoInfo> videoList = new ArrayList<>();

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_video_selection);

        initUi();
        initData();

        showImages();
    }

    private void initUi() {
        tableLayout = (TableLayout)findComponentById(ResourceTable.Id_tablelayout_video_list);
        textNoVideoTip = (Text)findComponentById(ResourceTable.Id_text_no_video_tip);
    }

    private void initData() {
        String resourcePath = "resources/rawfile/videoinfo2.json";
        String jsonString = CommonUtil.getJsonFileToString(this, resourcePath);

        // json字符串转成对象集合
        List<VideoInfo> videoInfos = ZSONArray.stringToClassList(jsonString, VideoInfo.class);
        videoList.clear();
        videoList.addAll(videoInfos);
    }

    public void showImages() {
        tableLayout.removeAllComponents();
        if (videoList.isEmpty()) {
            textNoVideoTip.setVisibility(Component.VISIBLE);
            textNoVideoTip.setText("No image.");
        } else {
            textNoVideoTip.setVisibility(Component.HIDE);
        }

        int screenWidth = ScreenUtil.getScreenWidth(this);
        int imageWidth = (screenWidth - 20 * 3) / 3;
        int imageHeight = (imageWidth * 540) / 408;

        for (VideoInfo videoInfo : videoList) {
            Image img = new Image(this);
            img.setId(videoInfo.getId());
            img.setHeight(imageHeight);
            img.setWidth(imageWidth);
            img.setMarginTop(10);
            img.setMarginLeft(10);
            img.setMarginRight(10);
            img.setPixelMap(CommonUtil.getImageSource(this.getContext(), videoInfo.getCoverPath()));
            img.setScaleMode(Image.ScaleMode.ZOOM_CENTER);

            // 设置点击事件
            img.setClickedListener(component -> {
                int imageId = component.getId();
                LogUtil.info(TAG, "Image onSelected, imageId: " + imageId);

                VideoInfo video = findVideoInfoById(imageId);

                // 回到发起页
                Intent intent = new Intent();
                intent.setParam(Constants.IMAGE_SELECTION, video.getCoverPath());
                intent.setParam(Constants.VIDEO_SELECTION, video.getVideoPath());
                setResult(intent);

                // 销毁当前页面
                terminate();
            });
            tableLayout.addComponent(img);
        }
    }

    private VideoInfo findVideoInfoById(int videoId) {
        for (VideoInfo videoInfo : videoList) {
            if (videoId == videoInfo.getId()) {
                return videoInfo;
            }
        }

        return null;
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
