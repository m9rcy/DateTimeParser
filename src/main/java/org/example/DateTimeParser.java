package org.example;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class DateTimeParser {
    public static void main(String[] args) {
        String[] candidates =
                {"2016-11-21T17:54:51.841Z",
                        "2016-11-21T09:54:51.841-08:00",
                        "2016", // Java8 no can do?
                        "2016-11", // Java8 no can do?
                        "2016-11-21", // Java8 no can do?
                        "2016-11-21T01", // Java8 no can do?
                        "2016-11-21T01:02", // Java8 no can do?
                        "2016-11-21T01:02:03" // Java8 no can do?
                };
        java.time.format.DateTimeFormatter isoDateParser = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE)
                .optionalStart()
                .appendLiteral('T')
                .append(java.time.format.DateTimeFormatter.ISO_TIME)
                .toFormatter();

        String pattern = "yyyy[-MM][-dd['T'HH[:mm[:ss]]]][.SSSSSSXXX]";
        String pattern2 = "yyyy[-MM][-dd['T'HH[:mm[:ss[.SSSSSS]]]]][.SSSSSS][XXX]";

        java.time.format.DateTimeFormatter customOffsetDateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern(pattern2)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.NANO_OF_SECOND, 000000)
                .parseDefaulting(ChronoField.OFFSET_SECONDS, ZoneOffset
                        .of("+13:00")
                        .getTotalSeconds())
                .toFormatter();

        DateTimeFormatter JodaDateTimeFormatter = ISODateTimeFormat.dateTimeParser();

        for (String candidate : candidates) {
            System.out.println("\ncandidate:\t\"" + candidate + "\"");
            DateTime jodaDateTime = JodaDateTimeFormatter.parseDateTime(candidate);
            System.out.println("Joda:\t" + jodaDateTime);
            try {
                OffsetDateTime java8OffsetDateTime = OffsetDateTime.parse(candidate, customOffsetDateTimeFormatter);
                System.out.println("Java8:\t" + java8OffsetDateTime);
                long jodaMillis = jodaDateTime.getMillis();
                long javaMillis = java8OffsetDateTime
                        .toInstant()
                        .toEpochMilli();
                System.out.printf("jodaMillis:%d %s javaMillis:%d\n",
                                  jodaMillis,
                                  (jodaMillis == javaMillis) ? "==" : "!=",
                                  javaMillis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
