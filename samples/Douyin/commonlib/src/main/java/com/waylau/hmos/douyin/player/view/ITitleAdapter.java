package com.waylau.hmos.douyin.player.view;

import ohos.agp.components.Text;

/**
 * This interface is required for adding a title component.
 */
public interface ITitleAdapter extends IBaseComponentAdapter {
    /**
     * Must return "Text Component"，because the setText() is used
     *
     * @return Text component
     */
    @Override
    Text initComponent();

    /**
     * Update the title when the video resource changes.
     *
     * @param str title name
     */
    void onTitleChange(String str);
}
