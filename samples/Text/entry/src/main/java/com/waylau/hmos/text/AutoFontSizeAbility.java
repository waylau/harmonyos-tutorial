package com.waylau.hmos.text;

import com.waylau.hmos.text.slice.AutoFontSizeAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AutoFontSizeAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AutoFontSizeAbilitySlice.class.getName());
    }
}
