package com.waylau.hmos.todo.slice;

import com.waylau.hmos.todo.ResourceTable;
import com.waylau.hmos.todo.custom.data.CustomData;
import com.waylau.hmos.todo.datamodel.Category;
import com.waylau.hmos.todo.views.adapter.CategoryListItemProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;

import java.util.List;

public class EditAbilitySlice extends AbilitySlice {
    private ComponentContainer createComponent() {
        Component editorComponent = LayoutScatter.getInstance(this).parse(ResourceTable.Layout_editor,
                null, false);

        return (ComponentContainer) editorComponent;
    }

    @Override
    public void onStart(Intent intent) {
        setUIContent(createComponent());
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
