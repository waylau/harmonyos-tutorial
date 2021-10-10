package com.waylau.hmos.douyin.components;

import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.utils.AppUtil;
import com.waylau.hmos.douyin.player.view.ISliderAdapter;
import com.waylau.hmos.douyin.utils.ElementUtils;
import com.waylau.hmos.douyin.utils.StringUtils;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DependentLayout;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Slider;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.LayoutAlignment;
import ohos.app.Context;
import ohos.bundle.AbilityInfo;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    /**
     * Index of the left margin
     */
    public static final int LEFT_MARGIN_INDEX = 0;
    /**
     * Index of the top margin
     */
    public static final int TOP_MARGIN_INDEX = 1;
    /**
     * Index of the right margin
     */
    public static final int RIGHT_MARGIN_INDEX = 2;
    /**
     * Index of the right margin
     */
    public static final int BOTTOM_MARGIN_INDEX = 3;

    /**
     * Default zone.
     */
    public static final String DEFAULT_ZONE = "GMT+00:00";

    private static final HiLogLabel LABEL = new HiLogLabel(0, 0, "VideoBoxSeekBar");

    private final Component mParentComponent;
    private final Slider seekBar;
    private final Text currentTimeText;
    private final Text endTimeText;
    private final Component pleaseHoldComp;

    private Date date;
    private SimpleDateFormat dateFormat;
    /**
     * Time format. The hour is not hidden based on the media resource duration.
     */
    private String pattern = "";
    /**
     * Indicates the end timestamp, in milliseconds.
     */
    private long endTime;
    /**
     * Record margins value,different margins for landscape and portrait.
     */
    private int[] lastMargins;

    public VideoPlayerSlider(Context context) {
        this(context, null);
    }

    public VideoPlayerSlider(Context context, AttrSet attrSet) {
        this(context, attrSet, null);
    }

    public VideoPlayerSlider(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        mParentComponent =
                LayoutScatter.getInstance(context)
                        .parse(ResourceTable.Layout_hm_sample_view_video_box_seek_bar_style1, this, true);
        seekBar = (Slider) mParentComponent.findComponentById(ResourceTable.Id_seek_bar);
        currentTimeText = (Text) mParentComponent.findComponentById(ResourceTable.Id_current_time);
        endTimeText = (Text) mParentComponent.findComponentById(ResourceTable.Id_end_time);
        pleaseHoldComp = new Component(context);
        DirectionalLayout.LayoutConfig config =
                new DirectionalLayout.LayoutConfig(
                        ComponentContainer.LayoutConfig.MATCH_PARENT,
                        (int) AppUtil.getFloatResource(context, ResourceTable.Float_seek_bar_height),
                        LayoutAlignment.VERTICAL_CENTER,
                        1);
        pleaseHoldComp.setLayoutConfig(config);
    }

    @Override
    public Component initComponent() {
        seekBar.setMinValue(MIN_VALUE);
        seekBar.setMaxValue(MAX_VALUE);
        currentTimeText.setText(AppUtil.getStringResource(getContext(), ResourceTable.String_progress_start_time));
        return mParentComponent;
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
                                HiLog.error(
                                        LABEL, "Invalid currentTime = " + currentTime + " in onMediaProgressChange()");
                            }
                        });
    }

    @Override
    public void onBufferProgressChanged(int percent) {
        getContext()
                .getUITaskDispatcher()
                .asyncDispatch(
                        () -> {
                            seekBar.setViceProgress(percent);
                        });
    }

    @Override
    public void initMediaEndTime(long endTime) {
        HiLog.debug(LABEL, "Slider end time = " + endTime);
        this.endTime = endTime;
        setPattern(endTime);

        // Change pattern when video reload
        dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DEFAULT_ZONE));
        if (date == null) {
            date = new Date();
        }

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
            // Checks whether pattern is empty.
            if (StringUtils.isEmpty(pattern)) {
                setPattern(ms);
            }
            dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setTimeZone(TimeZone.getTimeZone(DEFAULT_ZONE));
            date = new Date();
        }
        date.setTime(ms);
        return dateFormat.format(date);
    }

    @Override
    public void onClick(Component component) {
    }

    @Override
    public DirectionalLayout.LayoutConfig initLayoutConfig() {
        return new DirectionalLayout.LayoutConfig(
                ComponentContainer.LayoutConfig.MATCH_PARENT,
                ComponentContainer.LayoutConfig.MATCH_CONTENT,
                LayoutAlignment.VERTICAL_CENTER,
                1);
    }

    @Override
    public void onOrientationChanged(
            AbilityInfo.DisplayOrientation displayOrientation,
            ComponentContainer bottomArea,
            ComponentContainer aboveBottomArea) {
        if (lastMargins == null) {
            lastMargins = getMargins();
            HiLog.debug(LABEL, "VideoPlayerSlider margin = " + Arrays.toString(lastMargins));
        }
        DependentLayout.LayoutConfig currentTimeConfig =
                new LayoutConfig(
                        ComponentContainer.LayoutConfig.MATCH_CONTENT, ComponentContainer.LayoutConfig.MATCH_CONTENT);
        LayoutConfig endTimeConfig =
                new LayoutConfig(
                        ComponentContainer.LayoutConfig.MATCH_CONTENT, ComponentContainer.LayoutConfig.MATCH_CONTENT);
        DependentLayout.LayoutConfig seekBarConfig =
                new LayoutConfig(
                        ComponentContainer.LayoutConfig.MATCH_PARENT, ComponentContainer.LayoutConfig.MATCH_CONTENT);
        int childIndex;
        switch (displayOrientation) {
            case LANDSCAPE:
                // Clear the margin attribute in landscape mode.
                setMarginsLeftAndRight(0, 0);
                setMarginsTopAndBottom(0, 0);

                currentTimeConfig.addRule(LayoutConfig.CENTER_IN_PARENT);
                currentTimeConfig.addRule(LayoutConfig.ALIGN_PARENT_START);
                currentTimeConfig.setMarginRight(18);
                currentTimeText.setLayoutConfig(currentTimeConfig);

                endTimeConfig.addRule(LayoutConfig.CENTER_IN_PARENT);
                endTimeConfig.addRule(LayoutConfig.ALIGN_PARENT_END);
                endTimeConfig.setMarginLeft(18);
                endTimeText.setLayoutConfig(endTimeConfig);

                seekBarConfig.addRule(LayoutConfig.CENTER_IN_PARENT);
                seekBarConfig.addRule(LayoutConfig.END_OF, ResourceTable.Id_current_time);
                seekBarConfig.addRule(LayoutConfig.START_OF, ResourceTable.Id_end_time);
                seekBar.setLayoutConfig(seekBarConfig);

                bottomArea.setBackground(
                        ElementUtils.getGradientColor(0x00000080, 0x00000040, ShapeElement.Orientation.BOTTOM_TO_TOP));
                aboveBottomArea.setBackground(
                        ElementUtils.getGradientColor(0x00000040, 0x00000000, ShapeElement.Orientation.BOTTOM_TO_TOP));

                childIndex = bottomArea.getChildIndex(this);

                bottomArea.removeComponent(this);

                if (bottomArea.getChildIndex(pleaseHoldComp) == -1) {
                    bottomArea.addComponent(pleaseHoldComp, childIndex);
                }

                if (aboveBottomArea.getChildIndex(this) == -1) {
                    aboveBottomArea.addComponent(this);
                }

                if (aboveBottomArea.getChildCount() == 0) {
                    aboveBottomArea.setVisibility(Component.HIDE);
                } else {
                    aboveBottomArea.setVisibility(Component.VISIBLE);
                }
                break;
            default:
                currentTimeConfig.addRule(LayoutConfig.ALIGN_PARENT_START);
                currentTimeConfig.addRule(LayoutConfig.ALIGN_PARENT_BOTTOM);
                currentTimeConfig.setMarginLeft(36);
                currentTimeText.setLayoutConfig(currentTimeConfig);

                endTimeConfig.addRule(LayoutConfig.ALIGN_PARENT_END);
                endTimeConfig.addRule(LayoutConfig.ALIGN_PARENT_BOTTOM);
                endTimeConfig.setMarginRight(36);
                endTimeText.setLayoutConfig(endTimeConfig);

                seekBarConfig.addRule(LayoutConfig.CENTER_IN_PARENT);
                seekBar.setLayoutConfig(seekBarConfig);

                childIndex = bottomArea.getChildIndex(pleaseHoldComp);

                aboveBottomArea.removeComponent(this);
                if (aboveBottomArea.getChildCount() == 0) {
                    aboveBottomArea.setVisibility(Component.HIDE);
                    bottomArea.setBackground(
                            ElementUtils.getGradientColor(
                                    0x00000080, 0x00000000, ShapeElement.Orientation.BOTTOM_TO_TOP));
                } else {
                    aboveBottomArea.setVisibility(Component.VISIBLE);
                    bottomArea.setBackground(
                            ElementUtils.getGradientColor(
                                    0x00000080, 0x00000040, ShapeElement.Orientation.BOTTOM_TO_TOP));
                }

                // Do not add this again.
                if (bottomArea.getChildIndex(this) == -1) {
                    bottomArea.removeComponent(pleaseHoldComp);
                    bottomArea.addComponent(this, childIndex, initLayoutConfig());
                }
                // Restore the margin attribute in landscape mode.
                setMarginsLeftAndRight(lastMargins[LEFT_MARGIN_INDEX], lastMargins[RIGHT_MARGIN_INDEX]);
                setMarginsTopAndBottom(lastMargins[TOP_MARGIN_INDEX], lastMargins[BOTTOM_MARGIN_INDEX]);
                break;
        }
    }

    @Override
    public void onVideoSourceChanged() {
    }
}
