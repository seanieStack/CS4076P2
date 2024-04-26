package seanie.mark.cs4076p2server.controllers;


import seanie.mark.cs4076p2server.models.Module;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import static java.lang.System.out; // Double check these are the correct imports
import static java.lang.System.in ;

public class removeModuleController {

    private final String details;
    private final List<Module> currentModules;

    public removeModuleController(String details, List<Module> currentModules) {
        this.details = details;
        this.currentModules = currentModules;

    }


    public static boolean removeModule (String details , List<Module> currentModules) {
        //Test this out
        try {
            //BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            Boolean result = false ; // might cause an error
            String[] splitDetails = details.trim().split(" "); // Should work now

            String moduleCode = splitDetails[0]; // Test these values to ensure correctness
            String time = splitDetails[1];
            String day = splitDetails[2];
            String room = splitDetails[3];
            /**
             Module m = new Module(moduleCode) ;
             if (splitDetails.length > 10 ){
             System.out.println(" > 10  details provided thus evaluated method to false ");
             return false ; // Incorrect number of details provided
             } else {
             m.removeTimetableEntry(startTime,day,room);
             return true ;
             }
             */

            String removeModuleOutput = " " + moduleCode + " " + time + " " + day + " " + room;
            //out.println(removeModuleOutput); // Attempt to remove the module
            String removed = ActionHandler.removeClass(removeModuleOutput, currentModules);
            if (removed.startsWith("cr")) {
                result = true ; // was removed
            }else {
                result = false ; // not removed
            }
           // out.println(ActionHandler.removeClass(removeModuleOutput,currentModules));
           // String message = in.readLine();
            //System.out.println(message);

            if (removed.contains("cr")) {
                return true; // Removal was successful
            } else if (removed.equals("nsc") || removed.equals("cnf")) {
                return false; // Removal was unsuccessful
            } else {
                return false;
            }

           // return result;
        } catch (Exception e) {
            e.printStackTrace();

            return false ; // False if error occurs
        }

    }
}

