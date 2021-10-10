package com.waylau.hmos.douyin.utils;

import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.io.IOException;

/**
 * AppUtil
 */
public class AppUtil {
    private static final HiLogLabel LABEL = new HiLogLabel(0, 0, "AppUtil");

    /**
     * Get string resource
     *
     * @param context context
     * @param id      id
     * @return String
     */
    public static String getStringResource(Context context, int id) {
        try {
            return context.getResourceManager().getElement(id).getString();
        } catch (IOException e) {
            HiLog.info(LABEL, "IOException");
        } catch (NotExistException e) {
            HiLog.info(LABEL, "NotExistException");
        } catch (WrongTypeException e) {
            HiLog.info(LABEL, "WrongTypeException");
        }
        return "";
    }
}
