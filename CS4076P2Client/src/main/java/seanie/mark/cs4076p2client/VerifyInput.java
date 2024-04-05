package seanie.mark.cs4076p2client;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class VerifyInput {

    public static boolean isDifferentTime ( String startTime , String endTime) {
        LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));
        return !start.equals(end);
    }

     public static boolean isValidSessionLength ( String startTime , String endTime) {
        LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));
        Duration duration = Duration.between(start,end);
        long longHours = duration.toHours();
        int hours = (int) longHours;

         return hours <= 3;
     }
}

