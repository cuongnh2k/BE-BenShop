package com.example.bekiashop.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public final class DateUtil {

    private DateUtil() {
    }

    public static Date convertToUtilDate(LocalDateTime dateToConvert) {
        if (Objects.isNull(dateToConvert)) {
            return null;
        }

        return Date.from(dateToConvert
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date convertToUtilDate(LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            return null;
        }

        return Date.from(localDate
                .atTime(12, 0, 0)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date convertToUtilDate(LocalTime localTime) {
        if (Objects.isNull(localTime)) {
            return null;
        }

        return Date
                .from(localTime.atDate(LocalDate.now())
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date convertToUtilDate(Long timestampInMillisecond) {
        if (Objects.isNull(timestampInMillisecond)) {
            return null;
        }

        return new Date(timestampInMillisecond);
    }

    public static LocalDateTime convertToLocalDateTime(Long timestampInMillisecond) {
        if (Objects.isNull(timestampInMillisecond)) {
            return null;
        }

        return convertToLocalDateTime(new Date(timestampInMillisecond));
    }

    public static LocalDate convertToLocalDate(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDate convertToLocalDate(Long timestampInMillisecond) {
        if (Objects.isNull(timestampInMillisecond)) {
            return null;
        }

        return new Date(timestampInMillisecond).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Optional<String> convertToTimeString(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return Optional.empty();
        }
        Date date = convertToUtilDate(localDateTime);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        return Optional.of(sdf.format(date));
    }
}