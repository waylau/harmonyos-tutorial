package com.waylau.hmos.dataflowstatistics.slice;


import com.waylau.hmos.dataflowstatistics.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;
import ohos.net.*;

import java.io.InputStream;
import java.net.*;
import java.util.concurrent.atomic.AtomicLong;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private TaskDispatcher dispatcher;
    private Image image;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 为按钮设置点击事件回调
        Button buttonOpen =
                (Button) findComponentById(ResourceTable.Id_button_open);
        buttonOpen.setClickedListener(listener -> open());

        dispatcher = getGlobalTaskDispatcher(TaskPriority.DEFAULT);
    }

    private void open() {
        HiLog.info(LABEL_LOG, "before open");

        // 获取所有网卡的下行流量
        AtomicLong rx = new AtomicLong(DataFlowStatistics.getAllRxBytes());

        // 获取所有网卡的上行流量
        AtomicLong tx = new AtomicLong(DataFlowStatistics.getAllTxBytes());

        // 启动线程任务
        dispatcher.syncDispatch(() -> {
            NetManager netManager = NetManager.getInstance(getContext());

            if (!netManager.hasDefaultNet()) {
                return;
            }
            NetHandle netHandle = netManager.getDefaultNet();

            // 可以获取网络状态的变化
            netManager.addDefaultNetStatusCallback(callback);

            // 通过openConnection来获取URLConnection
            HttpURLConnection connection = null;
            try {
                String urlString = "https://waylau.com/images/waylau_181_181.jpg";
                URL url = new URL(urlString);

                URLConnection urlConnection = netHandle.openConnection(url,
                        java.net.Proxy.NO_PROXY);
                if (urlConnection instanceof HttpURLConnection) {
                    connection = (HttpURLConnection) urlConnection;
                }
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(10000);
                connection.connect();

                // 之后可进行url的其他操作
                int code = connection.getResponseCode();
                HiLog.info(LABEL_LOG, "ResponseCode：%{public}s", code);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();

                // 获取所有网卡的下行流量
                rx.set(DataFlowStatistics.getAllRxBytes() - rx.get());

                // 获取所有网卡的上行流量
                tx.set(DataFlowStatistics.getAllTxBytes() - tx.get());

                HiLog.info(LABEL_LOG, "connection disconnect, rx: %{public}s, tx: %{public}s",
                        rx.get(), tx.get());
            }
        });

        HiLog.info(LABEL_LOG, "end open");
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
