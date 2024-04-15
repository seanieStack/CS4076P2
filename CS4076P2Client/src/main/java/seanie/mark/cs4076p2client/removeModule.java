package seanie.mark.cs4076p2client;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
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

        title.getChildren().addAll(screenDescriptor);
        module.getChildren().addAll(moduleLabel, userInputModule);
        settings.getChildren().addAll(selectStartTime, timeSeparator, selectEndTime, timeDaySeparator, dayMenu);
        room.getChildren().addAll(roomLabel, userInputRoom);
        finishButton.getChildren().addAll(submitButton, backButton);

        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(10, 10, 10, 10));
        form.getChildren().addAll(module, settings, room, finishButton, displayText);

        stage.setOnCloseRequest((WindowEvent we) -> Utillity.quitApp(in, out));

        root.setTop(title);
        root.setCenter(form);

        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(String.valueOf(getClass().getResource("/" + Utillity.getRandomImage()))),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        root.setBackground(new Background(backgroundImage));

        Scene scene;
        scene = new Scene(root, 500, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); //Added to fix Mac font issue
        Utillity.enterForSubmisson(scene,submitButton);

        stage.setScene(scene);
        stage.setTitle("Remove Module");
        stage.getIcons().add(Utillity.icon);
        stage.setResizable(false);
        stage.show();

    }
}
