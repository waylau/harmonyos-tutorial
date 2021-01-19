package com.waylau.hmos.roundprogressbar;

import com.waylau.hmos.roundprogressbar.slice.StartMaxAngleAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class StartMaxAngleAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(StartMaxAngleAbilitySlice.class.getName());
    }
}
