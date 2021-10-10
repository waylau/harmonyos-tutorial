package com.waylau.hmos.douyin.data;

import com.waylau.hmos.douyin.model.ResolutionModel;

import java.util.List;

/**
 * Basic video information,used to parses entry\src\main\resources\rawfile\videos.json
 */
public class VideoInfo {
    private String videoName;

    private int currentAnthology;

    private List<ResolutionModel> resolutions;

    private String videoDesc;

    private boolean isTrailer;

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
}
