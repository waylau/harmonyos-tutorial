package com.waylau.hmos.imagesourceexifutils.slice;

import com.waylau.hmos.imagesourceexifutils.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.global.resource.NotExistException;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ExifUtils;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.ImageInfo;
import ohos.utils.Pair;

import java.io.*;
import java.nio.file.Paths;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private ImageSource imageSource;
    private PixelMap pixelMap;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button buttonGetInfo =
                (Button) findComponentById(ResourceTable.Id_button_get_info);

        // 为按钮设置点击事件回调
        buttonGetInfo.setClickedListener(listener -> {
            try {
                getInfo();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotExistException e) {
                e.printStackTrace();
            }
        });
    }

    private void getInfo() throws IOException, NotExistException {
        // 获取图片流
         InputStream drawableInputStream =  getResourceManager().getResource(ResourceTable.Media_waylau_616_616);

        // 获取图片
        //FileDescriptor fd = getResourceManager().getRawFileEntry("resources/base/media/IMG_20200711_090331.jpg").openRawFileDescriptor().getFileDescriptor();

        // 创建图像数据源ImageSource对象
        //imageSource = ImageSource.create(fd, this.getSourceOptions());
        imageSource = ImageSource.create(drawableInputStream, this.getSourceOptions());

        // 获取嵌入图像文件的缩略图的基本信息
        ImageInfo imageInfo = imageSource.getThumbnailInfo();
        HiLog.info(LABEL_LOG, "imageInfo: %{public}s", imageInfo);

        // 获取嵌入图像文件缩略图的原始数据
        byte[] imageThumbnailBytes = imageSource.getImageThumbnailBytes();
        HiLog.info(LABEL_LOG, "imageThumbnailBytes: %{public}s", imageThumbnailBytes);

        // 获取嵌入图像文件的经纬度信息。

        Pair<Float, Float> lat = ExifUtils.getLatLong(imageSource);
        HiLog.info(LABEL_LOG, "lat first: %{public}s", lat.f);
        HiLog.info(LABEL_LOG, "lat second: %{public}s", lat.s);

        // 获取嵌入图像文件的海拔信息
        double defaultValue = 100;
        double altitude = ExifUtils.getAltitude(imageSource, defaultValue);
        HiLog.info(LABEL_LOG, "altitude: %{public}s", altitude);
    }

    // 设置数据源的格式信息
    private ImageSource.SourceOptions getSourceOptions() {
        ImageSource.SourceOptions sourceOptions = new ImageSource.SourceOptions();
        sourceOptions.formatHint = "image/jpeg";

        return sourceOptions;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (imageSource != null) {
            // 释放资源
            imageSource.release();
        }
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
