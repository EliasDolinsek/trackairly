package com.dolinsek.elias.trackairly.core.data;

import com.dolinsek.elias.trackairly.ConfigProvider;
import com.dolinsek.elias.trackairly.core.timeEvents.Action;
import com.dolinsek.elias.trackairly.core.timeEvents.TimeEvent;
import com.dolinsek.elias.trackairly.core.timeEvents.TimeEventsTrigger;
import com.dolinsek.elias.trackairly.core.times.*;

import java.util.ArrayList;
import java.util.Calendar;

public class DataProvider {

    private static TrackingData trackingData;
    private static Tracker tracker;
    private static ArrayList<Action> actions;
    private static TimeEventsTrigger timeEventsTrigger;
    
    private static final OfflineDataHandler offlineDataHandler = new OfflineDataHandler();

    public static void init() throws Exception {
        trackingData = offlineDataHandler.getTrackingData(ConfigProvider.getConfig().getDataFile());
        tracker = new Tracker(trackingData);
        actions = offlineDataHandler.getActions(ConfigProvider.getConfig().getActionsFile());
        timeEventsTrigger = new TimeEventsTrigger(actions);
        timeEventsTrigger.checkDayTimeChanges();
    }

    public static TrackingData getTrackingData() {
        return trackingData;
    }

    public static Tracker getTracker() {
        return tracker;
    }

    public static ArrayList<Action> getActions() {
		return actions;
	}
    
    public static void addAction(Action action) {
    	actions.add(action);
    }

    public static TimeEventsTrigger getTimeEventsTrigger() {
        return timeEventsTrigger;
    }

    public static void updateTimeEventsTriggerData(){
        timeEventsTrigger.setActions(actions);
    }

    public static long getTodaysRunningTime(){
        try {
            return DataProvider.getThisDataDay(DataProvider.getThisDataMonth(DataProvider.getThisDataYear())).getTotalRunningTime();
        } catch (Exception e){
            System.err.println("No tracking data (DataProvider)");
            return 0;
        }
    }

    public static DataYear getThisDataYear() {
        for (DataYear dataYear : DataProvider.getTrackingData().getDataYears()) {
            if (dataYear.getYear() == Calendar.getInstance().get(Calendar.YEAR)) {
                return dataYear;
            }
        }

        return null;
    }

    public static DataMonth getThisDataMonth(DataYear dataYear){
        if (dataYear == null) return null;
        for (DataMonth dataMonth:dataYear.getDataMonths()){
            if (dataMonth.getMonth() == Calendar.getInstance().get(Calendar.MONTH)){
                return dataMonth;
            }
        }

        return null;
    }

    public static DataDay getThisDataDay(DataMonth dataMonth){
        if (dataMonth == null) return null;
        for (DataDay dataDay:dataMonth.getDataDays()){
            if (dataDay.getDay() == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
                return dataDay;
            }
        }

        return null;
    }
}
