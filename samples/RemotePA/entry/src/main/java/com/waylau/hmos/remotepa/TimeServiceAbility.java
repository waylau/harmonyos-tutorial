package com.waylau.hmos.remotepa;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.rpc.IRemoteObject;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class TimeServiceAbility extends Ability {
    private static final String LOG_TAG = TimeServiceAbility.class.getSimpleName();
    private static final HiLogLabel LOG_LABEL =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, LOG_TAG);

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
    }

    @Override
    public IRemoteObject onConnect(Intent intent) {
        return null;
    }

    @Override
    public void onDisconnect(Intent intent) {
    }
}