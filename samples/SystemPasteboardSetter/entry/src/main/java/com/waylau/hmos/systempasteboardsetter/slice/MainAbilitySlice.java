package com.waylau.hmos.systempasteboardsetter.slice;

import com.waylau.hmos.systempasteboardsetter.ResourceTable;
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
        Text textSetPasteData = (Text) findComponentById(ResourceTable.Id_text_set_paste_data);
        textSetPasteData.setClickedListener(listener -> setPasteData());
    }

    private void setPasteData() {
        HiLog.info(LABEL_LOG, "before setPasteData");

        // 获取系统剪贴板服务
        SystemPasteboard pasteboard = SystemPasteboard.getSystemPasteboard(this.getContext());

        // 向系统剪贴板中写入一条纯文本数据
        if (pasteboard != null) {
            pasteboard.setPasteData(PasteData.creatPlainTextData("Welcome to waylau.com!"));
        }

        HiLog.info(LABEL_LOG, "end setPasteData");
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
