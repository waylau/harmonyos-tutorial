package com.waylau.hmos.audiocapturer.slice;

import com.waylau.hmos.audiocapturer.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.global.resource.NotExistException;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.audio.*;

import java.io.*;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    // 音频采集类
    private AudioCapturer audioCapturer;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化AudioCapturer
        initAudioCapturer();

        // 为按钮设置点击事件回调
        Button buttonStart =
                (Button) findComponentById(ResourceTable.Id_button_start);
        buttonStart.setClickedListener(listener ->
        {
            try {
                start();
            } catch (IOException | NotExistException e) {
                e.printStackTrace();
            }
        });

        Button buttonStop =
                (Button) findComponentById(ResourceTable.Id_button_stop);
        buttonStop.setClickedListener(listener -> audioCapturer.stop());
    }

    private void start() throws IOException, NotExistException {
        HiLog.info(LABEL_LOG, "before start");

        audioCapturer.start();

        HiLog.info(LABEL_LOG, "end start");
    }


    @Override
    public void onStop() {
        super.onStop();

        // 关闭、释放资源
        release();
    }

    private void initAudioCapturer() {
        HiLog.info(LABEL_LOG, "before initAudioCapturer");

        AudioStreamInfo audioStreamInfo = new AudioStreamInfo.Builder().encodingFormat(
                AudioStreamInfo.EncodingFormat.ENCODING_PCM_16BIT) // 16-bit PCM
                .channelMask(AudioStreamInfo.ChannelMask.CHANNEL_IN_STEREO) // 双声道输入
                .sampleRate(44100) // 44.1kHz
                .build();

        AudioCapturerInfo audioCapturerInfo = new AudioCapturerInfo.Builder()
                .audioStreamInfo(audioStreamInfo)
                .build();

        audioCapturer = new AudioCapturer(audioCapturerInfo);

        HiLog.info(LABEL_LOG, "end initAudioCapturer");
    }

    private void release() {
        HiLog.info(LABEL_LOG, "release");

        if (audioCapturer != null) {
            // 关闭、释放资源
            audioCapturer.release();
            audioCapturer = null;
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
