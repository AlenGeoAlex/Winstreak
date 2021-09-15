package me.alen_alex.winstreak.utility;

public class TimeUtility {

    public static String getFormatedTime(int sec){
        int numberOfMinutes = ((sec % 86400 ) % 3600 ) / 60;
       int  numberOfSeconds = ((sec % 86400 ) % 3600 ) % 60  ;
       if(numberOfSeconds > 0)
            return (Message.parseMessage("&f"+(String.valueOf(numberOfMinutes)+" &aMins &f: &f"+String.valueOf(numberOfSeconds)+" &bSecs")));
       else
           return "&b&lUpdating Now";
    }

}
