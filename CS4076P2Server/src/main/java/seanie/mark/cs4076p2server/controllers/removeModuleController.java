package seanie.mark.cs4076p2server.controllers;


import seanie.mark.cs4076p2server.models.Module;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class removeModuleController extends RecursiveTask<Boolean> {

    private final String details;

    private final List<Module> currentModules;
    public removeModuleController(String details, List<Module> currentModules) {
        this.details = details;
        this.currentModules = currentModules;
    }

    @Override
    protected Boolean compute() {
    //Test this out

        String[] splitDetails = details.split(",");// Adjust based on actual data format
        String moduleCode = splitDetails[0]; // Test these values to ensure correctness
        String startTime = splitDetails[2];
        String day = splitDetails[1];
        String room = splitDetails[4];
        Module m = new Module(moduleCode) ;
        if (splitDetails.length > 10 ){
            System.out.println(" > 10  details provided thus evaluated method to false ");
            return false ; // Incorrect number of details provided
        } else {
            m.removeTimetableEntry(startTime,day,room);
            return true ;
        }

    }
}
