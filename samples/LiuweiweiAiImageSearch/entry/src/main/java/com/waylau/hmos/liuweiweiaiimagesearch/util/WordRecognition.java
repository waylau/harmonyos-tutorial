package com.waylau.hmos.liuweiweiaiimagesearch.util;

import com.waylau.hmos.liuweiweiaiimagesearch.slice.MainAbilitySlice;
import ohos.ai.cv.common.ConnectionCallback;
import ohos.ai.cv.common.VisionCallback;
import ohos.ai.cv.common.VisionConfiguration;
import ohos.ai.cv.common.VisionImage;
import ohos.ai.cv.common.VisionManager;
import ohos.ai.cv.text.ITextDetector;
import ohos.ai.cv.text.Text;
import ohos.ai.cv.text.TextConfiguration;
import ohos.ai.cv.text.TextDetectType;
import ohos.app.Context;
import ohos.eventhandler.InnerEvent;
import ohos.global.resource.NotExistException;
import ohos.global.resource.Resource;
import ohos.global.resource.ResourceManager;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;
import ohos.media.image.common.Rect;
import ohos.media.image.common.Size;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordRecognition {
    private static final boolean IS_ASYNC = false;
    private static final int IS_ASYNC_CODE = 700;
    private Context slice;
    private ITextDetector textDetector;
    private PixelMap pixelMap;
    private MainAbilitySlice.MyEventHandle handle;
    private int[] pictureLists;
    private int mediaId;
    private Map maps = new HashMap<>();
    private int index;
    private int result;

    public void setParams(Context context, int[] pictureIds, MainAbilitySlice.MyEventHandle myEventHandle) {
        slice = context;
        pictureLists = pictureIds;
        handle = myEventHandle;
    }

    public void wordRecognition(Context context, int resId, MainAbilitySlice.MyEventHandle myEventHandle) {
        mediaId = resId;
        // 实例化ITextDetector接口
        textDetector = VisionManager.getTextDetector(context);

        // 实例化VisionImage对象image，并传入待检测图片pixelMap
        pixelMap = getPixelMap(resId);
        VisionImage image = VisionImage.fromPixelMap(pixelMap);

        // 定义VisionCallback<Text>回调，异步模式下用到
        VisionCallback<Text> visionCallback = getVisionCallback();

        // 定义ConnectionCallback回调，实现连接能力引擎成功与否后的操作
        ConnectionCallback connectionCallback = getConnectionCallback(image, visionCallback);

        // 建立与能力引擎的连接
        VisionManager.init(context, connectionCallback);
    }

    private VisionCallback getVisionCallback() {
        return new VisionCallback<Text>() {
            @Override
            public void onResult(Text text) {
                sendResult(text.getValue());
            }

            @Override
            public void onError(int i) {
            }

            @Override
            public void onProcessing(float v) {
            }
        };
    }

    private ConnectionCallback getConnectionCallback(VisionImage image, VisionCallback<Text> visionCallback) {
        return new ConnectionCallback() {
            @Override
            public void onServiceConnect() {
                // 实例化Text对象text
                Text text = new Text();

                // 通过TextConfiguration配置textDetector()方法的运行参数
                TextConfiguration.Builder builder = new TextConfiguration.Builder();
                builder.setProcessMode(VisionConfiguration.MODE_IN);
                builder.setDetectType(TextDetectType.TYPE_TEXT_DETECT_FOCUS_SHOOT);
                builder.setLanguage(TextConfiguration.AUTO);
                TextConfiguration config = builder.build();
                textDetector.setVisionConfiguration(config);
                // 调用ITextDetector的detect()方法
                if (!IS_ASYNC) {
                    int result2 = textDetector.detect(image, text, null); // 同步
                    sendResult(text.getValue());
                } else {
                    int result2 = textDetector.detect(image, null, visionCallback); // 异步
                }
            }

            @Override
            public void onServiceDisconnect() {
                // 释放
                if ((!IS_ASYNC && (result == 0)) || (IS_ASYNC && (result == IS_ASYNC_CODE))) {
                    textDetector.release();
                }
                if (pixelMap != null) {
                    pixelMap.release();
                    pixelMap = null;
                }
                VisionManager.destroy();
            }
        };
    }

    public void sendResult(String value) {
        if (textDetector != null) {
            textDetector.release();
        }
        if (pixelMap != null) {
            pixelMap.release();
            pixelMap = null;
            VisionManager.destroy();
        }
        if (value != null) {
            maps.put(mediaId, value);
        }
        if ((maps != null) && (maps.size() == pictureLists.length)) {
            InnerEvent event = InnerEvent.get(1, 0, maps);
            handle.sendEvent(event);
        } else {
            wordRecognition(slice, pictureLists[index], handle);
            index++;
        }
    }

    // 获取图片
    private PixelMap getPixelMap(int resId) {
        ResourceManager manager = slice.getResourceManager();

        byte[] datas = new byte[0];
        try {
            Resource resource = manager.getResource(resId);
            datas = readBytes(resource);
            resource.close();
        } catch (IOException | NotExistException e) {
            LogUtil.error("get pixelmap failed, read resource bytes failed, ", e.getLocalizedMessage());
        }

        ImageSource.SourceOptions srcOpts = new ImageSource.SourceOptions();
        srcOpts.formatHint = "image/jpg";
        ImageSource imageSource;
        imageSource = ImageSource.create(datas, srcOpts);
        ImageSource.DecodingOptions decodingOpts = new ImageSource.DecodingOptions();
        decodingOpts.desiredSize = new Size(0, 0);
        decodingOpts.desiredRegion = new Rect(0, 0, 0, 0);
        decodingOpts.desiredPixelFormat = PixelFormat.ARGB_8888;
        pixelMap = imageSource.createPixelmap(decodingOpts);
        return pixelMap;
    }

    private static byte[] readBytes(Resource resource) {
        final int bufferSize = 1024;
        final int ioEnd = -1;

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffers = new byte[bufferSize];
        byte[] results = new byte[0];
        while (true) {
            try {
                int readLen = resource.read(buffers, 0, bufferSize);
                if (readLen == ioEnd) {
                    results = output.toByteArray();
                    break;
                }
                output.write(buffers, 0, readLen);
            } catch (IOException e) {
                LogUtil.error("OrcAbilitySlice.getPixelMap", "read resource failed ");
                break;
            } finally {
                try {
                    output.close();
                } catch (IOException e) {
                    LogUtil.error("OrcAbilitySlice.getPixelMap", "close output failed");
                }
            }
        }
        return results;
    }
}