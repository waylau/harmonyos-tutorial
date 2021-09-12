package com.waylau.hmos.avsession;

import ohos.aafwk.content.Intent;
import ohos.global.resource.RawFileEntry;
import ohos.media.common.sessioncore.AVBrowserResult;
import ohos.media.common.sessioncore.AVBrowserRoot;
import ohos.media.common.sessioncore.AVPlaybackState;
import ohos.media.common.sessioncore.AVSessionCallback;
import ohos.media.player.Player;
import ohos.media.sessioncore.AVBrowserService;
import ohos.media.sessioncore.AVSession;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.PacMap;

import java.io.IOException;

public class AVService extends AVBrowserService {
    private static final String TAG = AVService.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    // 根媒体ID
    private static final String AV_ROOT_ID = "av_root_id";
    // 媒体会话
    private AVSession avSession;
    // 媒体播放器
    private Player player;

    @Override
    public void onStart(Intent intent) {
        HiLog.info(LABEL_LOG, "AVService::onStart");
        super.onStart(intent);

        try {
            initPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AVBrowserRoot onGetRoot(String clientPackageName, int clientUid, PacMap rootHints) {
        // 响应客户端avBrowser.connect()方法。若同意连接，则返回有效的AVBrowserRoot实例，否则返回null
        return new AVBrowserRoot(AV_ROOT_ID, null);
    }

    @Override
    public void onLoadAVElementList(String parentId, AVBrowserResult result) {
        HiLog.info(LABEL_LOG, "onLoadChildren");
        // 响应客户端avBrowser.subscribeByParentMediaId(...)方法。
        // 先执行该方法detachForRetrieveAsync()
        result.detachForRetrieveAsync();
        // externalAudioItems缓存媒体文件，请开发者自行实现。
        // result.sendAVElementList(externalAudioItems.getAudioItems());
    }

    @Override
    public void onLoadAVElementList(String s, AVBrowserResult avBrowserResult, PacMap pacMap) {
        // 响应客户端avBrowser.subscribeByParentMediaId(String, PacMap, AVSubscriptionCallback)方法。
    }

    @Override
    public void onLoadAVElement(String s, AVBrowserResult avBrowserResult) {
        // 响应客户端avBrowser.getAVElement(String, AVElementCallback)方法。
    }

    private void initPlayer() throws IOException {
        HiLog.info(LABEL_LOG, "before initPlayer");

        avSession = new AVSession(this, "AVService");
        setAVToken(avSession.getAVToken());
        // 设置sessioncallback，用于响应客户端的媒体控制器发起的播放控制指令。
        avSession.setAVSessionCallback(avSessionCallback);
        // 设置播放状态初始状态为AVPlaybackState.PLAYBACK_STATE_NONE。
        AVPlaybackState playbackState =
                new AVPlaybackState.Builder()
                        .setAVPlaybackState(AVPlaybackState.PLAYBACK_STATE_NONE, 0, 1.0f).build();
        avSession.setAVPlaybackState(playbackState);
        // 完成播放器的初始化，如果使用多个Player，也可以在执行播放时初始化。
        player = new Player(this);

        RawFileEntry rawFileEntry = this.getResourceManager().getRawFileEntry("resources/rawfile/test.wav");
        player.setSource(rawFileEntry.openRawFileDescriptor());

        HiLog.info(LABEL_LOG, "end initPlayer");
    }

    private AVSessionCallback avSessionCallback = new AVSessionCallback() {
        @Override
        public void onPlay() {
            HiLog.info(LABEL_LOG, "before onPlay");

            super.onPlay();
            // 当客户端调用avController.getPlayControls().play()时，该方法会被回调。
            // 响应播放请求，开始播放。
            if (avSession.getAVController().getAVPlaybackState().getAVPlaybackState() == AVPlaybackState.PLAYBACK_STATE_PAUSED) {
                if (player.play()) {
                    AVPlaybackState playbackState = new AVPlaybackState.Builder().setAVPlaybackState(
                            AVPlaybackState.PLAYBACK_STATE_PLAYING, player.getCurrentTime(),
                            player.getPlaybackSpeed()).build();
                    avSession.setAVPlaybackState(playbackState);
                }
            }

            HiLog.info(LABEL_LOG, "end onPlay");
        }

        @Override
        public void onPause() {
            HiLog.info(LABEL_LOG, "before onPause");

            // 当客户端调用avController.getPlayControls().pause()时，该方法会被回调。
            // 响应暂停请求，暂停播放。
            super.onPause();

            HiLog.info(LABEL_LOG, "end onPause");
        }

        @Override
        public void onStop() {
            HiLog.info(LABEL_LOG, "before onStop");

            // 当客户端调用avController.getPlayControls().stop()时，该方法会被回调。
            // 响应停止请求，停止播放。
            super.onStop();

            HiLog.info(LABEL_LOG, "end onStop");
        }
    };
}
