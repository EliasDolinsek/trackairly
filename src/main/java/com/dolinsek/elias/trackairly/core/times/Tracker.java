package com.dolinsek.elias.trackairly.core.times;

import com.dolinsek.elias.trackairly.ConfigProvider;
import com.dolinsek.elias.trackairly.core.data.OfflineDataHandler;

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

    public void stopForDayChange() throws IOException {
        dataTime.setDayChangeStop(true);
        stop();
    }

    public void startForDayChange(OnTimeChangedListener listener) throws IOException {
        dataTime.setDayChangeStop(true);
        start(listener);
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
