package com.waylau.hmos.audioplayer.slice;

import com.waylau.hmos.audioplayer.Music;
import com.waylau.hmos.audioplayer.PlayerStateEnum;
import com.waylau.hmos.audioplayer.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.audio.*;
import ohos.media.player.Player;
import ohos.utils.zson.ZSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private Text currentName; // 当前音乐名称
    private int currentIndex = 0; // 当前音乐索引
    private Text totalNum; // 当前音乐总数
    private RoundProgressBar playerProgress;
    private List<Music> audioList; // 音乐列表

    // 音频播放类
    private AudioRenderer player;
    private TaskDispatcher dispatcher;

    PlayerStateEnum playerState = PlayerStateEnum.STOP;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化AudioRenderer
        initAudioRenderer();

        // 添加点击事件来触发访问数据
        Button buttonQuery = (Button) findComponentById(ResourceTable.Id_button_previous);
        buttonQuery.setClickedListener(listener -> {
            try {
                this.doPrevious();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button buttonInsert = (Button) findComponentById(ResourceTable.Id_button_play_pause);
        buttonInsert.setClickedListener(listener -> {
            try {
                this.doPlayPause();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button buttonDelete = (Button) findComponentById(ResourceTable.Id_button_next);
        buttonDelete.setClickedListener(listener -> {
            try {
                this.doNext();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        totalNum = (Text) findComponentById(ResourceTable.Id_text_total_num);

        currentName = (Text) findComponentById(ResourceTable.Id_text_current_name);

        // 跑马灯效果
        currentName.setTruncationMode(Text.TruncationMode.AUTO_SCROLLING);
        currentName.setAutoScrollingCount(Text.AUTO_SCROLLING_FOREVER);
        currentName.startAutoScrolling();

        playerProgress = (RoundProgressBar) findComponentById(ResourceTable.Id_round_progress_bar);

        dispatcher = getUITaskDispatcher();

        // 初始化音乐列表
        try {
            initData();
        } catch (DataAbilityRemoteException e) {
            e.printStackTrace();
        }
    }
    private void initAudioRenderer() {
        HiLog.info(LABEL_LOG, "before initAudioRenderer");

        AudioDeviceDescriptor[]  audioDevices = AudioManager.getDevices(AudioDeviceDescriptor.DeviceFlag.INPUT_DEVICES_FLAG);

        for (AudioDeviceDescriptor audioDevice : audioDevices) {
            HiLog.info(LABEL_LOG, "end initAudioRenderer" + ZSONObject.toZSONString(audioDevice));
        }
        AudioStreamInfo audioStreamInfo = new AudioStreamInfo.Builder().sampleRate(48000)
                .audioStreamFlag(AudioStreamInfo.AudioStreamFlag.AUDIO_STREAM_FLAG_MAY_DUCK) // 混音
                .encodingFormat(AudioStreamInfo.EncodingFormat.ENCODING_PCM_16BIT) // 16-bit PCM
                .channelMask(AudioStreamInfo.ChannelMask.CHANNEL_OUT_STEREO) // 双声道输出
                .streamUsage(AudioStreamInfo.StreamUsage.STREAM_USAGE_MEDIA) // 媒体类音频
                .build();
        /*
        AudioStreamInfo audioStreamInfo = new AudioStreamInfo.Builder().sampleRate(44100) // 44.1kHz
                .audioStreamFlag(AudioStreamInfo.AudioStreamFlag.AUDIO_STREAM_FLAG_MAY_DUCK) // 混音
                .encodingFormat(AudioStreamInfo.EncodingFormat.ENCODING_PCM_16BIT) // 16-bit PCM
                .channelMask(AudioStreamInfo.ChannelMask.CHANNEL_OUT_STEREO) // 双声道输出
                .streamUsage(AudioStreamInfo.StreamUsage.STREAM_USAGE_MEDIA) // 媒体类音频
                .build();
*/
        AudioRendererInfo audioRendererInfo = new AudioRendererInfo.Builder().audioStreamInfo(audioStreamInfo)
                .audioStreamOutputFlag(AudioRendererInfo.
                        AudioStreamOutputFlag.AUDIO_STREAM_OUTPUT_FLAG_DIRECT_PCM) // pcm格式的输出流
                .bufferSizeInBytes(1024)
                .isOffload(false) // false表示分段传输buffer并播放，true表示整个音频流一次性传输到HAL层播放
                .build();

        player = new AudioRenderer(audioRendererInfo, AudioRenderer.PlayMode.MODE_STREAM);
        player.setVolume(90f); // 音量

        HiLog.info(LABEL_LOG, "end initAudioRenderer");
    }

    private void start(String fileName) throws IOException {
        HiLog.info(LABEL_LOG, "before start: %{public}s", fileName);

        RawFileEntry rawFileEntry = getResourceManager()
                .getRawFileEntry("resources/rawfile/" + fileName);
        Resource soundInputStream = rawFileEntry.openRawFile();

        int bufSize = player.getBufferFrameSize();

        HiLog.info(LABEL_LOG, "bufSize: %{public}s", bufSize);

        if (bufSize <=0) {
            HiLog.info(LABEL_LOG, "bufSize is empty");
            return;
        }

        byte[] buffer = new byte[4096];
        try {
            player.start();

            int count = 0;

            // 源文件内容写入目标文件
            while((count = soundInputStream.read(buffer)) >= 0){
                player.write(buffer,0,count);

                HiLog.info(LABEL_LOG, "getFile buffer.length: %{public}s, state:%{public}s",
                        buffer.length, getState().getValue());
            }

            soundInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        playerState = PlayerStateEnum.PLAY;

        HiLog.info(LABEL_LOG, "end start");
    }

    @Override
    public void onStop() {
        super.onStop();

        // 关闭、释放资源
        release();
    }

    private void release() {
        HiLog.info(LABEL_LOG, "release");

        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }


    private void initData() throws DataAbilityRemoteException {
        HiLog.info(LABEL_LOG, "before initData");

        Music music1 = new Music("Twins - 恋爱大过天", "002.mp3");
        Music music2 = new Music("杨千嬅 - 假如让我说下去", "002.mp3");
        Music music3 = new Music("郑伊健 - 友情岁月", "003.mp3");

        audioList = new ArrayList<>(
                Arrays.asList(
                        music1,
                        music2,
                        music3));

        int size = audioList.size();

        // 显示总数
        totalNum.setText(size + "");

        HiLog.info(LABEL_LOG, "end initData, audioList size: %{public}s", size);
    }

    private void doPrevious() throws IOException {
        // 查询所有用户

        currentIndex--;

        // 取模
        currentIndex = Math.floorMod(currentIndex, audioList.size());

        player.stop();
        playerState = PlayerStateEnum.STOP;

        doPlayPause();
    }

    private void doPlayPause() throws IOException {

        Music music = audioList.get(currentIndex);

        currentName.setText(music.getName());


        HiLog.info(LABEL_LOG, "getPosition​: %{public}s", player.getPosition() );
        HiLog.info(LABEL_LOG, "getState​: %{public}s", player.getState() );

        // TODO: player.isNowPlaying() 这个获取不到状态
        if (playerState == PlayerStateEnum.PLAY) {
            HiLog.info(LABEL_LOG, "before pause");
            player.pause();
            playerState = PlayerStateEnum.PAUSE;
            HiLog.info(LABEL_LOG, "end pause");
        } else if (playerState == PlayerStateEnum.PAUSE){
            player.start();
            playerState = PlayerStateEnum.PLAY;
        } else {
            start(music.getFilePath());
        }


    }

    private void doNext() throws IOException {
        currentIndex++;

        // 取模
        currentIndex = Math.floorMod(currentIndex, audioList.size());

        player.stop();
        playerState = PlayerStateEnum.STOP;

        doPlayPause();
    }

    private Player.IPlayerCallback playerCallback = new Player.IPlayerCallback() {
        @Override
        public void onPrepared() {

            HiLog.info(LABEL_LOG, "onPrepared");

            // 延迟1秒，再刷新进度
            /*
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (player.isNowPlaying()) {
                        HiLog.info(LABEL_LOG, "before playerProgress");

                        int progressValue = player.getPosition​() * 100 / player.getState​();
                        playerProgress.setProgressValue(progressValue);
                        playerProgress.setProgressHintText(progressValue + "%");

                        HiLog.info(LABEL_LOG, "playerProgress: %{public}s", progressValue);
                    }

                    HiLog.info(LABEL_LOG, "getPosition​: %{public}s", player.getPosition​() );
                    HiLog.info(LABEL_LOG, "getState​: %{public}s", player.getState​() );

                    dispatcher.delayDispatch(this, 200);
                }
            };

            dispatcher.asyncDispatch(runnable);
            */
            HiLog.info(LABEL_LOG, "getPosition​: %{public}s", player.getPosition() );
            HiLog.info(LABEL_LOG, "getState​: %{public}s", player.getState() );
        }

        @Override
        public void onMessage(int i, int i1) {
            HiLog.info(LABEL_LOG, "onMessage");
        }

        @Override
        public void onError(int i, int i1) {
            HiLog.info(LABEL_LOG, "onError, i: %{public}s, i1: %{public}s", i, i1);
        }

        @Override
        public void onResolutionChanged(int i, int i1) {
            HiLog.info(LABEL_LOG, "onResolutionChanged");
        }

        @Override
        public void onPlayBackComplete() {
            HiLog.info(LABEL_LOG, "onPlayBackComplete");
        }

        @Override
        public void onRewindToComplete() {
            HiLog.info(LABEL_LOG, "onRewindToComplete");
        }

        @Override
        public void onBufferingChange(int i) {
            HiLog.info(LABEL_LOG, "onBufferingChange");
        }

        @Override
        public void onNewTimedMetaData(Player.MediaTimedMetaData mediaTimedMetaData) {
            HiLog.info(LABEL_LOG, "onNewTimedMetaData");
        }

        @Override
        public void onMediaTimeIncontinuity(Player.MediaTimeInfo mediaTimeInfo) {
            HiLog.info(LABEL_LOG, "onMediaTimeIncontinuity");
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
