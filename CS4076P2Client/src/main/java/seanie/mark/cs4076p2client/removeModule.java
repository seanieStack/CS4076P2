package seanie.mark.cs4076p2client;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.PrintWriter;


public class removeModule {
    public Stage stage;

    private final Runnable returnToMain;

    private final BufferedReader in;
    private final PrintWriter out;

    public removeModule(Stage stage, Runnable returnToMain, BufferedReader in, PrintWriter out) {
        this.stage = stage;
        this.returnToMain = returnToMain;
        this.in = in;
        this.out = out;
        initializeScreen();
    }

    private void  initializeScreen () {

        Label moduleLabel = new Label("Module:");
        TextField userInputModule = new TextField();

        Label startTimeLabel = new Label("Start Time:");
        ChoiceBox<String> selectStartTime = new ChoiceBox<>();
        selectStartTime.getItems().addAll("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        selectStartTime.setValue("09:00"); // Default start time


        Label endTimeLabel = new Label("End Time:");

        ChoiceBox<String> selectEndTime = new ChoiceBox<>();
        selectEndTime.getItems().addAll("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        selectEndTime.setValue("10:00"); //Default end time


        Label dayLabel = new Label("Day : ");
        ChoiceBox<String> dayMenu = new ChoiceBox<>();
        dayMenu.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        dayMenu.setValue("Monday"); //Default value


        Label roomLabel = new Label("Room:");
        TextField userInputRoom = new TextField();
        HBox root2 = new HBox();
        root2.setSpacing(10);
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Return");
        root2.getChildren().addAll(submitButton, backButton);

        Text displayText = new Text();
        backButton.setOnAction(e -> returnToMain.run());

        submitButton.setOnAction(e -> {
            String userModule = userInputModule.getText();
            String userDay = dayMenu.getSelectionModel().getSelectedItem();
            String startTime = selectStartTime.getSelectionModel().getSelectedItem();
            String endTime = selectEndTime.getSelectionModel().getSelectedItem();
            String userRoom = userInputRoom.getText();

            boolean differentTime = VerifyInput.isDifferentTime(startTime, endTime);
            boolean validLength = VerifyInput.isValidSessionLength(startTime, endTime);


            if (!differentTime) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Start / End time : An error has occurred");
                alert.setHeaderText("Duplicate time values detected ");
                alert.setContentText("Start and end time can not have the same value !");
                alert.show();
            } else if (!validLength) {

                Alert alert = new Alert(Alert.AlertType.ERROR); //Create method condensing popup window logic
                alert.setTitle("Start / End time : An error has occurred");
                alert.setHeaderText("Session length exceeds 3 hours");
                alert.setContentText("Module sessions can not exceed 3 hours !");
                alert.show();
            }

            String resultText = "rc " + userModule + " " + startTime + "-" + endTime + " " + userDay + " " + userRoom;

            String response;

            try{
                out.println(resultText);

                response = in.readLine();

                if(response.startsWith("cr")){
                    displayText.setText("Class removed at " + response.substring(2));
                } else if (response.equals("nsc")){
                    displayText.setText("Class not found in timetable");
                } else if (response.equals("cnf")){
                    displayText.setText("Class not in timetable");
                } else {
                    displayText.setText("Error removing module " + response);
                }
            } catch (Exception ex){
                displayText.setText("Error Adding Module");
            }
        });


        VBox root = new VBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.getChildren().addAll(root2, moduleLabel, userInputModule, startTimeLabel, selectStartTime, endTimeLabel, selectEndTime, dayLabel, dayMenu, roomLabel, userInputRoom, submitButton, displayText);

        Scene scene;
        scene = new Scene(root, 500, 600);

        stage.setOnCloseRequest((WindowEvent we) -> Utillity.quitApp(in, out));



        Utillity.enterForSubmisson(scene,submitButton);

        stage.setScene(scene);
        stage.setTitle("Remove Module");
        stage.show();

    }
}
