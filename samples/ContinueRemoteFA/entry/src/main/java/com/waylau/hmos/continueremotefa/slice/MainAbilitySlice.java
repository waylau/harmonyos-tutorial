package com.waylau.hmos.continueremotefa.slice;

import com.waylau.hmos.continueremotefa.DeviceUtils;
import com.waylau.hmos.continueremotefa.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 监听跨端迁移FA的事件
        Button buttonContinueRemoteFA = (Button)findComponentById(ResourceTable.Id_button_continue_remote_fa);
        buttonContinueRemoteFA.setClickedListener(listener -> continueRemoteFA());

        // 监听拉回迁移FA的事件
        Button buttonContinueEversibly = (Button)findComponentById(ResourceTable.Id_button_continue_eversibly);
        buttonContinueEversibly.setClickedListener(listener -> continueEversibly());
    }


    private void continueRemoteFA() {
        HiLog.info(LABEL_LOG, "before startRemoteFA");

        String deviceId = DeviceUtils.getDeviceId();

        HiLog.info(LABEL_LOG, "get deviceId: %{public}s", deviceId);

        if (deviceId != null) {
            // 发起迁移流程
            continueAbility(deviceId);
        }
    }

    private void continueEversibly() {
        // 发起回迁流程
        continueEversibly();
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
