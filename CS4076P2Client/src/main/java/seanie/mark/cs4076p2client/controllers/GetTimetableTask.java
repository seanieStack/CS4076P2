package seanie.mark.cs4076p2client.controllers;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import seanie.mark.cs4076p2client.App;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class GetTimetableTask extends Task<GridPane> {

    private final BufferedReader in;
    private final PrintWriter out;

    public GetTimetableTask() {
        this.in = App.in;
        this.out = App.out;
    }

    @Override
    protected GridPane call() throws Exception {
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
}
