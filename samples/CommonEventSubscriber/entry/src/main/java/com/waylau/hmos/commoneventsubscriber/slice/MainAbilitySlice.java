package com.waylau.hmos.commoneventsubscriber.slice;

import com.waylau.hmos.commoneventsubscriber.ResourceTable;
import com.waylau.hmos.commoneventsubscriber.WelcomeCommonEventSubscriber;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.event.commonevent.CommonEventManager;
import ohos.event.commonevent.CommonEventSubscribeInfo;
import ohos.event.commonevent.MatchingSkills;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.RemoteException;


public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);
    private static final String EVENT_NAME =
            "com.waylau.hmos.commoneventpublisher.EVENT";
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 订阅事件
        subscribeEvent();
    }

    private void subscribeEvent() {
        MatchingSkills matchingSkills = new MatchingSkills();
        matchingSkills.addEvent(EVENT_NAME);
        CommonEventSubscribeInfo subscribeInfo = new CommonEventSubscribeInfo(matchingSkills);
        subscribeInfo.setPriority(100);

        WelcomeCommonEventSubscriber subscriber = new WelcomeCommonEventSubscriber(subscribeInfo);
        try {
            CommonEventManager.subscribeCommonEvent(subscriber);
        } catch (RemoteException e) {
            HiLog.error(LABEL_LOG, "%{public}s", "subscribeEvent remoteException.");
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
