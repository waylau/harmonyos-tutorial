package com.waylau.hmos.electronicalbum.slice;

import com.waylau.hmos.electronicalbum.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.Image;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private Image image;
    private List<String> imageList; // 图片列表
    private AnimatorProperty animator;
    private TaskDispatcher dispatcher;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化组件
        image = (Image) findComponentById(ResourceTable.Id_image);
        dispatcher = getUITaskDispatcher();
        animator = image.createAnimatorProperty();

        // 初始化数据
        initData();

        // 启动动画
        startMove();
    }

    @Override
    public void onStop() {
        super.onStop();

        // 释放资源
        if (animator != null) {
            animator.release();
            animator = null;
        }
    }

    private void startMove() {
        final int[] currentIndex = {0};

        // 延迟1秒，再刷新进度
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HiLog.info(LABEL_LOG, "before run");

                currentIndex[0]++;

                // 取模
                currentIndex[0] = Math.floorMod(currentIndex[0], imageList.size());

                updateImage(imageList.get(currentIndex[0]));

                int random = new Random().nextInt(10);
                HiLog.info(LABEL_LOG, "random: %{public}s", random);

                animator.moveFromX(5 * random)
                        .moveToX(200 * random)
                        .rotate(36 * random)
                        .alpha(random)
                        .setDuration(250 * random).
                        setDelay(50 * random)
                        .setLoopedCount(1);

                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();

                dispatcher.delayDispatch(this, 4500);

                HiLog.info(LABEL_LOG, "end run");
            }
        };

        dispatcher.asyncDispatch(runnable);
    }

    // 更新图片
    private void updateImage(String fileName) {
        HiLog.info(LABEL_LOG, "before setPixelMap, fileName: %{public}s", fileName);

        try {
            RawFileEntry rawFileEntry = getResourceManager()
                    .getRawFileEntry("resources/rawfile/" + fileName);
            // 获取数据目录
            File dataDir = new File(this.getDataDir().toString());
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            // 构建目标文件
            File targetFile = new File(Paths.get(dataDir.toString(), fileName).toString());

            // 获取源文件
            Resource resource = rawFileEntry.openRawFile();

            // 新建目标文件
            FileOutputStream fos = new FileOutputStream(targetFile);

            byte[] buffer = new byte[4096];
            int count = 0;

            // 源文件内容写入目标文件
            while ((count = ((Resource) resource).read(buffer)) >= 0) {
                fos.write(buffer, 0, count);
            }

            resource.close();
            fos.close();


            // 获取目标文件FileDescriptor
            FileInputStream fileIs = new FileInputStream(targetFile);
            FileDescriptor fd = fileIs.getFD();

            // 创建图像数据源ImageSource对象
            ImageSource imageSource = ImageSource.create(fd, getSourceOptions());

            // 普通解码叠加旋转、缩放、裁剪
            PixelMap pixelMap = imageSource.createPixelmap(getDecodingOptions());
            image.setPixelMap(pixelMap);

            HiLog.info(LABEL_LOG, "end setPixelMap, fileName: %{public}s", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        HiLog.info(LABEL_LOG, "before initData");

        imageList = new ArrayList<>(
                Arrays.asList(
                        "node.jpg",
                        "netty.jpg",
                        "spring5.jpg",
                        "cloud.jpg",
                        "boot.jpg"));

        int size = imageList.size();

        HiLog.info(LABEL_LOG, "end initData, imageList size: %{public}s", size);
    }

    // 设置解码格式
    private ImageSource.DecodingOptions getDecodingOptions() {
        ImageSource.DecodingOptions decodingOpts = new ImageSource.DecodingOptions();
        return decodingOpts;
    }

    // 设置数据源的格式信息
    private ImageSource.SourceOptions getSourceOptions() {
        ImageSource.SourceOptions sourceOptions = new ImageSource.SourceOptions();
        sourceOptions.formatHint = "image/jpeg";

        return sourceOptions;
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
