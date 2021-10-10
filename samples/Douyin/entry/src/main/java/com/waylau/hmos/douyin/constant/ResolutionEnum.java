package com.waylau.hmos.douyin.constant;

import com.waylau.hmos.douyin.ResourceTable;

/**
 * Used to parses entry\src\main\resources\rawfile\videos.json
 */
public enum ResolutionEnum {
    AUTO(
            0,
            ResourceTable.String_control_episodes_auto,
            ResourceTable.String_short_name_control_episodes_auto),

    SD(
            1,
            ResourceTable.String_control_episodes_standard_quality,
            ResourceTable.String_short_name_control_episodes_standard_quality),

    HD(
            2,
            ResourceTable.String_control_episodes_high_quality,
            ResourceTable.String_short_name_control_episodes_high_quality),

    FHD(
            3,
            ResourceTable.String_control_episodes_full_high_quality,
            ResourceTable.String_short_name_control_episodes_full_high_quality),

    HDR(
            4,
            ResourceTable.String_control_episodes_hdr,
            ResourceTable.String_short_name_control_episodes_hdr);

    private int code;

    private int name;

    private int shortName;

    ResolutionEnum(int code, int name, int shortName) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getShortName() {
        return shortName;
    }

    public void setShortName(int shortName) {
        this.shortName = shortName;
    }
}
