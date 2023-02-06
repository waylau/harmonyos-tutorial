/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.dialog;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.store.VideoInfoRepository;
import com.waylau.hmos.shortvideo.util.CommonUtil;

import ohos.agp.components.*;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;

/**
 * 视频编辑弹出框
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-06
 */
public class CommentDialog extends CommonDialog {
    private Context context;
    private Image imageVideoCover = null; // 封面
    private TextField textVideoContent = null; // 视频内容
    private Button buttonPublish = null; // 发布
    private VideoInfo videoInfo;

    public CommentDialog(Context context) {
        super(context);
        this.context = context;
        Component container = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_ability_video_publish_layout, null, false);
        setContentCustomComponent(container);

        imageVideoCover = (Image)container.findComponentById(ResourceTable.Id_image_video_cover);
        textVideoContent = (TextField)container.findComponentById(ResourceTable.Id_textfield_video_content);
        buttonPublish = (Button)container.findComponentById(ResourceTable.Id_button_video_publish);
        buttonPublish.setText("保存");

        buttonPublish.setClickedListener(component -> {
            String videoContent = textVideoContent.getText();
            videoInfo.setContent(videoContent);

            checkPublish(videoInfo);
        });
    }

    private void checkPublish(VideoInfo video) {
        if (video.getContent() == null || video.getContent().isEmpty()) {
            new ToastDialog(context).setText("请输入视频的内容！").setAlignment(LayoutAlignment.CENTER).show();
        }

        // 更新
        VideoInfoRepository.update(video);

        destroy();
    }
    /**
     * 设置数据
     *
     * @param videoInfo 数据
     */
    public void setData(VideoInfo videoInfo) {
         this.videoInfo = videoInfo;

        // 刷新视频封面
        imageVideoCover.setPixelMap(CommonUtil.getImageSource(context, videoInfo.getCoverPath()));
        textVideoContent.setText(videoInfo.getContent());
    }

}
