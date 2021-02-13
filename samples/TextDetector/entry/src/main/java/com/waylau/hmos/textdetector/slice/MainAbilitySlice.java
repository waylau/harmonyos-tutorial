package com.waylau.hmos.textdetector.slice;

import com.waylau.hmos.textdetector.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.ai.cv.common.ConnectionCallback;
import ohos.ai.cv.common.VisionImage;
import ohos.ai.cv.common.VisionManager;
import ohos.ai.cv.text.ITextDetector;
import ohos.global.resource.NotExistException;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;

import java.io.IOException;
import java.io.InputStream;


public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private ITextDetector textDetector;

    private ConnectionCallback connectionCallback = new ConnectionCallback() {
        @Override
        public void onServiceConnect() {
            HiLog.info(LABEL_LOG, "onServiceConnect");
            try {
                getInfo();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotExistException e) {
                e.printStackTrace();
            }
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

        if (textDetector != null) {
            // 关闭、释放资源
            textDetector.release();
            textDetector = null;
        }

        // 调用VisionManager.destroy()方法，断开与能力引擎的连接
        VisionManager.destroy();
    }

    private void getInfo() throws IOException, NotExistException {
        HiLog.info(LABEL_LOG, "before getInfo");

        // 实例化IBarcodeDetector接口
        textDetector = VisionManager.getTextDetector(this);

        // 获取图片流
        InputStream drawableInputStream = getResourceManager().getResource(ResourceTable.Media_cloud_native);

        // 创建图像数据源ImageSource对象
        ImageSource imageSource = ImageSource.create(drawableInputStream, this.getSourceOptions());

        // 普通解码叠加旋转、缩放、裁剪
        PixelMap pixelMap = imageSource.createPixelmap(this.getDecodingOptions());

        VisionImage image = VisionImage.fromPixelMap(pixelMap);

        // 获取文字识别结果
        ohos.ai.cv.text.Text text = new ohos.ai.cv.text.Text();
        int result = textDetector.detect(image, text, null); // 同步

        // 结果展示在Text上
        ohos.agp.components.Text textResult =
                (ohos.agp.components.Text) findComponentById(ResourceTable.Id_text);
        textResult.setText(text.getValue());

        HiLog.info(LABEL_LOG, "end getInfo, result: %{public}s, text: %{public}s",
                result, text.getValue());
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
        decodingOpts.desiredPixelFormat = PixelFormat.ARGB_8888;

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
