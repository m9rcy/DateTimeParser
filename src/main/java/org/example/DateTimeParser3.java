package org.example;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class DateTimeParser3 {
    public static void main(String[] args) {
        String[] dates = { "2012-01-05T21:21:52.834Z", "2012-01-05", "2012-01-05T21:21"};
        OffsetDateTime odt = OffsetDateTime.now (ZoneId.systemDefault () );

        DateTimeFormatter formatter =
                new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd['T'HH:mm:ss.SSSz]")
                                              .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                                              .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                                              .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                                              .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
                                              .parseDefaulting(ChronoField.OFFSET_SECONDS, odt.getOffset().getTotalSeconds())
                                              .toFormatter();
        for (String date : dates) {
            OffsetDateTime zonedDateTime = OffsetDateTime.parse(date, formatter);

            System.out.println(zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", Locale.forLanguageTag("en-NZ"))));
        }
    }
}
