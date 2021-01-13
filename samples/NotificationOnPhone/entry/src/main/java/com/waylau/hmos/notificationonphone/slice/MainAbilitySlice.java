package com.waylau.hmos.notificationonphone.slice;

import com.waylau.hmos.notificationonphone.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.agp.utils.Color;
import ohos.event.notification.NotificationHelper;
import ohos.event.notification.NotificationRequest;
import ohos.event.notification.NotificationSlot;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.RemoteException;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private int notificationId = 0; // 递增的序列

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        // 添加点击事件来触发
        Text textPublishNotification = (Text) findComponentById(ResourceTable.Id_text_publish_notification);
        textPublishNotification.setClickedListener(listener -> publishNotification());


        // 添加点击事件来触发
        Text textCancelNotification = (Text) findComponentById(ResourceTable.Id_text_cancel_notification);
        textCancelNotification.setClickedListener(listener -> cancelNotification());
    }

    private void publishNotification() {
        HiLog.info(LABEL_LOG, "before publishNotification");

        // 创建notificationSlot对象
        NotificationSlot slot =
                new NotificationSlot("slot_001", "slot_default", NotificationSlot.LEVEL_HIGH);

        slot.setDescription("NotificationSlotDescription");
        slot.setEnableVibration(true); // 设置振动提醒
        slot.setLockscreenVisibleness(NotificationRequest.VISIBLENESS_TYPE_PUBLIC);//设置锁屏模式
        slot.setEnableLight(true); // 设置开启呼吸灯提醒
        slot.setLedLightColor(Color.RED.getValue());// 设置呼吸灯的提醒颜色

        try {
            NotificationHelper.addNotificationSlot(slot);

            notificationId++;
            NotificationRequest request = new NotificationRequest(notificationId);
            request.setSlotId(slot.getId());

            NotificationHelper.publishNotification(request);
        } catch (RemoteException ex) {
            HiLog.warn(LABEL_LOG, "publishNotification occur exception.");
        }

        HiLog.info(LABEL_LOG, "end publishNotification");
    }

    private void cancelNotification() {
        HiLog.info(LABEL_LOG, "before cancelNotification");
        try {
            NotificationHelper.cancelNotification(notificationId);
        } catch (RemoteException ex) {
            HiLog.warn(LABEL_LOG, "cancelNotification occur exception.");
        }

        HiLog.info(LABEL_LOG, "end cancelNotification");
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
