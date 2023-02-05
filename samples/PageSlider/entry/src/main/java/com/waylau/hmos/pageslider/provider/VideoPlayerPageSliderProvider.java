/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.pageslider.provider;


import com.waylau.hmos.pageslider.ResourceTable;
import com.waylau.hmos.pageslider.bean.VideoInfo;
import com.waylau.hmos.pageslider.util.CommonUtil;
import com.waylau.hmos.pageslider.util.LogUtil;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.List;

/**
 * VideoPlayer PageSliderProvider
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-05
 */
public class VideoPlayerPageSliderProvider extends PageSliderProvider {
    private static final String TAG = VideoPlayerPageSliderProvider.class.getSimpleName();

    // 数据源，每个页面对应list中的一项
    private List<VideoInfo> list;
    private Context context;

    public VideoPlayerPageSliderProvider(List<VideoInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object createPageInContainer(ComponentContainer componentContainer, int i) {
        LogUtil.info(TAG, "createPageInContainer, i:" + i);

        Component videoPlayerViewLayout =
                LayoutScatter.getInstance(context).parse(ResourceTable.Layout_video_player_view_layout, null, false);

        VideoInfo videoInfo = list.get(i);

        Image imageMePortrait = (Image) videoPlayerViewLayout.findComponentById(ResourceTable.Id_image_video_cover);

        imageMePortrait.setPixelMap(CommonUtil.getImageSource(context, videoInfo.getCoverPath()));

        componentContainer.addComponent(videoPlayerViewLayout);

        return videoPlayerViewLayout;
    }

    @Override
    public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {
        LogUtil.info(TAG, "destroyPageFromContainer, i:" + i);
        componentContainer.removeComponent((Component) o);
    }

    @Override
    public boolean isPageMatchToObject(Component component, Object o) {
        // 可添加具体处理逻辑
        return true;
    }

}