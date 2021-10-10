package com.waylau.hmos.douyin.constant;

/**
 * ControlCode enum
 */
public enum ControlCode {
    /**
     * start to play the video
     */
    PLAY(1001),

    /**
     * forward the video-playing per second.
     */
    FORWARD(1002),

    /**
     * reduce the volume
     */
    BACKWARD(1003),

    /**
     * add the volume
     */
    VOLUME_ADD(1004),

    /**
     * reduce the volume
     */
    VOLUME_REDUCED(1005),

    /**
     * direct to the video
     */
    SEEK(1006),

    /**
     * switch the playback speed
     */
    SWITCH_SPEED(1007),

    /**
     * toggle resolution
     */
    SWITCH_RESOLUTION(1008),

    /**
     * select the video to be played
     */
    SWITCH_VIDEO(1009),

    /**
     * stop remote connection
     */
    STOP_CONNECTION(1010),

    /**
     * synchronize the video progress.
     */
    SYNC_VIDEO_PROCESS(1011),

    /**
     * synchronizing video playback status
     */
    SYNC_VIDEO_STATUS(1012),

    /**
     * synchronize video volume
     */
    SYNC_VIDEO_VOLUME(1013);

    private final int code;

    ControlCode(int value) {
        this.code = value;
    }

    public int getCode() {
        return code;
    }
}
