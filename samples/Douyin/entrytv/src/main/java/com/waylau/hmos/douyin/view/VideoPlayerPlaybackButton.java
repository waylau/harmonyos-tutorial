package com.waylau.hmos.douyin.view;

import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.player.core.PlayerStatus;
import com.waylau.hmos.douyin.player.view.IPlaybackButtonAdapter;
import com.waylau.hmos.douyin.utils.ElementUtils;

import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.element.Element;
import ohos.app.Context;
import ohos.bundle.AbilityInfo;

/**
 * VideoPlayerView playback button
 */
public class VideoPlayerPlaybackButton extends Component implements IPlaybackButtonAdapter {
    public VideoPlayerPlaybackButton(Context context) {
        super(context);
    }

    @Override
    public Element getPlaybackElement() {
        return ElementUtils.getElementByResId(getContext(), ResourceTable.Media_ic_circular_play);
    }

    @Override
    public Element getPauseElement() {
        return ElementUtils.getElementByResId(getContext(), ResourceTable.Media_ic_circular_pause);
    }

    @Override
    public Component initComponent() {
        setBackground(getPlaybackElement());
        return this;
    }

    @Override
    public void onClick(Component component) {
    }

    @Override
    public DirectionalLayout.LayoutConfig initLayoutConfig() {
        return null;
    }

    @Override
    public void onOrientationChanged(
            AbilityInfo.DisplayOrientation displayOrientation, ComponentContainer from, ComponentContainer to) {
    }

    @Override
    public void onVideoSourceChanged() {
    }

    @Override
    public void onPlayStatusChange(PlayerStatus status) {
        switch (status) {
            case COMPLETE:
            case PAUSE:
            case STOP:
            case PREPARING:
            case BUFFERING:
            case PREPARED:
            case ERROR:
            case IDLE:
                setBackground(getPlaybackElement());
                break;
            case PLAY:
                setBackground(getPauseElement());
                break;
        }
    }
}
