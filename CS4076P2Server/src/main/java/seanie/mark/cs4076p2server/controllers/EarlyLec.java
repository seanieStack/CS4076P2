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

        // Create tasks
        CheckOverlapTask checkTask = new CheckOverlapTask(checkAvailabilityController, details, currentModules);
        RemoveModuleTask removeTask = new RemoveModuleTask(removeModuleController, details, currentModules);
        AddModuleTask addTask = new AddModuleTask(addModuleController, details, currentModules);

        // Fork tasks
        checkTask.fork();
        removeTask.fork();
        addTask.fork();

        // Execute tasks separately (divide)
        Boolean checkTaskResult = checkTask.compute();
        Boolean removeTaskResult = removeTask.join();
        Boolean addTaskResult = addTask.join();
        //combine results ( conquer)
        if ( !checkTaskResult && removeTaskResult && addTaskResult) {
            return "sr"; // All operations successful
        } else if (checkTaskResult){
            return "ol"; // overlap exists
        } else if (!removeTaskResult){
            return "fa"; //Failed removing
        } else {
            return "fa"; // Failed adding
        }

    }
}


