package seanie.mark.cs4076p2client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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

        HBox root2 = new HBox();
        root2.setSpacing(10);

        Button submitButton = new Button("Submit");
        Button backButton = new Button("Return");
        root2.getChildren().addAll(submitButton, backButton);
        backButton.setOnAction(e -> returnToMain.run());

        Text displayText = new Text();

        submitButton.setOnAction(e -> {

                    try {
                        stage.setFullScreen(true); // Added this so all text displays properly
                        out.println("ds");

                        StringBuilder responseBuilder = new StringBuilder();
                        String line;
                        while (!(line = in.readLine()).equals("END_OF_TIMETABLE")) {
                            responseBuilder.append(line);
                            responseBuilder.append('\n'); // add the newline back
                        }
                        String response = responseBuilder.toString();



                        System.out.println(response);

                        GridPane timetable = new GridPane();

                        String [] times = Utillity.getTimes();
                        String [] days = Utillity.getDays();

                        timetable.setPadding(new javafx.geometry.Insets(12,12,12,12));
                        timetable.setVgap(100);
                        timetable.setHgap(100);


                        Label mondayLabel = new Label("Monday");
                        Label tuesdayLabel = new Label("Tuesday");
                        Label wednesdayLabel = new Label("Wednesday");
                        Label thursdayLabel = new Label("Thursday");
                        Label fridayLabel   = new Label("Friday");


                        GridPane.setConstraints(mondayLabel,1,0);
                        GridPane.setConstraints(tuesdayLabel,2,0);
                        GridPane.setConstraints(wednesdayLabel,3,0);
                        GridPane.setConstraints(thursdayLabel,4,0);
                        GridPane.setConstraints(fridayLabel,5,0);
                        //TODO: Refactor this into a loop later

                        for (int j = 0 ; j < times.length; j++){
                            Label timeLabel = new Label(times[j]);
                            GridPane.setConstraints(timeLabel,0,j+1);
                            timetable.getChildren().add(timeLabel);
                        }


                        // parts[i] =  0 = Module, 1 = Day , 2 = Start Time , 3 = End Time , 4 = Room
                        // response takes the form "CS4096,Monday,09:00,10:00,KGB-12";
                        int nodes[] =  Utillity.moduleNodes(response);
                        String parts []= Utillity.splitPayload(response);
                        Label exampleLabel = new Label(parts[0]+ " " +parts[4]);
                        GridPane.setConstraints(exampleLabel,nodes[0],nodes[1] );
                        //TODO: Add functionallity for multiple modules (loop)



                        timetable.getChildren().addAll(mondayLabel,tuesdayLabel,wednesdayLabel,thursdayLabel,fridayLabel,exampleLabel);
                        layout.getChildren().add(timetable);


                    } catch (Exception ex) {
                        ex.printStackTrace();
                        displayText.setText("Error Sending Display Schedule Request");
                    }
                });



        stage.setOnCloseRequest((WindowEvent we) -> Utillity.quitApp(in, out));

        layout.getChildren().addAll(root2, displayText);
    }
}
