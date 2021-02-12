package com.waylau.hmos.avsession.slice;

import com.waylau.hmos.avsession.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.bundle.ElementName;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.common.AVDescription;
import ohos.media.common.AVMetadata;
import ohos.media.common.sessioncore.*;
import ohos.media.image.PixelMap;
import ohos.media.sessioncore.AVBrowser;
import ohos.media.sessioncore.AVController;

import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    // 媒体浏览器
    private AVBrowser avBrowser;
    // 媒体控制器
    private AVController avController;

    private List<AVElement> list;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        initBrowser();

        // 为按钮设置点击事件回调
        Button buttonPlay =
                (Button) findComponentById(ResourceTable.Id_button_play);
        buttonPlay.setClickedListener(listener -> play());

        Button buttonPause =
                (Button) findComponentById(ResourceTable.Id_button_pause);
        buttonPause.setClickedListener(listener -> pause());

        Button buttonStop =
                (Button) findComponentById(ResourceTable.Id_button_stop);
        buttonStop.setClickedListener(listener -> stop());
    }

    private void stop() {
        avController.getPlayControls().stop();
    }

    private void pause() {
        toPlayOrPause();
    }

    private void play() {
        toPlayOrPause();
    }

    public void toPlayOrPause() {
        switch (avController.getAVPlaybackState().getAVPlaybackState()) {
            case AVPlaybackState.PLAYBACK_STATE_NONE: {
                avController.getPlayControls().prepareToPlay();
                avController.getPlayControls().play();
                HiLog.info(LABEL_LOG, "end play");
                break;
            }
            case AVPlaybackState.PLAYBACK_STATE_PLAYING: {
                avController.getPlayControls().pause();
                HiLog.info(LABEL_LOG, "end pause");
                break;
            }
            case AVPlaybackState.PLAYBACK_STATE_PAUSED: {
                avController.getPlayControls().play();
                HiLog.info(LABEL_LOG, "end play");
                break;
            }
            default: {
                break;
            }
        }
    }

    private void initBrowser() {
        HiLog.info(LABEL_LOG, "before initBrowser");

        // 用于指向媒体浏览器服务的包路径和类名
        ElementName elementName =
                new ElementName("", "com.waylau.hmos.avsession", "com.waylau.hmos.avsession.AVService");
        // connectionCallback在调用avBrowser.connect方法后进行回调。
        avBrowser = new AVBrowser(this, elementName, connectionCallback, null);
        // avBrowser发送对媒体浏览器服务的连接请求。
        avBrowser.connect();
        // 将媒体控制器注册到ability以接收按键事件。
        AVController.setControllerForAbility(this.getAbility(), avController);

        HiLog.info(LABEL_LOG, "end initBrowser");
    }

    // 发起连接（avBrowser.connect）后的回调方法实现
    private AVConnectionCallback connectionCallback = new AVConnectionCallback() {
        @Override
        public void onConnected() {
            // 成功连接媒体浏览器服务时回调该方法，否则回调onConnectionFailed()。
            // 重复订阅会报错，所以先解除订阅。
            avBrowser.unsubscribeByParentMediaId(avBrowser.getRootMediaId());
            // 第二个参数AVSubscriptionCallback，用于处理订阅信息的回调。
            avBrowser.subscribeByParentMediaId(avBrowser.getRootMediaId(), avSubscriptionCallback);
            AVToken token = avBrowser.getAVToken();
            avController = new AVController(getContext(), token); // AVController第一个参数为当前类的context
            // 参数AVControllerCallback，用于处理服务端播放状态及信息变化时回调。
            avController.setAVControllerCallback(avControllerCallback);

            HiLog.info(LABEL_LOG, "end onConnected");
        }

    };

    // 发起订阅信息(avBrowser.subscribeByParentMediaId)后的回调方法实现
    private AVSubscriptionCallback avSubscriptionCallback = new AVSubscriptionCallback() {
        @Override
        public void onAVElementListLoaded(String parentId, List<AVElement> children) {
            // 订阅成功时回调该方法，parentID为标识，children为服务端回传的媒体列表
            super.onAVElementListLoaded(parentId, children);
            list.addAll(children);

            HiLog.info(LABEL_LOG, "end onAVElementListLoaded, size: %{public}s", list.size());
        }
    };

    // 服务对客户端的媒体数据或播放状态变更后的回调
    private AVControllerCallback avControllerCallback = new AVControllerCallback() {
        @Override
        public void onAVMetadataChanged(AVMetadata metadata) {
            // 当服务端调用avSession.setAVMetadata(avMetadata)时，此方法会被回调。
            super.onAVMetadataChanged(metadata);
            AVDescription description = metadata.getAVDescription();
            String title = description.getTitle().toString();
            PixelMap pixelMap = description.getIcon();

            HiLog.info(LABEL_LOG, "end onAVMetadataChanged, title: %{public}s", title);
        }

        @Override
        public void onAVPlaybackStateChanged(AVPlaybackState playbackState) {
            // 当服务端调用avSession.setAVPlaybackState(...)时，此方法会被回调。
            super.onAVPlaybackStateChanged(playbackState);
            long position = playbackState.getCurrentPosition();

            HiLog.info(LABEL_LOG, "end onAVMetadataChanged, position: %{public}s", position);
        }
    };

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
