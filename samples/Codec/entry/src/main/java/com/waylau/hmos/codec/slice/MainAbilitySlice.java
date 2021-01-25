package com.waylau.hmos.codec.slice;

import com.waylau.hmos.codec.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.global.resource.RawFileEntry;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.codec.Codec;
import ohos.media.codec.TrackInfo;
import ohos.media.common.BufferInfo;
import ohos.media.common.Format;
import ohos.media.common.Source;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button buttonEncode =
                (Button) findComponentById(ResourceTable.Id_button_encode);

        Button buttonDecode =
                (Button) findComponentById(ResourceTable.Id_button_decode);

        // 为按钮设置点击事件回调
        buttonEncode.setClickedListener(listener -> {
            try {
                encode();
            } catch (IOException e) {
                HiLog.error(LABEL_LOG, "exception : %{public}s", e);
            }
        });

        buttonDecode.setClickedListener(listener -> {
            try {
                decode();
            } catch (IOException e) {
                HiLog.error(LABEL_LOG, "exception : %{public}s", e);
            }
        });
    }

    private void decode() throws IOException {
        HiLog.info(LABEL_LOG, "before decode()");

        //1、创建解码Codec实例，可调用createDecoder()创建
        final Codec decoder = Codec.createDecoder();

        //2、调用setSource()设置数据源，支持设定文件路径或者文件File Descriptor
        RawFileEntry rawFileEntry = this.getResourceManager().getRawFileEntry("resources/rawfile/video.mp4");
        FileDescriptor fd = rawFileEntry.openRawFileDescriptor().getFileDescriptor();
        decoder.setSource(new Source(fd), new TrackInfo());

        //3、构造数据源格式或者从Extractor中读取数据源格式，并设置给Codec实例，调用setSourceFormat()
        Format fmt = new Format();
        fmt.putStringValue(Format.MIME, Format.VIDEO_AVC);
        fmt.putIntValue(Format.WIDTH, 1920);
        fmt.putIntValue(Format.HEIGHT, 1080);
        fmt.putIntValue(Format.BIT_RATE, 392000);
        fmt.putIntValue(Format.FRAME_RATE, 30);
        fmt.putIntValue(Format.FRAME_INTERVAL, -1);
        decoder.setSourceFormat(fmt);

        //4、设置监听器
        decoder.registerCodecListener(listener);

        //5、开始编码
        decoder.start();

        //6、停止编码
        decoder.stop();

        //7、释放资源
        decoder.release();

        HiLog.info(LABEL_LOG, "end decode()");
    }

    private void encode() throws IOException {
        HiLog.info(LABEL_LOG, "before encode()");

        //1、创建编码Codec实例，可调用createEncoder()创建
        final Codec encoder = Codec.createEncoder();

        //2、调用setSource()设置数据源，支持设定文件路径或者文件File Descriptor
        // 获取源文件
        RawFileEntry rawFileEntry = this.getResourceManager().getRawFileEntry("resources/rawfile/video.mp4");
        FileDescriptor fd = rawFileEntry.openRawFileDescriptor().getFileDescriptor();
        encoder.setSource(new Source(fd), new TrackInfo());

        //3、构造数据源格式或者从Extractor中读取数据源格式，并设置给Codec实例，调用setSourceFormat()
        Format fmt = new Format();
        fmt.putStringValue(Format.MIME, Format.VIDEO_AVC);
        fmt.putIntValue(Format.WIDTH, 1920);
        fmt.putIntValue(Format.HEIGHT, 1080);
        fmt.putIntValue(Format.BIT_RATE, 392000);
        fmt.putIntValue(Format.FRAME_RATE, 30);
        fmt.putIntValue(Format.FRAME_INTERVAL, -1);
        encoder.setSourceFormat(fmt);

        //4、设置监听器
        encoder.registerCodecListener(listener);

        //5、开始编码
        encoder.start();

        //6、停止编码
        encoder.stop();

        //7、释放资源
        encoder.release();

        HiLog.info(LABEL_LOG, "end encode()");
    }

    private Codec.ICodecListener listener = new Codec.ICodecListener() {
        @Override
        public void onReadBuffer(ByteBuffer byteBuffer, BufferInfo bufferInfo, int trackId) {
            HiLog.info(LABEL_LOG, "onReadBuffer trackId: %{public}s", trackId);
        }

        @Override
        public void onError(int errorCode, int act, int trackId) {
            throw new RuntimeException();
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
