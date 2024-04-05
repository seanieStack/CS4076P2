package seanie.mark.cs4076p2client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



import java.io.BufferedReader;
import java.io.PrintWriter;


public class showTimetable {
    public Stage stage;
    private final Runnable returnToMain;
    private final BufferedReader in;
    private final PrintWriter out;
    
    public showTimetable(Stage stage,  Runnable returnToMain, BufferedReader in, PrintWriter out) {
        this.stage = stage;
        this.returnToMain = returnToMain;
        this.in = in;
        this.out = out;
        initializeScreen();
    }

    private void  initializeScreen () {
        VBox layout = new VBox(10);

        
        Scene scene = new Scene(layout, 500, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Welcome Screen");


        Label moduleLabel = new Label("Module:");
        TextField userInputModule = new TextField();

        HBox root2 = new HBox();
        root2.setSpacing(10);

        Button submitButton = new Button("Submit");
        Button backButton = new Button("Return");
        root2.getChildren().addAll(submitButton, backButton);
        backButton.setOnAction(e -> returnToMain.run());

        Text displayText = new Text();

        submitButton.setOnAction(e -> {
                    String userModule = userInputModule.getText();

                    try {
                        out.println("ds " + userModule);
                        String response = in.readLine();

                        switch (response){
                            case "cp":
                                displayText.setText("Schedule Displayed in server Console");
                                break;
                            case "cnf":
                                displayText.setText("Module not found");
                                Alert notFoundAlert = new Alert(AlertType.ERROR); // Displays in Chinese 
                                notFoundAlert.setTitle("Error : Module not found");
                                notFoundAlert.setHeaderText(null);
                                notFoundAlert.setContentText("This module does not exist in our record , try adding it by selecting 'return' ,'add module'");
                                notFoundAlert.show();
                                break;
                        }

                    } catch (Exception ex) {
                        displayText.setText("Error Sending Display Schedule Request");
                    }
                });

        stage.setOnCloseRequest((WindowEvent we) -> Utillity.quitApp(in, out));

        layout.getChildren().addAll(moduleLabel, userInputModule, root2, displayText);
    }
}
