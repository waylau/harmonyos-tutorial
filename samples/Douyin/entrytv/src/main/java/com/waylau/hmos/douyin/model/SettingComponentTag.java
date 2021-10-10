package com.waylau.hmos.douyin.model;

/**
 * Component Tags
 */
public class SettingComponentTag {
    private final String lever;

    private final int parentType;

    public SettingComponentTag(String lever, int parentType) {
        this.lever = lever;
        this.parentType = parentType;
    }

    public String getLever() {
        return lever;
    }

    public int getParentType() {
        return parentType;
    }

}
