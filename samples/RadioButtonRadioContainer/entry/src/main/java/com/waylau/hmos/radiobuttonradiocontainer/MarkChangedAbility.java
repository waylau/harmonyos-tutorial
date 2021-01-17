package com.waylau.hmos.radiobuttonradiocontainer;

import com.waylau.hmos.radiobuttonradiocontainer.slice.MarkChangedAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MarkChangedAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MarkChangedAbilitySlice.class.getName());
    }
}
