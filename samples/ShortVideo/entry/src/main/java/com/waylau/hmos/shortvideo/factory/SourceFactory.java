/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.factory;

import com.waylau.hmos.shortvideo.util.LogUtil;
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
public class SourceFactory {
    private static final String TAG = SourceFactory.class.getSimpleName();
    private static final String NET_HTTP_MATCH = "http";
    private static final String NET_RTMP_MATCH = "rtmp";
    private static final String NET_RTSP_MATCH = "rtsp";
    private static final String STORAGE_MATCH = "/storage/";

    private Source source;

    /**
     * constructor of SourceFactory
     *
     * @param context context
     * @param path path
     */
    public SourceFactory(Context context, String path) {
        try {
            initSourceType(context, path);
        } catch (IOException e) {
            LogUtil.error(TAG, "Audio resource is unavailable: ");
        }
    }

    private void initSourceType(Context context, String path) throws IOException {
        if (context == null || path == null) {
            return;
        }
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
    }

    /**
     * getSource
     *
     * @return Source Source
     */
    public Source getSource() {
        return source;
    }
}
