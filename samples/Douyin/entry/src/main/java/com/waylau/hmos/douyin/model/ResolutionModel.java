package com.waylau.hmos.douyin.model;

import com.waylau.hmos.douyin.constant.Constants;
import com.waylau.hmos.douyin.utils.PathUtils;

/**
 * Used to parses entry\src\main\resources\rawfile\videos.json
 */
public class ResolutionModel {
    private int name;

    private int shortName;

    private String url;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getShortName() {
        return shortName;
    }

    public void setShortName(int shortName) {
        this.shortName = shortName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ResolutionModel{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", url='" + getUrl() + '\'' +
                '}';
    }
}
