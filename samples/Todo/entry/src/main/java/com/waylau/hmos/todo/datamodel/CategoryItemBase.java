package com.waylau.hmos.todo.datamodel;

import ohos.agp.components.Component;

/**
 * Category item base interface, implement this interface to create a new kind of category item
 */
public interface CategoryItemBase {
    /**
     * Bind data to component item
     *
     * @param component component item in list container
     */
    void bindComponent(Component component);

    /**
     * Create component
     *
     * @return component
     */
    Component createComponent();

    /**
     * Get the item type of this category item
     *
     * @return category item type
     */
    int getItemType();
}

