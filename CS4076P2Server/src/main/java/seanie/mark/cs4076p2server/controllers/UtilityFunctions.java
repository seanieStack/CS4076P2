package seanie.mark.cs4076p2server.controllers;

import seanie.mark.cs4076p2server.models.Module;
import seanie.mark.cs4076p2server.models.TimetableEntry;

import java.util.*;

public class UtilityFunctions {

    static String getFullTimetableAsString(List<Module> currentModules) {
        StringBuilder timetableBuilder = new StringBuilder();

        for (Module module : currentModules) {
            timetableBuilder.append("").append(module.getModuleCode());
            for (TimetableEntry entry : module.getTimetable()) {
                timetableBuilder.append(",").append(entry.getDay())
                        .append(",").append(entry.getStartTime())
                        .append(",").append(entry.getEndTime())
                        .append(",").append(entry.getRoom())
                        .append("!") // Added this as symbol to indicate end of a class
                        .append("\n");
            }
            timetableBuilder.append("\n");
        }
        timetableBuilder.append("END_OF_TIMETABLE");
        return timetableBuilder.toString();
    }



    static int stringTimeToIntTime(String time){
        String temp = time.substring(0, 2);
        temp += time.substring(3);

        return Integer.parseInt(temp);
    }

    static boolean checkOverlap(String time, String day, List<Module> currentModules){
        List<TimetableEntry> allEntries = new ArrayList<>();
        for(Module m : currentModules){
            allEntries.addAll(m.getTimetable());
        }

        for(TimetableEntry m : allEntries){
            if(m.getDay().equals(day)){
                String[] timeSplit = time.split("-");
                int startTime1 = stringTimeToIntTime(timeSplit[0]);
                int endTime1 = stringTimeToIntTime(timeSplit[1]);

                int startTime2 = stringTimeToIntTime(m.getStartTime());
                int endTime2 = stringTimeToIntTime(m.getEndTime());

                return endTime1 > startTime2 && startTime1 < endTime2;

            }
        }
        return false;
    }

    public static boolean isValidModule(String module) {
        if (module.length() != 6) {
            return false;
        }

        for (int i = 0; i < 2; i++) {
            char ch = module.charAt(i);
            if (!Character.isLetter(ch)) {
                return false;
            }
        }

        for (int i = 2; i < 6; i++) {
            char ch = module.charAt(i);
            if (!Character.isDigit(ch)) {
                return false;
            }
        }

        return true;
    }

    static boolean isValidRoom(String room) {

        Set<String> campusBuildings = new HashSet<>(Arrays.asList(
                "SG", "S", "KGB", "KB", "CSG", "CS", "GLG", "GL", "FB", "FG", "F", "ERB", "ER",
                "LCB", "LC", "LB", "LG", "L", "SR", "PG", "PM", "P", "HSG", "HS", "A", "AM", "B",
                "BM", "CG", "C", "CM", "DG", "DM", "D", "EG", "E", "EM", "AD", "IWG", "IW",
                "GEM", "GEMS"
        ));

        if (!room.contains("-")) {
            return false;
        }

        String prefix = room.toUpperCase().split("-")[0];
        return campusBuildings.contains(prefix);
    }

}
