package com.waylau.hmos.douyin.constant;

/**
 * Universal constant for remote control.
 */
public class RemoteConstant {
    /**
     * remote control request token.
     */
    public static final String REMOTE_CONTROL_REQUEST_TOKEN = "remote.control.request.token";
    /**
     * request code of controlling remote device.
     */
    public static final int REQUEST_CONTROL_REMOTE_DEVICE = 200;
    /**
     * request code of syncing video status.
     */
    public static final int REQUEST_SYNC_VIDEO_STATUS = 201;

    /**
     * intent key of remote device id.
     */
    public static final String INTENT_PARAM_REMOTE_DEVICE_ID = "REMOTE_DEVICE_ID";

    /**
     * intent key of currently playing video.
     */
    public static final String INTENT_PARAM_REMOTE_VIDEO_PATH = "REMOTE_VIDEO_PATH";

    /**
     * intent key of currently playing video index.
     */
    public static final String INTENT_PARAM_REMOTE_VIDEO_INDEX = "REMOTE_VIDEO_INDEX";

    /**
     * intent key of current playback progress.
     */
    public static final String INTENT_PARAM_REMOTE_START_POSITION = "REMOTE_START_POSITION";

    /**
     * key of remote-control value.
     */
    public static final String REMOTE_KEY_CONTROL_VALUE = "controlValue";

    /**
     * key of remote video total time.
     */
    public static final String REMOTE_KEY_VIDEO_TOTAL_TIME = "totalTime";

    /**
     * key of remote video current progress.
     */
    public static final String REMOTE_KEY_VIDEO_CURRENT_PROGRESS = "currentProgress";

    /**
     * key of remote video current playback status.
     */
    public static final String REMOTE_KEY_VIDEO_CURRENT_PLAYBACK_STATUS = "currentPlaybackStatus";

    /**
     * key of remote video current volume.
     */
    public static final String REMOTE_KEY_VIDEO_CURRENT_VOLUME = "currentVolume";
}
