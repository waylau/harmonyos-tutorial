package com.waylau.hmos.douyin.utils;

import ohos.agp.colors.RgbColor;
import ohos.agp.components.element.PixelMapElement;
import ohos.agp.components.element.ShapeElement;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.io.IOException;

/**
 * Element utils
 */
public class ElementUtils {
    private static final HiLogLabel LABEL = new HiLogLabel(0, 0, "ElementUtils");

    /**
     * Obtains images based on resource id.
     *
     * @param context Context.
     * @param resId   Resource id.
     * @return based on a given image resource and immediately decode the resource.
     */
    public static PixelMapElement getElementByResId(Context context, int resId) {
        try {
            Resource resource = context.getResourceManager().getResource(resId);
            return new PixelMapElement(resource);
        } catch (IOException | NotExistException e) {
            HiLog.error(LABEL, "getElementByResId failed");
        }
        return null;
    }

    /**
     * Obtains the gradient color ShapeElement object.
     *
     * @param startColor  Color at beginning of gradient.
     * @param endColor    Color at end of gradient.
     * @param orientation orientation.
     * @return Returns gradient color ShapeElement.
     */
    public static ShapeElement getGradientColor(int startColor, int endColor, ShapeElement.Orientation orientation) {
        ShapeElement element = new ShapeElement();
        RgbColor startRgbColor = new RgbColor(startColor);
        RgbColor endRgbColor = new RgbColor(endColor);
        RgbColor[] rgb = new RgbColor[]{startRgbColor, endRgbColor};
        element.setRgbColors(rgb);
        element.setGradientOrientation(orientation);
        return element;
    }
}
