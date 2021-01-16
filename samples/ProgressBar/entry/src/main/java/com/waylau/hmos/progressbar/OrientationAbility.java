package com.waylau.hmos.progressbar;

import com.waylau.hmos.progressbar.slice.OrientationAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class OrientationAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(OrientationAbilitySlice.class.getName());
    }
}
