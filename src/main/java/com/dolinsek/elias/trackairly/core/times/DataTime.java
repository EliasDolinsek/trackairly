package com.dolinsek.elias.trackairly.core.times;

import com.dolinsek.elias.trackairly.core.data.DataObject;
import org.json.JSONObject;

public class DataTime extends DataCollection {

    private long startTime, stopTime, runningTime; //in millis
    private volatile boolean hiddenStart, hiddenStop;

    private DataTime() {
        startTime = 0;
        stopTime = 0;
        runningTime = 0;
        hiddenStart = false;
        hiddenStop = false;
    }

    public DataTime(long startTime, long stopTime, long runningTime) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.runningTime = runningTime;
    }

    public DataTime(long startTime, long stopTime, long runningTime, boolean hiddenStart, boolean hiddenStop) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.runningTime = runningTime;
        this.hiddenStart = hiddenStart;
        this.hiddenStop = hiddenStop;
    }

    public static DataTime defaultDataTime(){
        return new DataTime();
    }

    @Override
    public JSONObject toJSON() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("startTime", startTime);
        jsonObject.put("hiddenStart", hiddenStart);
        jsonObject.put("hiddenStop", hiddenStop);
        jsonObject.put("runningTime", runningTime);

        if (stopTime == 0){
            jsonObject.put("stopTime", System.currentTimeMillis());
        } else {
            jsonObject.put("stopTime", stopTime);
        }
        return jsonObject;
    }

    @Override
    public DataTime fromJSON(JSONObject jsonObject) {
        long startTime = 0, stopTime = 0, runningTime = 0;
        boolean hiddenStart = false, hiddenStop = false;

        try {
            startTime = jsonObject.getLong("startTime");
        } catch (Exception ignore) {}

        try {
            stopTime = jsonObject.getLong("stopTime");
        } catch (Exception ignore) {}

        try {
            runningTime = jsonObject.getLong("runningTime");
        } catch (Exception ignore) {}

        try {
            hiddenStart = jsonObject.getBoolean("hiddenStart");
        } catch (Exception ignore) {
            try {
                hiddenStart = jsonObject.getBoolean("dayChangeStart");
            } catch (Exception ignore1) { }
        }

        try {
            hiddenStop = jsonObject.getBoolean("hiddenStop");
        } catch (Exception ignore) {
            try {
                hiddenStart = jsonObject.getBoolean("dayChangeStop");
            } catch (Exception ignore1) { }
        }

        return new DataTime(startTime, stopTime, runningTime, hiddenStart, hiddenStop);
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

    public void setHiddenStop(boolean hiddenStop) {
        this.hiddenStop = hiddenStop;
    }

    public void setRunningTime(long runningTime) {
        this.runningTime = runningTime;
    }
}
