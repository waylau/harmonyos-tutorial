/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.bean;

import com.waylau.hmos.shortvideo.api.IVideoPlayer;
import ohos.agp.graphics.Surface;

/**
 * 视频信息实体 视频路径、作者、内容、评论
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class VideoInfo {
    // 视频路径
    private String videoPath;

    // 作者
    private String author;

    // 头像
    private String portrait;

    // 内容
    private String content;

    // 点赞数量
    private int thumbsUpCount;

    // 评论数量
    private int commentCount;

    // 收藏数量
    private int favoriteCount;

    // 是否关注
    private boolean isFollow;

    // 是否点赞
    private boolean isThumbsUp;

    // 是否收藏
    private boolean isFavorite;

    // 播放器
    private IVideoPlayer VideoPlayer;

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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
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
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public boolean isThumbsUp() {
        return isThumbsUp;
    }

    public void setThumbsUp(boolean thumbsUp) {
        isThumbsUp = thumbsUp;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public IVideoPlayer getVideoPlayer() {
        return VideoPlayer;
    }

    public void setVideoPlayer(IVideoPlayer videoPlayer) {
        VideoPlayer = videoPlayer;
    }

}
