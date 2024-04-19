package seanie.mark.cs4076p2server.controllers;

import seanie.mark.cs4076p2server.models.Module;

import java.util.ArrayList;
import java.util.List;

public class ActionHandler {
    static synchronized String addClass(String details, List<Module> currentModules) {
        if(currentModules.size() < 5){

            String[] parts = details.strip().split(" ");

            String moduleCode = parts[0];
            String time = parts[1];
            String day = parts[2];
            String room = parts[3];

            boolean overlapping = UtilityFunctions.checkOverlap(time, day, currentModules);
            if(overlapping){

                return "ol";
            }

            boolean validModule = UtilityFunctions.isValidModule(moduleCode);
            if (!validModule) {

                return "im";
            }

            boolean validRoom = UtilityFunctions.isValidRoom(room);
            if (!validRoom) {

                return "ir";
            }


            List<String> currentModulesNames = new ArrayList<>();
            for(Module m : currentModules){
                currentModulesNames.add(m.getModuleCode());
            }


            if (!currentModulesNames.contains(moduleCode)){
                Module module = new Module(moduleCode);
                module.addTimetableEntry(time, day, room);
                currentModules.add(module);
            }
            else{
                for(Module m : currentModules){
                    if(m.getModuleCode().equals(moduleCode)){
                        m.addTimetableEntry(time, day, room);
                    }
                }
            }

            return "ca";

        }
        else {

            return "ttf";
        }
    }

    static synchronized String removeClass(String details, List<Module> currentModules) {
        String[] parts = details.strip().split(" ");

        String moduleCode = parts[0];
        String time = parts[1];
        String day = parts[2];
        String room = parts[3];

        boolean removed = false;

        for(Module m : currentModules){
            if(m.getModuleCode().equals(moduleCode) ) {
                if (m.removeTimetableEntry(time, day, room).equals("cr")) {
                    removed = true;
                } else {
                    return "nsc";
                }
            }
        }
        if (removed){
            return "cr" + " " + time + " " + day + " " + room;
        }
        else{
            return "cnf";
        }
    }

    static String displaySchedule(String details, List<Module> currentModules) {
//        String[] parts = details.strip().split(" ");
//
//        String moduleCode = parts[0];
//
//        for(Module m : currentModules){
//            if(m.getModuleCode().equals(moduleCode)){
//                List<TimetableEntry> timetable = m.getTimetable();
//                for(TimetableEntry t : timetable){
//                    System.out.println(t.getTime() + " " + t.getDay() + " " + t.getRoom());
//                }
//                return "cp";
//            }
//        }
//        return "cnf";
//    }

        String x = UtilityFunctions.getFullTimetableAsString(currentModules);

        System.out.println(x);

        return x;
    }

}


