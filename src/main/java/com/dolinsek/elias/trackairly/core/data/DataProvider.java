package com.dolinsek.elias.trackairly.core.data;

import com.dolinsek.elias.trackairly.ConfigProvider;
import com.dolinsek.elias.trackairly.core.timeEvents.Action;
import com.dolinsek.elias.trackairly.core.times.*;

import java.util.ArrayList;
import java.util.Calendar;

public class DataProvider {

    private static TrackingData trackingData;
    private static Tracker tracker;
    private static ArrayList<Action> actions;

    public static void init() throws Exception {
        final OfflineDataHandler offlineDataHandler = new OfflineDataHandler();
        trackingData = offlineDataHandler.getTrackingData(ConfigProvider.getConfig().getDataFile());
        tracker = new Tracker(trackingData);
        //actions = offlineDataHandler.getActions(ConfigP)
    }

    public static TrackingData getTrackingData() {
        return trackingData;
    }

    public static Tracker getTracker() {
        return tracker;
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
