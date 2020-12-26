package com.waylau.hmos.pageandabilityslicelifecycle;

import com.waylau.hmos.pageandabilityslicelifecycle.slice.MainAbilitySlice;
import com.waylau.hmos.pageandabilityslicelifecycle.slice.PayAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());

        // 使用addActionRounte方法添加路由
        addActionRoute("action.pay", PayAbilitySlice.class.getName());
    }
}
