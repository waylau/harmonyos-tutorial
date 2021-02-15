package com.waylau.hmos.dataabilityhelperaccessorm.slice;

import com.waylau.hmos.dataabilityhelperaccessorm.ResourceTable;
import com.waylau.hmos.dataabilityhelperaccessorm.User;
import com.waylau.hmos.dataabilityhelperaccessorm.UserDataAbility;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.zson.ZSONObject;

import java.util.List;
import java.util.Random;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private UserDataAbility userDataAbility = new UserDataAbility();

    private Text text;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        userDataAbility.onStart(intent);

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

    @Override
    public void onStop() {
        super.onStop();
        userDataAbility.onStop();
    }

    private void doQuery() {
        // 查询所有用户
        List<User> users = userDataAbility.queryAll();

        // 用户的信息显示在界面
        text.setText(ZSONObject.toZSONString(users) + "\n");
    }

    private void doInsert() {
        // 生成随机数据
        Random random = new Random();
        int age = random.nextInt();
        String name = "n" + System.currentTimeMillis();

        //  生成用户
        User user = new User();
        user.setUserName(name);
        user.setAge(age);

        // 插入用户
        userDataAbility.insert(user);
    }

    private void doUpdate() {
        // 查询所有用户
        List<User> users = userDataAbility.queryAll();

        // 更新所有用户
        users.forEach(user -> {
            user.setAge(43);
            userDataAbility.update(user);
        });
    }

    private void doDelete() {
        // 删除所有用户
        userDataAbility.deleteAll();
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
