package com.waylau.hmos.serviceabilitylifecycle;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.rpc.IRemoteObject;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.time.LocalDateTime;

public class TimeServiceAbility extends Ability {
    private static final HiLogLabel LOG_LABEL =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, "TimeServiceAbility");

    private TimeRemoteObject timeRemoteObject = new TimeRemoteObject();

    @Override
    public void onStart(Intent intent) {
        HiLog.info(LOG_LABEL, "onStart");
        super.onStart(intent);
    }

    @Override
    public void onBackground() {
        super.onBackground();
        HiLog.info(LOG_LABEL, "onBackground");
    }

    @Override
    public void onStop() {
        super.onStop();
        HiLog.info(LOG_LABEL, "onStop");
    }

    @Override
    public void onCommand(Intent intent, boolean restart, int startId) {
        super.onCommand(intent, restart, startId);
        HiLog.info(LOG_LABEL, "onCommand");
    }

    @Override
    public IRemoteObject onConnect(Intent intent) {
        super.onConnect(intent);
        HiLog.info(LOG_LABEL, "onConnect");

        LocalDateTime now = LocalDateTime.now();
        timeRemoteObject.setTime(now);

        return timeRemoteObject;
    }

    @Override
    public void onDisconnect(Intent intent) {
        super.onDisconnect(intent);
        HiLog.info(LOG_LABEL, "onDisconnect");
    }
}