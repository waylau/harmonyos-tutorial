package com.waylau.hmos.soundplayer.slice;

import com.waylau.hmos.soundplayer.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.global.resource.NotExistException;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.audio.AudioManager;
import ohos.media.audio.AudioRenderer;
import ohos.media.audio.SoundPlayer;

import java.io.IOException;


public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    // 短音播放类
    private SoundPlayer soundPlayer;

    private int soundId;
    private SoundPlayer.SoundPlayerParameters parameters;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化SoundPlayer
        try {
            initSoundPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 为按钮设置点击事件回调
        Button buttonPlay =
                (Button) findComponentById(ResourceTable.Id_button_play);
        buttonPlay.setClickedListener(listener -> play());

        Button buttonPause =
                (Button) findComponentById(ResourceTable.Id_button_pause);
        buttonPause.setClickedListener(listener -> soundPlayer.pause());

        Button buttonStop =
                (Button) findComponentById(ResourceTable.Id_button_stop);
        buttonStop.setClickedListener(listener -> soundPlayer.stop(soundId));
    }

    private void play() {
        HiLog.info(LABEL_LOG, "before play");

        // 短音播放
        soundPlayer.play(soundId, parameters);

        HiLog.info(LABEL_LOG, "end play, soundId:%{public}s", soundId);
    }


    @Override
    public void onStop() {
        super.onStop();

        // 关闭、释放资源
        release();
    }

    private void initSoundPlayer() throws IOException {
        HiLog.info(LABEL_LOG, "before initSoundPlayer");

        // 实例化SoundPlayer对象
        soundPlayer = new SoundPlayer(AudioManager.AudioVolumeType.STREAM_MUSIC.getValue());

        RawFileEntry rawFileEntry = this.getResourceManager().getRawFileEntry("resources/rawfile/test.wav");

        // 指定音频资源加载并创建短音
        soundId = soundPlayer.createSound(rawFileEntry.openRawFileDescriptor());

        // 指定音量，循环次数和播放速度
        parameters = new SoundPlayer.SoundPlayerParameters();
        parameters.setVolumes(new SoundPlayer.AudioVolumes());
        parameters.setLoop(10);
        parameters.setSpeed(1.0f);

        HiLog.info(LABEL_LOG, "end initSoundPlayer");
    }

    private void release() {
        HiLog.info(LABEL_LOG, "release");

        if (soundPlayer != null) {
            // 关闭、释放资源
            soundPlayer.release();
            soundPlayer = null;
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
