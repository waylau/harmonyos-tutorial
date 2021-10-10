package com.waylau.hmos.douyin.data;

import com.waylau.hmos.douyin.constant.ResolutionEnum;
import com.waylau.hmos.douyin.model.ResolutionModel;

import ohos.app.Context;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.zson.ZSONArray;
import ohos.utils.zson.ZSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for obtaining video information
 */
public class VideoInfoService {
    private static final HiLogLabel LABEL = new HiLogLabel(0, 0, "VideoInfoService");
    private static final int NEWS_CONTENT_SIZE = 1024;
    private static final int EOF = -1;
    private final Context context;
    private final Videos videos;

    /**
     * Service Initialization
     */
    public VideoInfoService(Context context) {
        this.context = context;

        String resourcePath = "resources/rawfile/videos.json";
        String videosFile = getVideosInfo(resourcePath);

        ZSONObject videosJson = ZSONObject.stringToZSON(videosFile);
        videos = new Videos();
        videos.setVideoName(videosJson.getString("name"));
        videos.setTotalAnthology(videosJson.getIntValue("total_anthology"));
        ZSONArray anthologyDetail = videosJson.getZSONArray("anthology_detail");
        ZSONObject anthology;
        List<VideoInfo> detail = new ArrayList<>();
        for (int i = 0; i < anthologyDetail.size(); i++) {
            anthology = anthologyDetail.getZSONObject(i);
            VideoInfo video = new VideoInfo();
            video.setVideoName(videos.getVideoName());
            video.setCurrentAnthology(anthology.getIntValue("current_anthology"));
            video.setResolutions(getResolution(anthology));
            video.setTotalTime(anthology.getString("totalTime"));
            video.setVideoDesc(anthology.getString("plot"));
            video.setTrailer(anthology.getBooleanValue("isTrailer"));
            detail.add(video);
        }
        videos.setDetail(detail);
    }

    /**
     * Obtain all video information.
     *
     * @return videos
     */
    public Videos getAllVideoInfo() {
        return videos;
    }

    /**
     * Obtaining Video Playback Information.
     *
     * @param index video index
     * @return videoJson
     */
    public VideoInfo getVideoInfoByIndex(int index) {
        return videos.getDetail().get(index);
    }

    /**
     * Get videos info from json
     *
     * @param resourcePath video file location
     * @return videoJson
     */
    private String getVideosInfo(String resourcePath) {
        try {
            Resource resource = context.getResourceManager().getRawFileEntry(resourcePath).openRawFile();
            byte[] tmp = new byte[NEWS_CONTENT_SIZE * NEWS_CONTENT_SIZE];
            int reads = resource.read(tmp);
            if (reads != EOF) {
                return new String(tmp, 0, reads, StandardCharsets.UTF_8);
            }
        } catch (IOException ex) {
            HiLog.error(LABEL, "getVideosInfo IO error");
        }
        return "";
    }

    private List<ResolutionModel> getResolution(ZSONObject anthology) {
        List<ResolutionModel> resolutions = new ArrayList<>();

        String url = anthology.getString("ultraHdUrl");
        if (url != null && !url.isEmpty()) {
            ResolutionModel resolution = new ResolutionModel();
            resolution.setName(ResolutionEnum.RESOLUTION_HDR.getName());
            resolution.setUrl(url);
            resolutions.add(resolution);
        }

        url = anthology.getString("fhdUrl");
        if (url != null && !url.isEmpty()) {
            ResolutionModel resolution = new ResolutionModel();
            resolution.setName(ResolutionEnum.RESOLUTION_4K.getName());
            resolution.setUrl(url);
            resolutions.add(resolution);
        }

        url = anthology.getString("hdUrl");
        if (url != null && !url.isEmpty()) {
            ResolutionModel resolution = new ResolutionModel();
            resolution.setName(ResolutionEnum.RESOLUTION_2K.getName());
            resolution.setUrl(url);
            resolutions.add(resolution);
        }

        url = anthology.getString("sdUrl");
        if (url != null && !url.isEmpty()) {
            ResolutionModel resolution = new ResolutionModel();
            resolution.setName(ResolutionEnum.RESOLUTION_BD.getName());
            resolution.setUrl(url);
            resolutions.add(resolution);
        }

        url = anthology.getString("autoUrl");
        if (url != null && !url.isEmpty()) {
            ResolutionModel resolution = new ResolutionModel();
            resolution.setName(ResolutionEnum.RESOLUTION_AUTO.getName());
            resolution.setUrl(url);
            resolutions.add(resolution);
        }
        return resolutions;
    }
}
