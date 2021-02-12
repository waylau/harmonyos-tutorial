package com.waylau.hmos.audiorenderer.slice;

import com.waylau.hmos.audiorenderer.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.global.resource.NotExistException;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.audio.AudioRenderer;
import ohos.media.audio.AudioRendererInfo;
import ohos.media.audio.AudioStreamInfo;

import java.io.*;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    // 音频播放类
    private AudioRenderer audioRenderer;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化AudioRenderer
        initAudioRenderer();

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

        Button buttonPause =
                (Button) findComponentById(ResourceTable.Id_button_pause);
        buttonPause.setClickedListener(listener -> audioRenderer.pause());

        Button buttonStop =
                (Button) findComponentById(ResourceTable.Id_button_stop);
        buttonStop.setClickedListener(listener -> audioRenderer.stop());
    }

    private void start() throws IOException, NotExistException {
        HiLog.info(LABEL_LOG, "before getFile");

        RawFileEntry rawFileEntry = this.getResourceManager().getRawFileEntry("resources/rawfile/test.wav");
        Resource soundInputStream = rawFileEntry.openRawFile();

        int bufSize = audioRenderer.getBufferFrameSize();

        HiLog.info(LABEL_LOG, "bufSize: %{public}s", bufSize);

        if (bufSize <=0) {
            HiLog.info(LABEL_LOG, "bufSize is empty");
            return;
        }

        byte[] buffer = new byte[4096];
        try {
            audioRenderer.start();

            int count = 0;

            // 源文件内容写入目标文件
            while((count = soundInputStream.read(buffer)) >= 0){
                audioRenderer.write(buffer,0,count);

                HiLog.info(LABEL_LOG, "getFile buffer.length: %{public}s, state:%{public}s",
                        buffer.length, getState().getValue());
            }

            soundInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        HiLog.info(LABEL_LOG, "end getFile");
    }


    @Override
    public void onStop() {
        super.onStop();

        // 关闭、释放资源
        release();
    }

    private void initAudioRenderer() {
        HiLog.info(LABEL_LOG, "before initAudioRenderer");

        AudioStreamInfo audioStreamInfo = new AudioStreamInfo.Builder().sampleRate(44100) // 44.1kHz
                .audioStreamFlag(AudioStreamInfo.AudioStreamFlag.AUDIO_STREAM_FLAG_MAY_DUCK) // 混音
                .encodingFormat(AudioStreamInfo.EncodingFormat.ENCODING_PCM_16BIT) // 16-bit PCM
                .channelMask(AudioStreamInfo.ChannelMask.CHANNEL_OUT_STEREO) // 双声道输出
                .streamUsage(AudioStreamInfo.StreamUsage.STREAM_USAGE_MEDIA) // 媒体类音频
                .build();

        AudioRendererInfo audioRendererInfo = new AudioRendererInfo.Builder().audioStreamInfo(audioStreamInfo)
                .audioStreamOutputFlag(AudioRendererInfo.
                        AudioStreamOutputFlag.AUDIO_STREAM_OUTPUT_FLAG_DIRECT_PCM) // pcm格式的输出流
                .bufferSizeInBytes(1024)
                .isOffload(false) // false表示分段传输buffer并播放，true表示整个音频流一次性传输到HAL层播放
                .build();

        audioRenderer = new AudioRenderer(audioRendererInfo, AudioRenderer.PlayMode.MODE_STREAM);
        audioRenderer.setVolume(90f); // 音量

        HiLog.info(LABEL_LOG, "end initAudioRenderer");
    }

    private void release() {
        HiLog.info(LABEL_LOG, "release");

        if (audioRenderer != null) {
            // 关闭、释放资源
            audioRenderer.release();
            audioRenderer = null;
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
