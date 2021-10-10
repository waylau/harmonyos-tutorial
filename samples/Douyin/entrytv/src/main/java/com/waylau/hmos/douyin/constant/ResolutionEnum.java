package com.waylau.hmos.douyin.constant;

/**
 * Resolution Enumeration Class
 */
public enum ResolutionEnum {
    RESOLUTION_AUTO("自动", 1),
    RESOLUTION_HDR("HDR", 2),
    RESOLUTION_4K("4K", 3),
    RESOLUTION_2K("2K", 4),
    RESOLUTION_BD("蓝光", 5),
    RESOLUTION_HD("清晰", 6);

    private String name;
    private int value;

    ResolutionEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Obtain the resolution name.
     *
     * @return name name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the resolution Name.
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
}
