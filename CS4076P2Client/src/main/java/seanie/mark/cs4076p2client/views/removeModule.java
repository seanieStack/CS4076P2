package seanie.mark.cs4076p2client.views;


import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seanie.mark.cs4076p2client.App;
import seanie.mark.cs4076p2client.controllers.MessageSenderTask;
import seanie.mark.cs4076p2client.controllers.Utility;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Objects;


public class removeModule {
    public Stage stage;

    private final Runnable returnToMain;

    private final BufferedReader in;
    private final PrintWriter out;

    public removeModule() {
        this.stage = homeScreen.stage;
        this.returnToMain = homeScreen::returnHome;
        this.in = App.in;
        this.out = App.out;
        initializeScreen();
    }

    private void  initializeScreen () {

        BorderPane root = new BorderPane();

        HBox settings = new HBox();
        settings.setPadding(new Insets(5, 0, 0, 0));
        settings.setAlignment(Pos.CENTER);

        HBox module = new HBox();
        module.setPadding(new Insets(5, 0, 0, 0));
        module.setAlignment(Pos.CENTER);

        HBox room = new HBox();
        room.setPadding(new Insets(5, 0, 0, 0));
        room.setAlignment(Pos.CENTER);

        HBox title = new HBox();
        title.setPadding(new Insets(20));
        title.setSpacing(10);
        title.setAlignment(Pos.CENTER);

        HBox finishButton = new HBox();
        finishButton.setSpacing(10);
        finishButton.setAlignment(Pos.CENTER);

        Label screenDescriptor = new Label("Remove Existing Module");
        screenDescriptor.setStyle("-fx-font-size: 20pt; -fx-font-weight: bold;");

        Label moduleLabel = new Label("Module:");
        TextField userInputModule = new TextField();

        ChoiceBox<String> selectStartTime = new ChoiceBox<>();
        selectStartTime.getItems().addAll("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        selectStartTime.setValue("09:00");

        Label timeSeparator = new Label("-");

        ChoiceBox<String> selectEndTime = new ChoiceBox<>();
        selectEndTime.getItems().addAll("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        selectEndTime.setValue("10:00");

        Label timeDaySeparator = new Label(" on ");

        ChoiceBox<String> dayMenu = new ChoiceBox<>();
        dayMenu.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        dayMenu.setValue("Monday");


        Label roomLabel = new Label("Room:");
        TextField userInputRoom = new TextField();

        Button submitButton = new Button("Submit");
        Button backButton = new Button("Return");

        // Create a Text element to display the result
        Text displayText = new Text();
        backButton.setOnAction(e -> returnToMain.run());

        submitButton.setOnAction(e -> {
            // Get the text from each TextField
            String userModule = userInputModule.getText();
            String userDay = dayMenu.getSelectionModel().getSelectedItem();
            String startTime = selectStartTime.getSelectionModel().getSelectedItem();
            String endTime = selectEndTime.getSelectionModel().getSelectedItem();
            String userRoom = userInputRoom.getText();

            Task<String> submitRemoveClass = new MessageSenderTask(in, out,"ac", userModule, userDay, startTime, endTime, userRoom);
            submitRemoveClass.setOnSucceeded(ev -> {
                String response = submitRemoveClass.getValue();
                switch (response){
                    case "cr":
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Module Removed !");
                        alert.setHeaderText(null);
                        alert.setContentText("Timetable is now updated to reflect " + userModule +" being removed ");
                        alert.show();
                        break;
                    case "nsc":
                        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                        overlapAlert.setTitle("Error remove Module : " + userModule);
                        overlapAlert.setHeaderText(null);
                        overlapAlert.setContentText("Module : " + userModule +" was not in the timetable at that time");
                        overlapAlert.show();
                        break;
                    case "cnf":
                        Alert ttFullAlert = new Alert(Alert.AlertType.ERROR);
                        ttFullAlert.setTitle("Error remove Module : " + userModule);
                        ttFullAlert.setHeaderText(null);
                        ttFullAlert.setContentText("Module : " + userModule +" is not a class in this timetable");
                        ttFullAlert.show();
                        break;
                }
            });
            submitRemoveClass.setOnFailed(ev ->{
                Throwable ex = submitRemoveClass.getException();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Data Provided");
                alert.setHeaderText("Invalid Data Provided");
                alert.setContentText(ex.getMessage());
                alert.show();
            });

            new Thread(submitRemoveClass).start();
        });

        title.getChildren().addAll(screenDescriptor);
        module.getChildren().addAll(moduleLabel, userInputModule);
        settings.getChildren().addAll(selectStartTime, timeSeparator, selectEndTime, timeDaySeparator, dayMenu);
        room.getChildren().addAll(roomLabel, userInputRoom);
        finishButton.getChildren().addAll(submitButton, backButton);

        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(10, 10, 10, 10));
        form.getChildren().addAll(module, settings, room, finishButton, displayText);

        stage.setOnCloseRequest((WindowEvent we) -> Utility.quitApp());

        root.setTop(title);
        root.setCenter(form);

        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(String.valueOf(getClass().getResource("/" + Utility.getRandomImage()))),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        root.setBackground(new Background(backgroundImage));

        Scene scene;
        scene = new Scene(root, 500, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm()); //Added to fix Mac font issue
        Utility.enterForSubmission(scene,submitButton);

        stage.setScene(scene);
        stage.setTitle("Remove Module");
        stage.getIcons().add(Utility.icon);
        stage.setResizable(false);
        stage.show();

    }
}
