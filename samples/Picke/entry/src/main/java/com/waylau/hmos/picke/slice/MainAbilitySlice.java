package com.waylau.hmos.picke.slice;

import com.waylau.hmos.picke.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Picker;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 获取Picker
        Picker picker = (Picker) findComponentById(ResourceTable.Id_test_picker);

        // 设置格式化
        picker.setFormatter(new Picker.Formatter() {
            @Override
            public String format(int i) {
                String value = "";
                switch (i) {
                    case 0:
                        value = "零";
                        break;
                    case 1:
                        value = "一";
                        break;
                    case 2:
                        value = "二";
                        break;
                    case 3:
                        value = "三";
                        break;
                    case 4:
                        value = "四";
                        break;
                    case 5:
                        value = "无";
                        break;
                    case 6:
                        value = "六";
                        break;
                    case 7:
                        value = "七";
                        break;
                    case 8:
                        value = "八";
                        break;
                    case 9:
                        value = "九";
                        break;
                }

                return value;
            }
        });

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
