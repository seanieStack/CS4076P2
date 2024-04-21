package seanie.mark.cs4076p2server.controllers;




import seanie.mark.cs4076p2server.models.Module;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class addModuleController extends RecursiveTask<Boolean> {

    private final String details;

    private final List<Module> currentModules;

    public addModuleController(String details, List<Module> currentModules) {
        this.details = details;
        this.currentModules = currentModules;
    }

    @Override
    protected Boolean compute() {
        //Test this out

        String[] splitDetails = details.split(",");// Adjust based on actual data format
        String moduleCode = splitDetails[0]; // Test these values to ensure correctness
        String startTime = splitDetails[2]; // New startTime
        String day = splitDetails[1]; // New day
        String room = splitDetails[4];  // Room does not change
        Module m = new Module(moduleCode) ;
        if (splitDetails.length > 8 ){ // 8 excludes 'command el'
            System.out.println(" > 10  details provided thus evaluated method to false ");
            return false ; // Incorrect number of details provided
        } else {
            m.addTimetableEntry(startTime,day,room); // Maybe some error checking here to ensure correctness
            System.out.println(m.getTimetable()); // To see if it has being removed
            return true ;
        }

    }
}
