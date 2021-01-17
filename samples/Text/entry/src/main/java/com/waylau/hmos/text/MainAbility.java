package com.waylau.hmos.text;

import com.waylau.hmos.text.slice.AutoFontSizeAbilitySlice;
import com.waylau.hmos.text.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
    }
}
