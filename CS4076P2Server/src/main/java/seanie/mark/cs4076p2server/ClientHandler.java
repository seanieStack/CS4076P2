package seanie.mark.cs4076p2server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import seanie.mark.cs4076p2server.exceptions.IncorrectActionException;

public class ClientHandler implements Runnable{
    private final Socket client;
    private final List<Module> currentModules;

    public ClientHandler(Socket client, List<Module> currentModules){
        this.client = client;
        this.currentModules = currentModules;
    }

    @Override
    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            boolean running = true;

            while(running) {

                String message = in.readLine();
                System.out.println("Message received: " + message + " from: " + Thread.currentThread().getName());

                try {
                    String action = message.substring(0, 2);
                    String details = message.substring(2);

                    List<String> possibleActions = Arrays.asList("ac", "rc", "ds", "st");
                    if (!possibleActions.contains(action)) {
                        throw new IncorrectActionException("Incorrect action\n");
                    }

                    switch (action) {
                        case "ac" -> out.println(ActionHandler.addClass(details, currentModules));
                        case "rc" -> out.println(ActionHandler.removeClass(details, currentModules));
                        case "ds" -> out.println(ActionHandler.displaySchedule(details, currentModules));
                        case "st" -> {
                            System.out.println("TERMINATE");
                            out.println("TERMINATE");
                            in.close();
                            out.close();
                            client.close();
                            running = false;
                        }
                    }
                } catch (IncorrectActionException e) {
                    out.println(e.getMessage());
                }
            }
        }catch(IOException e){
            System.out.println("Error handling client");
        }
    }

}
