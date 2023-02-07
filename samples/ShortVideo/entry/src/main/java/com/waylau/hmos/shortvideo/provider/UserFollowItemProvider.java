/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.provider;

import java.util.List;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.UserFollowInfo;
import com.waylau.hmos.shortvideo.listener.OperateResultListener;
import com.waylau.hmos.shortvideo.store.UserFollowInfoRepository;
import com.waylau.hmos.shortvideo.util.CommonUtil;

import ohos.agp.components.*;
import ohos.app.Context;

/**
 * FollowItemProvider
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-07
 */
public class UserFollowItemProvider extends BaseItemProvider {
    private static final String TAG = UserFollowItemProvider.class.getSimpleName();

    // 数据源，每个页面对应list中的一项
    private final List<UserFollowInfo> list;
    private final Context context;
    private final OperateResultListener listener;
    private Button buttonDelete;

    public UserFollowItemProvider(List<UserFollowInfo> list, Context context, OperateResultListener listener) {
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
        if (list != null && i >= 0 && i < list.size()) {
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
            LayoutScatter.getInstance(context).parse(ResourceTable.Layout_follow_list_item_layout, null, false);

        UserFollowInfo userFollowInfo = list.get(i);
        Text textItemFollowAuthor =
            (Text)videoListItemLayout.findComponentById(ResourceTable.Id_text_item_follow_author);
        textItemFollowAuthor.setText(userFollowInfo.getAuthor());
        Image imageItemeFollowPortrait =
            (Image)videoListItemLayout.findComponentById(ResourceTable.Id_image_item_follow_portrait);
        imageItemeFollowPortrait.setPixelMap(CommonUtil.getImageSource(context, userFollowInfo.getPortraitPath()));

        buttonDelete = (Button)videoListItemLayout.findComponentById(ResourceTable.Id_button_item_delete);
        buttonDelete.setClickedListener(c -> {
            UserFollowInfoRepository.delete(userFollowInfo);

            list.remove(i);
            listener.callBack();
        });

        return videoListItemLayout;
    }

}