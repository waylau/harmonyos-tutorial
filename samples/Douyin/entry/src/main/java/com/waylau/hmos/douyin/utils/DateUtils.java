package com.waylau.hmos.douyin.utils;

import java.util.Locale;

/**
 * Date utils
 */
public class DateUtils {
    private static final int ONE_SECONDS_MS = 1000;
    private static final int ONE_MINS_MINUTES = 60;
    private static final int NUMBER = 16;
    private static final String TIME_FORMAT = "%02d";
    private static final String SEMICOLON = ":";

    private DateUtils() {
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
