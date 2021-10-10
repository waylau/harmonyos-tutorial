package com.waylau.hmos.douyin.ability;

import com.waylau.hmos.douyin.constant.ControlCode;
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
 * Video Control Synchronization Service
 */
public class SyncControlServiceAbility extends Ability {
    private static final HiLogLabel LABEL = new HiLogLabel(0, 0, "SyncControlServiceAbility");
    private final MyRemote remote = new MyRemote(RemoteConstant.REQUEST_SYNC_VIDEO_STATUS);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        remote.setRemoteRequestCallback(
                this::sendEvent);
    }

    @Override
    public void onBackground() {
        super.onBackground();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected IRemoteObject onConnect(Intent intent) {
        super.onConnect(intent);
        return remote.asObject();
    }

    private void sendEvent(int controlCode, Map<?, ?> value) {
        try {
            Intent intent = new Intent();
            Operation operation = new Intent.OperationBuilder().withAction(Constants.PHONE_CONTROL_EVENT).build();
            intent.setOperation(operation);
            intent.setParam(Constants.KEY_CONTROL_CODE, controlCode);
            if (controlCode == ControlCode.SYNC_VIDEO_PROCESS.getCode()) {
                intent.setParam(Constants.KEY_CONTROL_VIDEO_TIME,
                        String.valueOf(value.get(RemoteConstant.REMOTE_KEY_VIDEO_TOTAL_TIME)));
                intent.setParam(Constants.KEY_CONTROL_VIDEO_PROGRESS,
                        String.valueOf(value.get(RemoteConstant.REMOTE_KEY_VIDEO_CURRENT_PROGRESS)));
            } else if (controlCode == ControlCode.SYNC_VIDEO_STATUS.getCode()) {
                intent.setParam(Constants.KEY_CONTROL_VIDEO_PLAYBACK_STATUS,
                        String.valueOf(value.get(RemoteConstant.REMOTE_KEY_VIDEO_CURRENT_PLAYBACK_STATUS)));
            } else {
                intent.setParam(Constants.KEY_CONTROL_VIDEO_VOLUME,
                        String.valueOf(value.get(RemoteConstant.REMOTE_KEY_VIDEO_CURRENT_VOLUME)));
            }
            CommonEventData eventData = new CommonEventData(intent);
            CommonEventManager.publishCommonEvent(eventData);
        } catch (RemoteException e) {
            HiLog.error(LABEL, "publishCommonEvent occur exception.");
        }
    }
}
