package com.waylau.ohms.javaabilityslicenavigation.slice;

import com.waylau.ohms.javaabilityslicenavigation.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class PayAbilitySlice extends AbilitySlice {
    static final HiLogLabel logLabel =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, "PayAbilitySlice");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_pay);

        // 添加点击事件来触发导航
        Text text = (Text) findComponentById(ResourceTable.Id_text_pay);
        text.setClickedListener(listener ->
                present(new MainAbilitySlice(), new Intent()));

        HiLog.info(logLabel, "onStart");
    }

    @Override
    public void onActive() {
        super.onActive();
        HiLog.info(logLabel, "onActive");
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
        HiLog.info(logLabel, "onForeground");
    }

    @Override
    public void onInactive() {
        HiLog.info(logLabel, "onInactive");
    }

    @Override
    public void onBackground() {
        HiLog.info(logLabel, "onBackground");
    }

    @Override
    public void onStop() {
        HiLog.info(logLabel, "onStop");
    }

}
