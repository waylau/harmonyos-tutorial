package com.waylau.hmos.douyin.player.view;

import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.bundle.AbilityInfo;

/**
 * Interface for Component Adapter.
 */
public interface IBaseComponentAdapter {
    /**
     * Component initialization.
     *
     * @return controller component.
     */
    Component initComponent();

    /**
     * Click event.
     *
     * @param component component be clicked.
     */
    void onClick(Component component);

    /**
     * Set layout config by yourself.
     *
     * @return The layout config you want.if return null,the template config is used.
     */
    DirectionalLayout.LayoutConfig initLayoutConfig();

    /**
     * Perceive the orientation changes of the screen.
     *
     * <p>This interface is invoked once when a component is added.
     *
     * <p>When the ability receives a callback, it will be invoked again.
     *
     * @param displayOrientation Enumerates ability display orientations{@link AbilityInfo.DisplayOrientation}.
     * @param from               Start position when component moves.
     * @param to                 End position when component moves.
     */
    void onOrientationChanged(
            AbilityInfo.DisplayOrientation displayOrientation, ComponentContainer from, ComponentContainer to);

    /**
     * This method is invoked when video resources are switched.
     */
    void onVideoSourceChanged();
}
