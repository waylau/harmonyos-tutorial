/*
 * Copyright (c) 2021 Huawei Device Co., Ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.waylau.hmos.pageslider.util;

import com.waylau.hmos.pageslider.bean.VideoInfo;
import ohos.agp.graphics.Surface;
import ohos.app.Context;
import ohos.media.common.Source;
import ohos.media.common.sessioncore.AVElement;
import ohos.media.player.Player;

/**
 * VideoPlayerPlugin
 */
public class VideoPlayerPlugin {
    private static final String TAG = VideoPlayerPlugin.class.getSimpleName();

    private static final int REWIND_TIME = 2000;

    private final Context context;

    private Player videoPlayer;

    private Runnable videoRunnable;

    /**
     * VideoPlayerPlugin
     *
     * @param sliceContext Context
     */
    public VideoPlayerPlugin(Context sliceContext) {
        context = sliceContext;
    }

    /**
     * start
     */
    public synchronized void startPlay() {
        if (videoPlayer == null) {
            return;
        }
        videoPlayer.play();
        LogUtil.info(TAG, "start play");
    }

    /**
     * Set source,prepare,start
     *
     * @param avElement AVElement
     * @param surface   Surface
     */
    public synchronized void startPlay(VideoInfo avElement, Surface surface) {
        if (videoPlayer != null) {
            videoPlayer.stop();
            videoPlayer.release();
            videoPlayer = null;
        }

        if (videoRunnable != null) {
            ThreadPoolManager.getInstance().cancel(videoRunnable);
        }

        videoPlayer = new Player(context);
        videoPlayer.enableSingleLooping(true); // 循环
        videoPlayer.setPlayerCallback(new VideoCallBack());

        videoRunnable = () -> play(avElement, surface);
        ThreadPoolManager.getInstance().execute(videoRunnable);
    }

    /**
     * pause
     */
    public synchronized void pausePlay() {
        if (videoPlayer == null) {
            return;
        }
        videoPlayer.pause();
        LogUtil.info(TAG, "pause play");
    }

    private void play(VideoInfo avElement, Surface surface) {
        Source source = SourceUtil.getSource(context, avElement.getVideoPath());
        videoPlayer.setSource(source);
        videoPlayer.setVideoSurface(surface);
        LogUtil.info(TAG, source.getUri());

        videoPlayer.prepare();
        videoPlayer.play();
    }

    /**
     * seek
     */
    public void seek() {
        if (videoPlayer == null) {
            return;
        }
        videoPlayer.rewindTo(videoPlayer.getCurrentTime() + REWIND_TIME);
        LogUtil.info(TAG, "seek" + videoPlayer.getCurrentTime());
    }

    /**
     * release player
     */
    public void release() {
        if (videoPlayer != null) {
            videoPlayer.stop();
            videoPlayer.release();
            videoPlayer = null;
        }
    }

    private static class VideoCallBack implements Player.IPlayerCallback {
        @Override
        public void onPrepared() {
            LogUtil.info(TAG, "onPrepared");
        }

        @Override
        public void onMessage(int type, int extra) {
            LogUtil.info(TAG, "onMessage" + type);
        }

        @Override
        public void onError(int errorType, int errorCode) {
            LogUtil.error(TAG, "onError" + errorType);
        }

        @Override
        public void onResolutionChanged(int width, int height) {
            LogUtil.info(TAG, "onResolutionChanged" + width);
        }

        @Override
        public void onPlayBackComplete() {
            LogUtil.info(TAG, "onPlayBackComplete");
        }

        @Override
        public void onRewindToComplete() {
            LogUtil.info(TAG, "onRewindToComplete");
        }

        @Override
        public void onBufferingChange(int percent) {
            LogUtil.info(TAG, "onBufferingChange" + percent);
        }

        @Override
        public void onNewTimedMetaData(Player.MediaTimedMetaData mediaTimedMetaData) {
            LogUtil.info(TAG, "onNewTimedMetaData" + mediaTimedMetaData.toString());
        }

        @Override
        public void onMediaTimeIncontinuity(Player.MediaTimeInfo mediaTimeInfo) {
            LogUtil.info(TAG, "onNewTimedMetaData" + mediaTimeInfo.toString());
        }
    }
}
