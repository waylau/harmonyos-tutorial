package com.waylau.hmos.distributedschedulingstartremotefa;

import ohos.account.AccountAbility;
import ohos.account.DistributedInfo;
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

    private DeviceUtils() {}

    /**
     * Get group id.
     * @return group id.
     */
    public static String getGroupId() {
        AccountAbility account = AccountAbility.getAccountAbility();
        DistributedInfo distributeInfo = account.queryOsAccountDistributedInfo();
        return distributeInfo.getId();
    }

    public static List<String> getAvailableDeviceId() {
        List<String> deviceIds = new ArrayList<>();

        List<DeviceInfo> deviceInfoList = DeviceManager.getDeviceList(DeviceInfo.FLAG_GET_ONLINE_DEVICE);
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

    /**
     * Get available id
     * @return available device ids
     */
    public static List<String> getAllAvailableDeviceId() {
        List<String> deviceIds = new ArrayList<>();

        List<DeviceInfo> deviceInfoList = DeviceManager.getDeviceList(DeviceInfo.FLAG_GET_ALL_DEVICE);
        if (deviceInfoList == null) {
            return deviceIds;
        }
        System.out.println("deviceInfoList size " + deviceInfoList.size());
        if (deviceInfoList.size() == 0) {
            HiLog.warn(LABEL_LOG, "did not find other device");
            return deviceIds;
        }

        for (DeviceInfo deviceInfo : deviceInfoList) {
            deviceIds.add(deviceInfo.getDeviceId());
        }

        return deviceIds;
    }

    /**
     * Get remote device info
     * @return Remote device info list.
     */
    public static List<DeviceInfo> getRemoteDevice() {
        List<DeviceInfo> deviceInfoList = DeviceManager.getDeviceList(DeviceInfo.FLAG_GET_ONLINE_DEVICE);
        return deviceInfoList;
    }

    /**
     * @Param []
     * @description  获取当前组网下可迁移的设备id
     * @return java.lang.String
     */
    public static String getDeviceId(){
        String deviceId = "";
        List<String> outerDevices = DeviceUtils.getAvailableDeviceId();

        if (outerDevices == null || outerDevices.size() == 0){
            HiLog.warn(LABEL_LOG, "did not find other device");
        }else {
            for (String item : outerDevices) {
                HiLog.info(LABEL_LOG, "outerDevices:%{public}s", item);
            }
            deviceId = outerDevices.get(0);
        }
        HiLog.info(LABEL_LOG, "getDeviceId:%{public}s", deviceId);
        return deviceId;
    };
}
