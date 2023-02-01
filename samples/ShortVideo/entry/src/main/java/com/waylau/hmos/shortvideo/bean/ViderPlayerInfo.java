package com.waylau.hmos.shortvideo.bean;

import com.waylau.hmos.shortvideo.api.IVideoPlayer;

public class ViderPlayerInfo {
    private VideoInfo videoInfo;
    private IVideoPlayer videoPlayer;

    public ViderPlayerInfo(VideoInfo videoInfo, IVideoPlayer videoPlayer) {
        this.videoInfo = videoInfo;
        this.videoPlayer = videoPlayer;
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
}
