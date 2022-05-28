package com.cgh.server.util;

public class TimeFormatter {

    public static String format(int seconds){
        int sec = seconds % 60;
        int min = (seconds / 60) % 60;
        int hours = (seconds / 60) / 60;

        String strSec = (sec < 10) ? "0" + sec : Integer.toString(sec);
        String strMin = (min < 10) ? "0" + min : Integer.toString(min);
        String strHours = (hours < 10) ? "0" + hours : Integer.toString(hours);

        return strHours + ":" + strMin + ":" + strSec;
    }
}
