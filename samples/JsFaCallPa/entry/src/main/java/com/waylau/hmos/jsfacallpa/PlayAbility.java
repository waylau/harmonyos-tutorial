package com.waylau.hmos.jsfacallpa;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.*;
import ohos.utils.zson.ZSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlayAbility extends Ability {
    private static final String TAG = PlayAbility.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private static final int ERROR = -1;
    private static final int SUCCESS = 0;
    private static final int PLUS = 1001;
    private PlayRemote remote;

    @Override
    public void onStart(Intent intent) {
        HiLog.error(LABEL_LOG, "PlayAbility::onStart");
        super.onStart(intent);
    }

    @Override
    public void onBackground() {
        super.onBackground();
        HiLog.info(LABEL_LOG, "PlayAbility::onBackground");
    }

    @Override
    public void onStop() {
        super.onStop();
        HiLog.info(LABEL_LOG, "PlayAbility::onStop");
    }

    @Override
    public void onCommand(Intent intent, boolean restart, int startId) {
    }

    @Override
    public IRemoteObject onConnect(Intent intent) {
        super.onConnect(intent);
        Context c = getContext();
        remote = new PlayRemote();
        return remote.asObject();
    }

    @Override
    public void onDisconnect(Intent intent) {
    }

    class PlayRemote extends RemoteObject implements IRemoteBroker {

        public PlayRemote() {
            super("PlayRemote 666");
        }

        @Override
        public boolean onRemoteRequest(int code, MessageParcel data, MessageParcel reply, MessageOption option) throws RemoteException {
            switch (code) {
                case PLUS: {

                    String zsonStr = data.readString();
                    RequestParam param = ZSONObject.stringToClass(zsonStr, RequestParam.class);

                    // 返回结果仅支持可序列化的Object类型
                    Map<String, Object> zsonResult = new HashMap<String, Object>();
                    zsonResult.put("code", SUCCESS);
                    zsonResult.put("abilityResult", param.getFirstNum() + param.getSecondNum());
                    reply.writeString(ZSONObject.toZSONString(zsonResult));
                    break;
                }
                default: {
                    reply.writeString("service not defined");
                    return false;
                }
            }
            return true;
        }

        @Override
        public IRemoteObject asObject() {
            return this;
        }
    }

}