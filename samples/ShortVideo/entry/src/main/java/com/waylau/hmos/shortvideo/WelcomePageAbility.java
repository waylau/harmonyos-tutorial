/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */
package com.waylau.hmos.shortvideo;

import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.slice.*;
import com.waylau.hmos.shortvideo.store.VideoInfoRepository;
import com.waylau.hmos.shortvideo.util.CommonUtil;
import com.waylau.hmos.shortvideo.util.DatabaseUtil;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.utils.zson.ZSONArray;

import java.util.List;

/**
 * 欢迎入口
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-05
 */
public class WelcomePageAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(WelcomeAbilitySlice.class.getName());

        // 初始化数据存储
        DatabaseUtil.initStore(this);

        initData();

    }

    private void initData() {
        String resourcePath = "resources/rawfile/videoinfo.json";
        String videosJson = CommonUtil.getJsonFileToString(this, resourcePath);

        // json字符串转成对象集合
        List<VideoInfo> videoInfos = ZSONArray.stringToClassList(videosJson, VideoInfo.class);

        // 处理视频对象
        for (VideoInfo bean : videoInfos) {
            VideoInfoRepository.insert(bean);
        }
    }

    @Override
    protected void onStop() {
        DatabaseUtil.deleteStore();
        super.onStop();
    }
}
