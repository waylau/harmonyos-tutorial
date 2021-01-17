package com.waylau.hmos.textfield;

import com.waylau.hmos.textfield.slice.MultipleLinesAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MultipleLinesAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MultipleLinesAbilitySlice.class.getName());
    }
}
