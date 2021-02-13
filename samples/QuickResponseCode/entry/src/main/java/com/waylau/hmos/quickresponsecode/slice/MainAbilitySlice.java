package com.waylau.hmos.quickresponsecode.slice;

import com.waylau.hmos.quickresponsecode.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.ai.cv.common.ConnectionCallback;
import ohos.ai.cv.common.VisionManager;
import ohos.ai.cv.qrcode.IBarcodeDetector;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;


public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private IBarcodeDetector barcodeDetector;

    private ConnectionCallback connectionCallback = new ConnectionCallback() {
        @Override
        public void onServiceConnect() {
            HiLog.info(LABEL_LOG, "onServiceConnect");
            getInfo();
        }

        @Override
        public void onServiceDisconnect() {
            HiLog.info(LABEL_LOG, "onServiceDisconnect");
        }
    };

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 为按钮设置点击事件回调
        Button buttonGet =
                (Button) findComponentById(ResourceTable.Id_button_get);
        buttonGet.setClickedListener(listener -> {
            // 建立与能力引擎的连接
            int result = VisionManager.init(this, connectionCallback);

            HiLog.info(LABEL_LOG, "VisionManager init, result: %{public}s", result);
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        // 关闭、释放资源
        release();
    }

    private void release() {
        HiLog.info(LABEL_LOG, "release");

        if (barcodeDetector != null) {
            // 关闭、释放资源
            barcodeDetector.release();
            barcodeDetector = null;
        }

        // 调用VisionManager.destroy()方法，断开与能力引擎的连接
        VisionManager.destroy();
    }

    private void getInfo() {
        HiLog.info(LABEL_LOG, "before getInfo");

        // 实例化IBarcodeDetector接口
        barcodeDetector = VisionManager.getBarcodeDetector(this);

        // 定义码生成图像的尺寸，并根据图像大小分配字节流数组空间
        final int SAMPLE_LENGTH = 500;
        byte[] byteArray = new byte[SAMPLE_LENGTH * SAMPLE_LENGTH * 4];

        // 根据输入的字符串信息生成相应的二维码图片字节流
        int result = barcodeDetector.detect("Welcome to waylau.com",
                byteArray, SAMPLE_LENGTH, SAMPLE_LENGTH);

        // 展示帧数据
        showImage(byteArray);

        HiLog.info(LABEL_LOG, "end getInfo, result: %{public}s", result);
    }

    private void showImage(byte[] byteArray) {
        Image image =
                (Image) findComponentById(ResourceTable.Id_image);

        // 创建图像数据源ImageSource对象
        ImageSource imageSource = ImageSource.create(byteArray, this.getSourceOptions());

        // 普通解码叠加旋转、缩放、裁剪
        PixelMap pixelMap = imageSource.createPixelmap(this.getDecodingOptions());

        Image imageDecode =
                (Image) findComponentById(ResourceTable.Id_image);

        imageDecode.setPixelMap(pixelMap);


        image.setPixelMap(pixelMap);
    }

    // 设置数据源的格式信息
    private ImageSource.SourceOptions getSourceOptions() {
        ImageSource.SourceOptions sourceOptions = new ImageSource.SourceOptions();
        sourceOptions.formatHint = "image/jpeg";

        return sourceOptions;
    }

    // 设置解码格式
    private ImageSource.DecodingOptions getDecodingOptions() {
        ImageSource.DecodingOptions decodingOpts = new ImageSource.DecodingOptions();
        decodingOpts.desiredPixelFormat= PixelFormat.ARGB_8888;

        return decodingOpts;
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
