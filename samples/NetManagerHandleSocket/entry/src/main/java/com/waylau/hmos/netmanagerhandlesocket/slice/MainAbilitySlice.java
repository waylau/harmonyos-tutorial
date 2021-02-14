package com.waylau.hmos.netmanagerhandlesocket.slice;

import com.waylau.hmos.netmanagerhandlesocket.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.net.*;

import java.net.*;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private final static String HOST = "127.0.0.1";
    private final static int PORT = 8551;

    private TaskDispatcher dispatcher;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 为按钮设置点击事件回调
        Button buttonStart =
                (Button) findComponentById(ResourceTable.Id_button_start);
        buttonStart.setClickedListener(listener -> initServer());

        Button buttonOpen =
                (Button) findComponentById(ResourceTable.Id_button_open);
        buttonOpen.setClickedListener(listener -> open());

        dispatcher = getGlobalTaskDispatcher(TaskPriority.DEFAULT);
    }

    private void open() {
        HiLog.info(LABEL_LOG, "before open");

        // 启动线程任务
        dispatcher.syncDispatch(() -> {
            NetManager netManager = NetManager.getInstance(null);

            if (!netManager.hasDefaultNet()) {
                HiLog.error(LABEL_LOG, "netManager.hasDefaultNet() failed");
                return;
            }

            NetHandle netHandle = netManager.getDefaultNet();

            // 通过Socket绑定来进行数据传输
            DatagramSocket socket = null;
            try {
                // 绑定到Socket
                InetAddress address = netHandle.getByName(HOST);
                socket = new DatagramSocket();
                netHandle.bindSocket(socket);

                // 发送数据
                String data = "Welcome to waylau.com";
                DatagramPacket request = new DatagramPacket(data.getBytes("utf-8"), data.length(), address, PORT);
                socket.send(request);

                // 显示到界面
                HiLog.info(LABEL_LOG, "send data: " + data);
            } catch (Exception e) {
                HiLog.error(LABEL_LOG, "send IOException: " + e.toString());
            } finally {
                if (null != socket) {
                    socket.close();
                }
            }
        });

        HiLog.info(LABEL_LOG, "end open");
    }

    private void initServer() {
        HiLog.info(LABEL_LOG, "before initServer");

        // 启动线程任务
        dispatcher.asyncDispatch(() -> {
            NetManager netManager = NetManager.getInstance(null);

            if (!netManager.hasDefaultNet()) {
                HiLog.error(LABEL_LOG, "netManager.hasDefaultNet() failed");
                return;
            }

            NetHandle netHandle = netManager.getDefaultNet();

            // 通过Socket绑定来进行数据传输
            DatagramSocket socket = null;
            try {
                // 绑定到Socket
                InetAddress address = netHandle.getByName(HOST);
                socket = new DatagramSocket(PORT, address);
                netHandle.bindSocket(socket);
                HiLog.info(LABEL_LOG, "wait for receive data");

                // 接收数据
                byte[] buffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);
                int len = response.getLength();
                String data = new String(buffer, "utf-8").substring(0, len);

                // 显示到界面
                HiLog.info(LABEL_LOG, "receive data: " + data);
            } catch (Exception e) {
                HiLog.error(LABEL_LOG, "send IOException: " + e.toString());
            } finally {
                if (null != socket) {
                    socket.close();
                }
            }
        });

        HiLog.info(LABEL_LOG, "end initServer");
    }

    private NetStatusCallback callback = new NetStatusCallback() {
        public void onAvailable(NetHandle handle) {
            HiLog.info(LABEL_LOG, "onAvailable");
        }

        public void onBlockedStatusChanged(NetHandle handle, boolean blocked) {
            HiLog.info(LABEL_LOG, "onBlockedStatusChanged");
        }

        public void onLosing(NetHandle handle, long maxMsToLive) {
            HiLog.info(LABEL_LOG, "onLosing");
        }

        public void onLost(NetHandle handle) {
            HiLog.info(LABEL_LOG, "onLosing");
        }

        public void onUnavailable() {
            HiLog.info(LABEL_LOG, "onUnavailable");
        }

        public void onCapabilitiesChanged(NetHandle handle, NetCapabilities networkCapabilities) {
            HiLog.info(LABEL_LOG, "onCapabilitiesChanged");
        }

        public void onConnectionPropertiesChanged(NetHandle handle, ConnectionProperties connectionProperties) {
            HiLog.info(LABEL_LOG, "onConnectionPropertiesChanged");
        }
    };

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
