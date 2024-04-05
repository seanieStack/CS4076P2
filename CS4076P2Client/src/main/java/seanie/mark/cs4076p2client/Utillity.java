package seanie.mark.cs4076p2client;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        String trimmedInput = payload.substring(1, payload.length() - 1);

        return trimmedInput.split("><");
    }

    public static String[] splitTime(String timePart) {
        return timePart.split("-");
    }
    public static int [] moduleNodes (String payload) {
        String[] days = getDays();
        String[] times = getTimes();
        
        String day = splitPayload(payload)[2];
      

        String [] bothTimes =splitTime(splitPayload(payload)[1]);

        int nodeY = 0;

        for (int i = 0; i < times.length; i++) {
            if (bothTimes[0].equals(times[i])) {
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

    public static List<Module> rebuildModules(String timetableString) {
        List<Module> modules = new ArrayList<>();
        Module currentModule = null;
        List<TimetableEntry> currentTimetable = new ArrayList<>();

        // Split the input string into lines
        String[] lines = timetableString.split("\n");
        for (String line : lines) {
            if (line.startsWith("Module: ")) {
                // If we encounter a new module and currentModule is not null, save the previous module
                if (currentModule != null) {
                    modules.add(currentModule);
                    currentTimetable = new ArrayList<>(); // Reset for the next module
                }
                String moduleCode = line.substring(8); // Extract module code
                currentModule = new Module(moduleCode); // Create a new module with an empty timetable
            } else if (!line.trim().isEmpty()) {
                // Assuming line format is "Day: ..., Start Time: ..., End Time: ..., Room: ..."
                String[] parts = line.split(", ");
                String day = parts[0].split(": ")[1];
                String startTime = parts[1].split(": ")[1];
                String endTime = parts[2].split(": ")[1];
                String room = parts[3].split(": ")[1];

                TimetableEntry entry = new TimetableEntry(startTime+":"+endTime, day, room);
                currentTimetable.add(entry);
            }
        }

        // Don't forget to add the last module
        if (currentModule != null && !currentTimetable.isEmpty()) {
            modules.add(currentModule);
        }

        return modules;
    }
}
