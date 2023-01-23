/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.constant;

/**
 * PlayerStatus enum
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public enum PlayerStatus {
    /**
     * idel
     *
     */
    IDEL,
    /**
     * preparing
     *
     */
    PREPARING,
    /**
     * prepared
     *
     */
    PREPARED,
    /**
     * play
     *
     */
    PLAY,
    /**
     * pause
     *
     */
    PAUSE,
    /**
     * stop
     *
     */
    STOP,
    /**
     * complete
     *
     */
    COMPLETE,
    /**
     * error
     *
     */
    ERROR,
    /**
     * buffering
     *
     */
    BUFFERING
}
