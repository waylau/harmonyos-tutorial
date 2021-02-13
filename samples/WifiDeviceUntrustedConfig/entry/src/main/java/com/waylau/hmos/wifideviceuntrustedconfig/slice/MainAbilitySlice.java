package com.waylau.hmos.wifideviceuntrustedconfig.slice;

import com.waylau.hmos.wifideviceuntrustedconfig.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.wifi.*;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private WifiDevice wifiDevice;
    private WifiDeviceConfig config;
    private Text text;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化WLAN
        initWifiDevice();

        // 为按钮设置点击事件回调
        Button buttonAdd =
                (Button) findComponentById(ResourceTable.Id_button_add);
        buttonAdd.setClickedListener(listener -> addConfig());

        // 为按钮设置点击事件回调
        Button buttonRemove =
                (Button) findComponentById(ResourceTable.Id_button_remove);
        buttonRemove.setClickedListener(listener -> removeConfig());

        text = (Text) findComponentById(ResourceTable.Id_text);
    }

    private void initWifiDevice() {
        HiLog.info(LABEL_LOG, "before initWifiDevice");

        // 初始化热点配置
        config = new WifiDeviceConfig();
        config.setSsid("untrusted-exist");
        config.setPreSharedKey("123456789");
        config.setHiddenSsid(false);
        config.setSecurityType(WifiSecurity.PSK);

        // 获取WLAN设备
        wifiDevice = WifiDevice.getInstance(this);

        HiLog.info(LABEL_LOG, "end initWifiDevice");
    }

    private void addConfig() {
        HiLog.info(LABEL_LOG, "before addConfig");

        boolean isSuccess = wifiDevice.addUntrustedConfig(config);
        text.append("addUntrustedConfig: " + isSuccess + "\n");

        HiLog.info(LABEL_LOG, "end addConfig, isSuccess:%{public}s", isSuccess);
    }

    private void removeConfig() {
        HiLog.info(LABEL_LOG, "before removeConfig");

        boolean isSuccess = wifiDevice.removeUntrustedConfig(config);
        text.append("removeUntrustedConfig: " + isSuccess + "\n");

        HiLog.info(LABEL_LOG, "end removeConfig, isSuccess:%{public}s", isSuccess);
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
