package com.waylau.hmos.blecentralmanager.slice;

import com.waylau.hmos.blecentralmanager.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.bluetooth.ble.*;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.SequenceUuid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private static final UUID SERVER_UUID = UUID.randomUUID();
    private static final int[] STATE_ARRAY = {
            BlePeripheralDevice.CONNECTION_PRIORITY_NORMAL,
            BlePeripheralDevice.CONNECTION_PRIORITY_HIGH,
            BlePeripheralDevice.CONNECTION_PRIORITY_LOW};

    // 获取中心设备管理对象
    private BleCentralManager centralManager;

    // 获取BLE广播对象
    private BleAdvertiser advertiser;
    // 创建BLE广播参数和数据
    private BleAdvertiseData data;
    private BleAdvertiseSettings advertiseSettings;

    private Text text;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化蓝牙
        initBluetooth();

        // 为按钮设置点击事件回调
        Button buttonGet =
                (Button) findComponentById(ResourceTable.Id_button_scan);
        buttonGet.setClickedListener(listener -> scan());

        Button buttonPair =
                (Button) findComponentById(ResourceTable.Id_button_advertise);
        buttonPair.setClickedListener(listener -> advertise());

        text =
                (Text) findComponentById(ResourceTable.Id_text);
    }

    private void initBluetooth() {
        HiLog.info(LABEL_LOG, "before initBluetooth");

        // 获取中心设备管理对象
        ScanCallback centralManagerCallback = new ScanCallback();
        centralManager = new BleCentralManager(this, centralManagerCallback);

        // 创建扫描过滤器然后开始扫描
        List<BleScanFilter> filters = new ArrayList<>();
        centralManager.startScan(filters);

        // 获取BLE广播对象
        advertiser = new BleAdvertiser(this, advertiseCallback);

        // 创建BLE广播参数和数据
        data = new BleAdvertiseData.Builder()
                .addServiceUuid(SequenceUuid.uuidFromString(SERVER_UUID.toString()))  // 添加服务的UUID
                .addServiceData(SequenceUuid.uuidFromString(SERVER_UUID.toString()), new byte[]{0x11})    // 添加广播数据内容
                .build();
        advertiseSettings = new BleAdvertiseSettings.Builder()
                .setConnectable(true)                 // 设置是否可连接广播
                .setInterval(BleAdvertiseSettings.INTERVAL_SLOT_DEFAULT) // 设置广播间隔
                .setTxPower(BleAdvertiseSettings.TX_POWER_DEFAULT) // 设置广播功率
                .build();

        HiLog.info(LABEL_LOG, "end initBluetooth");
    }

    private void scan() {
        HiLog.info(LABEL_LOG, "before scan");
        List<BlePeripheralDevice> devices = centralManager.getDevicesByStates(STATE_ARRAY);

        for (BlePeripheralDevice device : devices) {
            Optional<String> deviceNameOptional = device.getDeviceName();
            String deviceName = deviceNameOptional.orElse("");
            String deviceAddr = device.getDeviceAddr();

            HiLog.info(LABEL_LOG, "scan deviceName: %{public}s, deviceAddr: %{public}s",
                    deviceName, deviceAddr);

            text.append(deviceName);
            text.append(deviceAddr);
        }
        HiLog.info(LABEL_LOG, "end scan");
    }

    private void advertise() {
        HiLog.info(LABEL_LOG, "before advertise");

        // 开始广播
        advertiser.startAdvertising(advertiseSettings, data, null);

        HiLog.info(LABEL_LOG, "end advertise");
    }

    // 实现扫描回调
    private class ScanCallback implements BleCentralManagerCallback {
        List<BleScanResult> results = new ArrayList<BleScanResult>();

        @Override
        public void scanResultEvent(BleScanResult var1) {
            HiLog.info(LABEL_LOG, "scanResultEvent");

            // 对扫描结果进行处理
            results.add(var1);
        }

        @Override
        public void scanFailedEvent(int var1) {
            HiLog.info(LABEL_LOG, "Start Scan failed,Code:" + var1);
        }

        @Override
        public void groupScanResultsEvent(List<BleScanResult> list) {
            HiLog.info(LABEL_LOG, "groupScanResultsEvent");
        }
    }

    // 实现BLE广播回调
    private BleAdvertiseCallback advertiseCallback = new BleAdvertiseCallback() {
        @Override
        public void startResultEvent(int result) {
            if (result == BleAdvertiseCallback.RESULT_SUCC) {
                // 开始BLE广播成功
                HiLog.info(LABEL_LOG, "startResultEvent success");
            } else {
                // 开始BLE广播失败
                HiLog.error(LABEL_LOG, "startResultEvent failed");
            }
        }
    };

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
