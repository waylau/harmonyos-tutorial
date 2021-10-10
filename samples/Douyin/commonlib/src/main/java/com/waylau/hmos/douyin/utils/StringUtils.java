package com.waylau.hmos.douyin.utils;

/**
 * String utils
 */
public class StringUtils {
    /**
     * Checks whether string is empty.
     *
     * @param cs string
     * @return Returns true if string is empty; returns false otherwise.
     */
    public static boolean isEmpty(CharSequence cs) {
        return (cs == null) || (cs.length() == 0);
    }
}
