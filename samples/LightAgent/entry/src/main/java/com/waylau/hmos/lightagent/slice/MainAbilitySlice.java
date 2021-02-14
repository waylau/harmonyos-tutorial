package com.waylau.hmos.lightagent.slice;

import com.waylau.hmos.lightagent.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.light.agent.LightAgent;
import ohos.light.bean.LightBrightness;
import ohos.light.bean.LightEffect;

import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private LightAgent lightAgent;
    private List<Integer> isEffectSupportLightIdList;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化对象
        initData();

        // 为按钮设置点击事件回调
        Button buttonAdd =
                (Button) findComponentById(ResourceTable.Id_button_on);
        buttonAdd.setClickedListener(listener -> on());

        Button buttonRemove =
                (Button) findComponentById(ResourceTable.Id_button_off);
        buttonRemove.setClickedListener(listener -> off());
    }

    private void initData() {
        HiLog.info(LABEL_LOG, "before initData");

        lightAgent = new LightAgent();

        // 查询硬件设备上的灯列表
        isEffectSupportLightIdList = lightAgent.getLightIdList();
        if (isEffectSupportLightIdList.isEmpty()) {
            HiLog.info(LABEL_LOG, "lightIdList is empty");
        }

        HiLog.info(LABEL_LOG, "end initData, size: %{public}s",
                isEffectSupportLightIdList.size());
    }

    private void off() {
        HiLog.info(LABEL_LOG, "before off");

        for (Integer lightId : isEffectSupportLightIdList) {
            // 关灯
            boolean turnOffResult = lightAgent.turnOff(lightId);

            HiLog.info(LABEL_LOG, "%{public}s turnOffResult : %{public}s ",
                    lightId, turnOffResult);
        }

        HiLog.info(LABEL_LOG, "end off");
    }


    private void on() {
        HiLog.info(LABEL_LOG, "before on");

        for (Integer lightId : isEffectSupportLightIdList) {
            // 创建自定义效果的一次性闪烁
            LightBrightness lightBrightness =
                    new LightBrightness(255, 255, 255);
            LightEffect lightEffect =
                    new LightEffect(lightBrightness, 1000, 1000);

            // 开灯
            boolean turnOnResult = lightAgent.turnOn(lightId, lightEffect);

            HiLog.info(LABEL_LOG, "%{public}s turnOnResult: %{public}s ",
                    lightId, turnOnResult);
        }

        HiLog.info(LABEL_LOG, "end on");
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