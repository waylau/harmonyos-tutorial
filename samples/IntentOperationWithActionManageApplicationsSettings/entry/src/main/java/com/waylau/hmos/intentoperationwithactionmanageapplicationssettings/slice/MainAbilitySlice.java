package com.waylau.hmos.intentoperationwithactionmanageapplicationssettings.slice;

import com.waylau.hmos.intentoperationwithactionmanageapplicationssettings.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.IntentConstants;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发请求
        Text text = (Text) findComponentById(ResourceTable.Id_text_helloworld);
        text.setClickedListener(listener -> this.goToSetting());

    }

    private void goToSetting( ) {
        HiLog.info(LABEL_LOG, "before goToSetting");
        Operation operation = new Intent.OperationBuilder()
                    .withAction(IntentConstants.ACTION_MANAGE_APPLICATIONS_SETTINGS) // 应用管理
                    .build();

        Intent intent = new Intent();
        intent.setOperation(operation);

        // 启动Ability
        startAbility(intent);

        HiLog.info(LABEL_LOG, "after goToSetting");
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
