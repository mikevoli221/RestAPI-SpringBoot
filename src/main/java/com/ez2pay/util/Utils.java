package com.ez2pay.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static boolean isNumber(String str){
        try{
            Double.parseDouble(str);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static String parseObjectToJson (Object object) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String converDateObjectToString (Date object) {
        try {
            DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");
            Instant instant = object.toInstant();
            LocalDateTime ldt = instant.atZone(ZoneId.of("GMT+7")).toLocalDateTime();
            return ldt.format(datePattern);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Date converStringToDateObject (String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss", Locale.ENGLISH);
            return formatter.parse(dateString);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
