package com.dolinsek.elias.runningTracker.core.data;

import com.dolinsek.elias.runningTracker.Config;
import com.dolinsek.elias.runningTracker.core.times.DataYear;
import com.dolinsek.elias.runningTracker.core.times.TrackingData;

import java.io.File;

public interface DataHandler {

    Config getConfig(File file) throws Exception;
    TrackingData getData(File file) throws Exception;
    void writeData(TrackingData trackingData, File file) throws Exception;
    void writeConfig(Config config, File file) throws Exception;

}
