package com.waylau.hmos.liuweiweiimagehandler.slice;

import com.waylau.hmos.liuweiweiimagehandler.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.PixelMapHolder;
import ohos.global.resource.NotExistException;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;
import ohos.media.image.common.Rect;
import ohos.media.image.common.Size;

import java.io.IOException;
import java.io.InputStream;

/**
 * 图像主页面
 */
public class MainAbilitySlice extends AbilitySlice {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "MainAbilitySlice");
    Image image;
    PixelMap imagePixelMap;
    Button whirlImageBtn;
    Button cropImageBtn;
    Button scaleImageBtn;
    Button mirrorImageBtn;
    private int whirlCount = 0;
    private boolean isCorp = false;
    private boolean isScale = false;
    private boolean isMirror = false;
    private float scaleX = 1.0f;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        initView();
    }

    private void initView() {
        if (findComponentById(ResourceTable.Id_whirl_image) instanceof Button) {
            whirlImageBtn = (Button) findComponentById(ResourceTable.Id_whirl_image);
        }
        if (findComponentById(ResourceTable.Id_crop_image) instanceof Button) {
            cropImageBtn = (Button) findComponentById(ResourceTable.Id_crop_image);
        }
        if (findComponentById(ResourceTable.Id_scale_image) instanceof Button) {
            scaleImageBtn = (Button) findComponentById(ResourceTable.Id_scale_image);
        }
        if (findComponentById(ResourceTable.Id_mirror_image) instanceof Button) {
            mirrorImageBtn = (Button) findComponentById(ResourceTable.Id_mirror_image);
        }
        if (findComponentById(ResourceTable.Id_image) instanceof Image) {
            image = (Image) findComponentById(ResourceTable.Id_image);
        }
        whirlImageBtn.setClickedListener(new ButtonClick());
        cropImageBtn.setClickedListener(new ButtonClick());
        scaleImageBtn.setClickedListener(new ButtonClick());
        mirrorImageBtn.setClickedListener(new ButtonClick());
    }

    private class ButtonClick implements Component.ClickedListener {
        @Override
        public void onClick(Component component) {
            int btnId = component.getId();
            switch (btnId) {
                case ResourceTable.Id_whirl_image:
                    // 旋转图片
                    whirlCount++;
                    isCorp = false;
                    isScale = false;
                    isMirror = false;
                    imagePixelMap = getPixelMapFromResource(ResourceTable.Media_space);
                    image.setPixelMap(imagePixelMap);
                    break;
                case ResourceTable.Id_crop_image:
                    // 剪裁图片
                    whirlCount = 0;
                    isCorp = !isCorp;
                    isScale = false;
                    isMirror = false;
                    imagePixelMap = getPixelMapFromResource(ResourceTable.Media_space);
                    image.setPixelMap(imagePixelMap);
                    break;
                case ResourceTable.Id_scale_image:
                    // 缩放图片
                    whirlCount = 0;
                    isCorp = false;
                    isScale = !isScale;
                    isMirror = false;
                    imagePixelMap = getPixelMapFromResource(ResourceTable.Media_space);
                    image.setPixelMap(imagePixelMap);
                    break;
                case ResourceTable.Id_mirror_image:
                    // 镜像图片
                    whirlCount = 0;
                    isCorp = false;
                    isScale = false;
                    isMirror = true;
                    imagePixelMap = getPixelMapFromResource(ResourceTable.Media_space);
                    mirrorImage(imagePixelMap);
                    image.setPixelMap(imagePixelMap);
                    break;
                default:
                    break;
            }
        }
    }

    private void mirrorImage(PixelMap pixelMap) {
        scaleX = -scaleX;
        image.addDrawTask(
                new Component.DrawTask() {
                    @Override
                    public void onDraw(Component component, Canvas canvas) {
                        if (isMirror) {
                            isMirror = false;
                            PixelMapHolder pmh = new PixelMapHolder(pixelMap);
                            canvas.scale(
                                    scaleX,
                                    1.0f,
                                    (float) pixelMap.getImageInfo().size.width / 2,
                                    (float) pixelMap.getImageInfo().size.height / 2);
                            canvas.drawPixelMapHolder(
                                    pmh,
                                    0,
                                    0,
                                    new Paint());
                        }
                    }
                });
    }

    /**
     * 通过图片ID返回PixelMap
     *
     * @param resourceId 图片的资源ID
     * @return 图片的PixelMap
     */
    private PixelMap getPixelMapFromResource(int resourceId) {
        InputStream inputStream = null;
        try {
            // 创建图像数据源ImageSource对象
            inputStream = getContext().getResourceManager().getResource(resourceId);
            ImageSource.SourceOptions srcOpts = new ImageSource.SourceOptions();
            srcOpts.formatHint = "image/jpg";
            ImageSource imageSource = ImageSource.create(inputStream, srcOpts);

            // 设置图片参数
            ImageSource.DecodingOptions decodingOptions = new ImageSource.DecodingOptions();
            // 旋转
            decodingOptions.rotateDegrees = 90 * whirlCount;
            // 缩放
            decodingOptions.desiredSize = new Size(isScale ? 512 : 0, isScale ? 384 : 0);
            // 剪裁
            decodingOptions.desiredRegion = new Rect(0, 0, isCorp ? 1024 : 0, isCorp ? 400 : 0);
            decodingOptions.desiredPixelFormat = PixelFormat.ARGB_8888;
            return imageSource.createPixelmap(decodingOptions);
        } catch (IOException e) {
            HiLog.info(LABEL_LOG, "IOException");
        } catch (NotExistException e) {
            HiLog.info(LABEL_LOG, "NotExistException");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    HiLog.info(LABEL_LOG, "inputStream IOException");
                }
            }
        }
        return null;
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