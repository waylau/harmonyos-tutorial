package com.waylau.hmos.distributedschedulingstartremotefa.slice;

import com.waylau.hmos.distributedschedulingstartremotefa.DeviceUtils;
import com.waylau.hmos.distributedschedulingstartremotefa.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;


public class MainAbilitySlice extends AbilitySlice implements IAbilityContinuation {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private static final String BUNDLE_NAME = "com.waylau.hmos.remotefa";
    private static final String ABILITY_NAME = BUNDLE_NAME + ".MainAbility";

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发导航
        Text text = (Text) findComponentById(ResourceTable.Id_text_helloworld);
        text.setClickedListener(listener -> startRemoteFA());
    }

    // 启动远程PA
    private void startRemoteFA() {
        HiLog.info(LABEL_LOG, "before startRemoteFA");

        String deviceId = DeviceUtils.getDeviceId();

        HiLog.info(LABEL_LOG, "get deviceId: %{public}s", deviceId);

        Intent intent = new Intent();
        Operation operation;

        // 指定待启动FA的bundleName和abilityName
        if (deviceId.isEmpty()) {
            // 不涉及分布式
            operation = new Intent.OperationBuilder()
                    .withDeviceId(deviceId)
                    .withBundleName(BUNDLE_NAME)
                    .withAbilityName(ABILITY_NAME)
                    .build();
            intent.setOperation(operation);
        } else {
            // 设置分布式标记，表明当前涉及分布式能力
            operation = new Intent.OperationBuilder()
                    .withDeviceId(deviceId)
                    .withBundleName(BUNDLE_NAME)
                    .withAbilityName(ABILITY_NAME)
                    .withFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE) // 设置分布式标记
                    .build();
        }

        intent.setOperation(operation);

        // 通过AbilitySlice包含的startAbility接口实现跨设备启动FA
        startAbility(intent);

        HiLog.info(LABEL_LOG, "after startRemoteFA");
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
        return true;
    }

    @Override
    public boolean onSaveData(IntentParams intentParams) {
        return true;
    }

    @Override
    public boolean onRestoreData(IntentParams intentParams) {
        return true;
    }

    @Override
    public void onCompleteContinuation(int i) {

    }
}
