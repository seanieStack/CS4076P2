package seanie.mark.cs4076p2client.controllers;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import seanie.mark.cs4076p2client.App;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utility {

    public static Image icon = new Image(String.valueOf(Utility.class.getResource("/UL_LOGO.png")));

    public static void quitApp() {
        try{
            BufferedReader in = App.in;
            PrintWriter out = App.out;

            out.println("st");
            String response = in.readLine();
            System.out.println(response);
            in.close();
            out.close();
            Platform.exit();
        } catch (Exception e) {
            System.out.println("Error in closing connection");
        }
    }

    public static void enterForSubmission(Scene scene, Button button) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                button.fire();
            }
        });
    }

     public static String[] getTimes() {
        return new String[]{"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};

    }

    public static String[] getDays() {
        return new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"} ;
    }

    public static String[] splitPayload ( String payload) {
        String trimmedInput = payload.substring(1, payload.length() - 1); //Causes lost of first module letter

        return trimmedInput.split(","); //Refactored from whitespace
    }


    public static int [] moduleNodes (String payload) {
        String[] days = getDays();
        String[] times = getTimes();
        
        String day = splitPayload(payload)[1];
      


        String startTime = splitPayload(payload)[2];


        int nodeY = 0;

        for (int i = 0; i < times.length; i++) {
            if (startTime.equals(times[i])) {
                nodeY = i + 1;
                break;
            }
        }

        int nodeX = 0;
        for (int j = 0; j < days.length; j++) {
            if (day.equals(days[j])) {
                nodeX = j + 1;
                break;
            }
        }

        return new int[]{nodeX,nodeY};
    }

    public static String getRandomImage() {
        String[] images = {
                "bg1.png"
        };
        int idx = 0;
        return images[idx];
    }

     public static int getNumClasses(String timetable ) {

        char target = '!'; // ! indicates end of entry
        long numCommas = countCharacter(timetable,target);

         return (int) numCommas;

    }

    public static long countCharacter(String str, char ch) {
        return str.chars().filter(c -> c == ch).count();
    }

    public static boolean isDifferentTime ( String startTime , String endTime) {
        LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));
        return !start.equals(end);
    }

    public static boolean isValidSessionLength (String startTime , String endTime) {
        LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));
        Duration duration = Duration.between(start,end);
        long longHours = duration.toHours();
        int hours = (int) longHours;

         return hours <= 3;
     }
}
