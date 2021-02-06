package com.waylau.hmos.todo.slice;

import com.waylau.hmos.todo.ResourceTable;
import com.waylau.hmos.todo.custom.data.CustomData;
import com.waylau.hmos.todo.datamodel.Category;
import com.waylau.hmos.todo.views.adapter.CategoryListItemProvider;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;

import java.util.List;

/**
 * Category List Ability Slice
 */
public class MainAbilitySlice extends AbilitySlice {
    private ComponentContainer createComponent() {
        Component mainComponent = LayoutScatter.getInstance(this).parse(ResourceTable.Layout_main_ability,
                null, false);

        // ListContainer item provider and listener. You can implement your own CustomData to achieve different effects
        List<Category> categoryList = CustomData.getCustomData(this);
        CategoryListItemProvider categoryListItemProvider = new CategoryListItemProvider(categoryList);

        ListContainer listContainer = (ListContainer) mainComponent.findComponentById(ResourceTable.Id_list_view);
        if (listContainer != null) {
            listContainer.setItemProvider(categoryListItemProvider);
            listContainer.setItemClickedListener(categoryListItemProvider);
        }



        return (ComponentContainer) mainComponent;
    }

    @Override
    public void onStart(Intent intent) {
        setUIContent(createComponent());

        // 点击事件
        Image imageAdd = (Image)findComponentById(ResourceTable.Id_title_area_add_icon);
        imageAdd.setClickedListener(listener -> present(new EditAbilitySlice(), new Intent()));
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
