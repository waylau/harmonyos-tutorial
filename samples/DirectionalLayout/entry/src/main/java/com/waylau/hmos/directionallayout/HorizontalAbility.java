package com.waylau.hmos.directionallayout;

import com.waylau.hmos.directionallayout.slice.HorizontalAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class HorizontalAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(HorizontalAbilitySlice.class.getName());
    }
}
