package com.waylau.hmos.categoryorientationagent.slice;

import com.waylau.hmos.categoryorientationagent.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.sensor.agent.CategoryOrientationAgent;
import ohos.sensor.bean.CategoryOrientation;
import ohos.sensor.data.CategoryOrientationData;
import ohos.sensor.listener.ICategoryOrientationDataCallback;
import ohos.utils.zson.ZSONObject;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);
    private static final int MATRIX_LENGTH = 9;
    private static final long INTERVAL = 100000000L;

    private ICategoryOrientationDataCallback orientationDataCallback;

    private CategoryOrientationAgent categoryOrientationAgent;

    private CategoryOrientation orientationSensor;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化对象
        initData();

        // 为按钮设置点击事件回调
        Button buttonAdd =
                (Button) findComponentById(ResourceTable.Id_button_add);
        buttonAdd.setClickedListener(listener -> add());

        Button buttonRemove =
                (Button) findComponentById(ResourceTable.Id_button_remove);
        buttonRemove.setClickedListener(listener -> remove());
    }

    private void initData() {
        orientationDataCallback = new ICategoryOrientationDataCallback() {
            @Override
            public void onSensorDataModified(CategoryOrientationData categoryOrientationData) {
                // 对接收的categoryOrientationData传感器数据对象解析和使用
                //获取传感器的维度信息
                int dim = categoryOrientationData.getSensorDataDim();

                // 获取方向类传感器的第一维数据
                float degree = categoryOrientationData.getValues()[0];

                // 根据旋转矢量传感器的数据获得旋转矩阵
                float[] rotationMatrix = new float[MATRIX_LENGTH];
                CategoryOrientationData.getDeviceRotationMatrix(rotationMatrix,
                        categoryOrientationData.values);

                // 根据计算出来的旋转矩阵获取设备的方向
                float[] rotationAngle = new float[MATRIX_LENGTH];
                float[] rotationAngleResult =
                        CategoryOrientationData.getDeviceOrientation(rotationMatrix, rotationAngle);

                HiLog.info(LABEL_LOG, "dim:%{public}s, degree: %{public}s, " +
                                "rotationMatrix: %{public}s, rotationAngle: %{public}s",
                        dim, degree, ZSONObject.toZSONString(rotationMatrix),
                        ZSONObject.toZSONString(rotationAngleResult));
            }

            @Override
            public void onAccuracyDataModified(CategoryOrientation categoryOrientation, int i) {
                // 使用变化的精度
                HiLog.info(LABEL_LOG, "onAccuracyDataModified");
            }

            @Override
            public void onCommandCompleted(CategoryOrientation categoryOrientation) {
                // 传感器执行命令回调
                HiLog.info(LABEL_LOG, "onCommandCompleted");
            }
        };

        categoryOrientationAgent = new CategoryOrientationAgent();
    }

    private void remove() {
        HiLog.info(LABEL_LOG, "before remove");

        if (orientationSensor != null) {
            // 取消订阅传感器数据
            categoryOrientationAgent.releaseSensorDataCallback(
                    orientationDataCallback, orientationSensor);
        }

        HiLog.info(LABEL_LOG, "end remove");
    }


    private void add() {
        HiLog.info(LABEL_LOG, "before add");

        // 获取传感器对象，并订阅传感器数据
        orientationSensor = categoryOrientationAgent.getSingleSensor(
                CategoryOrientation.SENSOR_TYPE_ORIENTATION);
        if (orientationSensor != null) {
            categoryOrientationAgent.setSensorDataCallback(
                    orientationDataCallback, orientationSensor, INTERVAL);
        }

        HiLog.info(LABEL_LOG, "end add");
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
