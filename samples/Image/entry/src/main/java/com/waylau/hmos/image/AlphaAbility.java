package com.waylau.hmos.image;

import com.waylau.hmos.image.slice.AlphaAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AlphaAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AlphaAbilitySlice.class.getName());
    }
}
