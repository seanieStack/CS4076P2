package seanie.mark.cs4076p2server.controllers;
import seanie.mark.cs4076p2server.models.Module;
import java.util.List;

public class CheckAvailabilityController  {

   private final String details;

    private final List<Module> currentModules;

    public CheckAvailabilityController(String details, List<Module> currentModules) {
        this.details = details;
        this.currentModules = currentModules;
    }

    public static Boolean checkOverlap(String details, List<Module> currentModules) {

        Boolean result ;
        String[] splitDetails = details.split(" ");
        String time = splitDetails[1];
        String day = splitDetails[2];

        result = UtilityFunctions.checkOverlap(time,day,currentModules);

        return result ;
    }
}