package com.waylau.hmos.douyin.player.view;

import ohos.agp.components.element.Element;

/**
 * Interface for PlaybackButtonAdapter.
 */
public interface IPlaybackButtonAdapter extends IBaseComponentAdapter, IPlayStatusListener {
    /**
     * Element of PlaybackButton.
     *
     * @return playback Element.
     */
    Element getPlaybackElement();

    /**
     * Element of Pause Button.
     *
     * @return pause Element.
     */
    Element getPauseElement();
}
