/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.api;

/**
 * ScreenChangeListener interface
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public interface ScreenChangeListener {
    /**
     * screenCallback
     *
     * @param width width
     * @param height height
     */
    void screenCallback(int width, int height);
}
