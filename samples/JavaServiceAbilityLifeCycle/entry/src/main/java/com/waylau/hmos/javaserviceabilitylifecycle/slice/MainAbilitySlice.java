package com.waylau.hmos.javaserviceabilitylifecycle.slice;

import com.waylau.hmos.javaserviceabilitylifecycle.ResourceTable;
import com.waylau.hmos.javaserviceabilitylifecycle.TimeRemoteObject;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityConnection;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Text;
import ohos.bundle.ElementName;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.IRemoteObject;

import java.time.LocalDateTime;

public class MainAbilitySlice extends AbilitySlice {
    private static final HiLogLabel LOG_LABEL =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, "MainAbilitySlice");
    private TimeRemoteObject timeRemoteObject;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件
        Text textStart = (Text) findComponentById(ResourceTable.Id_text_start);
        textStart.setClickedListener(listener -> {
            // 启动本地服务
            startupLocalService(intent);

            // 连接本地服务
            connectLocalService(intent);
        });

        // 添加点击事件
        Text textStop = (Text) findComponentById(ResourceTable.Id_text_stop);
        textStop.setClickedListener(listener -> {
            // 断开本地服务
            disconnectLocalService(intent);

            // 关闭本地服务
            stopLocalService(intent);
        });


        HiLog.info(LOG_LABEL, "onStart");
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    /**
     * 启动本地服务
     */
    private void startupLocalService(Intent intent) {
        //Intent intent = new Intent();
        //构建操作方式
        Operation operation = new Intent.OperationBuilder()
                // 设备ID
                .withDeviceId("")
                // 包名称
                .withBundleName("com.waylau.hmos.serviceabilitylifecycle")
                // 待启动的Ability名称
                .withAbilityName("com.waylau.hmos.serviceabilitylifecycle.TimeServiceAbility")
                .build();
        //设置操作
        intent.setOperation(operation);
        startAbility(intent);

        HiLog.info(LOG_LABEL, "startupLocalService");
    }

    /**
     * 关闭本地服务
     */
    private void stopLocalService(Intent intent) {
        stopAbility(intent);

        HiLog.info(LOG_LABEL, "stopLocalService");
    }

    // 创建连接回调实例
    private IAbilityConnection connection = new IAbilityConnection() {
        // 连接到Service的回调
        @Override
        public void onAbilityConnectDone(ElementName elementName,
                                         IRemoteObject iRemoteObject, int resultCode) {
            // Client侧需要定义与Service侧相同的IRemoteObject实现类。
            // 开发者获取服务端传过来IRemoteObject对象，并从中解析出服务端传过来的信息。
            timeRemoteObject = (TimeRemoteObject) iRemoteObject;

            HiLog.info(LOG_LABEL, "onAbilityConnectDone, time: %{public}s",
                    timeRemoteObject.getTime());
        }

        // 断开与连接的回调
        @Override
        public void onAbilityDisconnectDone(ElementName elementName, int resultCode) {
            HiLog.info(LOG_LABEL, "onAbilityDisconnectDone");
        }
    };

    /**
     * 连接本地服务
     */
    private void connectLocalService(Intent intent) {
        // 连接Service
        connectAbility(intent, connection);

        HiLog.info(LOG_LABEL, "connectLocalService");
    }

    /**
     * 断开连接本地服务
     */
    private void disconnectLocalService(Intent intent) {
        // 断开连接Service
        disconnectAbility(connection);

        HiLog.info(LOG_LABEL, "disconnectLocalService");
    }
}