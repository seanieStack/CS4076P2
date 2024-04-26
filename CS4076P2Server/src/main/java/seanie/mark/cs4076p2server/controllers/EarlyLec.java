package seanie.mark.cs4076p2server.controllers;
import seanie.mark.cs4076p2server.models.Module;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class EarlyLec extends RecursiveTask<String> {
    public addModuleController addModuleController ;

    public removeModuleController removeModuleController ;

    public CheckAvailabilityController checkAvailabilityController ;

    private final List<Module> currentModules;

    private final String details;

    public EarlyLec (addModuleController addModuleController , removeModuleController removeModuleController,CheckAvailabilityController CheckAvailabilityController, List<Module> currentModules, String details){
        this.addModuleController = addModuleController ;
        this.removeModuleController = removeModuleController;
        this.checkAvailabilityController = CheckAvailabilityController ;
        this.details = details ;
        this.currentModules = currentModules;
    }

    @Override
    protected String compute() {
        // Was able to implement fork join but not divide and conquer , its too late for me to even start looking at that

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // Create tasks
        CheckOverlapTask checkTask = new CheckOverlapTask(checkAvailabilityController, details, currentModules);
        RemoveModuleTask removeTask = new RemoveModuleTask(removeModuleController, details, currentModules);
        AddModuleTask addTask = new AddModuleTask(addModuleController, details, currentModules);

        // Execute tasks
        boolean overlap = forkJoinPool.invoke(checkTask);
        if (!overlap) {
            boolean removed = forkJoinPool.invoke(removeTask);
            if (!removed) {
                boolean added = forkJoinPool.invoke(addTask);
                if(added) {
                    return "sr"; // Al operation successful
                }else {
                    return "fa"; // failed adding module
                }

            } else {
                return "fr";  // Failed  removing module
            }
        } else {
            return "ol";  // Overlap exists
        }


    }
}


