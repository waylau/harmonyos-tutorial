package com.waylau.hmos.picke;

import com.waylau.hmos.picke.slice.TimePickerSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class TimePicker extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(TimePickerSlice.class.getName());
    }
}
