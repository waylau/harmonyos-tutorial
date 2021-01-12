package com.waylau.hmos.distributedschedulingstartstopremotepa;

import ohos.distributedschedule.interwork.DeviceInfo;
import ohos.distributedschedule.interwork.DeviceManager;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.ArrayList;
import java.util.List;

public class DeviceUtils {
    private static final String TAG = DeviceUtils.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private DeviceUtils() {
    }

    // 获取当前组网下可迁移的设备id列表
    public static List<String> getAvailableDeviceId() {
        List<String> deviceIds = new ArrayList<>();

        List<DeviceInfo> deviceInfoList =
                DeviceManager.getDeviceList(DeviceInfo.FLAG_GET_ALL_DEVICE);
        if (deviceInfoList == null) {
            return deviceIds;
        }

        if (deviceInfoList.size() == 0) {
            HiLog.warn(LABEL_LOG, "did not find other device");
            return deviceIds;
        }

        for (DeviceInfo deviceInfo : deviceInfoList) {
            deviceIds.add(deviceInfo.getDeviceId());
        }

        return deviceIds;
    }

    // 获取当前组网下可迁移的设备id
    // 如果有多个则取第一个
    public static String getDeviceId() {
        String deviceId = "";
        List<String> outerDevices = DeviceUtils.getAvailableDeviceId();

        if (outerDevices == null || outerDevices.size() == 0) {
            HiLog.warn(LABEL_LOG, "did not find other device");
        } else {
            for (String item : outerDevices) {
                HiLog.info(LABEL_LOG, "outerDevices:%{public}s", item);
            }
            deviceId = outerDevices.get(0);
        }
        HiLog.info(LABEL_LOG, "getDeviceId:%{public}s", deviceId);
        return deviceId;
    }
}
