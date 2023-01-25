package com.waylau.hmos.shortvideo;

import com.waylau.hmos.shortvideo.slice.MePageAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MePageAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MePageAbilitySlice.class.getName());
    }
}
