package com.dolinsek.elias.runningTracker.core.times;

import org.json.JSONObject;

public abstract class DataCollection {

    public abstract JSONObject toJSON();
    public abstract long getTotalRunningTime();

    public String getTotalRunningTimeAsString(){
        return getTotalRunningTimeAsString().toString();
    }

}
