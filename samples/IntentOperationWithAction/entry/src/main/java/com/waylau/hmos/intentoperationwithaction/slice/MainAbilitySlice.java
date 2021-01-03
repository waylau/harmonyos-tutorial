package com.waylau.hmos.intentoperationwithaction.slice;

import com.waylau.hmos.intentoperationwithaction.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbilitySlice extends AbilitySlice {
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, "MainAbilitySlice");
    private final static int CODE = 1;
    private static final String TEMP_KEY = "temperature";

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发请求
        Text text = (Text) findComponentById(ResourceTable.Id_text_helloworld);
        text.setClickedListener(listener -> this.queryWeather());
    }
    private void queryWeather() {
        HiLog.info(LABEL_LOG, "before queryWeather");

        Intent intent = new Intent();

        Operation operation = new Intent.OperationBuilder()
                .withAction("action.weather")
                .build();
        intent.setOperation(operation);

        // 上述方式等同于intent.setAction("action.weather");

        startAbilityForResult(intent, CODE);
    }

    @Override
    protected void onAbilityResult(int requestCode, int resultCode, Intent resultData) {
        HiLog.info(LABEL_LOG, "onAbilityResult");

        switch (requestCode) {
            case CODE:
                HiLog.info(LABEL_LOG, "code 1 result: %{public}s",
                        resultData.getStringParam(TEMP_KEY));
                break;
            default:
                HiLog.info(LABEL_LOG, "defualt result: %{public}s", resultData.getAction());
                break;
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
