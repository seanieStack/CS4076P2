package seanie.mark.cs4076p2client.views;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seanie.mark.cs4076p2client.controllers.Utility;

import java.util.Objects;

public class homeScreen extends Application {
    public static Stage stage;
    public static Scene mainScene;

    public static void startMainScreen() {
        launch();
    }

    @Override
    public void start(Stage stage) {

        homeScreen.stage = stage;

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("layout");

        //Add message at top of screen
        Label welcomelabel = new Label("Please select an option");

        //Register Buttons
        Button addModule = new Button("Add Module");
        Button removeModule = new Button("Remove Module");
        Button showTimetable = new Button("Display timetable");
        Button makeRequest = new Button("Make request");
        Button quitApplication = new Button("Quit application");

        //setOnAction for button
        addModule.setOnAction(event -> new addModule());
        removeModule.setOnAction(event -> new removeModule());
        showTimetable.setOnAction(event -> new showTimetable());
        makeRequest.setOnAction(event -> new requests());
        quitApplication.setOnAction((event -> Utility.quitApp()));

        layout.getChildren().addAll(welcomelabel, addModule, removeModule, showTimetable, makeRequest, quitApplication);

        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(String.valueOf(getClass().getResource("/" + Utility.getRandomImage()))),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        layout.setBackground(new Background(backgroundImage));

        mainScene = new Scene(layout, 500, 600);
        mainScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

        stage.setScene(mainScene); // Set the scene on the stage
        stage.setTitle("Welcome Screen");
        stage.getIcons().add(Utility.icon);
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest((WindowEvent we) -> Utility.quitApp());
    }

    public static void returnHome () {
        stage.setScene(mainScene);
    }

}
