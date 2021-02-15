package com.waylau.hmos.preferences.slice;

import com.waylau.hmos.preferences.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.data.DatabaseHelper;
import ohos.data.preferences.Preferences;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private static final String PREFERENCES_FILE = "preferences-file";
    private static final String PREFERENCES_KEY = "preferences-key";

    private DatabaseHelper databaseHelper;
    private Preferences preferences;
    private Preferences.PreferencesObserver observer;
    private Text text;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化数据
        initData();

        // 添加点击事件来触发访问数据
        Button buttonQuery = (Button) findComponentById(ResourceTable.Id_button_query);
        buttonQuery.setClickedListener(listener -> this.doQuery());

        Button buttonInsert = (Button) findComponentById(ResourceTable.Id_button_insert);
        buttonInsert.setClickedListener(listener -> this.doInsert());

        Button buttonUpdate = (Button) findComponentById(ResourceTable.Id_button_update);
        buttonUpdate.setClickedListener(listener -> this.doUpdate());

        Button buttonDelete = (Button) findComponentById(ResourceTable.Id_button_delete);
        buttonDelete.setClickedListener(listener -> this.doDelete());

        text = (Text) findComponentById(ResourceTable.Id_text);
    }

    private void initData() {
        HiLog.info(LABEL_LOG, "before initData");

        databaseHelper = new DatabaseHelper(this.getContext());

        // fileName表示文件名，其取值不能为空，也不能包含路径，
        // 默认存储目录可以通过context.getPreferencesDir()获取。
        preferences = databaseHelper.getPreferences(PREFERENCES_FILE);

        // 观察者
        observer = new MyPreferencesObserver();

        HiLog.info(LABEL_LOG, "end initData");
    }

    @Override
    public void onStop() {
        super.onStop();

        // 从内存中移除指定文件对应的Preferences单实例，
        // 并删除指定文件及其备份文件、损坏文件
        boolean result = databaseHelper.deletePreferences(PREFERENCES_FILE);
    }

    private void doQuery() {
        HiLog.info(LABEL_LOG, "before doQuery");

        // 查询
        String result = preferences.getString(PREFERENCES_KEY, "");

        // 用户的信息显示在界面
        text.setText(result + "\n");

        HiLog.info(LABEL_LOG, "end doQuery, result: %{public}s", result);
    }

    private void doInsert() {
        HiLog.info(LABEL_LOG, "before doInsert");

        // 生成随机数据
        String data = "d" + System.currentTimeMillis();

        // 将数据写入Preferences实例
        preferences.putString(PREFERENCES_KEY, data);

        // 注册观察者
        preferences.registerObserver(observer);

        // 通过flush或者flushSync将Preferences实例持久化
        preferences.flush(); // 异步
        // preferences.flushSync(); // 同步

        HiLog.info(LABEL_LOG, "end doInsert, data: %{public}s", data);
    }

    private void doUpdate() {
        HiLog.info(LABEL_LOG, "before doUpdate");

        // 更新就是从新做一次插入
        doInsert();

        HiLog.info(LABEL_LOG, "end doUpdate");
    }

    private void doDelete() {
        HiLog.info(LABEL_LOG, "before doDelete");

        // 删除
        preferences.delete(PREFERENCES_KEY);

        HiLog.info(LABEL_LOG, "end doDelete");
    }

    private class MyPreferencesObserver implements Preferences.PreferencesObserver {

        @Override
        public void onChange(Preferences preferences, String key) {
            HiLog.info(LABEL_LOG, "onChange, key: %{public}s", key);
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
