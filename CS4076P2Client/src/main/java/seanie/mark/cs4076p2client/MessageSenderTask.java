package seanie.mark.cs4076p2client;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

class MessageSenderTask extends Task<String>{

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
            throw new Exception("Invaild Action");
        }

        switch(action){
            case "ac":
                System.out.println("ac");
                response = addClass();
                break;
            case "rc":
                response = removeClass();
                break;
            case "rq":
//                response = requests();
                break;
        }
        return response;
    }

    private String addClass() throws IOException {
        String userModule = details[0];
        String userDay = details[1];
        String startTime = details[2];
        String endTime = details[3];
        String userRoom = details[4];

        boolean differentTime = VerifyInput.isDifferentTime(startTime, endTime);
        boolean validLength = VerifyInput.isValidSessionLength(startTime, endTime);


        if (!differentTime) {
            throw new IllegalArgumentException("Please Input Different Times");
        } else if (!validLength) {
            throw new IllegalArgumentException("Modules Cannot Exceed 3hrs");
        }

        String resultText = "ac " + userModule + " " + startTime + "-" + endTime + " " + userDay + " " + userRoom;
        String response;
        out.println(resultText);

        response = in.readLine();

        return response;
    }

    private String removeClass() throws IOException {
        String userModule = details[0];
        String userDay = details[1];
        String startTime = details[2];
        String endTime = details[3];
        String userRoom = details[4];

        boolean differentTime = VerifyInput.isDifferentTime(startTime, endTime);
        boolean validLength = VerifyInput.isValidSessionLength(startTime, endTime);


        if (!differentTime) {
            throw new IllegalArgumentException("Please Input Different Times");
        } else if (!validLength) {
            throw new IllegalArgumentException("Modules Cannot Exceed 3hrs");
        }

        String resultText = "rc" + userModule + " " + startTime + "-" + endTime + " " + userDay + " " + userRoom;
        String response;
        try{
            out.println(resultText);

            response = in.readLine();

        } catch (Exception ex){
            throw ex;
        }
        return response;
    }
}

