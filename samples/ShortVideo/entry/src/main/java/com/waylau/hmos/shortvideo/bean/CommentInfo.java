/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.bean;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

/**
 * CommentInfo
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-07
 */
@Entity(tableName = "comment_info_t")
public class CommentInfo extends OrmObject {
    // 此处为了自增的主键
    @PrimaryKey(autoGenerate = true)
    private Integer commentId;

    private String username;

    // 头像
    private String portraitPath;

    private Integer videoId;

    // 内容
    private String content;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPortraitPath() {
        return portraitPath;
    }

    public void setPortraitPath(String portraitPath) {
        this.portraitPath = portraitPath;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public void copy(UserInfo userInfo, VideoInfo videoInfo) {
        this.username = userInfo.getUsername();
        this.portraitPath = userInfo.getPortraitPath();
        this.videoId = videoInfo.getVideoId();
    }

}
