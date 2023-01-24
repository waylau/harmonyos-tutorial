/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.api;


import com.waylau.hmos.shortvideo.constant.PlayerStatusEnum;

/**
 * StatuChangeListener interface
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public interface StatuChangeListener {
    /**
     * statuCallback
     *
     * @param statu statu
     */
    void statuCallback(PlayerStatusEnum statu);
}
