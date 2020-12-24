package com.waylau.hmos.helloworld;

import com.waylau.hmos.helloworld.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);

        // 指定默认展示的AbilitySlice
        super.setMainRoute(MainAbilitySlice.class.getName());
    }
}
