/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.util;

import com.waylau.hmos.shortvideo.constant.Constants;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;

import java.io.IOException;

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
}
