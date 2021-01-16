package com.waylau.hmos.progressbar;

import com.waylau.hmos.progressbar.slice.ElementAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ElementAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ElementAbilitySlice.class.getName());
    }
}
