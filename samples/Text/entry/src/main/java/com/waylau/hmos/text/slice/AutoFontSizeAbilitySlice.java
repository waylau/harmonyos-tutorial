package com.waylau.hmos.text.slice;

import com.waylau.hmos.text.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Text;

public class AutoFontSizeAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_auto_font_size);

        Text textAutoFontSize = (Text) findComponentById(ResourceTable.Id_text_auto_font_size);

        // 设置自动调整规则
        textAutoFontSize.setAutoFontSizeRule(30, 100, 1);

        // 设置点击一次增多一个"!"
        textAutoFontSize.setClickedListener(listener ->
                textAutoFontSize.setText(textAutoFontSize.getText() + "!"));
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