package com.waylau.hmos.musicplayer.slice;

import com.waylau.hmos.musicplayer.Music;
import com.waylau.hmos.musicplayer.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.RoundProgressBar;
import ohos.agp.components.Text;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.global.resource.NotExistException;
import ohos.global.resource.RawFileDescriptor;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.audio.*;
import ohos.media.common.Source;
import ohos.media.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private Text currentName; // 当前音乐名称
    private int currentIndex = 0; // 当前音乐索引
    private Text totalNum; // 当前音乐总数
    private RoundProgressBar playerProgress;
    private List<Music> audioList; // 音乐列表
    // 音频播放器
    private SoundPlayer player;
    private SoundPlayer.SoundPlayerParameters parameters;

    private TaskDispatcher dispatcher;
    private boolean isStop;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

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

    private void start(String fileName) throws IOException {
        HiLog.info(LABEL_LOG, "before getFile: %{public}s", fileName);

        RawFileDescriptor filDescriptor = getResourceManager()
                .getRawFileEntry("resources/rawfile/" + fileName)
                .openRawFileDescriptor();

        // 指定音频资源加载并创建短音
        int soundId = player.createSound(filDescriptor);

        // 短音播放
        player.play(soundId, parameters);

        HiLog.info(LABEL_LOG, "isNowPlaying: %{public}s", player.play());

        // 延迟1秒，再刷新进度
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (player.play()) {
                    HiLog.info(LABEL_LOG, "before playerProgress");

                   // int progressValue = player.getCurrentTime() * 100 / player.getDuration();
                   // playerProgress.setProgressValue(progressValue);
                   /// playerProgress.setProgressHintText(progressValue + "");

                   // HiLog.info(LABEL_LOG, "playerProgress: %{public}s", progressValue);
                }

              //  HiLog.info(LABEL_LOG, "getCurrentTime: %{public}s", player.getCurrentTime() );
              //  HiLog.info(LABEL_LOG, "getDuration: %{public}s", player.getDuration() );

                dispatcher.delayDispatch(this, 200);
            }
        };

        dispatcher.asyncDispatch(runnable);

        HiLog.info(LABEL_LOG, "end getFile");
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
            player.release();
            player = null;
        }
    }


    private void initData() throws DataAbilityRemoteException {
        HiLog.info(LABEL_LOG, "before initData");

        // 实例化SoundPlayer对象
        player =
                new SoundPlayer(AudioManager.AudioVolumeType.STREAM_MUSIC.getValue());

        // 指定音量，循环次数和播放速度
        parameters = new SoundPlayer.SoundPlayerParameters();
        parameters.setVolumes(new SoundPlayer.AudioVolumes());
        parameters.setSpeed(1.0f);

        Music music1 = new Music("Twins - 恋爱大过天", "test.wav");
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

        doPlayPause();
    }

    private void doPlayPause() throws IOException {

        Music music = audioList.get(currentIndex);

        currentName.setText(music.getName());

        if (player.play()) {
            player.pause();
        } else {
            start(music.getFilePath());
        }

    }

    private void doNext() throws IOException {
        currentIndex++;

        // 取模
        currentIndex = Math.floorMod(currentIndex, audioList.size());

        doPlayPause();
    }

    private Player.IPlayerCallback playerCallback = new Player.IPlayerCallback() {
        @Override
        public void onPrepared() {

            HiLog.info(LABEL_LOG, "onPrepared");
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
