package com.waylau.hmos.text;

import com.waylau.hmos.text.slice.TitleDetailAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class TitleDetailAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(TitleDetailAbilitySlice.class.getName());
    }
}
