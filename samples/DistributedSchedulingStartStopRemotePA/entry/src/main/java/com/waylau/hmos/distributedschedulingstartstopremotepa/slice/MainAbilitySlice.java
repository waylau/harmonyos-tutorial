package com.waylau.hmos.distributedschedulingstartstopremotepa.slice;

import com.waylau.hmos.distributedschedulingstartstopremotepa.DeviceUtils;
import com.waylau.hmos.distributedschedulingstartstopremotepa.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;


public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private static final String BUNDLE_NAME = "com.waylau.hmos.remotepa";
    private static final String ABILITY_NAME = BUNDLE_NAME + ".TimeServiceAbility";


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发启动PA
        Text textStartPA = (Text) findComponentById(ResourceTable.Id_text_startpa);
        textStartPA.setClickedListener(listener -> startRemotePA());

        // 添加点击事件来触发关闭PA
        Text textStopPA = (Text) findComponentById(ResourceTable.Id_text_stoppa);
        textStopPA.setClickedListener(listener -> stopRemotePA());
    }

    // 启动远程PA
    private void startRemotePA() {
        HiLog.info(LABEL_LOG, "before startRemotePA");

        String deviceId = DeviceUtils.getDeviceId();

        HiLog.info(LABEL_LOG, "get deviceId: %{public}s", deviceId);

        // 指定待启动FA的deviceId、bundleName和abilityName
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId(deviceId)
                .withBundleName(BUNDLE_NAME)
                .withAbilityName(ABILITY_NAME)
                .build();

        Intent intent = new Intent();
        intent.setOperation(operation);

        // 如果deviceId不为空，则为分布式调用
        if (!deviceId.isEmpty()) {
             intent.addFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE); // 设置分布式标记
        }

        // 通过AbilitySlice包含的startAbility接口实现跨设备启动PA
        startAbility(intent);

        HiLog.info(LABEL_LOG, "after startRemotePA");
    }



    // 关闭远程PA
    private void stopRemotePA() {
        HiLog.info(LABEL_LOG, "before stopRemotePA");

        String deviceId = DeviceUtils.getDeviceId();

        HiLog.info(LABEL_LOG, "get deviceId: %{public}s", deviceId);

        // 指定待启动FA的deviceId、bundleName和abilityName
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId(deviceId)
                .withBundleName(BUNDLE_NAME)
                .withAbilityName(ABILITY_NAME)
                .build();

        Intent intent = new Intent();
        intent.setOperation(operation);

        // 如果deviceId不为空，则为分布式调用
        if (!deviceId.isEmpty()) {
            intent.addFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE); // 设置分布式标记
        }

        // 通过AbilitySlice包含的stopAbility接口实现跨设备关闭PA
        stopAbility(intent);

        HiLog.info(LABEL_LOG, "after stopRemotePA");
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

