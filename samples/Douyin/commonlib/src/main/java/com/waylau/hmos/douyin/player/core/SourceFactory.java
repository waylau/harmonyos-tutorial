package com.waylau.hmos.douyin.player.core;

import ohos.app.Context;
import ohos.global.resource.RawFileDescriptor;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.common.Source;

import java.io.IOException;

/**
 * SourceFactory
 */
public class SourceFactory {
    private static final HiLogLabel LABEL = new HiLogLabel(0, 0, "SourceFactory");
    private static final String NET_HTTP_MATCH = "http";
    private static final String NET_RTMP_MATCH = "rtmp";
    private static final String NET_RTSP_MATCH = "rtsp";
    private static final String STORAGE_MATCH = "/";

    private Source mPlayerSource;

    /**
     * constructor of SourceFactory
     *
     * @param context context
     * @param path    path
     */
    public SourceFactory(Context context, String path) {
        try {
            initSourceType(context, path);
        } catch (IOException e) {
            HiLog.error(LABEL, "Audio resource is unavailable: ");
        }
    }

    private void initSourceType(Context context, String path) throws IOException {
        if (context == null || path == null) {
            return;
        }
        // Start with "http" or "https"
        // Start with "rtmp"
        // Start with "rtsp"
        // Start with "/", like "/sdcard/***"
        if (path.substring(0, NET_HTTP_MATCH.length()).equalsIgnoreCase(NET_HTTP_MATCH)
                || path.substring(0, NET_RTMP_MATCH.length()).equalsIgnoreCase(NET_RTMP_MATCH)
                || path.substring(0, NET_RTSP_MATCH.length()).equalsIgnoreCase(NET_RTSP_MATCH)
                || path.startsWith(STORAGE_MATCH)) {
            mPlayerSource = new Source(path);
        } else {
            // App resource file, like "entry/resources/base/media/*.mp4"
            RawFileDescriptor fd = context.getResourceManager().getRawFileEntry(path).openRawFileDescriptor();
            mPlayerSource = new Source(fd.getFileDescriptor(), fd.getStartPosition(), fd.getFileSize());
        }
    }

    /**
     * getSource of Source
     *
     * @return Source
     */
    public Source getSource() {
        return mPlayerSource;
    }
}
