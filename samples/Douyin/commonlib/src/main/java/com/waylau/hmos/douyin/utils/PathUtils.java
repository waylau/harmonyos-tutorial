package com.waylau.hmos.douyin.utils;

/**
 * Path utils
 */
public class PathUtils {
    /**
     * URI identifier.
     * Structure:[scheme:][//authority][path][?query][#fragment]
     */
    public static final String FLAG_URI = "://";

    /**
     * Check whether the path is a URI.
     *
     * @param path path
     * @return Returns true if the path is URI; Returns false otherwise.
     */
    public static boolean isUri(String path) {
        return path.contains(FLAG_URI);
    }
}
