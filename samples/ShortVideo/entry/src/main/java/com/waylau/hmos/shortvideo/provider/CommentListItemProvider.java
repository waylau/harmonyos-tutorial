/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.provider;

import java.util.List;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.CommentInfo;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.listener.OperateResultListener;
import com.waylau.hmos.shortvideo.util.CommonUtil;

import ohos.agp.components.*;
import ohos.app.Context;

/**
 * Comment List Item Provider
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-11
 */
public class CommentListItemProvider extends BaseItemProvider {
    private static final String TAG = CommentListItemProvider.class.getSimpleName();

    private final List<CommentInfo> list;
    private final Context context;
    private final OperateResultListener listener;

    public CommentListItemProvider(List<CommentInfo> list, Context context, OperateResultListener listener) {
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
            LayoutScatter.getInstance(context).parse(ResourceTable.Layout_item_comment_dialog, null, false);

        CommentInfo commentInfo = list.get(i);
        Image imageUserPortrait = (Image) videoListItemLayout.findComponentById(ResourceTable.Id_image_item_comment_user_portrait);
        Text texUsername = (Text) videoListItemLayout.findComponentById(ResourceTable.Id_text_item_comment_user_name);
        Text textCommentContent = (Text) videoListItemLayout.findComponentById(ResourceTable.Id_text_item_comment_content);

        // 设置圆角
        imageUserPortrait.setCornerRadius(Constants.NUMBER_FLOAT_100);
        imageUserPortrait.setPixelMap(CommonUtil.getImageSource(context, commentInfo.getPortraitPath()));
        texUsername.setText(commentInfo.getUsername());
        textCommentContent.setText(commentInfo.getContent());

        return videoListItemLayout;
    }

}