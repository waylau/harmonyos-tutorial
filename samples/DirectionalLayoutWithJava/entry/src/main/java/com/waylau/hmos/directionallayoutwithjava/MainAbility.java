package com.waylau.hmos.directionallayoutwithjava;

import com.waylau.hmos.directionallayoutwithjava.slice.MainAbilitySlice;
import com.waylau.hmos.directionallayoutwithjava.slice.PayAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        //super.setMainRoute(MainAbilitySlice.class.getName());

        super.setMainRoute(PayAbilitySlice.class.getName());
    }
}
