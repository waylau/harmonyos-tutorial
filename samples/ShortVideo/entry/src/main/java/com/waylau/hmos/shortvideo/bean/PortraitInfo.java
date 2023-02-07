/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.bean;

/**
 * PortraitInfo
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-07
 */
public class PortraitInfo {
    private int id;
    // 头像
    private String portraitPath;

    public String getPortraitPath() {
        return portraitPath;
    }

    public void setPortraitPath(String portraitPath) {
        this.portraitPath = portraitPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
