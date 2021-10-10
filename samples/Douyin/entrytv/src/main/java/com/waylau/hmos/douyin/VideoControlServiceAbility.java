package com.waylau.hmos.douyin;

import com.waylau.hmos.douyin.constant.RemoteConstant;
import com.waylau.hmos.douyin.constant.Constants;
import com.waylau.hmos.douyin.remote.MyRemote;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.event.commonevent.CommonEventData;
import ohos.event.commonevent.CommonEventManager;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.IRemoteObject;
import ohos.rpc.RemoteException;

import java.util.Map;

/**
 * Video Remote Control Service
 */
public class VideoControlServiceAbility extends Ability {
    private static final HiLogLabel LABEL = new HiLogLabel(0, 0, "VideoControlServiceAbility");
    private final MyRemote remote = new MyRemote(RemoteConstant.REQUEST_CONTROL_REMOTE_DEVICE);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        remote.setRemoteRequestCallback(this::sendEvent);
    }

    @Override
    protected IRemoteObject onConnect(Intent intent) {
        super.onConnect(intent);
        return remote.asObject();
    }

    private void sendEvent(int controlCode, Map<?, ?> value) {
        try {
            Intent intent = new Intent();
            Operation operation = new Intent.OperationBuilder()
                    .withAction(Constants.TV_CONTROL_EVENT)
                    .build();
            intent.setOperation(operation);
            intent.setParam(Constants.KEY_CONTROL_CODE, controlCode);
            intent.setParam(Constants.KEY_CONTROL_VALUE, (String) value.get(RemoteConstant.REMOTE_KEY_CONTROL_VALUE));
            CommonEventData eventData = new CommonEventData(intent);
            CommonEventManager.publishCommonEvent(eventData);
        } catch (RemoteException e) {
            HiLog.error(LABEL, "publishCommonEvent occur exception.");
        }
    }
}
