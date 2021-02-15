package com.waylau.hmos.dataabilityhelperaccessorm;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;
import ohos.data.resultset.ResultSet;
import ohos.data.rdb.ValuesBucket;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;
import ohos.utils.PacMap;

import java.io.FileDescriptor;
import java.util.List;

public class UserDataAbility extends Ability {
    private static final String TAG = UserDataAbility.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private static final String DATABASE_NAME = "RdbStoreTest.db";
    private static final String DATABASE_NAME_ALIAS = TAG;

    private DatabaseHelper manager;
    private OrmContext ormContext = null;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        HiLog.info(LABEL_LOG, "UserDataAbility onStart");

        // 初始化DatabaseHelper、OrmContext
        manager = new DatabaseHelper(this);
        ormContext = manager.getOrmContext(DATABASE_NAME_ALIAS, DATABASE_NAME, UserStore.class);
    }

    @Override
    public void onStop() {
        super.onStop();
        HiLog.info(LABEL_LOG, "UserDataAbility onStop");

        // 删除数据库
        manager.deleteRdbStore(DATABASE_NAME);
    }

    public List<User> queryAll() {
        OrmPredicates predicates = ormContext.where(User.class);
        List<User> users = ormContext.query(predicates);
        users.forEach(user -> {
            HiLog.info(LABEL_LOG, "query user: %{public}s", user);
        });

        return users;
    }

    public int insert(User user) {
        HiLog.info(LABEL_LOG, "before insert");

        // 插入数据库
        ormContext.insert(user);
        boolean isSuccessed = ormContext.flush();

        // 获取UserId
        int userId = user.getUserId();

        HiLog.info(LABEL_LOG, "end insert: %{public}s, isSuccessed: %{public}s",
                userId, isSuccessed);
        return userId;
    }

    public int deleteAll() {
        HiLog.info(LABEL_LOG, "before delete");
        OrmPredicates predicates = ormContext.where(User.class);
        List<User> users = ormContext.query(predicates);

        users.forEach(user -> {
            boolean isSuccessed = ormContext.delete(user);
            HiLog.info(LABEL_LOG, "delete user: %{public}s, isSuccessed：%{public}s",
                    user.getUserId(), isSuccessed);
        });

        boolean isSuccessed = ormContext.flush();

        HiLog.info(LABEL_LOG, "end delete, isSuccessed: %{public}s",
                isSuccessed);

        return users.size();
    }

    public int update(User user) {
        HiLog.info(LABEL_LOG, "before update");

        ormContext.update(user);

        boolean isSuccessed = ormContext.flush();

        HiLog.info(LABEL_LOG, "end update, isSuccessed: %{public}s",
                isSuccessed);
        return 1;
    }
}