package seanie.mark.cs4076p2client.controllers;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageSenderTask extends Task<String>{

    String action;
    String[] details;

    BufferedReader in;
    PrintWriter out;

    public MessageSenderTask(BufferedReader in, PrintWriter out, String action, String... details){
        System.out.println("MessageSenderTask");
        this.action = action;
        this.details = details;
        this.in = in;
        this.out = out;
    }

    @Override
    protected String call() throws Exception {
        System.out.println("MessageSenderTask call");
        String response = null;

        if(!action.equals("ac") && !action.equals("rc") && !action.equals("rq")){
            throw new Exception("Invalid Action");
        }

        switch(action){
            case "ac":
                System.out.println("ac");
                response = addRemoveClass("ac");
                break;
            case "rc":
                response = addRemoveClass("rc");
                break;
            case "rq":
                response = requests();
                break;
        }
        return response;
    }

    private String addRemoveClass(String op) throws IOException {
        String userModule = details[0];
        String userDay = details[1];
        String startTime = details[2];
        String endTime = details[3];
        String userRoom = details[4];

        boolean differentTime = Utility.isDifferentTime(startTime, endTime);
        boolean validLength = Utility.isValidSessionLength(startTime, endTime);


        if (!differentTime) {
            throw new IllegalArgumentException("Please Input Different Times");
        } else if (!validLength) {
            throw new IllegalArgumentException("Modules Cannot Exceed 3hrs");
        }

        String resultText = op + " " + userModule + " " + startTime + "-" + endTime + " " + userDay + " " + userRoom;
        String response;
        out.println(resultText);

        response = in.readLine();

        return response;
    }

    private String requests() throws IOException {
        String userModule = details[0];
        String userDay = details[1];
        String startTime = details[2];
        String endTime = details[3];
        String userRoom = details[4];
        String differentStartTime = details[5];
        String differentEndTime = details[6];

        boolean originDifferentTime = Utility.isDifferentTime(startTime, endTime);
        boolean originValidLength = Utility.isValidSessionLength(startTime, endTime);

        boolean newDifferentTime = Utility.isDifferentTime(differentStartTime, differentEndTime);
        boolean newValidLength = Utility.isValidSessionLength(differentStartTime, differentEndTime);

        if (!originDifferentTime || !newDifferentTime) {
            throw new IllegalArgumentException("Please Input Different Times");
        } else if (!originValidLength || !newValidLength) {
            throw new IllegalArgumentException("Modules Cannot Exceed 3hrs");
        } else if (startTime.equals("09:00")) {
            throw new IllegalArgumentException("Start Time Cannot Be 9:00AM");
        } else if (startTime.equals(differentStartTime) || endTime.equals(differentEndTime)) {
            throw new IllegalArgumentException("Please Review Your Selections");
        }

        String resultText = "el " + userModule + " " + startTime + "-" + endTime + " " + userDay + " " + userRoom + " " + differentStartTime + "-" + differentEndTime;

        String response;

        out.println(resultText);

        response = in.readLine();

        return response;
    }
}


