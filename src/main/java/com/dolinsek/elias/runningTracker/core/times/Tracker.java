package com.dolinsek.elias.runningTracker.core.times;

public class Tracker {

    private DataTime dataTime;
    private TrackingData trackingData;

    public Tracker(TrackingData trackingData) {
        this.trackingData = trackingData;
    }

    private void start(){
        if(dataTime != null) stop();
        dataTime = new DataTime();
        dataTime.start();
    }

    private void stop(){
        dataTime.stop();
        trackingData.addDataTime(dataTime);
        dataTime = null;
    }
}
