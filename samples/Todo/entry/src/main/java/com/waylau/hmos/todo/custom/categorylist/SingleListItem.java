package com.waylau.hmos.todo.custom.categorylist;

import com.waylau.hmos.todo.ResourceTable;
import com.waylau.hmos.todo.datamodel.CategoryItemBase;

import ohos.agp.components.Component;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.app.Context;

/**
 * SingleList item which display a left text and a right text in the list container item.
 */
public class SingleListItem implements CategoryItemBase {
    /**
     * SingleListItem type
     */
    public static final int ITEM_TYPE = 2;

    private final Context context;
    private String leftText;
    private String rightText;

    SingleListItem(Context context, String leftText, String rightText) {
        this.context = context;
        this.leftText = leftText;
        this.rightText = rightText;
    }

    /**
     * Set component content
     *
     * @param component the component to set
     */
    @Override
    public void bindComponent(Component component) {
        // item context
        Text contentText = (Text) component.findComponentById(ResourceTable.Id_item_content_text);
        contentText.setText(leftText);

        // item right context
        //Text rightContentText = (Text) component.findComponentById(ResourceTable.Id_item_right_content_text);
        //rightContentText.setText(rightText);
    }

    /**
     * Create component
     *
     * @return component
     */
    @Override
    public Component createComponent() {
        return LayoutScatter.getInstance(context).parse(
                ResourceTable.Layout_list_item, null, false);
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
