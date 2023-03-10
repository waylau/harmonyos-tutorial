package com.waylau.hmos.commondialog.slice;

import com.waylau.hmos.commondialog.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 获取Button
        Button button = findComponentById(ResourceTable.Id_button_common_dialog);
        button.setClickedListener( listner -> {
            // 构造CommonDialog
            CommonDialog commonDialog = new CommonDialog(getContext());
            commonDialog.setTitleText("通知");
            commonDialog.setContentText("禁止划水");
            commonDialog.setContentImage(ResourceTable.Media_icon);
            commonDialog.setButton(IDialog.BUTTON1, "确定",  (iDialog, i) -> iDialog.destroy());

            commonDialog.show();
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
