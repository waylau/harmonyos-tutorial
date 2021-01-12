package com.waylau.hmos.commoneventpublisher.slice;

import com.waylau.hmos.commoneventpublisher.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Text;
import ohos.event.commonevent.CommonEventData;
import ohos.event.commonevent.CommonEventManager;
import ohos.event.commonevent.CommonEventPublishInfo;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.RemoteException;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private static final String EVENT_PERMISSION =
            "com.waylau.hmos.commoneventpublisher.PERMISSION";
    private static final String EVENT_NAME =
            "com.waylau.hmos.commoneventpublisher.EVENT";
    private static final int EVENT_CODE = 1;
    private static final String EVENT_DATA = "Welcome to waylau.com";
    private int index = 0; // 递增的序列

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发
        Text text = (Text) findComponentById(ResourceTable.Id_text_publish_event);
        text.setClickedListener(listener -> publishEvent());
    }

    private void publishEvent() {
        HiLog.info(LABEL_LOG, "before publishEvent");

        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withAction(EVENT_NAME) // 设置事件名称
                .build();
        intent.setOperation(operation);

        index++;
        CommonEventData eventData = new CommonEventData(intent, EVENT_CODE, EVENT_DATA
                + " times " + index);
        CommonEventPublishInfo publishInfo = new CommonEventPublishInfo();
        String[] permissions = {EVENT_PERMISSION};
        publishInfo.setSubscriberPermissions(permissions); // 设置权限
        try {
            CommonEventManager.publishCommonEvent(eventData, publishInfo);
        } catch (RemoteException e) {
            HiLog.info(LABEL_LOG, "publishCommonEvent occur exception.");
        }

        HiLog.info(LABEL_LOG, "end publishEvent, event data %{public}s", eventData.getData());
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
