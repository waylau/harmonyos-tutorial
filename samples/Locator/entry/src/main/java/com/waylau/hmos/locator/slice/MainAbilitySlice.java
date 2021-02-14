package com.waylau.hmos.locator.slice;

import com.waylau.hmos.locator.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.location.Location;
import ohos.location.Locator;
import ohos.location.LocatorCallback;
import ohos.location.RequestParam;
import ohos.utils.zson.ZSONObject;


public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private Locator locator;
    private MyLocatorCallback locatorCallback;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化对象
        initData();

        // 为按钮设置点击事件回调
        Button buttonOn =
                (Button) findComponentById(ResourceTable.Id_button_on);
        buttonOn.setClickedListener(listener -> on());

        Button buttonOff =
                (Button) findComponentById(ResourceTable.Id_button_off);
        buttonOff.setClickedListener(listener -> off());
    }

    private void initData() {
        HiLog.info(LABEL_LOG, "before initData");

        locator = new Locator(this.getContext());

        HiLog.info(LABEL_LOG, "end initData");
    }

    private void off() {
        HiLog.info(LABEL_LOG, "before off");

        locator.stopLocating(locatorCallback);

        HiLog.info(LABEL_LOG, "end off");
    }


    private void on() {
        HiLog.info(LABEL_LOG, "before on");

        locatorCallback = new MyLocatorCallback();

        // 定位精度优先策略
        RequestParam requestParam =
                new RequestParam(RequestParam.PRIORITY_ACCURACY, 0, 0);

        locator.startLocating(requestParam, locatorCallback);

        HiLog.info(LABEL_LOG, "end on");
    }

    private class MyLocatorCallback implements LocatorCallback {
        @Override
        public void onLocationReport(Location location) {
            HiLog.info(LABEL_LOG, "onLocationReport, location: %{public}s",
                    ZSONObject.toZSONString(location));
        }

        @Override
        public void onStatusChanged(int type) {
            HiLog.info(LABEL_LOG, "onStatusChanged, type: %{public}s", type);
        }

        @Override
        public void onErrorReport(int type) {
            HiLog.info(LABEL_LOG, "onErrorReport, type: %{public}s", type);
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
