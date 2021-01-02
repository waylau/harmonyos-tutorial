package com.waylau.hmos.dataabilityhelperaccessdatabase.slice;

import com.waylau.hmos.dataabilityhelperaccessdatabase.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.data.rdb.ValuesBucket;
import ohos.data.resultset.ResultSet;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;

public class MainAbilitySlice extends AbilitySlice {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00001, "MainAbilitySlice");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发访问数据
        Text text = (Text) findComponentById(ResourceTable.Id_text_helloworld);
        text.setClickedListener(listener -> this.doDatabaseAction());
    }

    private void doDatabaseAction() {

        DataAbilityHelper helper = DataAbilityHelper.creator(this);
        // 访问数据用的URI，注意用三个斜杠
        Uri uri =
                Uri.parse("dataability:///com.waylau.hmos.dataabilityhelperaccessdatabase.UserDataAbility");
        String[] columns = {"user_id", "user_name", "user_age"};

        // 查询
        doQuery(helper, uri, columns);

        // 插入
        doInsert(helper, uri, columns);

        // 查询
        doQuery(helper, uri, columns);

        // 更新
        doUpdate(helper, uri, columns);

        // 查询
        doQuery(helper, uri, columns);

        // 删除
        doDelete(helper, uri, columns);

        // 查询
        doQuery(helper, uri, columns);
    }

    private void doQuery(DataAbilityHelper helper, Uri uri, String[] columns) {
        // 构造查询条件
        DataAbilityPredicates predicates = new DataAbilityPredicates();
        predicates.between("user_id", 101, 200);

        // 进行查询
        ResultSet resultSet = null;
        try {
            resultSet = helper.query(uri, columns, predicates);

        } catch (DataAbilityRemoteException e) {
            e.printStackTrace();
        }


        if (resultSet != null && resultSet.getRowCount() > 0) {
            // 在此处理ResultSet中的记录
            while (resultSet.goToNextRow()) {
                // 处理结果
                HiLog.info(LABEL_LOG, "resultSet user_id：%{public}s, user_name：%{public}s, user_age：%{public}s",
                        resultSet.getInt(0), resultSet.getString(1), resultSet.getInt(2));
            }
        } else {
            HiLog.info(LABEL_LOG, "resultSet is null or row count is 0");
        }
    }

    private void doInsert(DataAbilityHelper helper, Uri uri, String[] columns) {
        // 构造查询条件
        DataAbilityPredicates predicates = new DataAbilityPredicates();
        predicates.between("user_Id", 101, 103);

        // 构造插入数据
        ValuesBucket valuesBucket = new ValuesBucket();
        valuesBucket.putInteger(columns[0], 101);
        valuesBucket.putString(columns[1], "Way Lau");
        valuesBucket.putInteger(columns[2], 33);

        try {
            int result = helper.insert(uri, valuesBucket);
            HiLog.info(LABEL_LOG, "insert result：%{public}s", result);
        } catch (DataAbilityRemoteException e) {
            e.printStackTrace();
        }
    }

    private void doUpdate(DataAbilityHelper helper, Uri uri, String[] columns) {
        // 构造查询条件
        DataAbilityPredicates predicates = new DataAbilityPredicates();
        predicates.equalTo("user_id", 101);

        // 构造更新数据
        ValuesBucket valuesBucket = new ValuesBucket();
        valuesBucket.putInteger(columns[0], 101);
        valuesBucket.putString(columns[1], "Way Lau");
        valuesBucket.putInteger(columns[2], 35);

        try {
            int result = helper.update(uri, valuesBucket, predicates);

            HiLog.info(LABEL_LOG, "update result：%{public}s", result);
        } catch (DataAbilityRemoteException e) {
            e.printStackTrace();
        }
    }


    private void doDelete(DataAbilityHelper helper, Uri uri, String[] columns) {
        // 构造查询条件
        DataAbilityPredicates predicates = new DataAbilityPredicates();
        predicates.equalTo("user_id", 101);

        try {
            int result = helper.delete(uri, predicates);

            HiLog.info(LABEL_LOG, "delete result：%{public}s", result);
        } catch (DataAbilityRemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
