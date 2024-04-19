package seanie.mark.cs4076p2client.views;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import seanie.mark.cs4076p2client.App;
import seanie.mark.cs4076p2client.controllers.MessageSenderTask;
import seanie.mark.cs4076p2client.controllers.Utility;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Objects;

//TODO: More input checks , then Remove module and Add it back with the new details

public class requests {
    public Stage stage;
    private final Runnable returnToMain;
    private final BufferedReader in;
    private final PrintWriter out;

    public requests() {
        this.stage = homeScreen.stage;
        this.returnToMain = homeScreen::returnHome;
        this.in = App.in;
        this.out = App.out;
        initializeScreen();
    }


    private void initializeScreen() {
        BorderPane root = new BorderPane();

        //Hbox for the setting such as startTime, endTime and Day
        HBox settings = new HBox();
        settings.setPadding(new Insets(5, 0, 0, 0));
        settings.setAlignment(Pos.CENTER);

        //Hbox for the module label and code
        HBox module = new HBox();
        module.setPadding(new Insets(5, 0, 0, 0));
        module.setAlignment(Pos.CENTER);

        //Hbox for the room label and name
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

        Label screenDescriptor = new Label("Request Change of Timetable");
        screenDescriptor.setStyle("-fx-font-size: 20pt; -fx-font-weight: bold;");

        Label moduleLabel = new Label("Module:");
        TextField userInputModule = new TextField();

        ChoiceBox<String> selectStartTime = new ChoiceBox<>();
        selectStartTime.getItems().addAll("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        selectStartTime.setValue("09:00");

        Label timeSeparator = new Label("-");
        Label timeSeparator2 = new Label("-");

        ChoiceBox<String> selectEndTime = new ChoiceBox<>();
        selectEndTime.getItems().addAll("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        selectEndTime.setValue("10:00");

        Label timeDaySeparator = new Label(" on ");

        ChoiceBox<String> dayMenu = new ChoiceBox<>();
        dayMenu.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        dayMenu.setValue("Monday");


        Label roomLabel = new Label("Room:");
        TextField userInputRoom = new TextField();

        HBox newTimes = new HBox();
        newTimes.setPadding(new Insets(5, 0, 0, 0));
        newTimes.setAlignment(Pos.CENTER);

        Label showOldTime = new Label("Old Times:");
        Label showNewTime = new Label("New Times:");

        ChoiceBox<String> selectNewStartTime = new ChoiceBox<>();
        selectNewStartTime.getItems().addAll(Utility.getTimes());
        selectNewStartTime.setValue("10:00");

        ChoiceBox<String> selectNewEndTime = new ChoiceBox<>();
        selectNewEndTime.getItems().addAll(Utility.getTimes());
        selectNewEndTime.setValue("10:00");


        VBox root2 = new VBox();
        root2.setAlignment(Pos.CENTER);
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

            Task<String> submitRequestClass = new MessageSenderTask(in, out,"rq", userModule, userDay, startTime, endTime, userRoom, differentStartTime, differentEndTime);
            submitRequestClass.setOnSucceeded(ev -> {
                String response = submitRequestClass.getValue();
//                switch (response){
//                    //TODO: Need Server setup to handle this
//                }
            });
            submitRequestClass.setOnFailed(ev ->{
                Throwable ex = submitRequestClass.getException();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Data Provided");
                alert.setHeaderText("Invalid Data Provided");
                alert.setContentText(ex.getMessage());
                alert.show();
            });

            new Thread(submitRequestClass).start();
        });

        title.getChildren().addAll(screenDescriptor);
        module.getChildren().addAll(moduleLabel, userInputModule);
        settings.getChildren().addAll(selectStartTime, timeSeparator,  selectEndTime, timeDaySeparator, dayMenu);
        room.getChildren().addAll(roomLabel, userInputRoom);
        newTimes.getChildren().addAll(selectNewStartTime, timeSeparator2, selectNewEndTime);
        finishButton.getChildren().addAll(submitButton, backButton);

        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(10, 10, 10, 10));
        form.getChildren().addAll(module, showOldTime, settings, room, showNewTime, newTimes, finishButton/*, displayText*/);

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
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Make request");
        stage.getIcons().add(Utility.icon);
        stage.setResizable(false);
        stage.show();
    }

}

