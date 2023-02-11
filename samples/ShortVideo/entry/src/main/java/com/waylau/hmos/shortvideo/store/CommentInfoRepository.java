/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.store;

import java.util.List;

import com.waylau.hmos.shortvideo.bean.CommentInfo;
import com.waylau.hmos.shortvideo.util.DatabaseUtil;
import com.waylau.hmos.shortvideo.util.LogUtil;

import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;

/**
 * 评论信息存储
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-11
 */
public class CommentInfoRepository {
    private static final String TAG = CommentInfoRepository.class.getSimpleName();
    private static OrmContext ormContext = DatabaseUtil.getOrmContext();

    public static List<CommentInfo> queryByUsernameAndVideoId(String username, Integer videoId) {
        OrmPredicates predicates = ormContext.where(CommentInfo.class);
        predicates.equalTo("username", username);
        predicates.equalTo("videoId", videoId);
        List<CommentInfo> videoInfos = ormContext.query(predicates);

        return videoInfos;
    }

    public static int insert(CommentInfo videoInfo) {
        LogUtil.info(TAG, "before insert");

        // 插入数据库
        ormContext.insert(videoInfo);
        boolean isSuccessed = ormContext.flush();

        // 获取Id
        int id = videoInfo.getCommentId();

        LogUtil.info(TAG, "end insert: " + id + ", isSuccessed: " + isSuccessed);
        return id;
    }

}
