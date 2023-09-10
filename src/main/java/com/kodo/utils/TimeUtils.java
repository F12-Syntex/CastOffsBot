package com.kodo.utils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * This class is used to format time
 */
public class TimeUtils {

    /**
     * Formats a duration in seconds to a human readable format
     * @param seconds The duration in seconds
     * @return The formatted duration
     */
    public static String formatDuration(long seconds) {
        if (seconds == 0) {
            return "now";
        } else {
            long years = TimeUnit.SECONDS.toDays(seconds) / 365;
            long days = TimeUnit.SECONDS.toDays(seconds) % 365;
            long hours = TimeUnit.SECONDS.toHours(seconds) % 24;
            long minutes = TimeUnit.SECONDS.toMinutes(seconds) % 60;
            long remainingSeconds = seconds % 60;

            return Arrays.stream(
                    new String[]{
                            formatTime("year", years),
                            formatTime("day", days),
                            formatTime("hour", hours),
                            formatTime("minute", minutes),
                            formatTime("second", remainingSeconds)})
                    .filter(e -> !e.isEmpty())
                    .collect(Collectors.joining(", "))
                    .replaceAll(", (?!.+,)", " and ");
        }
    }

    /**
     * Formats a time to a human readable format
     * @param s The string to append to the time
     * @param time The time
     * @return The formatted time
     */
    public static String formatTime(String s, long time){
        return time == 0 ? "" : time + " " + s + (time == 1 ? "" : "s");
    }

}
