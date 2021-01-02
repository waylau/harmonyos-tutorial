package com.waylau.hmos.dataabilityhelperaccessdatabase;

import ohos.data.orm.OrmDatabase;
import ohos.data.rdb.RdbOpenCallback;

public class UserStore extends OrmDatabase {

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public RdbOpenCallback getHelper() {
        return null;
    }
}
