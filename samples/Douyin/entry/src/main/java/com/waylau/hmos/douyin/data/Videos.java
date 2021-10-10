package com.waylau.hmos.douyin.data;

import java.util.List;

/**
 * Video list,used to parses entry\src\main\resources\rawfile\videos.json
 */
public class Videos {
    private String videoName;

    private int totalEpisodes;

    private List<VideoInfo> detail;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    public List<VideoInfo> getDetail() {
        return detail;
    }

    public void setDetail(List<VideoInfo> detail) {
        this.detail = detail;
    }
}
