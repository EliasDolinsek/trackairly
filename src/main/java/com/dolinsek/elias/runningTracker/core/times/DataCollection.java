package com.dolinsek.elias.runningTracker.core.times;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public abstract class DataCollection {

    public abstract JSONObject toJSON();
    public abstract long getTotalRunningTime();

    public static String getTotalRunningTimeAsString(long millis){
        return String.format("%02dh %02dm %02ds",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

}
