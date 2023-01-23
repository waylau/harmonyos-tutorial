/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.util;

import com.waylau.hmos.shortvideo.constant.Constants;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.Resource;
import ohos.global.resource.WrongTypeException;

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

    private CommonUtil() {
    }

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
}
