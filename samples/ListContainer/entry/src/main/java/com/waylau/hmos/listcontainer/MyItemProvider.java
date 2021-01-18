package com.waylau.hmos.listcontainer;

import com.waylau.hmos.listcontainer.slice.MainAbilitySlice;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;

import java.util.List;

public class MyItemProvider extends RecycleItemProvider {
    private List<MyItem> list;
    private AbilitySlice slice;

    public MyItemProvider(List<MyItem> list, MainAbilitySlice slice) {
        this.list = list;
        this.slice = slice;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component convertComponent, ComponentContainer componentContainer) {
        Component cpt = convertComponent;
        if (cpt == null) {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_my_item, null, false);
        }
        MyItem sampleItem = list.get(position);
        Text text = (Text) cpt.findComponentById(ResourceTable.Id_item_index);
        text.setText(sampleItem.getName());
        return cpt;
    }
}
