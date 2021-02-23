package org.example;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeParser2 {
    public static void main( String[] args ) {
        String[] candidates =
                { "2016-11-21T17:54:51.841Z",
                        "2016-11-21T09:54:51.841-08:00",
                        "2016", // Java8 no can do?
                        "2016-11", // Java8 no can do?
                        "2016-11-21", // Java8 no can do?
                        "2016-11-21T01", // Java8 no can do?
                        "2016-11-21T01:02", // Java8 no can do?
                        "2016-11-21T01:02:03" // Java8 no can do?
                };

        DateTimeFormatter formatter1 = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .appendPattern("XXX")
                .toFormatter();

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .appendPattern("[XXXXX][XXXX][X]")
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
                .toFormatter();

        String[] stringsPassed = {
                "2018-11-01T16:26:15+0100",
                "2018-10-31T08:27:00.0000000Z"
        };

        for (String sample : candidates) {
            OffsetDateTime odt = OffsetDateTime.parse(sample, formatter);
            System.out.println(odt);
        }
    }
}
