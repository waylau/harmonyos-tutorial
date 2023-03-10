package com.waylau.hmos.toastdialog.slice;

import com.waylau.hmos.toastdialog.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件，触发ToastDialog
        Button button = findComponentById(ResourceTable.Id_button_base_toast);
        button.setClickedListener( listener -> {
            // 获取自定义的布局
            DirectionalLayout directionalLayout = (DirectionalLayout)LayoutScatter.getInstance(this)
                    .parse(ResourceTable.Layout_ability_toast_dialog, null, false);

            new ToastDialog(getContext())
                    .setText("This is base ToastDialog")
                    // 设置位置
                    .setAlignment(LayoutAlignment.CENTER) // 居中
                    // 自定义组件
                    .setContentCustomComponent(directionalLayout)
                    .show();
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
