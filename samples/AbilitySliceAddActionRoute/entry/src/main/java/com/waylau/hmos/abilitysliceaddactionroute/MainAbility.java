package com.waylau.hmos.abilitysliceaddactionroute;

import com.waylau.hmos.abilitysliceaddactionroute.slice.MainAbilitySlice;
import com.waylau.hmos.abilitysliceaddactionroute.slice.PayAbilitySlice;
import com.waylau.hmos.abilitysliceaddactionroute.slice.ScanAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        // 指定默认展示的AbilitySlice
        super.setMainRoute(MainAbilitySlice.class.getName());

        // 配置路由规则
        addActionRoute("action.pay", PayAbilitySlice.class.getName());
        addActionRoute("action.scan", ScanAbilitySlice.class.getName());
    }
}
