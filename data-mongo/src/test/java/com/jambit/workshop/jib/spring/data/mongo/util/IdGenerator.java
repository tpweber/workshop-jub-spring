package com.jambit.workshop.jib.spring.data.mongo.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Generates ids.
 */
public final class IdGenerator {
    private static final DateTimeFormatter mFormatter = createDateTimeFormatter();

    public static String createId() {
        return mFormatter.format(Instant.now());
    }

    private static DateTimeFormatter createDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS").withZone(ZoneId.systemDefault());
    }

    private IdGenerator() {
        // nop
    }
}
