package com.dolinsek.elias.runningTracker.core.times;

import com.dolinsek.elias.runningTracker.ConfigProvider;
import com.dolinsek.elias.runningTracker.core.data.DataProvider;
import com.dolinsek.elias.runningTracker.core.data.OfflineDataHandler;

import java.io.IOException;

public class Tracker {

    private DataTime dataTime;
    private TrackingData trackingData;

    public Tracker(TrackingData trackingData) {
        this.trackingData = trackingData;
    }

    public void start(OnTimeChangedListener listener) throws IOException {
        if(dataTime != null) stop();
        dataTime = new DataTime();
        trackingData.addDataTime(dataTime);
        dataTime.start(listener);
    }

    public void stop() throws IOException {
        if (dataTime != null && isRunning()){
            dataTime.stop();
            new OfflineDataHandler().writeData(trackingData, ConfigProvider.getConfig().getDataFile());
        }
    }

    public boolean isRunning(){
        if (dataTime == null) return false;
        return dataTime.isRunning();
    }

    public String getRunningTimeAsString(){
        if (dataTime == null || !dataTime.isRunning()) return "stopped";
        return dataTime.getTotalRunningTimeAsString(dataTime.getTotalRunningTime());
    }
}
