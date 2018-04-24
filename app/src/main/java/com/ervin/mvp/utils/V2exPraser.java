package com.ervin.mvp.utils;

public class V2exPraser {

    public static String parseId(String str) {
        int idEnd = str.indexOf("#");
        return str.substring(3, idEnd);
    }

    public static String parseTime(String str) {
        String[] time = str.split(";");
        return time[2].replace("&nbsp","");
        /*int timeEnd = str.indexOf("  •");
        if (timeEnd == -1) {
            return str;
        }
        return str.substring(0, timeEnd);*/
    }
}
