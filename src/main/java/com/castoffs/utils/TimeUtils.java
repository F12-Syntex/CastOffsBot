package com.castoffs.utils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * The `TimeUtils` class is a utility class designed for formatting time durations into
 * human-readable formats. It provides methods for converting time in seconds into a more
 * readable format, including years, days, hours, minutes, and seconds.
 */
public class TimeUtils {

    /**
     * Formats a duration in seconds into a human-readable format.
     *
     * @param seconds The duration in seconds to be formatted.
     * @return The formatted duration as a string.
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
     * Formats a time value and appends a string to it, making it human-readable.
     *
     * @param s    The string to append to the time value.
     * @param time The time value to be formatted.
     * @return The formatted time value as a string.
     */
    public static String formatTime(String s, long time) {
        return time == 0 ? "" : time + " " + s + (time == 1 ? "" : "s");
    }
}
