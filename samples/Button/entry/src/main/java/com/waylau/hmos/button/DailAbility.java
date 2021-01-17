package com.waylau.hmos.button;

import com.waylau.hmos.button.slice.DailAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class DailAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(DailAbilitySlice.class.getName());
    }
}
