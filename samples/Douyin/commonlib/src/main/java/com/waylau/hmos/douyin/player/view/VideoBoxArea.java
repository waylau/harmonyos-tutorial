package com.waylau.hmos.douyin.player.view;

/**
 * VideoBox Area Enum.
 */
public enum VideoBoxArea {
    /**
     * Top area.
     */
    TOP("top"),

    /**
     * Bottom area.
     */
    BOTTOM("bottom"),

    /**
     * above bottom area
     */
    ABOVE_BOTTOM("above_bottom"),

    /**
     * Left side area.
     */
    LEFT("left"),

    /**
     * Right side area.
     */
    RIGHT("right");

    /**
     * Area name.
     */
    String areaName;

    VideoBoxArea(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }
}
