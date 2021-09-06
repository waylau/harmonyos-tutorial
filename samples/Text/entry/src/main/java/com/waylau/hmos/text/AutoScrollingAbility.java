package com.waylau.hmos.text;

import com.waylau.hmos.text.slice.AutoScrollingAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AutoScrollingAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AutoScrollingAbilitySlice.class.getName());
    }
}
