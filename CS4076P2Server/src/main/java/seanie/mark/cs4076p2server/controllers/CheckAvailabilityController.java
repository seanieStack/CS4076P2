package seanie.mark.cs4076p2server.controllers;


import seanie.mark.cs4076p2server.models.Module;
import java.util.List;
import java.util.concurrent.RecursiveTask;

//Multiple problems in this file , figure out why constructers go unused
public class CheckAvailabilityController  {

   private final String details;

    private final List<Module> currentModules;

    public CheckAvailabilityController(String details, List<Module> currentModules) {
        this.details = details;
        this.currentModules = currentModules;
    }

    public static Boolean checkOverlap(String details, List<Module> currentModules) {

        Boolean result ;
        String[] splitDetails = details.split(" ");// Adjust based on actual data format
        String moduleCode = splitDetails[0]; // Test these values to ensure correctness
        String time = splitDetails[1];
        String day = splitDetails[2];
        String room = splitDetails[3];

        result = UtilityFunctions.checkOverlap(time,day,currentModules);

        return result ;
    }
}