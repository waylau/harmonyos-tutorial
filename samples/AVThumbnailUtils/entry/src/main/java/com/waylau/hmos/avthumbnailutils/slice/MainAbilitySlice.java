package com.waylau.hmos.avthumbnailutils.slice;

import com.waylau.hmos.avthumbnailutils.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.PixelMap;
import ohos.media.image.common.Size;
import ohos.media.photokit.metadata.AVThumbnailUtils;

import java.io.*;
import java.nio.file.Paths;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 为按钮设置点击事件回调
        Button buttonGetImage =
                (Button) findComponentById(ResourceTable.Id_button_image);
        buttonGetImage.setClickedListener(listener -> {
            try {
                getImageInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // 为按钮设置点击事件回调
        Button buttonGetVideo =
                (Button) findComponentById(ResourceTable.Id_button_video);
        buttonGetVideo.setClickedListener(listener -> {
            try {
                getVideoInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void getVideoInfo() throws IOException {
        HiLog.info(LABEL_LOG, "before getVideoInfo");

        // 获取数据目录
        File dataDir = new File(this.getDataDir().toString());
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        // 构建目标文件
        File targetFile = new File(Paths.get(dataDir.toString(), "big_buck_bunny.mp4").toString());

        // 获取源文件
        RawFileEntry rawFileEntry =
                this.getResourceManager().getRawFileEntry("resources/rawfile/big_buck_bunny.mp4");
        Resource resource = rawFileEntry.openRawFile();

        // 新建目标文件
        FileOutputStream fos = new FileOutputStream(targetFile);

        byte[] buffer = new byte[4096];
        int count = 0;

        // 源文件内容写入目标文件
        while ((count = resource.read(buffer)) >= 0) {
            fos.write(buffer, 0, count);
        }

        resource.close();
        fos.close();

        Size size = new Size();
        size.height = 640;
        size.width = 320;

        PixelMap pixelMap = AVThumbnailUtils.createVideoThumbnail(targetFile, size);

        // 显示图片
        Image image =
                (Image) findComponentById(ResourceTable.Id_image_vedio);
        image.setPixelMap(pixelMap);

        HiLog.info(LABEL_LOG, "end getVideoInfo");
    }

    private void getImageInfo() throws IOException {
        HiLog.info(LABEL_LOG, "before getImageInfo");

        // 获取数据目录
        File dataDir = new File(this.getDataDir().toString());
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        // 构建目标文件
        File targetFile = new File(Paths.get(dataDir.toString(), "waylau_616_616.jpeg").toString());

        // 获取源文件
        RawFileEntry rawFileEntry =
                this.getResourceManager().getRawFileEntry("resources/rawfile/waylau_616_616.jpeg");
        Resource resource = rawFileEntry.openRawFile();

        // 新建目标文件
        FileOutputStream fos = new FileOutputStream(targetFile);

        byte[] buffer = new byte[4096];
        int count = 0;

        // 源文件内容写入目标文件
        while ((count = resource.read(buffer)) >= 0) {
            fos.write(buffer, 0, count);
        }

        resource.close();
        fos.close();

        Size size = new Size();
        size.height = 250;
        size.width = 250;

        PixelMap pixelMap = AVThumbnailUtils.createImageThumbnail(targetFile, size);

        // 显示图片
        Image image =
                (Image) findComponentById(ResourceTable.Id_image_image);
        image.setPixelMap(pixelMap);

        HiLog.info(LABEL_LOG, "end getImageInfo");
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