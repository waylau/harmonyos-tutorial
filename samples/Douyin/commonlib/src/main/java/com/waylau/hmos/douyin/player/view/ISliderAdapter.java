package com.waylau.hmos.douyin.player.view;

import ohos.agp.components.Slider;

/**
 * Interface of Slider Adapter.
 */
public interface ISliderAdapter extends IBaseComponentAdapter {
    /**
     * pass slider value when status changed.
     *
     * @param listener value change listener.
     */
    void onValueChanged(Slider.ValueChangedListener listener);

    /**
     * When the multimedia progress changes, passively (not the callback of the user dragging the progress bar)
     * update the progress and current time
     *
     * @param currentTime current playing position of video,unit:milliseconds.
     * @param progress    the slider progress.
     */
    void onMediaProgressChanged(long currentTime, int progress);

    /**
     * Called when the buffering percentage updates.
     *
     * @param percent Indicates the media stream buffering percentage. The value ranges from 0 to 100.
     */
    void onBufferProgressChanged(int percent);

    /**
     * media end time initialization.
     *
     * @param endTime media end time.
     */
    void initMediaEndTime(long endTime);

    /**
     * When the next video is played, the end time is automatically removed.
     */
    void clearEndTime();
}
