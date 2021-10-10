package com.waylau.hmos.douyin.remote;

import com.waylau.hmos.douyin.constant.RemoteConstant;

import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.IRemoteBroker;
import ohos.rpc.IRemoteObject;
import ohos.rpc.MessageOption;
import ohos.rpc.MessageParcel;
import ohos.rpc.RemoteException;

import java.util.HashMap;
import java.util.Map;

/**
 * Establish a remote connection
 */
public class MyRemoteProxy implements IRemoteBroker {
    private static final HiLogLabel LABEL = new HiLogLabel(0, 0, "MyRemoteProxy");
    private static final int ERR_OK = 0;
    private final IRemoteObject remote;

    public MyRemoteProxy(IRemoteObject remote) {
        this.remote = remote;
    }

    @Override
    public IRemoteObject asObject() {
        return remote;
    }

    /**
     * send data to remote.
     *
     * @param requestType request type.
     * @param controlCode control code sent from phone controller.
     * @param extra       extra message.
     * @return Returns true if data is successfully sent to the remote; returns false otherwise.
     */
    public boolean sendDataToRemote(int requestType, int controlCode, String extra) {
        MessageParcel data = MessageParcel.obtain();
        data.writeInterfaceToken(RemoteConstant.REMOTE_CONTROL_REQUEST_TOKEN);
        data.writeInt(controlCode);
        Map<String, String> sendValue = new HashMap<>();
        sendValue.put(RemoteConstant.REMOTE_KEY_CONTROL_VALUE, extra);
        data.writeMap(sendValue);
        return sendDataToRemote(requestType, data);
    }

    /**
     * send data to Remote device.
     *
     * @param requestType request type in RemoteConstant.
     * @param controlCode control code in ControlCode.
     * @param extra       extra info.
     * @return Returns true if data is successfully sent to the remote; returns false otherwise.
     */
    public boolean sendDataToRemote(int requestType, int controlCode, Map<String, String> extra) {
        MessageParcel data = MessageParcel.obtain();
        data.writeInterfaceToken(RemoteConstant.REMOTE_CONTROL_REQUEST_TOKEN);
        data.writeInt(controlCode);
        data.writeMap(extra);
        return sendDataToRemote(requestType, data);
    }

    private boolean sendDataToRemote(int requestType, MessageParcel data) {
        boolean result = false;
        HiLog.info(LABEL, "send data to remote control service");
        MessageParcel reply = MessageParcel.obtain();
        MessageOption option = new MessageOption(MessageOption.TF_SYNC);
        try {
            boolean isSuccess = remote.sendRequest(requestType, data, reply, option);
            int ec = reply.readInt();
            if (!isSuccess || ec != ERR_OK) {
                HiLog.error(LABEL, "remote service return error.");
            } else {
                result = true;
            }
        } catch (RemoteException e) {
            HiLog.error(LABEL, "RemoteException");
        } finally {
            data.reclaim();
            reply.reclaim();
        }
        return result;
    }
}
