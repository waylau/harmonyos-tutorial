package com.waylau.hmos.commoneventsubscriber;

import ohos.event.commonevent.CommonEventData;
import ohos.event.commonevent.CommonEventSubscribeInfo;
import ohos.event.commonevent.CommonEventSubscriber;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class WelcomeCommonEventSubscriber extends CommonEventSubscriber {
    private static final String TAG = WelcomeCommonEventSubscriber.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    public WelcomeCommonEventSubscriber(CommonEventSubscribeInfo info) {
        super(info);
    }

    @Override
    public void onReceiveEvent(CommonEventData commonEventData) {
        HiLog.info(LABEL_LOG, "receive event data %{public}s", commonEventData.getData());
    }

}
