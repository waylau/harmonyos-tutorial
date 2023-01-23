/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.util;

import ohos.agp.utils.Point;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;
import ohos.global.configuration.DeviceCapability;

import java.util.Optional;

/**
 * Screen util
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class ScreenUtil {
    private ScreenUtil() {
    }

    /**
     * getScreenHeight
     *
     * @param context context
     * @return Screen Height
     */
    public static int getScreenHeight(Context context) {
        DisplayManager displayManager = DisplayManager.getInstance();
        Optional<Display> optDisplay = displayManager.getDefaultDisplay(context);
        Point point = new Point(0, 0);
        if (!optDisplay.isPresent()) {
            return (int) point.position[1];
        } else {
            Display display = optDisplay.get();
            display.getSize(point);
            return (int) point.position[1];
        }
    }

    /**
     * getScreenWidth
     *
     * @param context context
     * @return Screen Width
     */
    public static int getScreenWidth(Context context) {
        DisplayManager displayManager = DisplayManager.getInstance();
        Optional<Display> optDisplay = displayManager.getDefaultDisplay(context);
        Point point = new Point(0, 0);
        if (!optDisplay.isPresent()) {
            return (int) point.position[0];
        } else {
            Display display = optDisplay.get();
            display.getSize(point);
            return (int) point.position[0];
        }
    }

    /**
     * dp2px
     *
     * @param context context
     * @param size size
     * @return int
     */
    public static int dp2px(Context context, int size) {
        int density = context.getResourceManager().getDeviceCapability().screenDensity / DeviceCapability.SCREEN_MDPI;
        return size * density;
    }

    /**
     * px2dip
     *
     * @param context context
     * @param size size
     * @return int
     */
    public static int px2dip(Context context, int size) {
        int density = context.getResourceManager().getDeviceCapability().screenDensity / DeviceCapability.SCREEN_MDPI;
        return size / density;
    }
}
