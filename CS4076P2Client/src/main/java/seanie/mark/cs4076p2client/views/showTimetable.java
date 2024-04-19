package seanie.mark.cs4076p2client.views;

import javafx.concurrent.Task;
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
import seanie.mark.cs4076p2client.controllers.GetTimetableTask;
import seanie.mark.cs4076p2client.controllers.MessageSenderTask;
import seanie.mark.cs4076p2client.controllers.Utility;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Arrays;
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

    private void initializeScreen() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Welcome Screen");

        Text displayText = new Text();

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

            Task<GridPane> getTimetable = new GetTimetableTask();
            getTimetable.setOnSucceeded(e -> {
                GridPane timetable = getTimetable.getValue();
                layout.getChildren().add(timetable);
            });
            getTimetable.setOnFailed(e -> {
                displayText.setText("Error Getting Timetable");
                layout.getChildren().add(displayText);
            });
            new Thread(getTimetable).start();
        } catch (Exception ex) {
            displayText.setText("Error Sending Display Schedule Request");
            layout.getChildren().add(displayText);
        }

        HBox root2 = new HBox();
        root2.setSpacing(10);
        root2.setAlignment(Pos.CENTER);

        Button backButton = new Button("Return");
        root2.getChildren().addAll(backButton);
        backButton.setOnAction(e -> returnToMain.run());

        layout.getChildren().add(root2);

        stage.setResizable(false);
        stage.getIcons().add(Utility.icon);
        stage.setOnCloseRequest((WindowEvent we) -> Utility.quitApp());
    }
}
