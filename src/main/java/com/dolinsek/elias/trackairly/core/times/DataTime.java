package com.dolinsek.elias.trackairly.core.times;

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
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            stopTime = jsonObject.getLong("stopTime");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            runningTime = jsonObject.getLong("runningTime");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            hiddenStart = jsonObject.getBoolean("hiddenStart");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            try {
                hiddenStart = jsonObject.getBoolean("dayChangeStart");
            } catch (Exception eOld) {
                System.err.println(eOld.getMessage());
            }
        }

        try {
            hiddenStop = jsonObject.getBoolean("hiddenStop");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            try {
                hiddenStart = jsonObject.getBoolean("dayChangeStop");
            } catch (Exception eOld) {
                System.err.println(eOld.getMessage());
            }
        }

        if (runningTime == 0) runningTime = stopTime - startTime;

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

    public boolean isHiddenStart() {
        return hiddenStart;
    }

    public boolean isHiddenStop() {
        return hiddenStop;
    }

    public void setHiddenStart(boolean hiddenStart) {
        this.hiddenStart = hiddenStart;
    }

    public void setRunningTime(long runningTime) {
        this.runningTime = runningTime;
    }
}
