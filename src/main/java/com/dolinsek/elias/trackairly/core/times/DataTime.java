package com.dolinsek.elias.trackairly.core.times;

import org.json.JSONObject;

public class DataTime extends DataCollection {

    private long startTime, stopTime;
    private volatile boolean isRunning = false, dayChangeStart, dayChangeStop;

    public DataTime() {
    }

    public DataTime(long startTime, long stopTime) {
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public DataTime(long startTime, long stopTime, boolean dayChangeStart, boolean dayChangeStop) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.dayChangeStart = dayChangeStart;
        this.dayChangeStop = dayChangeStop;
    }

    public void start(OnTimeChangedListener listener) {
        startTime = System.currentTimeMillis();
        stopTime = 0;
        isRunning = true;

        new Thread(() -> {
            while (isRunning) {
                listener.onSecondChanged();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stop() {
        stopTime = System.currentTimeMillis();
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public JSONObject toJSON() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("startTime", startTime);
        jsonObject.put("dayChangeStart", dayChangeStart);
        jsonObject.put("dayChangeStop", dayChangeStop);

        if (stopTime == 0){
            jsonObject.put("stopTime", System.currentTimeMillis());
        } else {
            jsonObject.put("stopTime", stopTime);
        }
        return jsonObject;
    }

    @Override
    public long getTotalRunningTime() {
        if (stopTime == 0) {
            return System.currentTimeMillis() - startTime;
        } else {
            return stopTime - startTime;
        }
    }

    public long getStartTime() {
        return startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public boolean isDayChangeStart() {
        return dayChangeStart;
    }

    public void setDayChangeStart(boolean dayChangeStart) {
        this.dayChangeStart = dayChangeStart;
    }

    public boolean isDayChangeStop() {
        return dayChangeStop;
    }

    public void setDayChangeStop(boolean dayChangeStop) {
        this.dayChangeStop = dayChangeStop;
    }
}
