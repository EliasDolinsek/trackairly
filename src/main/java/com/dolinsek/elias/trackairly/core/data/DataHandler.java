package com.dolinsek.elias.trackairly.core.data;

import com.dolinsek.elias.trackairly.Config;
import com.dolinsek.elias.trackairly.core.timeEvents.Action;
import com.dolinsek.elias.trackairly.core.times.TrackingData;

import java.io.File;
import java.util.ArrayList;

public interface DataHandler {

    Config getConfig(File file) throws Exception;
    TrackingData getTrackingData(File file) throws Exception;
    ArrayList<Action> getActions(File file) throws Exception;

    void writeTrackingData(TrackingData trackingData, File file) throws Exception;
    void writeConfig(Config config, File file) throws Exception;
    void writeActions(ArrayList<Action> actions, File file) throws Exception;

}
