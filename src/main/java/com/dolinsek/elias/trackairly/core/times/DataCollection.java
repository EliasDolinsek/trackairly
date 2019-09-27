package com.dolinsek.elias.trackairly.core.times;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class DataCollection {

    public abstract JSONObject toJSON();

    public abstract long getTotalRunningTime();

    public static String getTotalRunningTimeAsString(long millis, boolean includeSeconds) {
        if (includeSeconds){
            return String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        } else {
            return String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
        }
    }

    public static String monthToString(int month) {
        switch (month) {
            case 0:
                return "JAN";
            case 1:
                return "FEB";
            case 2:
                return "MAR";
            case 3:
                return "APR";
            case 4:
                return "MAY";
            case 5:
                return "JUL";
            case 6:
                return "JLY";
            case 7:
                return "AUG";
            case 8:
                return "SEP";
            case 9:
                return "OCT";
            case 10:
                return "NOV";
            case 11:
                return "DEC";
            default:
                return "N/A";
        }
    }

    public static String yearDateToString(long millis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(new Date(millis));
    }

    public void sort() {
    }


}
