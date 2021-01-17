package com.waylau.hmos.button.slice;

import com.waylau.hmos.button.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

public class ClickedListenerAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_clicked_listener);

        Button button =
                (Button) findComponentById(ResourceTable.Id_button_clicked_listener);

        // 为按钮设置点击事件回调
        button.setClickedListener(listener ->
                button.setText("Button was clicked!"));
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
