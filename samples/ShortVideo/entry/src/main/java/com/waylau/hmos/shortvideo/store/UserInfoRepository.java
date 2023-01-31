/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.store;

import com.waylau.hmos.shortvideo.bean.UserInfo;
import com.waylau.hmos.shortvideo.util.DatabaseUtil;
import com.waylau.hmos.shortvideo.util.LogUtil;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

import java.util.List;

/**
 * 用户信息存储
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-30
 */
public class UserInfoRepository {
    private static final String TAG = UserInfoRepository.class.getSimpleName();
    private static OrmContext ormContext = DatabaseUtil.getOrmContext();

    public static List<UserInfo> queryAll() {
        OrmPredicates predicates = ormContext.where(UserInfo.class);
        List<UserInfo> userInfos = ormContext.query(predicates);

        return userInfos;
    }

    public static UserInfo query(String username) {
        if(username == null) {
            return new UserInfo();
        }

        OrmPredicates predicates = ormContext.where(UserInfo.class);
        predicates.equalTo("username", username);

        List<UserInfo> userInfos = ormContext.query(predicates);

        // 只取其一
        return userInfos.isEmpty()? null:userInfos.get(0);
    }

    public static int insert(UserInfo userInfo) {
        LogUtil.info(TAG, "before insert");

        // 插入数据库
        ormContext.insert(userInfo);
        boolean isSuccessed = ormContext.flush();

        // 获取userId
        int userId = userInfo.getUserId();

        LogUtil.info(TAG, "end insert: " + userId);
        return userId;
    }

    public static int deleteAll() {
        LogUtil.info(TAG, "before delete");
        OrmPredicates predicates = ormContext.where(UserInfo.class);
        List<UserInfo> userInfos = ormContext.query(predicates);

        userInfos.forEach(userInfo -> {
            boolean isSuccessed = ormContext.delete(userInfo);
            LogUtil.info(TAG, "delete userInfo:" + userInfo.getUserId() + ",isSuccessed：" + isSuccessed);
        });

        boolean isSuccessed = ormContext.flush();

        LogUtil.info(TAG, "end delete, isSuccessed:" + isSuccessed);

        return userInfos.size();
    }

    public static int update(UserInfo UserInfo) {
        LogUtil.info(TAG, "before update");

        ormContext.update(UserInfo);

        boolean isSuccessed = ormContext.flush();

        LogUtil.info(TAG, "end update, isSuccessed: " + isSuccessed);
        return 1;
    }
}
