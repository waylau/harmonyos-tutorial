package com.waylau.hmos.pixelmap.slice;

import com.waylau.hmos.pixelmap.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;
import ohos.media.image.common.Position;
import ohos.media.image.common.Rect;
import ohos.media.image.common.Size;

import java.nio.IntBuffer;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private PixelMap pixelMap1;
    private PixelMap pixelMap2;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button buttonCreate =
                (Button) findComponentById(ResourceTable.Id_button_create);
        buttonCreate.setClickedListener(listener -> createPixelMap());

        Button buttonGet =
                (Button) findComponentById(ResourceTable.Id_button_get);
        buttonGet.setClickedListener(listener -> getPixelMapInfo());

        Button buttonReadWrite =
                (Button) findComponentById(ResourceTable.Id_button_read_write);
        buttonReadWrite.setClickedListener(listener -> readWritePixels());
    }

    private void createPixelMap() {
        HiLog.info(LABEL_LOG, "before createPixelMap");

        // 像素颜色数组
        int[] defaultColors = new int[]{5, 5, 5, 5, 6, 6, 3, 3, 3, 0};

        // 初始化选项
        PixelMap.InitializationOptions initializationOptions = new PixelMap.InitializationOptions();
        initializationOptions.size = new Size(3, 2);
        initializationOptions.pixelFormat = PixelFormat.ARGB_8888;

        // 根据像素颜色数组、初始化选项创建位图对象PixelMap
        pixelMap1 = PixelMap.create(defaultColors, initializationOptions);

        // 根据PixelMap作为数据源创建
        pixelMap2 = PixelMap.create(pixelMap1, initializationOptions);

        HiLog.info(LABEL_LOG, "end createPixelMap");
    }

    private void getPixelMapInfo() {
        //从位图对象中获取信息
        long capacity = pixelMap1.getPixelBytesCapacity();
        long bytesNumber = pixelMap1.getPixelBytesNumber();
        int rowBytes = pixelMap1.getBytesNumberPerRow();
        byte[] ninePatchData = pixelMap1.getNinePatchChunk();

        HiLog.info(LABEL_LOG, "capacity: %{public}s", capacity);
        HiLog.info(LABEL_LOG, "bytesNumber: %{public}s", bytesNumber);
        HiLog.info(LABEL_LOG, "rowBytes: %{public}s", rowBytes);
        HiLog.info(LABEL_LOG, "ninePatchData: %{public}s", ninePatchData);
    }

    private void readWritePixels() {
        // 读取指定位置像素
        int color = pixelMap1.readPixel(new Position(1, 1));
        HiLog.info(LABEL_LOG, "readPixel color: %{public}s", color);

        // 读取指定区域像素
        int[] pixelArray = new int[50];
        Rect region = new Rect(0, 0, 3, 2);
        pixelMap1.readPixels(pixelArray, 0, 10, region);
        HiLog.info(LABEL_LOG, "readPixel pixelArray: %{public}s", pixelArray);

        // 读取像素到Buffer
        IntBuffer pixelBuf = IntBuffer.allocate(50);
        pixelMap1.readPixels(pixelBuf);
        HiLog.info(LABEL_LOG, "readPixel pixelBuf: %{public}s", pixelBuf);

        // 在指定位置写入像素
        pixelMap1.writePixel(new Position(1, 1), 0xFF112233);

        // 在指定区域写入像素
        pixelMap1.writePixels(pixelArray, 0, 10, region);

        // 写入Buffer中的像素
        pixelMap1.writePixels(pixelBuf);
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
