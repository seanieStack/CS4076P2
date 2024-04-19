package seanie.mark.cs4076p2client.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seanie.mark.cs4076p2client.App;
import seanie.mark.cs4076p2client.controllers.Utility;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Objects;


public class showTimetable {
    public Stage stage;
    private final Runnable returnToMain;
    private final BufferedReader in;
    private final PrintWriter out;
    
    public showTimetable() {
        this.stage = homeScreen.stage;
        this.returnToMain = homeScreen::returnHome;
        this.in = App.in;
        this.out = App.out;
        initializeScreen();
    }

    private void  initializeScreen () {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.TOP_CENTER);

        
        Scene scene = new Scene(layout, 500, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Welcome Screen");

        HBox root2 = new HBox();
        root2.setSpacing(10);
        root2.setAlignment(Pos.CENTER);

        Button submitButton = new Button("Submit");
        Button backButton = new Button("Return");
        root2.getChildren().addAll(submitButton, backButton);
        backButton.setOnAction(e -> returnToMain.run());

        Text displayText = new Text();

        submitButton.setOnAction(e -> {

                    try {
                        BackgroundImage backgroundImage = new BackgroundImage(
                                new Image(String.valueOf(getClass().getResource("/bg2.png"))),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(100, 100, true, true, true, false)
                        );
                        layout.setBackground(new Background(backgroundImage));

                        stage.setFullScreen(true); // Added this so all text displays properly
                        out.println("ds");

                        StringBuilder responseBuilder = new StringBuilder();
                        String line;
                        while (!(line = in.readLine()).equals("END_OF_TIMETABLE")) {
                            responseBuilder.append(line);
                            responseBuilder.append('\n'); // add the newline back
                        }
                        String response = responseBuilder.toString();

                        String[] classEntries = response.split("!\\s*"); // For multiple entries 
                        int numClasses = Utility.getNumClasses(response);

                        GridPane timetable = new GridPane();
                        timetable.setAlignment(Pos.CENTER);

                        String [] times = Utility.getTimes();
                        String [] days = Utility.getDays();
                        
                        //Changed to a better size
                        timetable.setPadding(new javafx.geometry.Insets(6,6,6,6));
                        timetable.setVgap(30);
                        timetable.setHgap(30);


                        Label mondayLabel = new Label("Monday");
                        Label tuesdayLabel = new Label("Tuesday");
                        Label wednesdayLabel = new Label("Wednesday");
                        Label thursdayLabel = new Label("Thursday");
                        Label fridayLabel   = new Label("Friday");


                        GridPane.setConstraints(mondayLabel,1,0);
                        GridPane.setConstraints(tuesdayLabel,2,0);
                        GridPane.setConstraints(wednesdayLabel,3,0);
                        GridPane.setConstraints(thursdayLabel,4,0);
                        GridPane.setConstraints(fridayLabel,5,0);
                        //TODO: Refactor this into a loop later

                        for (int j = 0 ; j < times.length; j++){
                            Label timeLabel = new Label(times[j]);
                            GridPane.setConstraints(timeLabel,0,j+1);
                            timetable.getChildren().add(timeLabel);
                        }


                        // parts[i] =  0 = Module, 1 = Day , 2 = Start Time , 3 = End Time , 4 = Room
                        // response takes the form "CS4096,Monday,09:00,10:00,KGB-12";
                        /**
                        int nodes[] =  Utility.moduleNodes(response);
                        String parts []= Utility.splitPayload(response);
                        Label exampleLabel = new Label(parts[0]+ " " +parts[4]);
                        GridPane.setConstraints(exampleLabel,nodes[0],nodes[1] );
                        //TODO: Add functionality for multiple modules (loop)
                        */
                        
                        if (classEntries.length != numClasses) {
                            System.out.println("Error: Mismatch between expected number of classes and actual data.");
                            return;  // Handling possible data mismatch
                        }

                        for (int i = 0; i < numClasses; i++) {
                            String entry = classEntries[i].trim();
                            if (entry.isEmpty()) continue;  // Skip any empty entries due to split

                            String[] parts = entry.split(",");  // Split entry into parts
                            if (parts.length < 5) continue;  // Ensure all parts are present

                            int[] nodes = Utility.moduleNodes(entry);  // Determine grid positions based on time and day
                            Label moduleWithNodes = new Label(parts[0] + " " + parts[4]);  // Format: ModuleCode Room
                            GridPane.setConstraints(moduleWithNodes, nodes[0], nodes[1]);  // Set position in the grid
                            timetable.getChildren().add(moduleWithNodes); //Removed redundant "exampleLabel" here
                        }


                        timetable.getChildren().addAll(mondayLabel,tuesdayLabel,wednesdayLabel,thursdayLabel,fridayLabel);
                        layout.getChildren().add(timetable);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        displayText.setText("Error Sending Display Schedule Request");
                    }
                });


        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(String.valueOf(getClass().getResource("/" + Utility.getRandomImage()))),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        layout.setBackground(new Background(backgroundImage));

        stage.setResizable(false);
        stage.getIcons().add(Utility.icon);
        stage.setOnCloseRequest((WindowEvent we) -> Utility.quitApp());

        layout.getChildren().addAll(root2, displayText);
    }
}
