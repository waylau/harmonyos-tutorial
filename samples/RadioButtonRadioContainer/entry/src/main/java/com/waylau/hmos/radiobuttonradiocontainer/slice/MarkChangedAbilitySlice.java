package com.waylau.hmos.radiobuttonradiocontainer.slice;

import com.waylau.hmos.radiobuttonradiocontainer.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.RadioContainer;
import ohos.agp.components.Text;

public class MarkChangedAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_mark_changed);

        // 获取Text
        Text answer = (Text) findComponentById(ResourceTable.Id_text_answer);

        // 获取RadioContainer
        RadioContainer radioContainer =
                (RadioContainer) findComponentById(ResourceTable.Id_radio_container);

        // 设置状态监听
        radioContainer.setMarkChangedListener((radioContainer1, index) -> {
            answer.setText((++index) + "");
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}