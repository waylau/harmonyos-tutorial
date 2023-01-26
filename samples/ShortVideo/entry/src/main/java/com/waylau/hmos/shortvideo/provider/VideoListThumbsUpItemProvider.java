/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.provider;

import java.util.List;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.util.CommonUtil;

import ohos.agp.components.*;
import ohos.app.Context;

/**
 * Video List Thumbs Up Item Provider
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-26
 */
public class VideoListThumbsUpItemProvider extends BaseItemProvider {
    private static final String TAG = VideoListThumbsUpItemProvider.class.getSimpleName();

    // 数据源，每个页面对应list中的一项
    private List<VideoInfo> list;
    private Context context;

    public VideoListThumbsUpItemProvider(List<VideoInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        if (list != null && i >= 0 && i < list.size()){
            return list.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        final Component videoListItemLayout =
            LayoutScatter.getInstance(context).parse(ResourceTable.Layout_video_list_item_thumbs_up_layout, null, false);

        VideoInfo videoInfo = list.get(i);
        Text textItemVideoContent = (Text)videoListItemLayout.findComponentById(ResourceTable.Id_text_item_video_content);
        textItemVideoContent.setText(videoInfo.getContent());
        Image imagevideoCover = (Image)videoListItemLayout.findComponentById(ResourceTable.Id_image_item_video_cover);
        imagevideoCover.setPixelMap(CommonUtil.getImageSource(context, videoInfo.getCoverPath()));

        return videoListItemLayout;
    }

}