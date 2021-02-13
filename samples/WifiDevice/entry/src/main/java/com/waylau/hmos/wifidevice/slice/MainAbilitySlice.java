package com.waylau.hmos.wifidevice.slice;

import com.waylau.hmos.wifidevice.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.wifi.*;

import java.util.List;
import java.util.Optional;


public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private WifiDevice wifiDevice;
    private ohos.agp.components.Text text;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化WLAN
        initWifiDevice();

        // 为按钮设置点击事件回调
        Button buttonGet =
                (Button) findComponentById(ResourceTable.Id_button_get);
        buttonGet.setClickedListener(listener -> getInfo());

        text = (Text) findComponentById(ResourceTable.Id_text);
    }

    private void initWifiDevice() {
        HiLog.info(LABEL_LOG, "before initWifiDevice");

        // 获取WLAN设备
        wifiDevice = WifiDevice.getInstance(this);

        // 调用获取WLAN开关状态接口.
        // 若WLAN打开，则返回true，否则返回false
        boolean isWifiActive = wifiDevice.isWifiActive();

        HiLog.info(LABEL_LOG, "end initWifiDevice, isWifiActive: %{public}s",
                isWifiActive);
    }

    private void getInfo() {
        HiLog.info(LABEL_LOG, "before getInfo");

        // 调用WLAN扫描接口
        boolean isScanSuccess = wifiDevice.scan(); // true
        text.append("isScanSuccess: " + isScanSuccess + "\n");

        HiLog.info(LABEL_LOG, "isScanSuccess: %{public}s", isScanSuccess);

        // 调用获取扫描结果
        List<WifiScanInfo> scanInfos = wifiDevice.getScanInfoList();

        for (WifiScanInfo scanInfo : scanInfos) {
            String ssid = scanInfo.getSsid();
            text.append("ssid: " + ssid + "\n");

            HiLog.info(LABEL_LOG, "ssid: %{public}s", ssid);
        }

        // 调用WLAN连接状态接口,确定当前设备是否连接WLAN
        boolean isConnected = wifiDevice.isConnected();
        text.append("isConnected: " + isConnected + "\n");

        HiLog.info(LABEL_LOG, "isConnected: %{public}s", isConnected);

        if (isConnected) {
            // 获取WLAN连接信息
            Optional<WifiLinkedInfo> linkedInfo = wifiDevice.getLinkedInfo();

            // 获取连接信息中的SSID
            String ssid = linkedInfo.get().getSsid();

            // 获取WLAN的IP信息
            Optional<IpInfo> ipInfo = wifiDevice.getIpInfo();

            // 获取IP信息中的IP地址与网关
            int ipAddress = ipInfo.get().getIpAddress();
            int gateway = ipInfo.get().getGateway();
            text.append("ipAddress: " + ipAddress + "; gateway:" + gateway + "\n");

            HiLog.info(LABEL_LOG, "ipAddress: %{public}s, gateway: %{public}s",
                    ipAddress, gateway);
        }

        // 获取当前设备的国家码
        String countryCode = wifiDevice.getCountryCode();
        text.append("countryCode: " + countryCode + "\n");

        HiLog.info(LABEL_LOG, "countryCode: %{public}s", countryCode);

        // 获取当前设备是否支持指定的能力
        boolean isSupport = wifiDevice.isFeatureSupported(WifiUtils.WIFI_FEATURE_INFRA);
        text.append("WIFI_FEATURE_INFRA: " + isSupport + "\n");
        HiLog.info(LABEL_LOG, "WIFI_FEATURE_INFRA: %{public}s", isSupport);

        isSupport = wifiDevice.isFeatureSupported(WifiUtils.WIFI_FEATURE_INFRA_5G);
        text.append("WIFI_FEATURE_INFRA_5G: " + isSupport + "\n");
        HiLog.info(LABEL_LOG, "WIFI_FEATURE_INFRA_5G: %{public}s", isSupport);

        isSupport = wifiDevice.isFeatureSupported(WifiUtils.WIFI_FEATURE_PASSPOINT);
        text.append("WIFI_FEATURE_PASSPOINT: " + isSupport + "\n");
        HiLog.info(LABEL_LOG, "WIFI_FEATURE_PASSPOINT: %{public}s", isSupport);

        isSupport = wifiDevice.isFeatureSupported(WifiUtils.WIFI_FEATURE_P2P);
        text.append("WIFI_FEATURE_P2P: " + isSupport + "\n");
        HiLog.info(LABEL_LOG, "WIFI_FEATURE_P2P: %{public}s", isSupport);

        isSupport = wifiDevice.isFeatureSupported(WifiUtils.WIFI_FEATURE_MOBILE_HOTSPOT);
        text.append("WIFI_FEATURE_MOBILE_HOTSPOT: " + isSupport + "\n");
        HiLog.info(LABEL_LOG, "WIFI_FEATURE_MOBILE_HOTSPOT: %{public}s", isSupport);

        isSupport = wifiDevice.isFeatureSupported(WifiUtils.WIFI_FEATURE_AWARE);
        text.append("WIFI_FEATURE_AWARE: " + isSupport + "\n");
        HiLog.info(LABEL_LOG, "WIFI_FEATURE_AWARE: %{public}s", isSupport);

        isSupport = wifiDevice.isFeatureSupported(WifiUtils.WIFI_FEATURE_AP_STA);
        text.append("WIFI_FEATURE_AP_STA: " + isSupport + "\n");
        HiLog.info(LABEL_LOG, "WIFI_FEATURE_AP_STA: %{public}s", isSupport);

        isSupport = wifiDevice.isFeatureSupported(WifiUtils.WIFI_FEATURE_WPA3_SAE);
        text.append("WIFI_FEATURE_WPA3_SAE: " + isSupport + "\n");
        HiLog.info(LABEL_LOG, "WIFI_FEATURE_WPA3_SAE: %{public}s", isSupport);

        isSupport = wifiDevice.isFeatureSupported(WifiUtils.WIFI_FEATURE_WPA3_SUITE_B);
        text.append("WIFI_FEATURE_WPA3_SUITE_B: " + isSupport + "\n");
        HiLog.info(LABEL_LOG, "WIFI_FEATURE_WPA3_SUITE_B: %{public}s", isSupport);

        isSupport = wifiDevice.isFeatureSupported(WifiUtils.WIFI_FEATURE_OWE);
        text.append("WIFI_FEATURE_OWE: " + isSupport + "\n");
        HiLog.info(LABEL_LOG, "WIFI_FEATURE_OWE: %{public}s", isSupport);

        HiLog.info(LABEL_LOG, "end getInfo");
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
