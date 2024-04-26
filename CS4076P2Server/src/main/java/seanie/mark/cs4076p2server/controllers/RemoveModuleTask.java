package seanie.mark.cs4076p2server.controllers;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import seanie.mark.cs4076p2server.models.Module;

public class RemoveModuleTask extends RecursiveTask<Boolean> {
    private removeModuleController controller;
    private String details;
    private List<Module> currentModules;

    public RemoveModuleTask(removeModuleController controller, String details, List<Module> currentModules) {
        this.controller = controller;
        this.details = details;
        this.currentModules = currentModules;
    }

    @Override
    protected Boolean compute() {
        return controller.removeModule(details, currentModules);
    }
}