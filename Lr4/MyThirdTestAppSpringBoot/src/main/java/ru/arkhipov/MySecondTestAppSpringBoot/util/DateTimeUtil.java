package ru.arkhipov.MySecondTestAppSpringBoot.util;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    public static SimpleDateFormat getCustomFormat(){
        return new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.systemDefault());

    public static Instant parseTimeStamp(String timestamp) throws DateTimeParseException{
        return Instant.from(formatter.parse(timestamp));
    }

    public static long  calculateTimeDifference(String startTime, String endTime){
        try{
            Instant start = parseTimeStamp(startTime);
            Instant end = parseTimeStamp(endTime);
            return Duration.between(start, end).toMillis();
        } catch (DateTimeParseException e){
            throw new IllegalArgumentException("Invalid timestamp format", e);
        }
    }
}
