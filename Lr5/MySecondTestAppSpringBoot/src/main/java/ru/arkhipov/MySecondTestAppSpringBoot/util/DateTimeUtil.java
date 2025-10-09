package ru.arkhipov.MySecondTestAppSpringBoot.util;

import java.text.SimpleDateFormat;

public class DateTimeUtil {
    public static SimpleDateFormat getCustomFormat(){
        return new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }
}
