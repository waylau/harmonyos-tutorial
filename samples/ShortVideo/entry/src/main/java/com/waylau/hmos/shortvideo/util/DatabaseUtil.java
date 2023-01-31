/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.util;

import com.waylau.hmos.shortvideo.store.DataStore;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

/**
 * Database util
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-30
 */
public class DatabaseUtil {
    private static final String TAG = DatabaseUtil.class.getSimpleName();

    // 数据库别
    private static final String DATABASE_NAME = "ShortVideoStore.db";

    // 数据库别名
    private static final String DATABASE_NAME_ALIAS = "ShortVideoStore";

    private static DatabaseHelper helper = null;
    private static OrmContext ormContext = null;

    private DatabaseUtil() {}

    /**
     * 获取OrmContext
     *
     * @param context context
     * @return OrmContext
     */
    public static void initStore(Context context) {
        // 初始化OrmContext
        if (helper == null || ormContext == null) {
            helper = new DatabaseHelper(context);
            ormContext = helper.getOrmContext(DATABASE_NAME_ALIAS, DATABASE_NAME, DataStore.class);
        }
    }

    /**
     * 获取OrmContext
     *
     * @return OrmContext
     */
    public static OrmContext getOrmContext() {
        return ormContext;
    }

    /**
     * 删除存储
     */
    public static void deleteStore() {
        // 删除数据库
        helper.deleteRdbStore(DATABASE_NAME);
        helper = null;
        ormContext = null;
    }

}
