/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.pageslider.util;

import ohos.app.Context;
import ohos.global.resource.RawFileDescriptor;
import ohos.media.common.Source;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * SourceFactory
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class SourceUtil {
    private static final String TAG = SourceUtil.class.getSimpleName();
    private static final String NET_HTTP_MATCH = "http";
    private static final String NET_RTMP_MATCH = "rtmp";
    private static final String NET_RTSP_MATCH = "rtsp";
    private static final String STORAGE_MATCH = "/storage/";

    public static Source getSource(Context context, String path) {
        Source source = null;

        if (context == null || path == null) {
            return source;
        }

        try {
            if (path.substring(0, NET_HTTP_MATCH.length()).equalsIgnoreCase(NET_HTTP_MATCH)
                || path.substring(0, NET_RTMP_MATCH.length()).equalsIgnoreCase(NET_RTMP_MATCH)
                || path.substring(0, NET_RTSP_MATCH.length()).equalsIgnoreCase(NET_RTSP_MATCH)) {
                source = new Source(path);
            } else if (path.startsWith(STORAGE_MATCH)) {
                File file = new File(path);
                if (file.exists()) {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    FileDescriptor fileDescriptor = fileInputStream.getFD();
                    source = new Source(fileDescriptor);
                }
            } else {
                RawFileDescriptor fd = context.getResourceManager().getRawFileEntry(path).openRawFileDescriptor();
                source = new Source(fd.getFileDescriptor(), fd.getStartPosition(), fd.getFileSize());
            }
        } catch (IOException e) {
            LogUtil.error(TAG, "resource is unavailable: " + e.getMessage());
        }

        return source;
    }
}
