package com.waylau.hmos.douyin.remote;

import com.waylau.hmos.douyin.constant.RemoteConstant;

import ohos.rpc.IRemoteBroker;
import ohos.rpc.IRemoteObject;
import ohos.rpc.MessageOption;
import ohos.rpc.MessageParcel;
import ohos.rpc.RemoteObject;

import java.util.Map;

/**
 * Proxy for remote device.
 */
public class MyRemote extends RemoteObject implements IRemoteBroker {
    private static final int ERR_OK = 0;
    private final int requestType;
    private RemoteRequestCallback remoteRequestCallback;

    public MyRemote(int requestType) {
        super(MyRemote.class.getName());
        this.requestType = requestType;
    }

    @Override
    public IRemoteObject asObject() {
        return this;
    }

    @Override
    public boolean onRemoteRequest(int code, MessageParcel data, MessageParcel reply, MessageOption option) {
        if (code == requestType && RemoteConstant.REMOTE_CONTROL_REQUEST_TOKEN.equals(data.readInterfaceToken())) {
            reply.writeInt(ERR_OK);
            remoteRequestCallback.sendEvent(data.readInt(), data.readMap());
            return true;
        }
        return false;
    }

    /**
     * register remote request callback.
     *
     * @param remoteRequestCallback remote request callback.
     */
    public void setRemoteRequestCallback(RemoteRequestCallback remoteRequestCallback) {
        this.remoteRequestCallback = remoteRequestCallback;
    }

    /**
     * unregister remote request callback.
     */
    public interface RemoteRequestCallback {
        void sendEvent(int controlCode, Map<?, ?> value);
    }
}
