package com.dolinsek.elias.runningTracker;

import com.dolinsek.elias.runningTracker.core.data.DataHandler;
import com.dolinsek.elias.runningTracker.core.times.DataYear;
import com.dolinsek.elias.runningTracker.core.times.Tracker;
import com.dolinsek.elias.runningTracker.core.times.TrackingData;
import org.json.JSONObject;

import java.io.File;

public class Config {

    public static File DEFAULT_CONFIG_FILE = new File("config.json");
    public static File DEFAULT_DATA_FILE = new File("data.json");

    private File dataFile;
    private boolean startTrackerOnLaunch;

    public Config(File dataFile, boolean startTrackerOnLaunch){
        this.dataFile = dataFile;
        this.startTrackerOnLaunch = startTrackerOnLaunch;
    }

    public static Config defaultFromDataHandler(DataHandler dataHandler) throws Exception {
        return fromDataHandler(dataHandler, DEFAULT_CONFIG_FILE);
    }

    public static Config fromDataHandler(DataHandler dataHandler, File file) throws Exception {
        return dataHandler.getConfig(file);
    }

    public File getDataFile() {
        return dataFile;
    }

    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }

    public boolean startTrackerOnLaunch() {
        return startTrackerOnLaunch;
    }

    public void setStartTrackerOnLaunch(boolean startTrackerOnLaunch) {
        this.startTrackerOnLaunch = startTrackerOnLaunch;
    }

    public JSONObject toJSON(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataFileLocation", dataFile.getPath());
        jsonObject.put("startTrackerOnLaunch", startTrackerOnLaunch);
        return jsonObject;
    }
}
