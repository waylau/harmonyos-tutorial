package com.waylau.hmos.checkbox;

import com.waylau.hmos.checkbox.slice.OnOffAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class OnOffAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(OnOffAbilitySlice.class.getName());
    }
}
