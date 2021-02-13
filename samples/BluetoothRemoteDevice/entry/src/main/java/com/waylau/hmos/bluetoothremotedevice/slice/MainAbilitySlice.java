package com.waylau.hmos.bluetoothremotedevice.slice;

import com.waylau.hmos.bluetoothremotedevice.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.bluetooth.BluetoothHost;
import ohos.bluetooth.BluetoothRemoteDevice;
import ohos.event.commonevent.*;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.RemoteException;

import java.util.Optional;


public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private BluetoothHost bluetoothHost;

    private String selectedDeviceAddr;

    private Text text;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化蓝牙
        initBluetooth();

        // 为按钮设置点击事件回调
        Button buttonGet =
                (Button) findComponentById(ResourceTable.Id_button_get);
        buttonGet.setClickedListener(listener -> getInfo());

        Button buttonPair =
                (Button) findComponentById(ResourceTable.Id_button_pair);
        buttonPair.setClickedListener(listener -> pair());

        text =
                (Text) findComponentById(ResourceTable.Id_text);
    }

    private void pair() {
        HiLog.info(LABEL_LOG, "before pair device addr: %{public}s",
                selectedDeviceAddr);

        // 配对
        BluetoothRemoteDevice device = bluetoothHost.getRemoteDev(selectedDeviceAddr);
        boolean result = device.startPair();

        HiLog.info(LABEL_LOG, "end pair device addr: %{public}s, result: %{public}s",
                selectedDeviceAddr, result);
    }

    private void initBluetooth() {
        HiLog.info(LABEL_LOG, "before initBluetooth");

        // 获取蓝牙本机管理对象
        bluetoothHost = BluetoothHost.getDefaultHost(this);

        // 调用打开接口
        bluetoothHost.enableBt();

        // 获取本机蓝牙名称
        Optional<String> nameOptional = bluetoothHost.getLocalName();

        // 调用获取蓝牙开关状态接口
        int state = bluetoothHost.getBtState();

        HiLog.info(LABEL_LOG, "end initBluetooth, name: %{public}s, state: %{public}s",
                nameOptional.get(), state);
    }

    private void getInfo() {
        HiLog.info(LABEL_LOG, "before getInfo bluetooth name: %{public}s, state: %{public}s",
                bluetoothHost.getLocalName().get(), bluetoothHost.getBtState());

        // 注册广播BluetoothRemoteDevice.EVENT_DEVICE_DISCOVERED
        MatchingSkills matchingSkills = new MatchingSkills();
        matchingSkills.addEvent(BluetoothRemoteDevice.EVENT_DEVICE_DISCOVERED); // 自定义事件
        CommonEventSubscribeInfo subscribeInfo = new CommonEventSubscribeInfo(matchingSkills);
        MyCommonEventSubscriber subscriber = new MyCommonEventSubscriber(subscribeInfo);

        try {
            CommonEventManager.subscribeCommonEvent(subscriber);
        } catch (RemoteException e) {
            HiLog.info(LABEL_LOG, "subscribeCommonEvent occur exception.");
        }

        // 开始扫描
        bluetoothHost.startBtDiscovery();

        HiLog.info(LABEL_LOG, "end getInfo");
    }

    // 接收系统广播
    class MyCommonEventSubscriber extends CommonEventSubscriber {
        public MyCommonEventSubscriber(CommonEventSubscribeInfo subscribeInfo) {
            super(subscribeInfo);
        }

        @Override
        public void onReceiveEvent(CommonEventData var) {
            Intent info = var.getIntent();
            if (info == null) {
                return;
            }
            //获取系统广播的action
            String action = info.getAction();

            //判断是否为扫描到设备的广播
            if (action == BluetoothRemoteDevice.EVENT_DEVICE_DISCOVERED) {
                IntentParams myParam = info.getParams();
                BluetoothRemoteDevice device =
                        (BluetoothRemoteDevice) myParam.getParam(BluetoothRemoteDevice.REMOTE_DEVICE_PARAM_DEVICE);

                // 获取远端蓝牙设备地址
                String deviceAddr = device.getDeviceAddr();

                //  获取远端蓝牙设备名称
                Optional<String> deviceNameOptional = device.getDeviceName();
                String deviceName = deviceNameOptional.orElse("");

                // 获取远端设备配对状态
                int pairState = device.getPairState();

                HiLog.info(LABEL_LOG, "Remote Device, deviceAddr: %{public}s, deviceName: %{public}s, pairState: %{public}s",
                        deviceAddr, deviceName, pairState);

                // 0为可以配对，选为待配对的对象
                if (pairState == 0) {
                    selectedDeviceAddr = deviceAddr;
                    text.setText(selectedDeviceAddr);
                }
            }
        }
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
