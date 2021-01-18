package com.waylau.hmos.picke;

import com.waylau.hmos.picke.slice.DatePickerAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class DatePickerAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(DatePickerAbilitySlice.class.getName());
    }
}
