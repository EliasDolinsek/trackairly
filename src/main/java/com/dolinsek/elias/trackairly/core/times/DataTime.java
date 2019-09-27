package com.dolinsek.elias.trackairly.core.times;

import org.json.JSONObject;

import java.util.Timer;

public class DataTime extends DataCollection {

    private long startTime, stopTime, runningTime; //in millis
    private volatile boolean dayChangeStart, dayChangeStop;

    public DataTime() {
    }

    public DataTime(long startTime, long stopTime, long runningTime) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.runningTime = runningTime;
    }

    public DataTime(long startTime, long stopTime, long runningTime, boolean dayChangeStart, boolean dayChangeStop) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.runningTime = runningTime;
        this.dayChangeStart = dayChangeStart;
        this.dayChangeStop = dayChangeStop;
    }

    public JSONObject toJSON() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("startTime", startTime);
        jsonObject.put("dayChangeStart", dayChangeStart);
        jsonObject.put("dayChangeStop", dayChangeStop);
        jsonObject.put("runningTime", runningTime);

        if (stopTime == 0){
            jsonObject.put("stopTime", System.currentTimeMillis());
        } else {
            jsonObject.put("stopTime", stopTime);
        }
        return jsonObject;
    }

    @Override
    public long getTotalRunningTime() {
        return runningTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setDayChangeStop(boolean dayChangeStop) {
        this.dayChangeStop = dayChangeStop;
    }

    public void setRunningTime(long runningTime) {
        this.runningTime = runningTime;
    }
}
