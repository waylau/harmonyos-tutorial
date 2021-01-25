package com.waylau.hmos.recorder.slice;

import com.waylau.hmos.recorder.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.global.resource.RawFileDescriptor;
import ohos.media.common.Source;
import ohos.media.common.StorageProperty;
import ohos.media.recorder.Recorder;

import java.io.IOException;

public class MainAbilitySlice extends AbilitySlice {
    private final Recorder recorder = new Recorder();

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Text text =
                (Text) findComponentById(ResourceTable.Id_text_helloworld);

        Button buttonStart =
                (Button) findComponentById(ResourceTable.Id_button_start);

        // 为按钮设置点击事件回调
        buttonStart.setClickedListener(listener -> {
            recorder.start();
            text.setText("Start");
        });

        Button buttonPause =
                (Button) findComponentById(ResourceTable.Id_button_pause);

        // 为按钮设置点击事件回调
        buttonPause.setClickedListener(listener -> {
            recorder.pause();
            text.setText("Pause");
        });

        Button buttonStop =
                (Button) findComponentById(ResourceTable.Id_button_stop);

        // 为按钮设置点击事件回调
        buttonStop.setClickedListener(listener -> {
            recorder.stop();
            text.setText("Stop");
        });
    }

    @Override
    public void onActive() {
        super.onActive();

        RawFileDescriptor filDescriptor = null;
        try {
            filDescriptor = getResourceManager()
                    .getRawFileEntry("resources/rawfile/video.mp4")
                    .openRawFileDescriptor();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 设置媒体源
        Source source = new Source(filDescriptor.getFileDescriptor(),
                filDescriptor.getStartPosition(), filDescriptor.getFileSize());
        source.setRecorderAudioSource(Recorder.AudioSource.DEFAULT);
        recorder.setSource(source);

        // 设置存储属性
        String path = "record.mp4";
        StorageProperty storageProperty = new StorageProperty.Builder()
                .setRecorderPath(path)
                .setRecorderMaxDurationMs(-1)
                .setRecorderMaxFileSizeBytes(-1)
                .build();
        recorder.setStorageProperty(storageProperty);

        // 准备
        recorder.prepare();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.stop();
        }
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
