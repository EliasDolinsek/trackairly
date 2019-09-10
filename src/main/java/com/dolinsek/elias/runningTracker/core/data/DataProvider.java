package com.dolinsek.elias.runningTracker.core.data;

import com.dolinsek.elias.runningTracker.ConfigProvider;
import com.dolinsek.elias.runningTracker.core.times.Tracker;
import com.dolinsek.elias.runningTracker.core.times.TrackingData;

public class DataProvider {

    private static TrackingData trackingData;
    private static Tracker tracker;

    public static void init() throws Exception {
        trackingData = new OfflineDataHandler().getData(ConfigProvider.getConfig().getDataFile());
        tracker = new Tracker(trackingData);
    }

    public static TrackingData getTrackingData() {
        return trackingData;
    }

    public static Tracker getTracker() {
        return tracker;
    }
}
