package com.waylau.hmos.todo.custom.categorylist;

import com.waylau.hmos.todo.ResourceTable;
import com.waylau.hmos.todo.datamodel.CategoryItemBase;

import ohos.agp.components.Component;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.app.Context;

/**
 * Head item which display a category name in the list container item.
 */
public class HeadItem implements CategoryItemBase {
    /**
     * HeadItem type
     */
    public static final int ITEM_TYPE = 0;

    private final Context context;
    private String headText;

    HeadItem(Context context, String headText) {
        this.context = context;
        this.headText = headText;
    }

    /**
     * Set component content
     *
     * @param component the component to set
     */
    @Override
    public void bindComponent(Component component) {
        Text headerText = (Text) component.findComponentById(ResourceTable.Id_header_item_content_text);
        headerText.setText(headText);
    }

    /**
     * Create component
     *
     * @return component
     */
    @Override
    public Component createComponent() {
        return LayoutScatter.getInstance(context).parse(ResourceTable.Layout_list_item_header,
                null, false);
    }

    /**
     * Get the item type of this category item
     *
     * @return category item type
     */
    @Override
    public int getItemType() {
        return ITEM_TYPE;
    }
}
