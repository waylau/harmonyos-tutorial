package com.waylau.hmos.picker;

import com.waylau.hmos.picker.slice.DatePickerAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class DatePickerAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(DatePickerAbilitySlice.class.getName());
    }
}
