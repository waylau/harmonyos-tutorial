package com.waylau.hmos.serviceabilitylifecycle;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.rpc.IRemoteObject;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.time.LocalDateTime;

public class TimeServiceAbility extends Ability {
    private static final String TAG = TimeServiceAbility.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private TimeRemoteObject timeRemoteObject = new TimeRemoteObject();

    @Override
    public void onStart(Intent intent) {
        HiLog.info(LABEL_LOG, "onStart");
        super.onStart(intent);
    }

    @Override
    public void onBackground() {
        super.onBackground();
        HiLog.info(LABEL_LOG, "onBackground");
    }

    @Override
    public void onStop() {
        super.onStop();
        HiLog.info(LABEL_LOG, "onStop");
    }

    @Override
    public void onCommand(Intent intent, boolean restart, int startId) {
        super.onCommand(intent, restart, startId);
        HiLog.info(LABEL_LOG, "onCommand");
    }

    @Override
    public IRemoteObject onConnect(Intent intent) {
        super.onConnect(intent);
        HiLog.info(LABEL_LOG, "onConnect");

        LocalDateTime now = LocalDateTime.now();
        timeRemoteObject.setTime(now);

        return timeRemoteObject;
    }

    @Override
    public void onDisconnect(Intent intent) {
        super.onDisconnect(intent);
        HiLog.info(LABEL_LOG, "onDisconnect");
    }
}