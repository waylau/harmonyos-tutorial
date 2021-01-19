package com.waylau.hmos.dependentlayout;

import com.waylau.hmos.dependentlayout.slice.FullAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class FullAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(FullAbilitySlice.class.getName());
    }
}
