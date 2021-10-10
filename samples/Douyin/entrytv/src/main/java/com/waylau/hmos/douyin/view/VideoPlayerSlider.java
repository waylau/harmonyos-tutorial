package com.waylau.hmos.douyin.view;

import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.player.view.ISliderAdapter;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DependentLayout;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Slider;
import ohos.agp.components.Text;
import ohos.app.Context;
import ohos.bundle.AbilityInfo;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * VideoPlayerView slider
 */

public class VideoPlayerSlider extends DependentLayout implements ISliderAdapter {
    /**
     * Slider max value
     */
    public static final int MAX_VALUE = 100;
    /**
     * Slider min value
     */
    public static final int MIN_VALUE = 0;
    /**
     * Invalid value. The time or progress accepts this value and does not refresh.
     */
    public static final int INVALID_VALUE = -1;

    private static final HiLogLabel LABEL = new HiLogLabel(0, 0, "VideoPlayerSlider");
    private final Component parentComponent;
    private final Slider seekBar;
    private final Text currentTimeText;
    private final Text endTimeText;
    private Date date;
    private SimpleDateFormat dateFormat;
    /**
     * Time format. The hour is not hidden based on the media resource duration.
     */
    private String pattern;
    /**
     * Indicates the end timestamp, in milliseconds.
     */
    private long endTime;

    public VideoPlayerSlider(Context context) {
        this(context, null);
    }

    public VideoPlayerSlider(Context context, AttrSet attrSet) {
        this(context, attrSet, null);
    }

    public VideoPlayerSlider(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        parentComponent =
                LayoutScatter.getInstance(context)
                        .parse(ResourceTable.Layout_view_video_box_seek_bar_style1, this, true);
        seekBar = (Slider) parentComponent.findComponentById(ResourceTable.Id_seek_bar);
        currentTimeText = (Text) parentComponent.findComponentById(ResourceTable.Id_current_time);
        endTimeText = (Text) parentComponent.findComponentById(ResourceTable.Id_end_time);
    }

    @Override
    public Component initComponent() {
        seekBar.setMinValue(MIN_VALUE);
        seekBar.setMaxValue(MAX_VALUE);
        currentTimeText.setText("00:00");
        return parentComponent;
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
            AbilityInfo.DisplayOrientation displayOrientation,
            ComponentContainer fromLayout,
            ComponentContainer toLayout) {
    }

    @Override
    public void onVideoSourceChanged() {
    }

    @Override
    public void onValueChanged(Slider.ValueChangedListener listener) {
        seekBar.setValueChangedListener(listener);
    }

    @Override
    public synchronized void onMediaProgressChanged(long currentTime, int progress) {
        getContext()
                .getUITaskDispatcher()
                .asyncDispatch(
                        () -> {
                            if (progress >= MIN_VALUE && progress <= MAX_VALUE) {
                                seekBar.setProgressValue(progress);
                            } else if (progress == INVALID_VALUE) {
                                HiLog.debug(LABEL, "Not refresh progress");
                            } else {
                                HiLog.error(LABEL, "Invalid progress = " + progress + " in onMediaProgressChange()");
                            }

                            if (currentTime >= MIN_VALUE && currentTime <= endTime) {
                                currentTimeText.setText(ms2TimeString(currentTime));
                            } else if (currentTime == INVALID_VALUE) {
                                HiLog.debug(LABEL, "Not refresh current time");
                            } else {
                                HiLog.error(LABEL, "Invalid currentTime = " + currentTime
                                        + " in onMediaProgressChange()");
                            }
                        });
    }

    @Override
    public void onBufferProgressChanged(int percent) {
        getContext().getUITaskDispatcher().asyncDispatch(() -> {
            seekBar.setViceProgress(percent);
        });
    }

    @Override
    public void initMediaEndTime(long endTime) {
        this.endTime = endTime;
        setPattern(endTime);
        getContext().getUITaskDispatcher().asyncDispatch(() -> endTimeText.setText(ms2TimeString(endTime)));
    }

    @Override
    public void clearEndTime() {
        endTimeText.setText("");
    }

    /**
     * Sets the pattern of the formatter to determine whether to display hour based on the media file duration.
     *
     * @param endTime Media file duration
     */
    private void setPattern(long endTime) {
        if (endTime > 60 * 60 * 1000 - 1) {
            pattern = "HH:mm:ss";
        } else {
            pattern = "mm:ss";
        }
    }

    /**
     * Millisecond timestamp-to-time character string
     *
     * @param ms millisecond timestamp
     * @return Time string
     */
    private String ms2TimeString(long ms) {
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
            date = new Date();
        }
        date.setTime(ms);
        return dateFormat.format(date);
    }
}
