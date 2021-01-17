package com.waylau.hmos.button;

import com.waylau.hmos.button.slice.ClickedListenerAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ClickedListenerAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ClickedListenerAbilitySlice.class.getName());
    }
}
