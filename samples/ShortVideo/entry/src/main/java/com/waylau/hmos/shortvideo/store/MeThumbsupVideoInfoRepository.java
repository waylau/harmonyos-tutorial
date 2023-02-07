/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.store;

import java.util.List;

import com.waylau.hmos.shortvideo.bean.MeThumbsupVideoInfo;
import com.waylau.hmos.shortvideo.util.DatabaseUtil;
import com.waylau.hmos.shortvideo.util.LogUtil;

import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

/**
 * 点赞信息存储
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-07
 */
public class MeThumbsupVideoInfoRepository {
    private static final String TAG = MeThumbsupVideoInfoRepository.class.getSimpleName();
    private static OrmContext ormContext = DatabaseUtil.getOrmContext();

    public static List<MeThumbsupVideoInfo> queryAll() {
        OrmPredicates predicates = ormContext.where(MeThumbsupVideoInfo.class);
        List<MeThumbsupVideoInfo> videoInfos = ormContext.query(predicates);

        return videoInfos;
    }

    public static List<MeThumbsupVideoInfo> queryByUsername(String username) {
        OrmPredicates predicates = ormContext.where(MeThumbsupVideoInfo.class);
        predicates.equalTo("username", username);
        List<MeThumbsupVideoInfo> videoInfos = ormContext.query(predicates);

        return videoInfos;
    }

    public static int insert(MeThumbsupVideoInfo videoInfo) {
        LogUtil.info(TAG, "before insert");

        // 插入数据库
        ormContext.insert(videoInfo);
        boolean isSuccessed = ormContext.flush();

        // 获取Id
        int id = videoInfo.getMeThumbsupId();

        LogUtil.info(TAG, "end insert: " + id + ", isSuccessed: " + isSuccessed);
        return id;
    }

    public static int update(MeThumbsupVideoInfo videoInfo) {
        LogUtil.info(TAG, "before update");

        ormContext.update(videoInfo);

        boolean isSuccessed = ormContext.flush();

        LogUtil.info(TAG, "end update, isSuccessed: " + isSuccessed);
        return 1;
    }

    public static int delete(MeThumbsupVideoInfo videoInfo) {
        LogUtil.info(TAG, "before update");

        ormContext.delete(videoInfo);

        boolean isSuccessed = ormContext.flush();

        LogUtil.info(TAG, "end delete, isSuccessed: " + isSuccessed);
        return 1;
    }
}
