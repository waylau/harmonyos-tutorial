package com.waylau.hmos.todo.custom.categorylist;

import com.waylau.hmos.todo.datamodel.CategoryItemBase;
import ohos.app.Context;

/**
 * A factory to produce category item.
 */
public class CategoryItemFactory {
    /**
     * The total count of item type
     */
    public static final int TOTAL_ITEM_TYPE = 3;

    /**
     * The method to create a list item of a specify category
     *
     * @param context       current context
     * @param componentType the component type of list item
     * @param obj           list item content
     * @return category item
     */
    public static CategoryItemBase createListItem(Context context, int componentType, String... obj) {
        CategoryItemBase item = null;
        switch (componentType) {
            case SingleListItem.ITEM_TYPE:
                // SingleListItem accept 2 Strings
                if (obj == null || obj.length < 2) {
                    break;
                }
                item = new SingleListItem(context, obj[0], obj[1]);
                break;
            case HeadItem.ITEM_TYPE:
                // HeadItem accept 1 String
                if (obj == null || obj.length < 1) {
                    break;
                }
                item = new HeadItem(context, obj[0]);
                break;
            default:
                break;
        }
        return item;
    }
}
