package com.waylau.hmos.radiostateobserver.slice;

import com.waylau.hmos.radiostateobserver.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.eventhandler.EventRunner;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.telephony.*;
import ohos.utils.zson.ZSONObject;

import java.util.List;


public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private Text text;

    private RadioInfoManager radioInfoManager;
    private MyRadioStateObserver observer;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 为按钮设置点击事件回调
        Button buttonAdd =
                (Button) findComponentById(ResourceTable.Id_button_add);
        buttonAdd.setClickedListener(listener -> add());

        Button buttonRemove =
                (Button) findComponentById(ResourceTable.Id_button_remove);
        buttonRemove.setClickedListener(listener -> remove());

        text = (Text) findComponentById(ResourceTable.Id_text);

        // 获取RadioInfoManager对象
        radioInfoManager = RadioInfoManager.getInstance(this.getContext());
    }

    private void remove() {
        HiLog.info(LABEL_LOG, "before remove");

        // 停止观察
        radioInfoManager.removeObserver(observer);

        text.append("remove observer\n");

        HiLog.info(LABEL_LOG, "end remove");
    }


    private void add() {
        HiLog.info(LABEL_LOG, "before add");

        // 执行回调的runner
        EventRunner runner = EventRunner.create();

        // 创建MyRadioStateObserver的对象
        int slotId = 0; // 卡槽1
        observer = new MyRadioStateObserver(slotId, runner);

        // 添加回调，以NETWORK_STATE和SIGNAL_INFO为例
        radioInfoManager.addObserver(observer, RadioStateObserver.OBSERVE_MASK_NETWORK_STATE | RadioStateObserver.OBSERVE_MASK_SIGNAL_INFO);

        text.append("add observer\n");

        HiLog.info(LABEL_LOG, "end add");
    }

    // 创建继承RadioStateObserver的类MyRadioStateObserver
    private class MyRadioStateObserver extends RadioStateObserver {
        // 构造方法，在当前线程的runner中执行回调，slotId需要传入要观察的卡槽ID（0或1）
        MyRadioStateObserver(int slotId) {
            super(slotId);
        }

        // 构造方法，在执行runner中执行回调
        MyRadioStateObserver(int slotId, EventRunner runner) {
            super(slotId, runner);
        }

        // 网络注册状态变化的回调方法
        @Override
        public void onNetworkStateUpdated(NetworkState state) {
            String stateString = ZSONObject.toZSONString(state.getNsaState());
            HiLog.info(LABEL_LOG, "onNetworkStateUpdated, state: %{public}s",
                    stateString);
        }

        // 信号信息变化的回调方法
        @Override
        public void onSignalInfoUpdated(List<SignalInformation> signalInfos) {
            HiLog.info(LABEL_LOG, "onSignalInfoUpdated");

            for (SignalInformation signal : signalInfos) {
                int signalNetworkType = signal.getNetworkType();
                int signalLevel = signal.getSignalLevel();

                HiLog.info(LABEL_LOG, "signalNetworkType: %{public}s, signalLevel: %{public}s",
                        signalNetworkType, signalLevel);
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
