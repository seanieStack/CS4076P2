package seanie.mark.cs4076p2server.controllers;




import seanie.mark.cs4076p2server.models.Module;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class addModuleController  {

    private final String details;

    private final List<Module> currentModules;



    public addModuleController(String details, List<Module> currentModules ) {
        this.details = details;
        this.currentModules = currentModules;
    }


    public static Boolean addModule(String details, List<Module> currentModules) {
        //Test this out
        try {
            boolean result = false; //default value

            String[] splitDetails = details.trim().split(" ");
            String moduleCode = splitDetails[0];
            String newTime = splitDetails[4]; // New startTime
            String day = splitDetails[2]; // New day
            String room = splitDetails[3];  // Room does not change


            String addText = " " + moduleCode + " " + newTime + " " + day + " " + room;

            String added = ActionHandler.addClass(addText,currentModules);

            ActionHandler.addClass(addText,currentModules);

            if (added.startsWith(" ca")) {
                result = true ; // was added
                System.out.println("added ok ");
            }
            else if (added.startsWith(" im")) {
                result = false  ;
                System.out.println(" invalid module");
            }else if (added.startsWith(" ol")) {
                result = false  ;
                System.out.println(" overlapping");
            }else if (added.startsWith(" ir")) {
                result = false  ;
                System.out.println(" invalid room");
            }else if (added.startsWith(" tff")) {
                result = false  ;
                System.out.println(" tff");
            }

            return  result ;
        } catch (Exception e ) {
            e.printStackTrace();
            return false  ; // if an error occurs
        }

    }
}
