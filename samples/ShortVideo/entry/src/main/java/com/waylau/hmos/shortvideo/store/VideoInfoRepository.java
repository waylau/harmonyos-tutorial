/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.store;

import java.util.List;

import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.util.DatabaseUtil;
import com.waylau.hmos.shortvideo.util.LogUtil;

import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

/**
 * 用户信息存储
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-30
 */
public class VideoInfoRepository {
    private static final String TAG = VideoInfoRepository.class.getSimpleName();
    private static OrmContext ormContext = DatabaseUtil.getOrmContext();

    public static List<VideoInfo> queryAll() {
        OrmPredicates predicates = ormContext.where(VideoInfo.class);
        List<VideoInfo> videoInfos = ormContext.query(predicates);

        return videoInfos;
    }

    public static int insert(VideoInfo videoInfo) {
        LogUtil.info(TAG, "before insert");

        // 插入数据库
        ormContext.insert(videoInfo);
        boolean isSuccessed = ormContext.flush();

        // 获取userId
        int videoInfoId = videoInfo.getId();

        LogUtil.info(TAG, "end insert: " + videoInfoId);
        return videoInfoId;
    }

    public static int update(VideoInfo videoInfo) {
        LogUtil.info(TAG, "before update");

        ormContext.update(videoInfo);

        boolean isSuccessed = ormContext.flush();

        LogUtil.info(TAG, "end update, isSuccessed: " + isSuccessed);
        return 1;
    }
}
