package seanie.mark.cs4076p2client.controllers;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class MessageSenderTask extends Task<String>{

    String action;
    String[] details;

    BufferedReader in;
    PrintWriter out;

    public MessageSenderTask(BufferedReader in, PrintWriter out, String action, String... details){
        this.action = action;
        this.details = details;
        this.in = in;
        this.out = out;
    }


    @Override
    protected String call() throws Exception {
        String response = null;

        if(!action.equals("ac") && !action.equals("rc") && !action.equals("rq")){
            throw new Exception("Invalid Action");
        }

        switch(action){
            case "ac":
                response = addRemoveClass("ac");
                break;
            case "rc":
                response = addRemoveClass("rc");
                break;
            case "rq":
                response = requests();
                break;
        }
        return response;
    }

    private String addRemoveClass(String op) throws IOException {
        String userModule = details[0];
        String userDay = details[1];
        String startTime = details[2];
        String endTime = details[3];
        String userRoom = details[4];

        boolean differentTime = Utility.isDifferentTime(startTime, endTime);
        boolean validLength = Utility.isValidSessionLength(startTime, endTime);


        if (!differentTime) {
            throw new IllegalArgumentException("Please Input Different Times");
        } else if (!validLength) {
            throw new IllegalArgumentException("Modules Cannot Exceed 3hrs");
        }

        String resultText = op + " " + userModule + " " + startTime + "-" + endTime + " " + userDay + " " + userRoom;
        String response;
        out.println(resultText);

        response = in.readLine();

        return response;
    }

    private GridPane getTimetable() throws Exception {
        out.println("ds");

        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while (!(line = in.readLine()).equals("END_OF_TIMETABLE")) {
            responseBuilder.append(line);
            responseBuilder.append('\n'); // add the newline back
        }
        String response = responseBuilder.toString();
        System.out.println(response);

        String[] classEntries = response.split("!\\s*");// For multiple entries
        System.out.println(Arrays.toString(classEntries));
        int numClasses = Utility.getNumClasses(response);

        GridPane timetable = new GridPane();
        timetable.setAlignment(Pos.CENTER);

        String[] times = Utility.getTimes();
        String[] days = Utility.getDays();

        //Changed to a better size
        timetable.setPadding(new javafx.geometry.Insets(6, 6, 6, 6));
        timetable.setVgap(30);
        timetable.setHgap(30);

        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i]);
            GridPane.setConstraints(dayLabel, i + 1, 0);
            timetable.getChildren().add(dayLabel);
        }

        for (int j = 0; j < times.length; j++) {
            Label timeLabel = new Label(times[j]);
            GridPane.setConstraints(timeLabel, 0, j + 1);
            timetable.getChildren().add(timeLabel);
        }

        if (classEntries.length != numClasses) {
            System.out.println("Error: Mismatch between expected number of classes and actual data.");
            throw new Exception("Something Broke");  // Handling possible data mismatch
        }

        for (int i = 0; i < numClasses; i++) {
            String entry = classEntries[i].trim();
            if (entry.isEmpty()) continue;  // Skip any empty entries due to split

            String[] parts = entry.split(",");  // Split entry into parts
            if (parts.length < 5) continue;  // Ensure all parts are present

            int[] nodes = Utility.moduleNodes(entry);  // Determine grid positions based on time and day
            Label moduleWithNodes = new Label(parts[0] + " " + parts[4]);  // Format: ModuleCode Room
            GridPane.setConstraints(moduleWithNodes, nodes[0], nodes[1]);  // Set position in the grid
            timetable.getChildren().add(moduleWithNodes);
        }

        return timetable;
    }

    private String requests() throws IOException {
        String userModule = details[0];
        String userDay = details[1];
        String startTime = details[2];
        String endTime = details[3];
        String userRoom = details[4];
        String differentStartTime = details[5];
        String differentEndTime = details[6];

        boolean originDifferentTime = Utility.isDifferentTime(startTime, endTime);
        boolean originValidLength = Utility.isValidSessionLength(startTime, endTime);

        boolean newDifferentTime = Utility.isDifferentTime(differentStartTime, differentEndTime);
        boolean newValidLength = Utility.isValidSessionLength(differentStartTime, differentEndTime);

        if (!originDifferentTime || !newDifferentTime) {
            throw new IllegalArgumentException("Please Input Different Times");
        } else if (!originValidLength || !newValidLength) {
            throw new IllegalArgumentException("Modules Cannot Exceed 3hrs");
        } else if (startTime.equals("09:00")) {
            throw new IllegalArgumentException("Start Time Cannot Be 9:00AM");
        } else if (startTime.equals(differentStartTime) || endTime.equals(differentEndTime)) {
            throw new IllegalArgumentException("Please Review Your Selections");
        }

        String resultText = "el " + userModule + " " + startTime + "-" + endTime + " " + userDay + " " + userRoom + " " + differentStartTime + "-" + differentEndTime;

        String response;

        out.println(resultText);

        response = in.readLine();

        return response;
    }
}


