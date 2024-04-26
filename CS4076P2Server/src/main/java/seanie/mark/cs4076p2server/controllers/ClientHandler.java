package seanie.mark.cs4076p2server.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import seanie.mark.cs4076p2server.exceptions.IncorrectActionException;
import seanie.mark.cs4076p2server.models.Module;
import java.util.concurrent.ForkJoinPool;


public class ClientHandler implements Runnable{
    private final Socket client;
    private final List<Module> currentModules;
    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

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
                    String newDetails = " ";


                    // To ensure accurate timetable info 
                    if (details.length() > 70) {
                        String [] allDetails = details.split("!");
                        newDetails = allDetails[1];
                    }
                    

                    List<String> possibleActions = Arrays.asList("ac", "rc", "ds", "st","el");
                    if (!possibleActions.contains(action)) {
                        throw new IncorrectActionException("Incorrect action\n");
                    }

                    // Local Variable declaration for EarlyLec for case "el"
                    addModuleController addModuleController = new addModuleController(details,currentModules) ;
                    removeModuleController removeModuleController = new removeModuleController(details,currentModules) ;
                    CheckAvailabilityController checkAvailabilityController = new CheckAvailabilityController(details,currentModules) ;

                    switch (action) {
                        case "ac" -> out.println(ActionHandler.addClass(details, currentModules));
                        case "rc" -> out.println(ActionHandler.removeClass(details, currentModules));
                        case "ds" -> out.println(ActionHandler.displaySchedule(currentModules));
                       // case "el" -> out.println(new EarlyLec(addModuleController , removeModuleController, checkAvailabilityController, currentModules,details)); // Removed module ok
                        case "el" -> {
                            EarlyLec earlyLecTask = new EarlyLec(addModuleController, removeModuleController, checkAvailabilityController, currentModules, details);
                            String result = forkJoinPool.invoke(earlyLecTask); // Executes the task and waits for the result
                            System.out.println(result); // Now printing the actual result of the task
                            out.println(result);
                        }
                        //  case "el" -> out.println(earlyLec.compute());
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
