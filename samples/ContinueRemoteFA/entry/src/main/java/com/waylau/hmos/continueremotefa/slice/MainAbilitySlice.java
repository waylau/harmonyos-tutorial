package com.waylau.hmos.continueremotefa.slice;

import com.waylau.hmos.continueremotefa.DeviceUtils;
import com.waylau.hmos.continueremotefa.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.agp.components.Button;
import ohos.agp.components.TextField;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * 设备迁移与回迁
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class MainAbilitySlice extends AbilitySlice implements IAbilityContinuation {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private static final String MESSAGE_KEY = "com.waylau.hmos.continueremotefa.slice.MESSAGE_KEY";

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 监听跨端迁移FA的事件
        Button buttonContinueRemoteFA = (
                Button) findComponentById(ResourceTable.Id_button_continue_remote_fa);
        buttonContinueRemoteFA.setClickedListener(listener -> continueRemoteFA());

        // 监听拉回迁移FA的事件
        Button buttonContinueEversibly =
                (Button) findComponentById(ResourceTable.Id_button_continue_eversibly);
        buttonContinueEversibly.setClickedListener(listener -> continueEversibly());
    }

    private void continueRemoteFA() {
        HiLog.info(LABEL_LOG, "before startRemoteFA");

        String deviceId = DeviceUtils.getDeviceId();

        HiLog.info(LABEL_LOG, "get deviceId: %{public}s", deviceId);

        if (deviceId != null) {
            // 发起迁移流程
            //  continueAbility()是不可回迁的
            //  continueAbilityReversibly() 是可以回迁的
            continueAbilityReversibly(deviceId);
        }
    }

    private void continueEversibly() {
        // 发起回迁流程
        reverseContinueAbility();
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    public boolean onStartContinuation() {
        // 重写
        return true;
    }

    @Override
    public boolean onSaveData(IntentParams intentParams) {
        // 重写
        return true;
    }

    @Override
    public boolean onRestoreData(IntentParams intentParams) {
        // 重写
        return true;
    }

    @Override
    public void onCompleteContinuation(int i) {

    }

}
