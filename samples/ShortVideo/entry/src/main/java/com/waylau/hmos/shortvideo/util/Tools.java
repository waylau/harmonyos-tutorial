package com.waylau.hmos.shortvideo.util;

import ohos.app.Context;
import ohos.global.resource.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Tools {

    /**
     * 单例，构造方法私有
     */
    private Tools() {
    }

    /**
     * 获取单例
     *
     * @return 单例
     */
    public static Tools getInstance() {
        return SingletonHolder.sInstance;
    }

    /**
     * 静态内部类，为了单例使用
     */
    private static class SingletonHolder {
        private static final Tools sInstance = new Tools();
    }


    public String getJsonFileToString(Context context, String resourcePath) {
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
