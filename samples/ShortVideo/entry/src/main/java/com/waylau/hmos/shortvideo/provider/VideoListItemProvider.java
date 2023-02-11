/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.provider;

import java.util.List;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.dialog.VideoInfoEditDialog;
import com.waylau.hmos.shortvideo.listener.OperateResultListener;
import com.waylau.hmos.shortvideo.store.VideoInfoRepository;
import com.waylau.hmos.shortvideo.util.CommonUtil;
import com.waylau.hmos.shortvideo.util.LogUtil;

import ohos.agp.components.*;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.BaseDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.app.Context;

/**
 * Video List Item Provider
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-26
 */
public class VideoListItemProvider extends BaseItemProvider {
    private static final String TAG = VideoListItemProvider.class.getSimpleName();

    // 数据源，每个页面对应list中的一项
    private final List<VideoInfo> list;
    private final Context context;
    private final OperateResultListener listener;
    private Button buttonEdit;
    private Button buttonDelete;

    public VideoListItemProvider(List<VideoInfo> list, Context context, OperateResultListener listener) {
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
            LayoutScatter.getInstance(context).parse(ResourceTable.Layout_video_list_item_layout, null, false);

        VideoInfo videoInfo = list.get(i);
        Text textItemVideoContent = (Text)videoListItemLayout.findComponentById(ResourceTable.Id_text_item_video_content);
        textItemVideoContent.setText(videoInfo.getContent());
        Image imagevideoCover = (Image)videoListItemLayout.findComponentById(ResourceTable.Id_image_item_video_cover);
        imagevideoCover.setPixelMap(CommonUtil.getImageSource(context, videoInfo.getCoverPath()));

        buttonEdit = (Button)videoListItemLayout.findComponentById(ResourceTable.Id_button_item_edit);
        buttonDelete = (Button)videoListItemLayout.findComponentById(ResourceTable.Id_button_item_delete);

        buttonEdit.setClickedListener(c -> {
            // 创建弹框
            VideoInfoEditDialog editDialog = new VideoInfoEditDialog(context);
            editDialog.setAlignment(LayoutAlignment.BOTTOM);
            editDialog.setData(videoInfo);
            editDialog.registerRemoveCallback(new BaseDialog.RemoveCallback() {
                @Override
                public void onRemove(IDialog iDialog) {
                    LogUtil.info(TAG, "commentDialog onRemove");

                    textItemVideoContent.setText(videoInfo.getContent());

                    listener.callBack();
                }
            });

            // 显示弹框
            editDialog.show();

        });

        buttonDelete.setClickedListener(c -> {
            VideoInfoRepository.delete(videoInfo);

            list.remove(i);
            listener.callBack();
        });

        return videoListItemLayout;
    }

}