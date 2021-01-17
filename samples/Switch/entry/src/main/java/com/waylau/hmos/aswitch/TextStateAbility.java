package com.waylau.hmos.aswitch;

import com.waylau.hmos.aswitch.slice.TextStateAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class TextStateAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(TextStateAbilitySlice.class.getName());
    }
}
