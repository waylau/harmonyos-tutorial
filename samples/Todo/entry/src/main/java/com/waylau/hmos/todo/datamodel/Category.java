package com.waylau.hmos.todo.datamodel;

import ohos.agp.components.Component;

/**
 * Category interface, implement this interface to create a new kind of category
 */
public interface Category {
    /**
     * Get total category items count
     *
     * @return count of items
     */
    int getCategoryItemsCount();

    /**
     * Get the type of category item in specified index
     *
     * @param index category item index
     * @return category type
     */
    int getCategoryItemType(int index);

    /**
     * Whether the category has a head
     *
     * @return boolean
     */
    boolean hasHead();

    /**
     * Create component for category item in specified index
     *
     * @param index category item index
     * @return the created component
     */
    Component createComponent(int index);

    /**
     * Bind component for category item in specified index
     *
     * @param component component to bind
     * @param index     category item index
     */
    void bindComponent(Component component, int index);

    /**
     * Callback if the item is clicked
     *
     * @param position position of the item in this category
     */
    void onItemClick(int position);
}
