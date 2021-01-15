package com.waylau.hmos.directionallayoutwithxml;

import com.waylau.hmos.directionallayoutwithxml.slice.MainAbilitySlice;
import com.waylau.hmos.directionallayoutwithxml.slice.PayAbilitySlice;
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
