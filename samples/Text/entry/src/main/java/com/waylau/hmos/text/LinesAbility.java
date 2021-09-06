package com.waylau.hmos.text;

import com.waylau.hmos.text.slice.LinesAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class LinesAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(LinesAbilitySlice.class.getName());
    }
}
