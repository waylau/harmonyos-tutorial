package com.waylau.hmos.directionallayoutwithxml.slice;

import com.waylau.hmos.directionallayoutwithxml.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;

public class PayAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);

        // 加载XML布局作为根布局
        super.setUIContent(ResourceTable.Layout_ability_pay);

        // 获取组件
        Text textPay = (Text) findComponentById(ResourceTable.Id_text_pay);

        // 设置组件的属性
        ShapeElement background = new ShapeElement();
        background.setRgbColor(new RgbColor(0, 125, 255));
        background.setCornerRadius(25);
        textPay.setBackground(background);
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
