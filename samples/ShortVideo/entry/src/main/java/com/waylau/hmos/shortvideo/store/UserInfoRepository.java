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

}
