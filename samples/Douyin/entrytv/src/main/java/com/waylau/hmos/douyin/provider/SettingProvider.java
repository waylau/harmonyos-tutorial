package com.waylau.hmos.douyin.provider;

import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;

/**
 * Setting List Processing Class
 */
public class SettingProvider extends BaseItemProvider {
    /**
     * Page Slice
     */
    protected AbilitySlice slice;
    /**
     * Parent Component
     */
    protected int parentType;
    /**
     * Key Listening Event
     */
    protected Component.KeyEventListener itemOnKeyListener;

    public SettingProvider(
            AbilitySlice slice, int parentType, Component.KeyEventListener itemOnKeyListener) {
        this.slice = slice;
        this.parentType = parentType;
        this.itemOnKeyListener = itemOnKeyListener;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int index) {
        return index;
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public Component getComponent(int index, Component component, ComponentContainer componentContainer) {
        return null;
    }

    /**
     * Clear All Subcomponents
     */
    public void removeAllItem() {
    }
}
