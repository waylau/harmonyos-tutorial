package com.waylau.hmos.abilityslicenavigation.slice;

import com.waylau.hmos.abilityslicenavigation.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);

        // UI界面内容引用了布局文件ability_main.xml
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发导航到PayAbilitySlice
        Text text = (Text) findComponentById(ResourceTable.Id_text_helloworld);
        text.setClickedListener(listener ->
                present(new PayAbilitySlice(), new Intent()));
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
