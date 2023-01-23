/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.api;

/**
 * 视频播放模块
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public interface IVideoPlayModule {
    /**
     * bind
     *
     * @param player player
     */
    void bind(IVideoPlayer player);

    /**
     * unbind
     *
     */
    void unbind();
}
