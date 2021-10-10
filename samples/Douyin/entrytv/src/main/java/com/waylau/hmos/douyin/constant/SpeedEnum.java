package com.waylau.hmos.douyin.constant;

/**
 * Rate enumeration class
 */
public enum SpeedEnum {
    SPEED_1("1x", 1),
    SPEED_2("1.5x", 1.5f),
    SPEED_3("2x", 2),
    SPEED_4("2.5x", 2.5f),
    SPEED_5("3x", 3);

    private String name;
    private float value;

    SpeedEnum(String name, float value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Get the rate name.
     *
     * @return name name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the rate name.
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the rate value.
     *
     * @return value value
     */
    public float getValue() {
        return value;
    }

    /**
     * Set the rate value.
     *
     * @param value value
     */
    public void setValue(float value) {
        this.value = value;
    }
}
