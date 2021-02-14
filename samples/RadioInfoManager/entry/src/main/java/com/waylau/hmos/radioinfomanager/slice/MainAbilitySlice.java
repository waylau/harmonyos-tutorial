package com.waylau.hmos.radioinfomanager.slice;

import com.waylau.hmos.radioinfomanager.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.telephony.LteSignalInformation;
import ohos.telephony.RadioInfoManager;
import ohos.telephony.SignalInformation;

import java.util.List;


public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private Text text;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 为按钮设置点击事件回调
        Button buttonGet =
                (Button) findComponentById(ResourceTable.Id_button_get);
        buttonGet.setClickedListener(listener -> getInfo());

        text = (Text) findComponentById(ResourceTable.Id_text);
    }


    private void getInfo() {
        HiLog.info(LABEL_LOG, "before getInfo");

        // 获取RadioInfoManager对象
        RadioInfoManager radioInfoManager = RadioInfoManager.getInstance(this.getContext());

        // 获取信号信息
        int slotId = 0; // 卡槽1
        List<SignalInformation> signalList = radioInfoManager.getSignalInfoList(slotId);

        HiLog.info(LABEL_LOG, "signalList size: %{public}s", signalList.size());

        // 检查信号信息列表大小
        if (signalList.size() == 0) {
            return;
        }

        // 依次遍历list获取当前驻网networkType对应的信号信息
        LteSignalInformation lteSignal;
        for (SignalInformation signal : signalList) {
            int signalNetworkType = signal.getNetworkType();
            int signalLevel = signal.getSignalLevel();

            HiLog.info(LABEL_LOG, "signalNetworkType: %{public}s, signalLevel: %{public}s",
                    signalNetworkType, signalLevel);
            text.append("signalNetworkType: " + signalNetworkType
                    + ", signalLevel: " + signalLevel + "\n");
        }

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
