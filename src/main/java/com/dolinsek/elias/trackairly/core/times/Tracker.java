package com.dolinsek.elias.trackairly.core.times;

import com.dolinsek.elias.trackairly.ConfigProvider;
import com.dolinsek.elias.trackairly.core.data.DataProvider;
import com.dolinsek.elias.trackairly.core.data.OfflineDataHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Tracker {

    private int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    private DataTime dataTime;
    private TrackingData trackingData;

    private volatile boolean isRunning = false;
    private long seconds, previousRecordTime;

    private Timer timer = new Timer();

    public ArrayList<OnTimeChangedListener> onTimeChangedListeners = new ArrayList<>();

    public Tracker(TrackingData trackingData) {
        this.trackingData = trackingData;
    }

    public void start() throws IOException {
        if(isRunning) stop();
        seconds = 0;
        isRunning = true;

        dataTime = DataTime.defaultDataTime();
        trackingData.addDataTime(dataTime);

        dataTime.setStartTime(System.currentTimeMillis());

        startTimer();
    }

    public void stop() throws IOException {
        stop(System.currentTimeMillis());
    }

    private void stop(long time) throws IOException {
        if (dataTime != null && isRunning()){
            timer.cancel();
            isRunning = false;
            dataTime.setStopTime(time);
            new OfflineDataHandler().writeTrackingData(trackingData, ConfigProvider.getConfig().getDataFile());
        }
    }

    public void stopHidden() throws IOException {
        dataTime.setHiddenStop(true);
        stop();
    }

    public void startHidden() throws IOException {
        start();
        dataTime.setHiddenStart(true);
    }

    public boolean isRunning(){
        return isRunning;
    }

    public String getRunningTimeAsString(){
        if (dataTime == null || !isRunning) return "stopped";
        return DataCollection.getTotalRunningTimeAsString(dataTime.getTotalRunningTime(), true);
    }

    private void startTimer(){
        previousRecordTime = System.currentTimeMillis();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    checkAndReactToDateChange();
                    seconds++;
                    dataTime.setRunningTime(seconds * 1000);
                    onTimeChangedListeners.forEach(OnTimeChangedListener::onSecondChanged);
                    previousRecordTime = System.currentTimeMillis();
                    DataProvider.getTimeEventsTrigger().onDayRunningTimeChanged(DataProvider.getTodaysRunningTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000, 1000);
    }

    private void checkAndReactToDateChange() {
        final int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if (day != currentDay) {
            try {
                stopHidden();
                startHidden();
                day = currentDay;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
