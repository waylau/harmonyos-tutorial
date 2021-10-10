package com.waylau.hmos.douyin.utils;

import com.waylau.hmos.douyin.player.ui.widget.media.MeasureHelper;

import ohos.agp.utils.Point;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;

import java.util.Optional;

/**
 * Utils to measure screen size.
 */
public class ScreenUtils {
    /**
     * get Screen size from context.
     *
     * @param context component context.
     * @return Point instance
     */
    public static Point getScreenSize(Context context) {
        Point point = new Point(0, 0);
        DisplayManager displayManager = DisplayManager.getInstance();
        Optional<Display> optDisplay = displayManager.getDefaultDisplay(context);
        optDisplay.ifPresent(display -> display.getSize(point));
        HiLog.debug(MeasureHelper.LABEL, "Screen size = " + point.toString());
        return point;
    }

    /**
     * Screen orientation is portrait?
     *
     * @param context Component context.
     * @return whether component is in portrait mode.
     */
    public static boolean isPortrait(Context context) {
        Point screenSize = getScreenSize(context);
        return screenSize.getPointYToInt() >= screenSize.getPointXToInt();
    }
}
