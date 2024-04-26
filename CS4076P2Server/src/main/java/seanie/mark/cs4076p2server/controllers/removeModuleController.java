package seanie.mark.cs4076p2server.controllers;


import seanie.mark.cs4076p2server.models.Module;
import java.util.List;




public class removeModuleController {

    private final String details;
    private final List<Module> currentModules;

    public removeModuleController(String details, List<Module> currentModules) {
        this.details = details;
        this.currentModules = currentModules;

    }


    public static boolean removeModule (String details , List<Module> currentModules) {

        try {

            Boolean result = false ;
            String[] splitDetails = details.trim().split(" ");

            String moduleCode = splitDetails[0];
            String time = splitDetails[1];
            String day = splitDetails[2];
            String room = splitDetails[3];

            String removeModuleOutput = " " + moduleCode + " " + time + " " + day + " " + room;
            String removed = ActionHandler.removeClass(removeModuleOutput, currentModules);

            if (removed.contains("cr")) {
                return true; // Removal was successful
            } else if (removed.equals("nsc") || removed.equals("cnf")) {
                return false; // Removal was unsuccessful
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();

            return false ; // False if error occurs
        }

    }
}

