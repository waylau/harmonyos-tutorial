/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.bean;

import com.waylau.hmos.shortvideo.api.IVideoPlayer;

/**
 * ViderPlayerInfo
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class ViderPlayerInfo {
    private VideoInfo videoInfo;
    private IVideoPlayer videoPlayer;

    private UserInfo userInfo;

    public ViderPlayerInfo(VideoInfo videoInfo, IVideoPlayer videoPlayer,UserInfo userInfo) {
        this.videoInfo = videoInfo;
        this.videoPlayer = videoPlayer;
        this.userInfo = userInfo;
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
    }

    public IVideoPlayer getVideoPlayer() {
        return videoPlayer;
    }

    public void setVideoPlayer(IVideoPlayer videoPlayer) {
        this.videoPlayer = videoPlayer;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
