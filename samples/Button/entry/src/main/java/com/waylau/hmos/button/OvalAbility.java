package com.waylau.hmos.button;

import com.waylau.hmos.button.slice.OvalAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class OvalAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(OvalAbilitySlice.class.getName());
    }
}
