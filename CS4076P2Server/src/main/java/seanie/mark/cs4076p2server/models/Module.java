package seanie.mark.cs4076p2server.models;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private final String moduleCode;
    private final List<TimetableEntry> timetable;

    public Module(String moduleCode){
        this.moduleCode = moduleCode;
        this.timetable = new ArrayList<>();
    }

    public void addTimetableEntry(String time,String day, String room){
        timetable.add(new TimetableEntry(time, day, room));
    }

    public String getModuleCode(){
        return moduleCode;
    }

    public List<TimetableEntry> getTimetable(){
        return timetable;
    }

    public String removeTimetableEntry(String time, String day, String room) {
        for(TimetableEntry t : timetable){
            if(t.getTime().equals(time) && t.getDay().equals(day) && t.getRoom().equals(room)){
                timetable.remove(t);
                return "cr";
            }
        }
        return "nsc";
    }
}
