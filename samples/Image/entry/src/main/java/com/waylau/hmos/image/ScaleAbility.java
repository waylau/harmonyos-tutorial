package com.waylau.hmos.image;

import com.waylau.hmos.image.slice.ScaleAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ScaleAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ScaleAbilitySlice.class.getName());
    }
}
