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
import java.net.URL;

public class addModule {

    private final Stage stage;
    private final Runnable returnToMain;

    private final BufferedReader in;
    private final PrintWriter out;

    public addModule(Stage stage, Runnable returnToMain, BufferedReader in, PrintWriter out){
        this.stage = stage;
        this.returnToMain = returnToMain;
        this.in = in;
        this.out = out;
        initScreen();
    }

    public void initScreen() {

        BorderPane root = new BorderPane();

        //Hbox for the setting such as startTime, endTime and Day
        HBox settings = new HBox();
        settings.setPadding(new Insets(5, 0, 0, 0));
        settings.setAlignment(Pos.CENTER);

        //Hbox for the module lable and code
        HBox module = new HBox();
        module.setPadding(new Insets(5, 0, 0, 0));
        module.setAlignment(Pos.CENTER);

        //Hbox for the room lable and name
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

        Label screenDescriptor = new Label("Add New Module");
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

        // Set the action when the button is pressed
        submitButton.setOnAction(e -> {
            // Get the text from each TextField
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

            String resultText = "ac " + userModule + " " + startTime + "-" + endTime + " " + userDay + " " + userRoom;
            displayText.setText(resultText);

            String response;

            try{
                out.println(resultText);

                response = in.readLine();
                switch (response) {
                    case "ca":
                        displayText.setText("Module Added");
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Module Added !");
                        alert.setHeaderText(null);    
                        alert.setContentText("Timetable is now updated to reflect " + userModule +" being added ");
                        break;
                    case "ol":
                        displayText.setText("Module Overlaps");
                        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                        overlapAlert.setTitle("Error adding Module : " + userModule);
                        overlapAlert.setHeaderText(null);
                        overlapAlert.setContentText("Module : " + userModule +" already exists and was not added  ");
                        overlapAlert.show();

                        break;
                    case "ttf":
                        displayText.setText("Timetable Full");
                        Alert ttFullAlert = new Alert(Alert.AlertType.ERROR);
                        ttFullAlert.setTitle("Error adding Module : " + userModule);
                        ttFullAlert.setHeaderText(null);
                        ttFullAlert.setContentText("Module : " + userModule +" could not be added , A student can not exceed 5 modules   ");
                        ttFullAlert.show();

                        break;
                    case "im":
                        displayText.setText("Invalid Module");
                        Alert wrongFormatAlert = new Alert(Alert.AlertType.ERROR);
                        wrongFormatAlert.setTitle("Error adding Module : " + userModule);
                        wrongFormatAlert.setHeaderText(null);
                        wrongFormatAlert.setContentText("Module : " + userModule + " does not follow the proper module formatting e.g. CS4076");
                        wrongFormatAlert.show();

                        break;
                    default:
                        displayText.setText("Error Adding Module");
                        Alert unknownErrorAlert = new Alert(Alert.AlertType.ERROR);
                        unknownErrorAlert.setTitle("Error adding Module : " + userModule);
                        unknownErrorAlert.setHeaderText(null);
                        unknownErrorAlert.setContentText("An unknown error  ");
                        unknownErrorAlert.show();

                }
            } catch (Exception ex){
                displayText.setText("Error Adding Module");
            }
        });

//        title.getChildren().addAll(screenDescriptor);
        module.getChildren().addAll(moduleLabel, userInputModule);
        settings.getChildren().addAll(selectStartTime, timeSeparator, selectEndTime, timeDaySeparator, dayMenu);
        room.getChildren().addAll(roomLabel, userInputRoom);
        finishButton.getChildren().addAll(submitButton, backButton);

        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(10, 10, 10, 10));
        form.getChildren().addAll(screenDescriptor, module, settings, room, finishButton, displayText);

        stage.setOnCloseRequest((WindowEvent we) -> Utillity.quitApp(in, out));

        root.setTop(title);
        root.setCenter(form);

        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(String.valueOf(getClass().getResource("/bg1.png"))),
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
        stage.setTitle("Add Module");
        stage.getIcons().add(Utillity.icon);
        stage.setResizable(false);
        stage.show();
    }
}


