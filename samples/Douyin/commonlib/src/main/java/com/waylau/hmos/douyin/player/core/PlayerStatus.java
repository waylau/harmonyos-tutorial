package com.waylau.hmos.douyin.player.core;

/**
 * Enum of playback status constants.
 */
public enum PlayerStatus {
    /**
     * the wrong status of video
     */
    ERROR("error"),
    /**
     * the video is released
     */
    IDLE("idle"),

    /**
     * video is preparing
     */
    PREPARING("preparing"),

    /**
     * when the video become prepared will be ready to play
     */
    PREPARED("prepared"),

    /**
     * Start buffer more data
     */
    BUFFERING("buffering"),

    /**
     * Player resumes after the buffer is filled
     */
    BUFFERED("buffered"),

    /**
     * start the video or resume to play
     */
    PLAY("play"),

    /**
     * pause the playing
     */
    PAUSE("pause"),

    /**
     * stop the playing
     */
    STOP("stop"),

    /**
     * the video play completed
     */
    COMPLETE("complete");

    private final String name;

    PlayerStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
