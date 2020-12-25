package com.waylau.hmos.abilityslicenavigation;

import com.waylau.hmos.abilityslicenavigation.slice.MainAbilitySlice;
import com.waylau.hmos.abilityslicenavigation.slice.PayAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);

        // 指定默认显示的AbilitySlice
        super.setMainRoute(MainAbilitySlice.class.getName());

        // 使用addActionRounte方法添加路由
        addActionRoute("action.pay", PayAbilitySlice.class.getName());
    }
}
