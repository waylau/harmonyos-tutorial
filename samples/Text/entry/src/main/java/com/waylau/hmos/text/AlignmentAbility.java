package com.waylau.hmos.text;

import com.waylau.hmos.text.slice.AlignmentAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AlignmentAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AlignmentAbilitySlice.class.getName());
    }
}
