package com.waylau.hmos.douyin.data;

import com.waylau.hmos.douyin.model.ResolutionModel;

import java.util.List;

/**
 * Basic video information
 */
public class VideoInfo {
    private String videoName;

    private int currentAnthology;

    private List<ResolutionModel> resolutions;

    private String videoDesc;

    private boolean isTrailer;

    private String totalTime;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getCurrentAnthology() {
        return currentAnthology;
    }

    public void setCurrentAnthology(int currentAnthology) {
        this.currentAnthology = currentAnthology;
    }

    public List<ResolutionModel> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<ResolutionModel> resolutions) {
        this.resolutions = resolutions;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public boolean isTrailer() {
        return isTrailer;
    }

    public void setTrailer(boolean trailer) {
        isTrailer = trailer;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
