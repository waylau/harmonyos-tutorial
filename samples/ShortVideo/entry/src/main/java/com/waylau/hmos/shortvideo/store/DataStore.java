/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.store;

import com.waylau.hmos.shortvideo.bean.*;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

/**
 * 数据存储
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-01
 */
@Database(entities = {VideoInfo.class, UserInfo.class, MeThumbsupVideoInfo.class, MeFavoriteVideoInfo.class,
    UserFollowInfo.class, CommentInfo.class}, version = 1)
public abstract class DataStore extends OrmDatabase {}