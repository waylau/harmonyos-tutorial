/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.store;

import java.util.List;

import com.waylau.hmos.shortvideo.bean.UserFollowInfo;
import com.waylau.hmos.shortvideo.util.DatabaseUtil;
import com.waylau.hmos.shortvideo.util.LogUtil;

import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

/**
 * 关注信息存储
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-07
 */
public class UserFollowInfoRepository {
    private static final String TAG = UserFollowInfoRepository.class.getSimpleName();
    private static OrmContext ormContext = DatabaseUtil.getOrmContext();

    public static List<UserFollowInfo> queryByUsername(String username) {
        OrmPredicates predicates = ormContext.where(UserFollowInfo.class);
        predicates.equalTo("username", username);
        List<UserFollowInfo> videoInfos = ormContext.query(predicates);

        return videoInfos;
    }

    public static int insert(UserFollowInfo userFollowInfo) {
        LogUtil.info(TAG, "before insert");

        // 插入数据库
        ormContext.insert(userFollowInfo);
        boolean isSuccessed = ormContext.flush();

        // 获取Id
        int id = userFollowInfo.getFollowId();

        LogUtil.info(TAG, "end insert: " + id + ", isSuccessed: " + isSuccessed);
        return id;
    }

    public static int delete(UserFollowInfo userFollowInfo) {
        LogUtil.info(TAG, "before update");

        ormContext.delete(userFollowInfo);

        boolean isSuccessed = ormContext.flush();

        LogUtil.info(TAG, "end delete, isSuccessed: " + isSuccessed);
        return 1;
    }
}
