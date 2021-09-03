package com.waylau.hmos.systempasteboardgetter.slice;

import com.waylau.hmos.systempasteboardgetter.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.miscservices.pasteboard.PasteData;
import ohos.miscservices.pasteboard.SystemPasteboard;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发
        Text textGetPasteData =
                (Text) findComponentById(ResourceTable.Id_text_get_paste_data);
        textGetPasteData.setClickedListener(listener -> getPasteData());
    }

    private void getPasteData() {
        HiLog.info(LABEL_LOG, "before getPasteData");

        // 获取系统剪贴板服务
        SystemPasteboard pasteboard = SystemPasteboard.getSystemPasteboard(this.getContext());

        // 从系统剪贴板中读取纯文本数据
        if (pasteboard != null) {
            PasteData pasteData = pasteboard.getPasteData();

            if (pasteData != null) {
                PasteData.DataProperty dataProperty = pasteData.getProperty();
                boolean hasHtml = dataProperty.hasMimeType(PasteData.MIMETYPE_TEXT_HTML);
                boolean hasText = dataProperty.hasMimeType(PasteData.MIMETYPE_TEXT_PLAIN);

                if (hasHtml || hasText) {
                    String textString = "";

                    // 遍历剪贴板中所有的记录
                    for (int i = 0; i < pasteData.getRecordCount(); i++) {
                        PasteData.Record record = pasteData.getRecordAt(i);
                        String mimeType = record.getMimeType();
                        if (mimeType.equals(PasteData.MIMETYPE_TEXT_HTML)) {
                            textString = record.getHtmlText();
                            break;
                        } else if (mimeType.equals(PasteData.MIMETYPE_TEXT_PLAIN)) {//纯文本数据
                            textString = record.getPlainText().toString();
                            break;
                        }
                    }

                    // 将内容输出到界面
                    Text text = (Text) findComponentById(ResourceTable.Id_text_paste_data);
                    text.setText(textString);
                }
            } else {
                HiLog.info(LABEL_LOG, "PasteData is null");
            }


        }

        HiLog.info(LABEL_LOG, "end getPasteData");
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
