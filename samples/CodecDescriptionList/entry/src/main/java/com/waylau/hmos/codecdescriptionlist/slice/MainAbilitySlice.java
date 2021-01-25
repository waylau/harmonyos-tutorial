package com.waylau.hmos.codecdescriptionlist.slice;

import com.waylau.hmos.codecdescriptionlist.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.media.codec.CodecDescriptionList;
import ohos.media.common.Format;

import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button button =
                (Button) findComponentById(ResourceTable.Id_button);

        Text text =
                (Text) findComponentById(ResourceTable.Id_text);

        // 为按钮设置点击事件回调
        button.setClickedListener(listener -> {
                    showInfo(text);
                }
        );
    }

    private void showInfo(Text text) {
        // 获取某设备所支持的编解码器的MIME列表
        List<String> mimes = CodecDescriptionList.getSupportedMimes();
        text.setText("mimes:" + mimes);

        // 判断某设备是否支持指定MIME对应的解码器，支持返回true，否则返回false
        boolean isDecodeSupportedByMime =
                CodecDescriptionList.isDecodeSupportedByMime(Format.VIDEO_VP9);
        text.insert("isDecodeSupportedByMime:" + isDecodeSupportedByMime);

        // 判断某设备是否支持指定MIME对应的编码器，支持返回true，否则返回false
        boolean isEncodeSupportedByMime =
                CodecDescriptionList.isEncodeSupportedByMime(Format.AUDIO_FLAC);
        text.insert("isEncodeSupportedByMime:" + isEncodeSupportedByMime);

        // 判断某设备是否支持指定Format的编解码器，支持返回true，否则返回false
        Format format = new Format();
        format.putStringValue(Format.MIME, Format.VIDEO_AVC);
        format.putIntValue(Format.WIDTH, 2560);
        format.putIntValue(Format.HEIGHT, 1440);
        format.putIntValue(Format.FRAME_RATE, 30);
        format.putIntValue(Format.FRAME_INTERVAL, 1);
        boolean isDecoderSupportedByFormat = CodecDescriptionList.isDecoderSupportedByFormat(format);
        text.insert("isDecoderSupportedByFormat:" + isDecoderSupportedByFormat);
        boolean isEncoderSupportedByFormat = CodecDescriptionList.isEncoderSupportedByFormat(format);
        text.insert("isEncoderSupportedByFormat:" + isEncoderSupportedByFormat);
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
