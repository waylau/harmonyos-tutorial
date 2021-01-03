package com.waylau.hmos.intentoperationwithaction;

import com.waylau.hmos.intentoperationwithaction.slice.WeatherAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class WeatherAbility extends Ability {
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, "WeatherAbility");
    private final static int CODE = 1;
    private static final String TEMP_KEY = "temperature";

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(WeatherAbilitySlice.class.getName());
    }

    @Override
    protected void onActive() {
        HiLog.info(LABEL_LOG, "before onActive");

        super.onActive();

        Intent resultIntent = new Intent();
        resultIntent.setParam(TEMP_KEY, "17");

        setResult(CODE, resultIntent); // 暂存返回结果

        HiLog.info(LABEL_LOG, "after onActive");
    }

}
