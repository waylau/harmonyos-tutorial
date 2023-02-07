/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.store;

import java.util.List;

import com.waylau.hmos.shortvideo.bean.MeFavoriteVideoInfo;
import com.waylau.hmos.shortvideo.util.DatabaseUtil;
import com.waylau.hmos.shortvideo.util.LogUtil;

import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

/**
 * 收藏信息存储
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-07
 */
public class MeFavoriteVideoInfoRepository {
    private static final String TAG = MeFavoriteVideoInfoRepository.class.getSimpleName();
    private static OrmContext ormContext = DatabaseUtil.getOrmContext();

    public static List<MeFavoriteVideoInfo> queryByUsername(String username) {
        OrmPredicates predicates = ormContext.where(MeFavoriteVideoInfo.class);
        predicates.equalTo("username", username);
        List<MeFavoriteVideoInfo> videoInfos = ormContext.query(predicates);

        return videoInfos;
    }

    public static int insert(MeFavoriteVideoInfo videoInfo) {
        LogUtil.info(TAG, "before insert");

        // 插入数据库
        ormContext.insert(videoInfo);
        boolean isSuccessed = ormContext.flush();

        // 获取Id
        int id = videoInfo.getMeFavoriteId();

        LogUtil.info(TAG, "end insert: " + id + ", isSuccessed: " + isSuccessed);
        return id;
    }

    public static int delete(MeFavoriteVideoInfo videoInfo) {
        LogUtil.info(TAG, "before update");

        ormContext.delete(videoInfo);

        boolean isSuccessed = ormContext.flush();

        LogUtil.info(TAG, "end delete, isSuccessed: " + isSuccessed);
        return 1;
    }
}
