package com.waylau.hmos.todo.custom.categorylist;

import com.waylau.hmos.todo.ResourceTable;
import com.waylau.hmos.todo.datamodel.Category;
import com.waylau.hmos.todo.datamodel.CategoryItemBase;

import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;

import java.util.List;

/**
 * This is a sample to implement Category interface for test. You can declare your own Category to achieve different
 * UI designs.
 */
public class TestCategory implements Category {
    private static final float CORNER_RADIUS = 32;

    private static final int TOAST_DURATION = 2000;

    private final Context context;
    private final List<CategoryItemBase> categoryItems;
    private final int categoryId;

    /**
     * Category class for test
     *
     * @param categoryItems category items list
     */
    public TestCategory(Context context, List<CategoryItemBase> categoryItems, int id) {
        this.context = context;
        this.categoryItems = categoryItems;
        categoryId = id;
    }

    private void setComponentShapeAndBackground(Component component, int index) {
        // Set corners and background
        ComponentContainer.LayoutConfig layoutConfig = component.getLayoutConfig();
        Element element = component.getBackgroundElement();
        if (!(element instanceof ShapeElement)) {
            return;
        }

        ShapeElement shapeElement = (ShapeElement) element;
        boolean isCategoryFirstItem = hasHead() ? (index == 1) : (index == 0);
        boolean isCategoryLastItem = index == (categoryItems.size() - 1);
        if (isCategoryFirstItem && isCategoryLastItem) {
            // only one item in category, set all corner radius and bottom margin 12vp
            shapeElement.setCornerRadiiArray(new float[]{CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS,
                    CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS});
            Component componentItemDivider = component.findComponentById(ResourceTable.Id_item_divider);
            componentItemDivider.setVisibility(Component.HIDE);
        } else if (isCategoryFirstItem) {
            // first item in category, set top two corners radius
            shapeElement.setCornerRadiiArray(new float[]{CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS,
                    0, 0, 0, 0});
        } else if (isCategoryLastItem) {
            // last Item in category, set down two corners radius and bottom margin 12vp
            shapeElement.setCornerRadiiArray(new float[]{0, 0, 0, 0, CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS,
                    CORNER_RADIUS});
            Component componentItemDivider = component.findComponentById(ResourceTable.Id_item_divider);
            componentItemDivider.setVisibility(Component.HIDE);
        } else {
            // middle item in category, set no corners radius and show divider
            shapeElement.setCornerRadiiArray(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
            Component componentItemDivider = component.findComponentById(ResourceTable.Id_item_divider);
            componentItemDivider.setVisibility(Component.VISIBLE);
        }

        component.setLayoutConfig(layoutConfig);
        component.setBackground(shapeElement);
    }

    /**
     * Get total category items count
     *
     * @return count of items
     */
    @Override
    public int getCategoryItemsCount() {
        return categoryItems.size();
    }

    /**
     * Get the type of category item in specified index
     *
     * @param index category item index
     * @return category type
     */
    @Override
    public int getCategoryItemType(int index) {
        return categoryItems.get(index).getItemType();
    }

    /**
     * Whether the category has a head
     *
     * @return boolean
     */
    @Override
    public boolean hasHead() {
        return categoryItems.get(0).getItemType() == HeadItem.ITEM_TYPE;
    }

    /**
     * Create component for category item in specified index
     *
     * @param index category item index
     * @return Component
     */
    @Override
    public Component createComponent(int index) {
        CategoryItemBase categoryItem = categoryItems.get(index);
        return categoryItem.createComponent();
    }

    /**
     * Bind component for category item in specified index
     *
     * @param component component to bind
     * @param index     category item index
     */
    @Override
    public void bindComponent(Component component, int index) {
        CategoryItemBase categoryItem = categoryItems.get(index);
        categoryItem.bindComponent(component);

        if (categoryItem.getItemType() != HeadItem.ITEM_TYPE) {
            setComponentShapeAndBackground(component, index);
        }
    }

    /**
     * Callback if the item is clicked
     *
     * @param position position of the item in this category
     */
    @Override
    public void onItemClick(int position) {
        if (categoryItems.get(position).getItemType() == SingleListItem.ITEM_TYPE) {
            ToastDialog toast = new ToastDialog(context);
            toast.setText("Clicking Category " + categoryId + " item " + position).setDuration(TOAST_DURATION).show();
        }
    }
}
