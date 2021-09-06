package com.waylau.hmos.text;

import com.waylau.hmos.text.slice.ItalicWeightAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ItalicWeightAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ItalicWeightAbilitySlice.class.getName());
    }
}
