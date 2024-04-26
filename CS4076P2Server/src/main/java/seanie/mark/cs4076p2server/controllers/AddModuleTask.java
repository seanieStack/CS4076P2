package seanie.mark.cs4076p2server.controllers;


import java.util.List;
import java.util.concurrent.RecursiveTask;
import seanie.mark.cs4076p2server.models.Module;

public class AddModuleTask extends RecursiveTask<Boolean> {
    private addModuleController controller;
    private String details;
    private List<Module> currentModules;

    public AddModuleTask(addModuleController controller, String details, List<Module> currentModules) {
        this.controller = controller;
        this.details = details;
        this.currentModules = currentModules;
    }

    @Override
    protected Boolean compute() {
        if (!controller.addModule(details, currentModules)) {
            return  true ;
        } else {
            return  false;
        }

    }
}