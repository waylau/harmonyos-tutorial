package com.waylau.hmos.wifieventsubscriber.slice;

import com.waylau.hmos.wifieventsubscriber.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.event.commonevent.*;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.RemoteException;
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

        // 为按钮设置点击事件回调
        Button buttonSubscribe =
                (Button) findComponentById(ResourceTable.Id_button_subscribe);
        buttonSubscribe.setClickedListener(listener -> subscribe());

        text = (Text) findComponentById(ResourceTable.Id_text);
    }

    private void subscribe() {
        HiLog.info(LABEL_LOG, "before subscribe");

        // 注册消息
        MatchingSkills match = new MatchingSkills();

        // 增加获取WLAN状态变化消息
        match.addEvent(WifiEvents.EVENT_ACTIVE_STATE);
        CommonEventSubscribeInfo subscribeInfo = new CommonEventSubscribeInfo(match);
        subscribeInfo.setPriority(100);
        WifiEventSubscriber subscriber = new WifiEventSubscriber(subscribeInfo);

        try {
            CommonEventManager.subscribeCommonEvent(subscriber);
        } catch (RemoteException e) {
            HiLog.warn(LABEL_LOG, "subscribe in wifi events failed!");
        }

        HiLog.info(LABEL_LOG, "end subscribe");
    }

    // 构建消息接收者/注册者
    private class WifiEventSubscriber extends CommonEventSubscriber {
        WifiEventSubscriber(CommonEventSubscribeInfo info) {
            super(info);
        }

        @Override
        public void onReceiveEvent(CommonEventData commonEventData) {
            if (WifiEvents.EVENT_ACTIVE_STATE.equals(commonEventData.getIntent().getAction())) {
                // 获取附带参数
                IntentParams params = commonEventData.getIntent().getParams();
                if (params == null) {
                    return;
                }

                // WLAN状态
                int wifiState = (int) params.getParam(WifiEvents.PARAM_ACTIVE_STATE);

                if (wifiState == WifiEvents.STATE_ACTIVE) {
                    // 处理WLAN被打开消息
                    HiLog.info(LABEL_LOG, "Receive WifiEvents.STATE_ACTIVE %{public}d", wifiState);
                    text.append("Receive WifiEvents.STATE_ACTIVE\n");
                } else if (wifiState == WifiEvents.STATE_INACTIVE) {
                    // 处理WLAN被关闭消息
                    HiLog.info(LABEL_LOG, "Receive WifiEvents.STATE_INACTIVE %{public}d", wifiState);
                    text.append("Receive WifiEvents.STATE_INACTIVE\n");
                } else {
                    // 处理WLAN异常状态
                    HiLog.info(LABEL_LOG, "Unknown wifi state");
                    text.append("Receive Unknown wifi state\n");
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
