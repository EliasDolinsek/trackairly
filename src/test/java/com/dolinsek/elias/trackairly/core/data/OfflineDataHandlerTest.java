package com.dolinsek.elias.trackairly.core.data;

import com.dolinsek.elias.trackairly.core.times.DataDay;
import com.dolinsek.elias.trackairly.core.times.DataMonth;
import com.dolinsek.elias.trackairly.core.times.TrackingData;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class OfflineDataHandlerTest {

    @org.junit.Test
    public void getConfig() {
        OfflineDataHandler offlineDataHandler = new OfflineDataHandler();
        TrackingData trackingData =  null;

        try {
            trackingData = offlineDataHandler.getTrackingData(new File("/Users/eliasdolinsek/development/java-development/RunningTracker/src/test/java/com/dolinsek/elias/trackairly/core/data/test_data.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(trackingData.getDataYears().size(), 1);

        ArrayList<DataMonth> dataMonths = trackingData.getDataYears().get(0).getDataMonths();
        assertEquals(1, dataMonths.size());
        assertEquals(8, dataMonths.get(0).getMonth());

        ArrayList<DataDay> dataDays = dataMonths.get(0).getDataDays();
        assertEquals(4, dataDays.size());

        DataDay firstDataDay = dataDays.get(0);
        System.out.println(firstDataDay.toJSON());
    }

    @org.junit.Test
    public void getTrackingData() {
    }

    @org.junit.Test
    public void getActions() {
    }
}