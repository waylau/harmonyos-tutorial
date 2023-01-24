/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.manager;

import com.waylau.hmos.shortvideo.api.IVideoPlayerLifecycle;
import com.waylau.hmos.shortvideo.api.IVideoPlayer;
import com.waylau.hmos.shortvideo.constant.PlayerStatusEnum;

/**
 * VideoPlayer Lifecycle
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class VideoPlayerLifecycle implements IVideoPlayerLifecycle {
    private static final String TAG = VideoPlayerLifecycle.class.getSimpleName();
    private IVideoPlayer videoPlayer;

    /**
     * HmPlayerLifecycle
     *
     * @param player player
     */
    public VideoPlayerLifecycle(IVideoPlayer player) {
        videoPlayer = player;
    }

    @Override
    public void onStart() {}

    @Override
    public void onForeground() {
        String url = videoPlayer.getBuilder().getFilePath();
        int startMillisecond = videoPlayer.getBuilder().getStartMillisecond();
        videoPlayer.reload(url, startMillisecond);
    }

    @Override
    public void onBackground() {
        videoPlayer.getBuilder().setPause(videoPlayer.getPlayerStatus() == PlayerStatusEnum.PAUSE);
        videoPlayer.getBuilder().setStartMillisecond(videoPlayer.getCurrentPosition());
        videoPlayer.release();
    }

    @Override
    public void onStop() {
        videoPlayer.release();
    }
}
