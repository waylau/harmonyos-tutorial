/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.util;

import java.util.Locale;

/**
 * Date util
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class DateUtil {
    private static final int ONE_SECONDS_MS = 1000;
    private static final int ONE_MINS_MINUTES = 60;
    private static final int NUMBER = 16;
    private static final String TIME_FORMAT = "%02d";
    private static final String SEMICOLON = ":";

    private DateUtil() {
    }

    /**
     * conversion of msToString
     *
     * @param ms ms
     * @return string
     */
    public static String msToString(int ms) {
        StringBuilder sb = new StringBuilder(NUMBER);
        int seconds = ms / ONE_SECONDS_MS;
        int minutes = seconds / ONE_MINS_MINUTES;
        if (minutes > ONE_MINS_MINUTES) {
            sb.append(String.format(Locale.ENGLISH, TIME_FORMAT, minutes / ONE_MINS_MINUTES));
            sb.append(SEMICOLON);
            sb.append(String.format(Locale.ENGLISH, TIME_FORMAT, minutes % ONE_MINS_MINUTES));
            sb.append(SEMICOLON);
        } else {
            sb.append("00:");
            sb.append(String.format(Locale.ENGLISH, TIME_FORMAT, minutes));
            sb.append(SEMICOLON);
        }

        if (seconds > minutes * ONE_MINS_MINUTES) {
            sb.append(String.format(Locale.ENGLISH, TIME_FORMAT, seconds - minutes * ONE_MINS_MINUTES));
        } else {
            sb.append("00");
        }
        return sb.toString();
    }
}
