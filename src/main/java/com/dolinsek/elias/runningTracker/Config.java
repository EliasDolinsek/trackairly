package com.dolinsek.elias.runningTracker;

import com.dolinsek.elias.runningTracker.core.data.DataHandler;
import com.dolinsek.elias.runningTracker.core.times.DataYear;
import com.dolinsek.elias.runningTracker.core.times.Tracker;
import com.dolinsek.elias.runningTracker.core.times.TrackingData;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class Config {

    public static File DEFAULT_CONFIG_FILE = new File("config.json");
    public static Config DEFAULT_CONFIG = new Config(DEFAULT_CONFIG_FILE);

    private File dataFile;
    private TrackingData trackingData;

    private Tracker tracker;

    public Config(File dataFile){
        this.dataFile = dataFile;
        trackingData = new TrackingData(new ArrayList<>());
        tracker = new Tracker(trackingData);
    }

    public Config(File dataFile, TrackingData trackingData) {
        this.dataFile = dataFile;
        this.trackingData = trackingData;
        tracker = new Tracker(trackingData);
    }

    public static Config fromDefaultDataHandler(DataHandler dataHandler) throws Exception {
        return dataHandler.getConfig(DEFAULT_CONFIG_FILE);
    }

    public static Config fromDataHandler(DataHandler dataHandler, File file) throws Exception {
        return dataHandler.getConfig(file);
    }

    public Tracker getTracker() {
        return tracker;
    }

    public File getDataFile() {
        return dataFile;
    }

    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }

    public JSONObject toJSON(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataFileLocation", dataFile.getPath());
        return jsonObject;
    }
}
