package com.dolinsek.elias.runningTracker.core.times;

import org.json.JSONObject;

import java.time.Instant;

public class DataTime extends DataCollection {

    private long startTime, stopTime;

    public DataTime(){}

    public DataTime(long startTime, long stopTime) {
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public void start(){
        startTime = getCurrentTimeInUTC();
        stopTime = 0;
    }

    public void stop(){
        stopTime = getCurrentTimeInUTC();
    }

    private long getCurrentTimeInUTC(){
        return Instant.now().getEpochSecond() * 1000;
    }

    public JSONObject toJSON() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("startTime", startTime);
        jsonObject.put("stopTime", stopTime);
        return jsonObject;
    }

    @Override
    public long getTotalRunningTime() {
        return stopTime - startTime;
    }
}
