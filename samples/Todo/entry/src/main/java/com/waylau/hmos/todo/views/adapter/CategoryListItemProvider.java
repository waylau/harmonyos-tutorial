package com.waylau.hmos.todo.views.adapter;

import com.waylau.hmos.todo.custom.categorylist.CategoryItemFactory;
import com.waylau.hmos.todo.datamodel.Category;

import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.ListContainer;
import ohos.agp.components.RecycleItemProvider;

import java.util.List;

/**
 * Category list item provider.
 * You can create different styles of items easily by implement the Category interface. CategoryListItemProvider
 * will calculate the category index of each position and add the item created by Category interface into list
 * container. CategoryItemBase interface use to declare different styles of items in Category.
 */
public class CategoryListItemProvider extends RecycleItemProvider implements ListContainer.ItemClickedListener {
    private static final int ERROR_CATEGORY_INDEX = -1;
    private static final int ERROR_CATEGORY_ITEM_INDEX = -1;
    private static final int ERROR_COMPONENT_TYPE = -1;

    private List<Category> categoryList;

    /**
     * Category list item provider constructor
     *
     * @param categoryList data list
     */
    public CategoryListItemProvider(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (categoryList != null) {
            for (Category category : categoryList) {
                count += category.getCategoryItemsCount();
            }
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemComponentType(int position) {
        int categoryIndex = getCategoryIndex(position);
        int categoryItemIndex = getCategoryItemIndex(position);
        if (categoryIndex == ERROR_CATEGORY_INDEX || categoryItemIndex == ERROR_CATEGORY_ITEM_INDEX) {
            return ERROR_COMPONENT_TYPE;
        }

        return categoryList.get(categoryIndex).getCategoryItemType(categoryItemIndex);
    }

    @Override
    public int getComponentTypeCount() {
        return CategoryItemFactory.TOTAL_ITEM_TYPE;
    }

    private int getCategoryIndex(int position) {
        if (categoryList == null || position < 0 || position >= getCount()) {
            return ERROR_CATEGORY_INDEX;
        }

        int categoryIndex = 0;
        int categoryStartPosition = 0;
        for (Category category : categoryList) {
            if (position - categoryStartPosition < category.getCategoryItemsCount()) {
                return categoryIndex;
            }
            categoryStartPosition += category.getCategoryItemsCount();
            categoryIndex++;
        }

        return ERROR_CATEGORY_INDEX;
    }

    private int getCategoryItemIndex(int position) {
        if (categoryList == null || position < 0 || position >= getCount()) {
            return ERROR_CATEGORY_ITEM_INDEX;
        }

        int categoryStartPosition = 0;
        for (Category category : categoryList) {
            int categoryItemIdx = position - categoryStartPosition;
            if (categoryItemIdx < category.getCategoryItemsCount()) {
                return categoryItemIdx;
            }
            categoryStartPosition += category.getCategoryItemsCount();
        }

        return ERROR_CATEGORY_ITEM_INDEX;
    }

    @Override
    public Component getComponent(int position, Component component, ComponentContainer componentContainer) {
        int categoryIndex = getCategoryIndex(position);
        int categoryItemIndex = getCategoryItemIndex(position);

        if (categoryIndex == ERROR_CATEGORY_INDEX || categoryItemIndex == ERROR_CATEGORY_ITEM_INDEX) {
            return null;
        }

        Category category = categoryList.get(categoryIndex);
        if (component == null) {
            Component newComponent = category.createComponent(categoryItemIndex);
            category.bindComponent(newComponent, categoryItemIndex);
            return newComponent;
        } else {
            category.bindComponent(component, categoryItemIndex);
            return component;
        }
    }

    private void onClickCategoryItem(int position) {
        int categoryIdx = getCategoryIndex(position);
        int categoryItemIdx = getCategoryItemIndex(position);
        Category category = categoryList.get(categoryIdx);
        category.onItemClick(categoryItemIdx);
    }

    @Override
    public void onItemClicked(ListContainer listContainer, Component component, int position, long id) {
        onClickCategoryItem(position);
    }
}

