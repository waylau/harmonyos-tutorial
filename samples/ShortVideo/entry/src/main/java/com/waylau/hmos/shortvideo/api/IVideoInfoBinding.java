/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.api;

import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.bean.ViderPlayerInfo;

/**
 * 绑定视频信息
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public interface IVideoInfoBinding {
    /**
     * 绑定
     *
     * @param viderPlayerInfo viderPlayerInfo
     */
    void bind(ViderPlayerInfo viderPlayerInfo);

    /**
     * 解绑
     */
    void unbind();
}
