package com.waylau.hmos.text.slice;

import com.waylau.hmos.text.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;

public class AutoScrollingAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_auto_scrolling);

        Text textAutoScrolling =
                (Text) findComponentById(ResourceTable.Id_text_auto_scrolling);

        // 跑马灯效果
        textAutoScrolling.setTruncationMode(Text.TruncationMode.AUTO_SCROLLING);

        // 始终处于自动滚动状态
        textAutoScrolling.setAutoScrollingCount(Text.AUTO_SCROLLING_FOREVER);

        // 启动跑马灯效果
        textAutoScrolling.startAutoScrolling();
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
