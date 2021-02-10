package com.waylau.hmos.camerakit.slice;

import com.waylau.hmos.camerakit.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.surfaceprovider.SurfaceProvider;
import ohos.agp.graphics.Surface;
import ohos.agp.graphics.SurfaceOps;
import ohos.agp.utils.LayoutAlignment;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.camera.CameraKit;
import ohos.media.camera.device.*;
import ohos.media.image.Image;
import ohos.media.image.ImageReceiver;
import ohos.media.image.common.ImageFormat;
import ohos.utils.zson.ZSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static ohos.media.camera.device.Camera.FrameConfigType.FRAME_CONFIG_PICTURE;
import static ohos.media.camera.device.Camera.FrameConfigType.FRAME_CONFIG_PREVIEW;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    // 相机
    private Camera cameraDevice;
    // 相机创建和相机运行时的回调
    private CameraStateCallbackImpl cameraStateCallback = new CameraStateCallbackImpl();
    // 执行回调的EventHandler
    private EventHandler eventHandler = new EventHandler(EventRunner.create("CameraCb"));

    // 图像帧数据接收处理对象
    private ImageReceiver imageReceiver;
    // 配置预览的Surface
    private Surface previewSurface;
    private FrameConfig.Builder frameConfigBuilder;
    private CameraConfig.Builder cameraConfigBuilder;
    private FrameStateCallbackImpl frameStateCallbackImpl = new FrameStateCallbackImpl();
    private SurfaceProvider surfaceProvider;

    private FrameConfig previewFrameConfig;

    private FrameConfig.Builder framePictureConfigBuilder;


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化预览的Surface
        initSurface();

        Button buttonCreateCamera =
                (Button) findComponentById(ResourceTable.Id_button_create_camera);

        // 为按钮设置点击事件回调
        buttonCreateCamera.setClickedListener(listener -> createCamera());

        Button buttonSingleCapture =
                (Button) findComponentById(ResourceTable.Id_button_single_capture);

        // 为按钮设置点击事件回调
        buttonSingleCapture.setClickedListener(listener -> capture());
    }

    private void capture() {
        // 获取照片前准备
        captureInit();

        // 获取拍照配置模板
        framePictureConfigBuilder = cameraDevice.getFrameConfigBuilder(FRAME_CONFIG_PICTURE);
        // 配置拍照Surface
        framePictureConfigBuilder.addSurface(imageReceiver.getRecevingSurface());
        // 配置拍照其他参数
        framePictureConfigBuilder.setImageRotation(90);
        try {
            // 启动单帧捕获(拍照)
            cameraDevice.triggerSingleCapture(framePictureConfigBuilder.build());
        } catch (IllegalArgumentException e) {
            HiLog.error(LABEL_LOG, "Argument Exception");
        } catch (IllegalStateException e) {
            HiLog.error(LABEL_LOG, "State Exception");
        }
    }

    // 单帧捕获生成图像回调Listener
    private final ImageReceiver.IImageArrivalListener imageArrivalListener = new ImageReceiver.IImageArrivalListener() {
        @Override
        public void onImageArrival(ImageReceiver imageReceiver) {
            // 创建相机设备
            HiLog.info(LABEL_LOG, "onImageArrival");

            StringBuffer fileName = new StringBuffer("picture_");
            fileName.append(UUID.randomUUID()).append(".jpg"); // 定义生成图片文件名

            // 获取数据目录
            File dataDir = new File(getExternalCacheDir().toString());
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            // 构建目标文件
            String dirFile = dataDir.toString();

            File myFile = new File(dirFile, fileName.toString()); // 创建图片文件
            ImageSaver imageSaver = new ImageSaver(imageReceiver.readNextImage(), myFile); // 创建一个读写线程任务用于保存图片
            eventHandler.postTask(imageSaver); // 执行读写线程任务生成图片
        }
    };

    // 保存图片, 图片数据读写，及图像生成见run方法
    class ImageSaver implements Runnable {
        private final Image myImage;
        private final File myFile;

        ImageSaver(Image image, File file) {
            myImage = image;
            myFile = file;
        }

        @Override
        public void run() {
            Image.Component component = myImage.getComponent(ImageFormat.ComponentType.JPEG);
            byte[] bytes = new byte[component.remaining()];
            component.read(bytes);
            FileOutputStream output = null;
            try {
                output = new FileOutputStream(myFile);
                output.write(bytes); // 写图像数据
                output.flush();

                File dataDir = new File(getExternalCacheDir().toString());
                HiLog.info(LABEL_LOG, "dateDir list:", dataDir.list());
            } catch (IOException e) {
                HiLog.error(LABEL_LOG, "save picture occur exception!");
            } finally {
                myImage.release();
                if (output != null) {
                    try {
                        output.close(); // 关闭流
                    } catch (IOException e) {
                        HiLog.error(LABEL_LOG, "image release occur exception!");
                    }
                }
            }
        }
    }

    private void captureInit() {
        imageReceiver.setImageArrivalListener(imageArrivalListener);
    }

    private void createCamera() {
        // 获取CameraKit对象
        openCamera();
    }

    private void initSurface() {
        // 获取组件对象
        DirectionalLayout stackLayout = (DirectionalLayout) findComponentById(ResourceTable.Id_layout);
        surfaceProvider = new SurfaceProvider(getContext());
        // 放在 AGP 容器组件的顶层
        surfaceProvider.pinToZTop(true);
        // 设置样式
        DirectionalLayout.LayoutConfig layoutConfig = new DirectionalLayout.LayoutConfig();
        layoutConfig.alignment = LayoutAlignment.CENTER;
        layoutConfig.setMarginBottom(55);
        // 根据比例，设置相机长宽值
        layoutConfig.width = 1000;
        layoutConfig.height = 1000;
        surfaceProvider.setLayoutConfig(layoutConfig);
        // 将组件添加到容器中
        stackLayout.addComponent(surfaceProvider);
        // 获取SurfaceOps对象
        SurfaceOps surfaceOps = surfaceProvider.getSurfaceOps().get();
        // 设置像素格式
        surfaceOps.setFormat(ImageFormat.JPEG);
        // 设置屏幕一直打开
        surfaceOps.setKeepScreenOn(true);
        // 添加回调
        surfaceOps.addCallback(new SurfaceOpsCallBack());

        // 创建ImageReceiver对象，注意create函数中宽度要大于高度；5为最大支持的图像数，请根据实际设置
        imageReceiver = ImageReceiver.create(100, 100, ImageFormat.JPEG, 5);
    }

    private void openCamera() {
        // 获取CameraKit对象
        CameraKit cameraKit = CameraKit.getInstance(this);
        if (cameraKit != null) {
            try {
                // 获取当前设备的逻辑相机列表
                String[] cameraIds = cameraKit.getCameraIds();
                if (cameraIds.length <= 0) {
                    HiLog.error(LABEL_LOG, "cameraIds size is 0");
                } else {
                    for (String cameraId : cameraIds) {
                        CameraInfo cameraInfo = cameraKit.getCameraInfo(cameraId);
                        HiLog.info(LABEL_LOG,
                                "cameraId: %{public}s, CameraInfo：%{public}s",
                                cameraId, ZSONObject.toZSONString(cameraInfo));

                        CameraAbility cameraAbility = cameraKit.getCameraAbility(cameraId);
                        HiLog.info(LABEL_LOG,
                                "cameraId: %{public}s, CameraAbility：%{public}s",
                                cameraId, ZSONObject.toZSONString(cameraAbility));
                    }
                }

                // 相机创建和相机运行时的回调
                if (cameraStateCallback == null) {
                    HiLog.error(LABEL_LOG, "cameraStateCallback is null");
                }

                // 执行回调的EventHandler
                if (eventHandler == null) {
                    HiLog.error(LABEL_LOG, "eventHandler is null");
                }

                // 创建相机设备
                cameraKit.createCamera(cameraIds[1], cameraStateCallback, eventHandler);
            } catch (IllegalStateException e) {
                // 处理异常
                HiLog.error(LABEL_LOG, "exception %{public}s", e.getMessage());
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // 释放资源
        releaseCamera();
    }


    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private final class CameraStateCallbackImpl extends CameraStateCallback {
        @Override
        public void onCreated(Camera camera) {
            // 创建相机设备
            HiLog.info(LABEL_LOG, "Camera onCreated");

            cameraConfigBuilder = camera.getCameraConfigBuilder();
            if (cameraConfigBuilder == null) {
                HiLog.error(LABEL_LOG, "onCreated cameraConfigBuilder is null");
                return;
            }

            // 配置预览的Surface
            cameraConfigBuilder.addSurface(previewSurface);
            // 配置拍照的Surface
            cameraConfigBuilder.addSurface(imageReceiver.getRecevingSurface());
            // 配置帧结果的回调
            cameraConfigBuilder.setFrameStateCallback(frameStateCallbackImpl, eventHandler);

            try {
                // 相机设备配置
                camera.configure(cameraConfigBuilder.build());
            } catch (IllegalArgumentException e) {
                HiLog.error(LABEL_LOG, "Argument Exception");
            } catch (IllegalStateException e) {
                HiLog.error(LABEL_LOG, "State Exception");
            }
        }

        @Override
        public void onConfigured(Camera camera) {
            // 配置相机设备
            HiLog.info(LABEL_LOG, "Camera onConfigured");

            // 获取预览配置模板
            frameConfigBuilder = camera.getFrameConfigBuilder(FRAME_CONFIG_PREVIEW);
            // 配置预览Surface
            frameConfigBuilder.addSurface(previewSurface);
            previewFrameConfig = frameConfigBuilder.build();
            try {
                // 启动循环帧捕获
                camera.triggerLoopingCapture(previewFrameConfig);

                // 设置类变量
                cameraDevice = camera;
            } catch (IllegalArgumentException e) {
                HiLog.error(LABEL_LOG, "Argument Exception");
            } catch (IllegalStateException e) {
                HiLog.error(LABEL_LOG, "State Exception");
            }
        }

        @Override
        public void onPartialConfigured(Camera camera) {
            // 当使用了addDeferredSurfaceSize配置了相机，会接到此回调
            HiLog.info(LABEL_LOG, "Camera onPartialConfigured");
        }

        @Override
        public void onReleased(Camera camera) {
            // 释放相机设备
            HiLog.info(LABEL_LOG, "Camera onReleased");
        }

        @Override
        public void onFatalError(Camera camera, int errorCode) {
            HiLog.info(LABEL_LOG, "Camera onFatalError, errorCode: %{public}s", errorCode);
        }

        @Override
        public void onCreateFailed(String cameraId, int errorCode) {
            HiLog.info(LABEL_LOG, "Camera onCreateFailed, errorCode: %{public}s", errorCode);
        }

    }

    private final class FrameStateCallbackImpl extends FrameStateCallback {
        @Override
        public void onFrameStarted(Camera camera, FrameConfig frameConfig, long frameNumber, long timestamp) {
            // 开始帧捕获时，触发回调
            HiLog.info(LABEL_LOG, "onFrameStarted");
        }

        @Override
        public void onFrameFinished(Camera camera, FrameConfig frameConfig, FrameResult frameResult) {
            // 当帧捕获完成并且所有结果都可用时调用
            HiLog.info(LABEL_LOG, "onFrameFinished");
        }

        @Override
        public void onFrameProgressed(Camera camera, FrameConfig frameConfig, FrameResult frameResult) {
            // 在帧捕获期间有部分结果时调用
            HiLog.info(LABEL_LOG, "onFrameProgressed");
        }

        @Override
        public void onFrameError(Camera camera, FrameConfig frameConfig, int errorCode, FrameResult frameResult) {
            // 在帧捕获过程中发生错误时调用
            HiLog.info(LABEL_LOG, "onFrameError");
        }

        @Override
        public void onCaptureTriggerStarted(Camera camera, int captureTriggerId, long firstFrameNumber) {
            // 在启动触发器的帧捕获时调用
            HiLog.info(LABEL_LOG, "onCaptureTriggerStarted");
        }

        @Override
        public void onCaptureTriggerFinished(Camera camera, int captureTriggerId, long lastFrameNumber) {
            // 当触发的帧捕获动作完成时调用
            HiLog.info(LABEL_LOG, "onCaptureTriggerFinished");
        }

        @Override
        public void onCaptureTriggerInterrupted(Camera camera, int captureTriggerId) {
            // 当已触发的帧捕获动作提前停止时调用
            HiLog.info(LABEL_LOG, "onCaptureTriggerInterrupted");
        }
    }

    private class SurfaceOpsCallBack implements SurfaceOps.Callback {

        @Override
        public void surfaceCreated(SurfaceOps surfaceOps) {
            HiLog.info(LABEL_LOG, "surfaceCreated");

            // 获取surface对象
            previewSurface = surfaceOps.getSurface();
        }

        @Override
        public void surfaceChanged(SurfaceOps surfaceOps, int i, int i1, int i2) {
            HiLog.info(LABEL_LOG, "surfaceChanged");
        }

        @Override
        public void surfaceDestroyed(SurfaceOps surfaceOps) {
            HiLog.info(LABEL_LOG, "surfaceDestroyed");
        }
    }

    private void releaseCamera() {
        if (cameraDevice != null) {
            // 关闭相机和释放资源
            cameraDevice.release();
            cameraDevice = null;
        }

        // 拍照配置模板置空
        frameConfigBuilder = null;
        // 预览配置模板置空
        previewFrameConfig = null;
    }

}
