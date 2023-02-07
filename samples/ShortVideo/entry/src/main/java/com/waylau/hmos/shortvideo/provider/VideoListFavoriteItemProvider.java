/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.provider;

import java.util.List;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.MeFavoriteVideoInfo;
import com.waylau.hmos.shortvideo.listener.OperateResultListener;
import com.waylau.hmos.shortvideo.store.MeFavoriteVideoInfoRepository;
import com.waylau.hmos.shortvideo.util.CommonUtil;

import ohos.agp.components.*;
import ohos.app.Context;

/**
 * Video List Favorite Item Provider
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-26
 */
public class VideoListFavoriteItemProvider extends BaseItemProvider {
    private static final String TAG = VideoListFavoriteItemProvider.class.getSimpleName();

    // 数据源
    private List<MeFavoriteVideoInfo> list;
    private Context context;
    private Button buttonDelete;
    private final OperateResultListener listener;

    public VideoListFavoriteItemProvider(List<MeFavoriteVideoInfo> list, Context context,OperateResultListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
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
            LayoutScatter.getInstance(context).parse(ResourceTable.Layout_video_list_item_favorite_layout, null, false);

        MeFavoriteVideoInfo videoInfo = list.get(i);
        Text textItemVideoContent = (Text)videoListItemLayout.findComponentById(ResourceTable.Id_text_item_video_content);
        textItemVideoContent.setText(videoInfo.getContent());
        Image imagevideoCover = (Image)videoListItemLayout.findComponentById(ResourceTable.Id_image_item_video_cover);
        imagevideoCover.setPixelMap(CommonUtil.getImageSource(context, videoInfo.getCoverPath()));

        buttonDelete = (Button)videoListItemLayout.findComponentById(ResourceTable.Id_button_item_delete);
        buttonDelete.setClickedListener(c -> {
            MeFavoriteVideoInfoRepository.delete(videoInfo);

            list.remove(i);
            listener.callBack();
        });

        return videoListItemLayout;
    }

}