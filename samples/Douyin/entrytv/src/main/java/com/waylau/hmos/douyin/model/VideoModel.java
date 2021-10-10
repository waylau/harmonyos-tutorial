package com.waylau.hmos.douyin.model;

/**
 * Video Model Class
 */
public class VideoModel {
    private String videoName;

    private String videoDesc;

    private String videoTitleTime;

    private int videoImage;

    private String lever;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getVideoTitleTime() {
        return videoTitleTime;
    }

    public void setVideoTitleTime(String videoTitleTime) {
        this.videoTitleTime = videoTitleTime;
    }

    public int getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(int videoImage) {
        this.videoImage = videoImage;
    }

    public String getLever() {
        return lever;
    }

    public void setLever(String lever) {
        this.lever = lever;
    }
}
