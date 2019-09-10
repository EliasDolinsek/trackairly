package com.dolinsek.elias.runningTracker.core.times;

import org.json.JSONObject;

import java.awt.*;
import java.time.Instant;

public class DataTime extends DataCollection {

    private long startTime, stopTime;
    private volatile boolean isRunning = false;

    public DataTime() {
    }

    public DataTime(long startTime, long stopTime) {
        this.startTime = startTime;
        this.stopTime = stopTime;
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
}
