/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.bean;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

/**
 * MeFavoriteVideoInfo
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-07
 */
@Entity(tableName = "me_favorite_video_info_t")
public class MeFavoriteVideoInfo extends OrmObject {
    // 此处为了自增的主键
    @PrimaryKey(autoGenerate = true)
    private Integer meFavoriteId;

    private String username;

    private Integer videoId;

    // 封面
    private String coverPath;

    // 内容
    private String content;

    public Integer getMeFavoriteId() {
        return meFavoriteId;
    }

    public void setMeFavoriteId(Integer meFavoriteId) {
        this.meFavoriteId = meFavoriteId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 从VideoInfo复制属性
     * @param videoInfo
     */
    public void copy(VideoInfo videoInfo) {
        this.videoId = videoInfo.getVideoId();
        this.content = videoInfo.getContent();
        this.coverPath = videoInfo.getCoverPath();
    }


}
