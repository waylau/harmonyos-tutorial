/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.bean;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

/**
 * 视频信息实体 视频路径、作者、内容、评论
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
@Entity(tableName = "video_info_t")
public class VideoInfo extends OrmObject {
    // 此处为了自增的主键
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    // 视频路径
    private String videoPath;

    // 作者
    private String author;

    // 头像
    private String portraitPath;

    // 封面
    private String coverPath;

    // 内容
    private String content;

    // 点赞数量
    private int thumbsUpCount;

    // 评论数量
    private int commentCount;

    // 收藏数量
    private int favoriteCount;

    // 是否关注
    private boolean follow;

    // 是否点赞
    private boolean thumbsUp;

    // 是否收藏
    private boolean favorite;

    // 播放器
    //private IVideoPlayer VideoPlayer;

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPortraitPath() {
        return portraitPath;
    }

    public void setPortraitPath(String portraitPath) {
        this.portraitPath = portraitPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getThumbsUpCount() {
        return thumbsUpCount;
    }

    public void setThumbsUpCount(int thumbsUpCount) {
        this.thumbsUpCount = thumbsUpCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public boolean isThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(boolean thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    /*
    public IVideoPlayer getVideoPlayer() {
        return VideoPlayer;
    }

    public void setVideoPlayer(IVideoPlayer videoPlayer) {
        VideoPlayer = videoPlayer;
    }
*/
    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
