package net.tarau.testware.reporting.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Utilities around time representation.
 */
public class TimeUtils {

    public static final long NANO_IN_MICROSECOND = 1000;
    public static final long NANO_IN_MILLISECOND = 1000 * NANO_IN_MICROSECOND;
    public static final long NANO_IN_SECOND = 1000 * NANO_IN_MILLISECOND;
    public static final long NANO_IN_MINUTE = 60 * NANO_IN_SECOND;
    public static final long NANO_IN_HOUR = 60 * NANO_IN_MINUTE;
    public static final long NANO_IN_DAY = 24 * NANO_IN_HOUR;

    private static final String ZERO_MILI = "0ms";
    private static final String ZERO_NANO = "0ns";
    private static final String ZERO_SECOND = "0s";

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    public static String nanoDurationToString(Object value) {
        return nanoDurationToString(value, ZERO_NANO);
    }

    public static String nanoDurationToString(Object value, String zeroValue) {
        if (value == null) {
            return zeroValue;
        }
        if (value instanceof Number) {
            long longValue = ((Number) value).longValue();
            if (longValue == 0) {
                return zeroValue;
            }
            String sign = "";
            if (longValue < 0) {
                sign = "-";
                longValue = Math.abs(longValue);
            }
            if (longValue == Long.MIN_VALUE || longValue == Long.MAX_VALUE) {
                return "N/A";
            }
            if (longValue < NANO_IN_MICROSECOND) {
                return sign + longValue + "ns";
            } else if (longValue < NANO_IN_MILLISECOND) {
                return sign + String.format("%.1f\u00b5s", (float) longValue / (float) NANO_IN_MICROSECOND);
            } else if (longValue < NANO_IN_SECOND) {
                return sign + longValue / NANO_IN_MILLISECOND + "ms";
            } else if (longValue < NANO_IN_MINUTE) {
                long second = longValue / NANO_IN_SECOND;
                long mili = (longValue - second * NANO_IN_SECOND) / NANO_IN_MILLISECOND;
                if (mili == 0 || second > 10) {
                    return sign + second + "s";
                }
                return sign + second + "s " + mili + "ms";
            } else if (longValue < NANO_IN_HOUR) {
                long minute = longValue / NANO_IN_MINUTE;
                long second = (longValue - minute * NANO_IN_MINUTE) / NANO_IN_SECOND;
                if (second == 0) {
                    return sign + minute + "m";
                }
                return sign + minute + "m " + second + "s";
            } else if (longValue < NANO_IN_DAY) {
                long hour = longValue / NANO_IN_HOUR;
                long minute = (longValue - hour * NANO_IN_HOUR) / NANO_IN_MINUTE;
                long second = (longValue - hour * NANO_IN_HOUR - minute * NANO_IN_MINUTE) / NANO_IN_SECOND;
                String valueAsString = hour + "h";
                if (minute != 0) {
                    valueAsString += " " + minute + "m";
                }
                if (second != 0 && hour == 0) {
                    valueAsString += " " + second + "s";
                }
                return sign + valueAsString;
            } else {
                long days = longValue / NANO_IN_DAY;
                long hour = (longValue - days * NANO_IN_DAY) / NANO_IN_HOUR;
                long minute = (longValue - days * NANO_IN_DAY - hour * NANO_IN_HOUR) / NANO_IN_MINUTE;
                String valueAsString = days + "d";
                if (hour != 0) {
                    valueAsString += " " + hour + "h";
                }
                if (minute != 0) {
                    valueAsString += " " + minute + "m";
                }
                return sign + valueAsString;
            }
        }
        return "NaN";
    }

    public static String milliDurationAsString(Object value) {
        if (value instanceof Number) {
            long longValue = ((Number) value).longValue();
            if (longValue == Long.MIN_VALUE) {
                return "N/A";
            }
            return nanoDurationToString(longValue * NANO_IN_MILLISECOND, ZERO_MILI);
        }
        return nanoDurationToString(value);
    }

    public static String formatDateTime(Object value) {
        if (value == null) {
            return "N/A";
        }
        if (value instanceof Number) {
            return dateTimeFormatter.format(Instant.ofEpochMilli(((Number) value).longValue()).atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
        } else if (value instanceof Date) {
            return dateTimeFormatter.format(Instant.ofEpochMilli(((Date) value).getTime()).atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
        }
        return "N/A";
    }
}
