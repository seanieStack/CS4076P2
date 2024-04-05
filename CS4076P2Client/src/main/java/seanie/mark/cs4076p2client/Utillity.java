package seanie.mark.cs4076p2client;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Utillity {

    public static void quitApp(BufferedReader in, PrintWriter out) {
        try{
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

    public static void enterForSubmisson(Scene scene, Button button) {
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



}
