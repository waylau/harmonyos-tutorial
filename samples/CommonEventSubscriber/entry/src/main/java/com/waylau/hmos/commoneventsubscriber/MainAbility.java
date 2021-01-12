package com.waylau.hmos.commoneventsubscriber;

import com.waylau.hmos.commoneventsubscriber.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.event.commonevent.CommonEventManager;
import ohos.event.commonevent.CommonEventSubscribeInfo;
import ohos.event.commonevent.MatchingSkills;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.RemoteException;

public class MainAbility extends Ability {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private static final String EVENT_NAME =
            "com.waylau.hmos.commoneventpublisher.EVENT";

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());

        // 订阅事件
        subscribeEvent();
    }

    private void subscribeEvent() {
        HiLog.info(LABEL_LOG, "before subscribeEvent");

        MatchingSkills matchingSkills = new MatchingSkills();
        matchingSkills.addEvent(EVENT_NAME); // 自定义事件
        CommonEventSubscribeInfo subscribeInfo = new CommonEventSubscribeInfo(matchingSkills);
        WelcomeCommonEventSubscriber subscriber = new WelcomeCommonEventSubscriber(subscribeInfo);
        try {
            CommonEventManager.subscribeCommonEvent(subscriber);
        } catch (RemoteException e) {
            HiLog.info(LABEL_LOG, "subscribeCommonEvent occur exception.");
        }

        HiLog.info(LABEL_LOG, "end subscribeEvent");
    }

}
