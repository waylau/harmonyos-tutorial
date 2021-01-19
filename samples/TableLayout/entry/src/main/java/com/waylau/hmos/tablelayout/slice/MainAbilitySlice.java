package com.waylau.hmos.tablelayout.slice;

import com.waylau.hmos.tablelayout.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.element.FrameAnimationElement;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        FrameAnimationElement frameAnimationElement =
                new FrameAnimationElement(getContext(),
                        ResourceTable.Graphic_background_ability_main);

        // 创建一个组件用于承载帧动画
        Component component = new Component(getContext());
        component.setWidth(500);
        component.setHeight(500);
        component.setBackground(frameAnimationElement);

        // 启动动画
        frameAnimationElement.start();
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
