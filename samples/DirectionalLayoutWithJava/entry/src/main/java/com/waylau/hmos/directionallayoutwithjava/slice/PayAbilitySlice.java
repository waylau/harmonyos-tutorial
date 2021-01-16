package com.waylau.hmos.directionallayoutwithjava.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.LayoutAlignment;

public class PayAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);

        // 声明布局
        DirectionalLayout directionalLayout = new DirectionalLayout(getContext());

        // 设置布局大小
        directionalLayout.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
        directionalLayout.setHeight(ComponentContainer.LayoutConfig.MATCH_PARENT);

        // 设置布局属性
        directionalLayout.setOrientation(Component.VERTICAL);

        // 将布局添加到组件树中
        setUIContent(directionalLayout);

        // 声明Text组件
        Text textPay = new Text(getContext());
        textPay.setText("Show me the money");
        textPay.setTextSize(25, Text.TextSizeType.VP);
        textPay.setId(1);

        // 设置组件的属性
        ShapeElement background = new ShapeElement();
        background.setRgbColor(new RgbColor(0, 125, 255));
        background.setCornerRadius(25);
        textPay.setBackground(background);

        // 为组件添加对应布局的布局属性
        DirectionalLayout.LayoutConfig layoutConfig =
                new DirectionalLayout.LayoutConfig(ComponentContainer.LayoutConfig.MATCH_CONTENT,
                        ComponentContainer.LayoutConfig.MATCH_CONTENT);
        layoutConfig.alignment = LayoutAlignment.HORIZONTAL_CENTER;
        textPay.setLayoutConfig(layoutConfig);

        // 将组件添加到布局中（视布局需要对组件设置布局属性进行约束）
        directionalLayout.addComponent(textPay);
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
