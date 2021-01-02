package com.waylau.hmos.dataabilityhelperaccessdatabase;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.content.Intent;
import ohos.app.AbilityContext;
import ohos.data.DatabaseHelper;
import ohos.data.dataability.DataAbilityUtils;
import ohos.data.orm.OrmPredicates;
import ohos.data.rdb.*;
import ohos.data.resultset.ResultSet;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;
import ohos.utils.PacMap;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class UserDataAbility extends Ability {
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, "UserDataAbility");
    private static final String DATABASE_NAME = "RdbStoreTest.db";
    private static final String TABLE_NAME = "user_t";
    private RdbStore store = null;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        HiLog.info(LABEL_LOG, "UserDataAbility onStart");

        // 创建数据库连接，并获取连接对象
        DatabaseHelper helper = new DatabaseHelper(this);
        StoreConfig config = StoreConfig.newDefaultConfig(DATABASE_NAME);
        RdbOpenCallback callback = new RdbOpenCallback() {

            // 初始化数据库
            public void onCreate(RdbStore store) {
                store.executeSql("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        "(user_id INTEGER PRIMARY KEY, user_name TEXT NOT NULL, user_age INTEGER)");
            }

            @Override
            public void onUpgrade(RdbStore store, int oldVersion, int newVersion) {
            }
        };
        store = helper.getRdbStore(config, 1, callback, null);
    }

    @Override
    public ResultSet query(Uri uri, String[] columns, DataAbilityPredicates predicates) {

        if (store == null) {
            HiLog.error(LABEL_LOG, "failed to query, ormContext is null");
            return null;
        }

        // 查询数据库
        RdbPredicates rdbPredicates = DataAbilityUtils.createRdbPredicates(predicates, TABLE_NAME);
        ResultSet resultSet = store.query(rdbPredicates, columns);
        if (resultSet == null) {
            HiLog.info(LABEL_LOG, "resultSet is null");
        }

        // 返回结果
        return resultSet;
    }

    @Override
    public int insert(Uri uri, ValuesBucket value) {
        HiLog.info(LABEL_LOG, "UserDataAbility insert");
        // 参数校验
        if (store == null) {
            HiLog.error(LABEL_LOG, "failed to insert, ormContext is null");
            return -1;
        }

        // 插入数据库
        long userId = store.insert(TABLE_NAME, value);

        return Integer.valueOf(userId + "");
    }

    @Override
    public int delete(Uri uri, DataAbilityPredicates predicates) {
        if (store == null) {
            HiLog.error(LABEL_LOG, "failed to delete, ormContext is null");
            return -1;
        }

        RdbPredicates rdbPredicates =
                DataAbilityUtils.createRdbPredicates(predicates, TABLE_NAME);
        int value = store.delete(rdbPredicates);
        return value;
    }

    @Override
    public int update(Uri uri, ValuesBucket value, DataAbilityPredicates predicates) {
        if (store == null) {
            HiLog.error(LABEL_LOG, "failed to update, ormContext is null");
            return -1;
        }

        RdbPredicates rdbPredicates =
                DataAbilityUtils.createRdbPredicates(predicates, TABLE_NAME);
        int index = store.update(value, rdbPredicates);
        return index;
    }

    @Override
    public FileDescriptor openFile(Uri uri, String mode) {
        return null;
    }

    @Override
    public String[] getFileTypes(Uri uri, String mimeTypeFilter) {
        return new String[0];
    }

    @Override
    public PacMap call(String method, String arg, PacMap extras) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}