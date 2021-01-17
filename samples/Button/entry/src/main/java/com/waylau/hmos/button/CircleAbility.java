package com.waylau.hmos.button;

import com.waylau.hmos.button.slice.CircleAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class CircleAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(CircleAbilitySlice.class.getName());
    }
}
