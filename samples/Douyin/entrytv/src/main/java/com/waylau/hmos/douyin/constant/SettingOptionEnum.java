package com.waylau.hmos.douyin.constant;

/**
 * Setting Option Enumeration Class
 */
public enum SettingOptionEnum {
    EPISODES("选集", 1),
    SPEED_SELECTION("倍速播放", 2),
    RESOLUTION("清晰度", 3);

    private final int code;
    private String name;

    SettingOptionEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }
}
