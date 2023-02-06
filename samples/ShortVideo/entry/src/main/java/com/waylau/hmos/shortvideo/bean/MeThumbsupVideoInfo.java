package com.waylau.hmos.shortvideo.bean;

import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "me_thumbsup_video_info_t")
public class MeThumbsupVideoInfo  {
    // 此处为了自增的主键
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String username;

    private VideoInfo videoInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
    }
}
