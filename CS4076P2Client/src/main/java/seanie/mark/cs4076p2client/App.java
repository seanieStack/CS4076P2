package seanie.mark.cs4076p2client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



public class App extends Application {
    private Scene mainScene;
    private Stage stage;

    private static InetAddress host;
    private static final int PORT = 6558;



    @Override
    public void start(Stage stage){

        Socket link;
        BufferedReader in;
        PrintWriter out;
        try {
            link = new Socket(host, PORT);
            in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            out = new PrintWriter(link.getOutputStream(), true);

            BufferedReader finalIn = in;
            PrintWriter finalOut = out;

            this.stage = stage;


            VBox layout = new VBox(10); // Use a VBox layout with spacing of 10
            layout.getStyleClass().add("layout");
            StackPane root = new StackPane();
            Image image = new Image("file:src/main/resources/UL_LOGO.png");
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            layout.setBackground(new Background(backgroundImage));

            
            Label welcomelabel = new Label("Please select an option");

            Button addModule = new Button("Add Module");

            addModule.setOnAction(event -> new addModule(stage,this::returnHome, finalIn, finalOut));

            Button removeModule = new Button("Remove Module");
            removeModule.setOnAction(event -> new removeModule(stage,this::returnHome, finalIn, finalOut));

            Button showTimetable = new Button("Display timetable");
            showTimetable.setOnAction(event -> new showTimetable(stage,this::returnHome, finalIn, finalOut));
            Button quitApplication = new Button("Quit application");
            quitApplication.setOnAction((event -> Utillity.quitApp(finalIn, finalOut) ));


            layout.getChildren().addAll(welcomelabel, addModule, removeModule, showTimetable, quitApplication);

            mainScene = new Scene(layout, 500, 600);
            mainScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            stage.setScene(mainScene); // Set the scene on the stage
            stage.setTitle("Welcome Screen");
            stage.show();

            stage.setOnCloseRequest((WindowEvent we) -> Utillity.quitApp(finalIn, finalOut));

        }
        catch (IOException e){
            System.out.println("Unable to connect to server");
            System.exit(1);
        }


    }

    public  void returnHome () {
        stage.setScene(mainScene);
    }

    public static void main(String [] args ) {
        try{
            host = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e){
            System.out.println("Host ID not found!");
            System.exit(1);
        }

        launch();
    }
}

