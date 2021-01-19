package com.waylau.hmos.directionallayout;

import com.waylau.hmos.directionallayout.slice.WeightAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class WeightAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(WeightAbilitySlice.class.getName());
    }
}
