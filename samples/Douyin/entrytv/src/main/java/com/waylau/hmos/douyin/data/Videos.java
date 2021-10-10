package com.waylau.hmos.douyin.data;

import java.util.List;

/**
 * Video list
 */
public class Videos {
    private String videoName;

    private int totalAnthology;

    private List<VideoInfo> detail;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getTotalAnthology() {
        return totalAnthology;
    }

    public void setTotalAnthology(int totalAnthology) {
        this.totalAnthology = totalAnthology;
    }

    public List<VideoInfo> getDetail() {
        return detail;
    }

    public void setDetail(List<VideoInfo> detail) {
        this.detail = detail;
    }
}
