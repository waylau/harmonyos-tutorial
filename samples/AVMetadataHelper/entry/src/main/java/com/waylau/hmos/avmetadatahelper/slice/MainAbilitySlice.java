package com.waylau.hmos.avmetadatahelper.slice;

import com.waylau.hmos.avmetadatahelper.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.global.resource.RawFileDescriptor;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.PixelMap;
import ohos.media.photokit.metadata.AVMetadataHelper;

import java.io.IOException;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private AVMetadataHelper avMetadataHelper;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化AVMetadataHelper
        try {
            initAVMetadataHelper();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 为按钮设置点击事件回调
        Button buttonGet =
                (Button) findComponentById(ResourceTable.Id_button_get);
        buttonGet.setClickedListener(listener -> getInfo());
    }

    @Override
    public void onStop() {
        super.onStop();

        // 关闭、释放资源
        release();
    }

    private void initAVMetadataHelper() throws IOException {
        HiLog.info(LABEL_LOG, "before initAVMetadataHelper");
        RawFileDescriptor rawFileDescriptor = this.getResourceManager()
                .getRawFileEntry("resources/rawfile/big_buck_bunny.mp4").openRawFileDescriptor();

        // 创建媒体数据管理AVMetadataHelper对象
        avMetadataHelper = new AVMetadataHelper();

        // 读取指定的媒体文件描述符，读取数据的起始位置的偏移量以及读取的数据长度，设置媒体源。
        avMetadataHelper.setSource(rawFileDescriptor.getFileDescriptor(),
                rawFileDescriptor.getStartPosition(), rawFileDescriptor.getFileSize());

        HiLog.info(LABEL_LOG, "end initAVMetadataHelper, fd:%{public}s," +
                        "StartPosition:%{public}s,FileSize:%{public}s, ",
                rawFileDescriptor.getFileDescriptor(),
                rawFileDescriptor.getStartPosition(),
                rawFileDescriptor.getFileSize());
    }

    private void release() {
        HiLog.info(LABEL_LOG, "release");

        if (avMetadataHelper != null) {
            // 关闭、释放资源
            avMetadataHelper.release();
            avMetadataHelper = null;
        }
    }

    private void getInfo() {
        HiLog.info(LABEL_LOG, "before getInfo");

        // 获取媒体的时长信息
        String duration = avMetadataHelper.resolveMetadata(AVMetadataHelper.AV_KEY_DURATION);

        // 获取媒体的类型
        String mimetype = avMetadataHelper.resolveMetadata(AVMetadataHelper.AV_KEY_MIMETYPE);

        // 获取媒体的高度
        String videoHeight = avMetadataHelper.resolveMetadata(AVMetadataHelper.AV_KEY_VIDEO_HEIGHT);

        // 获取媒体的宽度
        String videoWidth = avMetadataHelper.resolveMetadata(AVMetadataHelper.AV_KEY_VIDEO_WIDTH);

        HiLog.info(LABEL_LOG, "resolveMetadata duration: %{public}s, mimetype: %{public}s, videoHeight: %{public}s, videoWidth: %{public}s", duration, mimetype, videoHeight, videoWidth);

        // 随机获取帧数据
        PixelMap pixelMap = avMetadataHelper.fetchVideoPixelMapByTime();

        HiLog.info(LABEL_LOG, "end getInfo, pixelMap: %{public}s", pixelMap);

        // 展示帧数据
        showImage(pixelMap);
    }

    private void showImage(PixelMap pixelMap) {
        Image image =
                (Image) findComponentById(ResourceTable.Id_image);
        image.setPixelMap(pixelMap);
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
