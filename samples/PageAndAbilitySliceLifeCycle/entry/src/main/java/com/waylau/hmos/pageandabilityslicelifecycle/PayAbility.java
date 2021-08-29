package com.waylau.hmos.pageandabilityslicelifecycle;

import com.waylau.hmos.pageandabilityslicelifecycle.slice.PayAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class PayAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(PayAbilitySlice.class.getName());
    }
}
