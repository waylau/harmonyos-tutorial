/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.constant;

/**
 * GestureConst
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class GestureConstants {
    private static final int DEFAULT_LONG_PRESS_TIMEOUT = 500;

    private static final int TOUCH_SLOP = 64;

    private static final int TAP_TIMEOUT = 100;

    private static final int DOUBLE_TAP_TIMEOUT = 300;

    private static final int DOUBLE_TAP_MIN_TIME = 40;

    private static final int DOUBLE_TAP_SLOP = 100;

    private static final int MINIMUM_FLING_VELOCITY = 50;

    private static final int MAXIMUM_FLING_VELOCITY = 8000;

    private static final int DOUBLE_TAP_TOUCH_SLOP = TOUCH_SLOP;

    private static final float AMBIGUOUS_GESTURE_MULTIPLIER = 2f;

    private GestureConstants() {
    }

    /**
     * getDoubleTapTimeout
     *
     * @return int
     */
    public static int getDoubleTapTimeout() {
        return DOUBLE_TAP_TIMEOUT;
    }

    /**
     * getDoubleTapMinTime
     *
     * @return int
     */
    public static int getDoubleTapMinTime() {
        return DOUBLE_TAP_MIN_TIME;
    }

    /**
     * getTouchSlop
     *
     * @return int
     */
    public static int getTouchSlop() {
        return TOUCH_SLOP;
    }

    /**
     * getLongPressTimeout
     *
     * @return int
     */
    public static int getLongPressTimeout() {
        return DEFAULT_LONG_PRESS_TIMEOUT;
    }

    /**
     * getTapTimeout
     *
     * @return int
     */
    public static int getTapTimeout() {
        return TAP_TIMEOUT;
    }

    /**
     * getDoubleTapSlop
     *
     * @return int
     */
    public static int getDoubleTapSlop() {
        return DOUBLE_TAP_SLOP;
    }

    /**
     * getMinimumFlingVelocity
     *
     * @return int
     */
    public static int getMinimumFlingVelocity() {
        return MINIMUM_FLING_VELOCITY;
    }

    /**
     * getMaximumFlingVelocity
     *
     * @return int
     */
    public static int getMaximumFlingVelocity() {
        return MAXIMUM_FLING_VELOCITY;
    }

    /**
     * getScaledTouchSlop
     *
     * @return int
     */
    public static int getScaledTouchSlop() {
        return TOUCH_SLOP;
    }

    /**
     * getScaledDoubleTapTouchSlop
     *
     * @return int
     */
    public static int getScaledDoubleTapTouchSlop() {
        return DOUBLE_TAP_TOUCH_SLOP;
    }

    /**
     * getScaledDoubleTapSlop
     *
     * @return int
     */
    public static int getScaledDoubleTapSlop() {
        return DOUBLE_TAP_SLOP;
    }

    /**
     * getScaledMinimumFlingVelocity
     *
     * @return int
     */
    public static int getScaledMinimumFlingVelocity() {
        return MINIMUM_FLING_VELOCITY;
    }

    /**
     * getScaledMaximumFlingVelocity
     *
     * @return int
     */
    public static int getScaledMaximumFlingVelocity() {
        return MAXIMUM_FLING_VELOCITY;
    }

    /**
     * getAmbiguousGestureMultiplier
     *
     * @return int
     */
    public static float getAmbiguousGestureMultiplier() {
        return AMBIGUOUS_GESTURE_MULTIPLIER;
    }
}
