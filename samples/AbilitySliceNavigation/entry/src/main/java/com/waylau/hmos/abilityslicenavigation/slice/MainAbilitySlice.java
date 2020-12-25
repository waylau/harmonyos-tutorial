package com.waylau.hmos.abilityslicenavigation.slice;

import com.waylau.hmos.abilityslicenavigation.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);

        // 指定ability_main.xml定义的UI
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发导航
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
