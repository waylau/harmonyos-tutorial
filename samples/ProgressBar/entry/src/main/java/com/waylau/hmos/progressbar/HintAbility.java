package com.waylau.hmos.progressbar;

import com.waylau.hmos.progressbar.slice.HintAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class HintAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(HintAbilitySlice.class.getName());
    }
}
