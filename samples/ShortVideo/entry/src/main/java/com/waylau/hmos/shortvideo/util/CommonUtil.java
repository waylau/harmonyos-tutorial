/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.util;

import com.waylau.hmos.shortvideo.constant.Constants;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.Resource;
import ohos.global.resource.WrongTypeException;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Common util
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class CommonUtil {
    private static final String TAG = CommonUtil.class.getSimpleName();

    private CommonUtil() {}

    /**
     * 通过资源ID获取颜色
     *
     * @param context comtext
     * @param resourceId id
     * @return int
     */
    public static int getColor(Context context, int resourceId) {
        try {
            return context.getResourceManager().getElement(resourceId).getColor();
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(TAG, e.getMessage());
        }
        return Constants.NUMBER_NEGATIVE_1;
    }

    /**
     * JSON文件转String
     * 
     * @param context 上下文
     * @param resourcePath 资源路径
     * @return String
     */
    public static String getJsonFileToString(Context context, String resourcePath) {
        try {
            Resource resource = context.getResourceManager().getRawFileEntry(resourcePath).openRawFile();
            byte[] tmp = new byte[1024 * 1024];
            int reads = resource.read(tmp);
            if (reads != -1) {
                return new String(tmp, 0, reads, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static PixelMap getImageSource(Context context, String resourcePath) {
        byte[] tmp = null;
        try {
            Resource resource = context.getResourceManager().getRawFileEntry(resourcePath).openRawFile();
            tmp = new byte[1024 * 1024];
            resource.read(tmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 用于 ImageSource的 create(bytes,srcOpts)方法
        ImageSource.SourceOptions srcOpts = new ImageSource.SourceOptions();

        // 设置图片原格式也可以用 null
        srcOpts.formatHint = "image/jpg";

        ImageSource imageSource = ImageSource.create(tmp, srcOpts);

        // 通过ImageSource创建 PixelMap文件
        PixelMap pixelMap = imageSource.createPixelmap(null);

        return pixelMap;
    }
}
