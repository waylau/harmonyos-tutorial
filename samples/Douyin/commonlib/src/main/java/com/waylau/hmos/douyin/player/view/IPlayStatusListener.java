package com.waylau.hmos.douyin.player.view;

import com.waylau.hmos.douyin.player.core.PlayerStatus;

/**
 * Playback status listener.
 */
public interface IPlayStatusListener {
    /**
     * Update of playback status.
     *
     * @param status the next playback status.
     */
    void onPlayStatusChange(PlayerStatus status);
}
