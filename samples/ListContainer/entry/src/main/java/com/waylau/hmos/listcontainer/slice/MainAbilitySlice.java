package com.waylau.hmos.listcontainer.slice;

import com.waylau.hmos.listcontainer.MyItem;
import com.waylau.hmos.listcontainer.MyItemProvider;
import com.waylau.hmos.listcontainer.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ListContainer;

import java.util.ArrayList;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 获取ListContainer
        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_list_container);

        // 提供ListContainer的数据
        List<MyItem> list = getData();
        MyItemProvider sampleItemProvider = new MyItemProvider(list, this);
        listContainer.setItemProvider(sampleItemProvider);
    }

    private ArrayList<MyItem> getData() {
        ArrayList<MyItem> list = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            list.add(new MyItem("Item" + i));
        }
        return list;
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
