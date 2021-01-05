package com.waylau.hmos.distributedschedulingstartremotefa.slice;

import com.waylau.hmos.distributedschedulingstartremotefa.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Text;
import ohos.distributedschedule.interwork.DeviceInfo;
import ohos.distributedschedule.interwork.DeviceManager;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.ArrayList;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, "MainAbilitySlice");

    private static final String BUNDLE_NAME = "com.waylau.hmos.remotefa";
    private static final String ABILITY_NAME = "com.waylau.hmos.remotefa.MainAbility";
    @Override
    public void onStart(Intent intent) {
        // 显示声明需要使用的权限
        requestPermissionsFromUser(new String[]{"ohos.permission.DISTRIBUTED_DATASYNC"}, 0);

        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发导航
        Text text = (Text) findComponentById(ResourceTable.Id_text_helloworld);
        text.setClickedListener(listener -> startRemoteFA());
    }

    // 启动远程PA
    private void startRemoteFA() {
        String deviceId = getFirstDeviceId();

        HiLog.info(LABEL_LOG, "get deviceId: %{public}s", deviceId);

        Intent intent = new Intent();

        // 指定待启动FA的bundleName和abilityName
        // 设置分布式标记，表明当前涉及分布式能力
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId(deviceId)
                .withBundleName(BUNDLE_NAME)
                .withAbilityName(ABILITY_NAME)
                .withFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE)
                .build();
        intent.setOperation(operation);

        // 通过AbilitySlice包含的startAbility接口实现跨设备启动FA
        startAbility(intent);

        HiLog.info(LABEL_LOG, "after startRemoteFA");
    }

    // 获得设备列表，实际项目中可在得到的在线设备列表中选择目标设备执行操作
    private String getFirstDeviceId() {
        String deviceId = null;

        // 调用DeviceManager的getDeviceList接口，通过FLAG_GET_ONLINE_DEVICE标记获得在线设备列表
        List<DeviceInfo> onlineDevices = DeviceManager.getDeviceList(DeviceInfo.FLAG_GET_ONLINE_DEVICE);
        // 判断组网设备是否为空
        if (onlineDevices.isEmpty()) {
            return deviceId;
        }

        int numDevices = onlineDevices.size();
        ArrayList<String> deviceIds = new ArrayList<>(numDevices);
        ArrayList<String> deviceNames = new ArrayList<>(numDevices);

        onlineDevices.forEach((device) -> {
            deviceIds.add(device.getDeviceId());
            deviceNames.add(device.getDeviceName());
        });

        // 以选择首个设备作为目标设备为例
        // 实际项目中，开发者也可按照具体场景，通过别的方式进行设备选择
        String selectDeviceId = deviceIds.get(0);
        return deviceId;
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
