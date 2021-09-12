package com.waylau.hmos.imagecodec.slice;

import com.waylau.hmos.imagecodec.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.global.resource.NotExistException;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImagePacker;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.Rect;
import ohos.media.image.common.Size;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

        Button buttonDecode =
                (Button) findComponentById(ResourceTable.Id_button_decode);

        // 为按钮设置点击事件回调
        buttonDecode.setClickedListener(listener -> {
            try {
                decode();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotExistException e) {
                e.printStackTrace();
            }
        });

        Button buttonEncode =
                (Button) findComponentById(ResourceTable.Id_button_encode);

        // 为按钮设置点击事件回调
        buttonEncode.setClickedListener(listener -> {
            try {
                encode();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void decode() throws IOException, NotExistException {
        // 获取图片流
        InputStream drawableInputStream =  getResourceManager().getResource(ResourceTable.Media_waylau_616_616);

        // 创建图像数据源ImageSource对象
        imageSource = ImageSource.create(drawableInputStream, this.getSourceOptions());

        // 普通解码叠加旋转、缩放、裁剪
        pixelMap = imageSource.createPixelmap(this.getDecodingOptions());

        Image imageDecode =
                (Image) findComponentById(ResourceTable.Id_image_decode);

        imageDecode.setPixelMap(pixelMap);
    }

    private void encode() throws IOException {
        HiLog.info(LABEL_LOG, "before encode()");

        // 创建图像编码ImagePacker对象
        ImagePacker imagePacker = ImagePacker.create();

        // 获取数据目录
        File dataDir = new File(this.getDataDir().toString());
        if(!dataDir.exists()){
            dataDir.mkdirs();
        }

        // 文件路径
        String filePath = Paths.get(dataDir.toString(),"test.jpeg").toString();

        // 构建目标文件
        File targetFile = new File(filePath);

        // 设置编码输出流和编码参数
        FileOutputStream outputStream = new FileOutputStream(targetFile);

        // 初始化打包
        imagePacker.initializePacking(outputStream, this.getPackingOptions());

        // 添加需要编码的PixelMap对象，进行编码操作。
        imagePacker.addImage(pixelMap);

        // 完成图像打包任务
        imagePacker.finalizePacking();

        Image imageEncode =
                (Image) findComponentById(ResourceTable.Id_image_encode);

        // 文件转成图像
        imageSource = ImageSource.create(targetFile,this.getSourceOptions());

        pixelMap =  imageSource.createPixelmap(this.getDecodingOptions());

        imageEncode.setPixelMap(pixelMap);

        HiLog.info(LABEL_LOG, "end encode()");
    }


    // 设置打包格式
    private ImagePacker.PackingOptions getPackingOptions() {
        ImagePacker.PackingOptions packingOptions = new ImagePacker.PackingOptions();
        packingOptions.format = "image/jpeg"; // 设置format为编码的图像格式，当前支持jpeg格式
        packingOptions.quality = 90; // 设置quality为图像质量，范围从0-100，100为最佳质量
        return packingOptions;
    }

    // 设置解码格式
    private ImageSource.DecodingOptions getDecodingOptions() {
        ImageSource.DecodingOptions decodingOpts = new ImageSource.DecodingOptions();
        decodingOpts.desiredSize = new Size(600, 600);
        decodingOpts.desiredRegion = new Rect(0, 0, 600, 600);
        decodingOpts.rotateDegrees = 90;

        return decodingOpts;
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
        if (pixelMap != null) {
            // 释放资源
            pixelMap.release();
        }

    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
