package seanie.mark.cs4076p2client;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.PrintWriter;

//TODO: More input checks , then Remove module and Add it back with the new details

public class requests {
    public Stage stage;
    private final Runnable returnToMain;
    private final BufferedReader in;
    private final PrintWriter out;

    public requests(Stage stage, Runnable returnToMain, BufferedReader in, PrintWriter out) {
        this.stage = stage;
        this.returnToMain = returnToMain;
        this.in = in;
        this.out = out;
        initializeScreen();
    }


    private void initializeScreen() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        Text displayText = new Text();
        displayText.setText("Enter the details of the module you wish to reschedule as well as the new start and end times of that module ");

        Label moduleLabel = new Label("Module:");
        TextField userInputModule = new TextField();

        Label startTimeLabel = new Label("Start Time:");
        ChoiceBox<String> selectStartTime = new ChoiceBox<>();
        selectStartTime.getItems().addAll(Utillity.getTimes());
        selectStartTime.setValue("09:00"); // Default start time


        Label endTimeLabel = new Label("End Time:");

        ChoiceBox<String> selectEndTime = new ChoiceBox<>();
        selectEndTime.getItems().addAll(Utillity.getTimes());
        selectEndTime.setValue("10:00"); //Default end time


        Label dayLabel = new Label("Day : ");
        ChoiceBox<String> dayMenu = new ChoiceBox<>();
        dayMenu.getItems().addAll(Utillity.getDays());
        dayMenu.setValue("Monday"); //Default value


        Label roomLabel = new Label("Room:");
        TextField userInputRoom = new TextField();

        Label newStartTime = new Label("New Start Time : ");
        ChoiceBox<String> selectNewStartTime = new ChoiceBox<>();
        selectNewStartTime.getItems().addAll(Utillity.getTimes());
        selectNewStartTime.setValue("10:00");

        Label newEndTime = new Label("New End Time : ");
        ChoiceBox<String> selectNewEndTime = new ChoiceBox<>();
        selectNewEndTime.getItems().addAll(Utillity.getTimes());
        selectNewEndTime.setValue("10:00");


        HBox root2 = new HBox();
        root2.setSpacing(10);
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Return");
        root2.getChildren().addAll(submitButton, backButton);

        backButton.setOnAction(e -> returnToMain.run());


        submitButton.setOnAction(e -> {
            String userModule = userInputModule.getText();
            String userDay = dayMenu.getSelectionModel().getSelectedItem();
            String startTime = selectStartTime.getSelectionModel().getSelectedItem();
            String endTime = selectEndTime.getSelectionModel().getSelectedItem();
            String userRoom = userInputRoom.getText();
            String differentStartTime = selectNewStartTime.getSelectionModel().getSelectedItem();
            String differentEndTime = selectEndTime.getSelectionModel().getSelectedItem();

            boolean originDifferentTime = VerifyInput.isDifferentTime(startTime, endTime);
            boolean originValidLength = VerifyInput.isValidSessionLength(startTime, endTime);

            boolean newDifferentTime = VerifyInput.isDifferentTime(differentStartTime,differentEndTime);
            boolean newValidLength = VerifyInput.isValidSessionLength(differentStartTime,differentEndTime);

            if (!originDifferentTime || ! newDifferentTime) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Start / End time : An error has occurred");
                alert.setHeaderText("Duplicate time values detected ");
                alert.setContentText("Start and end time can not have the same value !");
                alert.show();
            } else if (!originValidLength || !newValidLength) {

                Alert alert = new Alert(Alert.AlertType.ERROR); //Create method condensing popup window logic
                alert.setTitle("Start / End time : An error has occurred");
                alert.setHeaderText("Session length exceeds 3 hours");
                alert.setContentText("Module sessions can not exceed 3 hours !");
                alert.show();
            } else if (startTime.equals("09:00")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Start time : An error occurred");
                alert.setHeaderText("Start time can not be 9:00 AM");
                alert.setContentText("The first lectures of the day start at 9:00 An and can not start at 9AM");
                alert.show();
            }else if (startTime.equals(differentStartTime) || endTime.equals(differentEndTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Start / End times : An error has occurred ");
                alert.setHeaderText("You can not reschedule the lecture for the same time ");
                alert.setContentText("Please review your selections ");
            }

            String resultText = "rc " + userModule + " " + startTime + "-" + endTime + " " + userDay + " " + userRoom;

            String response;

        });




        root.getChildren().addAll(
                displayText,moduleLabel, userInputModule,
                startTimeLabel, selectStartTime,
                endTimeLabel, selectEndTime,
                dayLabel, dayMenu,
                roomLabel, userInputRoom,
                newStartTime, selectNewStartTime,
                newEndTime, selectNewEndTime,
                root2
        );

        Scene scene;
        scene = new Scene(root, 500, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Make request");
        stage.show();
    }

}

