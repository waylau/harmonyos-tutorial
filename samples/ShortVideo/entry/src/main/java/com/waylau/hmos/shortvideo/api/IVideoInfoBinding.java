/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.api;

import com.waylau.hmos.shortvideo.bean.VideoInfo;

/**
 * 绑定视频信息
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public interface IVideoInfoBinding {
    /**
     * bind
     *
     * @param videoInfo videoInfo
     */
    void bind(VideoInfo videoInfo);

    /**
     * unbind
     */
    void unbind();
}
