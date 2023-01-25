/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.api;

/**
 * 生命周期
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public interface IVideoPlayerLifecycle {

    void onForeground();

    void onBackground();

    void onStop();
}
