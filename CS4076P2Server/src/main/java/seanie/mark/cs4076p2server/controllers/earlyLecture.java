package seanie.mark.cs4076p2server.controllers;

import seanie.mark.cs4076p2server.models.Module;

import java.util.List;
import java.util.concurrent.RecursiveTask;


public class earlyLecture extends RecursiveTask<Integer> {
    private final String oldDetails;
    private final String newDetails;
    private final int flag ;
    private final List<Module> currentModules;



    public earlyLecture(String oldDetails, String newDetails, List<Module> currentModules,int flag) {
        this.oldDetails = oldDetails ;
        this.newDetails = newDetails;
        this.currentModules =currentModules;
        this.flag = flag;


    }

    protected Integer compute() {
        boolean isValid = false ;
        boolean isNoOverlap = false ;
        boolean removedOk = false ;
        boolean addedOk = false ;
        String[] oldDetailsArray  = oldDetails.split(",");
        String[] newDetailsArray  = newDetails.split(",");
        //String resultTextAddNewDetails = "ac " + userModule + " " + newStartTime + " " + newEndTime + " " + userDay + " " + userRoom;String resultTextAddOldDetails = userModule +" " + startTime + " " + endTime +" " +userDay + " "+  userRoom;
        //        displayText.setText(resultTextAddNewDetails);
        String oldModule = oldDetailsArray[0]  ;
        String newModule = newDetailsArray[0]  ;
        String oldStartTime = oldDetailsArray[1];
        String newStartTime = newDetailsArray[1];
        String oldEndTime = oldDetailsArray[2];
        String newEndTime = newDetailsArray[2];
        String oldDay = oldDetailsArray[3];
        String newDay = newDetailsArray[3];
        String oldRoom = newDetailsArray[4];
        String newRoom = newDetailsArray[4];

        if(flag == 0 ){ // CheckInputValidity
            if (oldModule.equals(newModule) && !oldStartTime.equals(newStartTime) && !oldEndTime.equals(newEndTime) && oldDay.equals(newDay) && oldRoom.equals(newRoom)){
                isValid = true ;
                return 101; // indicates success
            } else {
                return 201; // indicates failure
            }
        }else if (flag == 1 && isValid) { //CheckOverlap , might need to make use of isValid operand
            //String time, String day, List<Module> currentModules
            if (UtilityFunctions.checkOverlap(newStartTime,newDay,currentModules)){
                isNoOverlap = true;
                return 102 ;  // indicates success
            }else {
                return  202 ; // indicates failure
            }
        } else if (flag == 2  && isNoOverlap) {
            try {
                String removeClass = ActionHandler.removeClass(oldDetails,currentModules);
                if(removeClass.startsWith("cr")){
                    removedOk = true ;
                    return  103 ; // indicates success
                }else if (removeClass.startsWith("nsc") || removeClass.startsWith("cnf")) {
                    removedOk = false;
                    return 203 ;  //indicates failure
                }
            }catch (Exception e ) {
                e.printStackTrace();
            }
        }else if (flag == 3 && removedOk) {
            try{
                String addClass = ActionHandler.addClass(newDetails,currentModules);
                if(addClass.startsWith("ca")) {
                    addedOk = true ;
                    return 104 ; // indicates success
                }else if (addClass.startsWith("im") || addClass.startsWith("ttf") ||addClass.startsWith("ol")) {
                    addedOk = false ;
                    return  204 ; // indicates failure
                }
            }catch (Exception err) {
                err.printStackTrace();
            }
        }
        earlyLecture checkInputValidity = new earlyLecture(oldDetails,newDetails,currentModules, 0) ;
        checkInputValidity.fork();
        earlyLecture checkOverlap = new earlyLecture(oldDetails,newDetails,currentModules,1);
        checkOverlap.fork();
        earlyLecture removeModule = new earlyLecture(oldDetails,newDetails,currentModules,2);
        removeModule.fork() ;
        earlyLecture addModule = new earlyLecture(oldDetails,newDetails,currentModules,3);
        addModule.fork();
        int checkInputResult = checkInputValidity.join();
        int checkOverlapResult = checkOverlap.join();
        int removeModuleResult = removeModule.join();
        int addModuleResult = addModule.join();

        if (checkInputResult == 101 && checkOverlapResult ==102 && removeModuleResult == 103 && addModuleResult ==104) {
            return 1; //success
        } else {
            return  0 ; //failure
        }


    }


}
