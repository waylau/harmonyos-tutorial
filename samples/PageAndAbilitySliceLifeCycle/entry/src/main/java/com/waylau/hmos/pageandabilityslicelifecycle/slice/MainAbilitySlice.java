package com.waylau.hmos.pageandabilityslicelifecycle.slice;

import com.waylau.hmos.pageandabilityslicelifecycle.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbilitySlice extends AbilitySlice {

    static final HiLogLabel logLabel = new HiLogLabel(HiLog.LOG_APP, 0x00001, "MainAbilitySlice");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发导航
        Text text = (Text) findComponentById(ResourceTable.Id_text_helloworld);
        text.setClickedListener(listener ->
                present(new PayAbilitySlice(), new Intent()));

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
    public  void onInactive() {
        HiLog.info(logLabel, "onInactive");
    }

    @Override
    public  void onBackground() {
        HiLog.info(logLabel, "onBackground");
    }

    @Override
    public  void onStop() {
        HiLog.info(logLabel, "onStop");
    }

}
