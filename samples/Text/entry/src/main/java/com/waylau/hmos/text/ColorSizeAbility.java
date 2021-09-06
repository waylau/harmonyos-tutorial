package com.waylau.hmos.text;

import com.waylau.hmos.text.slice.ColorSizeAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ColorSizeAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ColorSizeAbilitySlice.class.getName());
    }
}
