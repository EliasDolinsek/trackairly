package com.dolinsek.elias.trackairly;

import com.dolinsek.elias.trackairly.core.data.DataHandler;
import org.json.JSONObject;

import java.io.File;

public class Config {

    public static File DEFAULT_CONFIG_FILE = new File("config.json");
    public static File DEFAULT_DATA_FILE = new File("data.json");

    private File dataFile;
    private boolean startTrackerOnLaunch, hideAfterAutostart, displayHideNotification;

    public Config(File dataFile, boolean startTrackerOnLaunch, boolean hideAfterAutostart, boolean displayHideNotification){
        this.dataFile = dataFile;
        this.startTrackerOnLaunch = startTrackerOnLaunch;
        this.hideAfterAutostart = hideAfterAutostart;
        this.displayHideNotification = displayHideNotification;
    }

    public static Config defaultConfig(){
        return new Config(Config.DEFAULT_DATA_FILE, true, true, true);
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

    public boolean hideAfterAutostart() {
        return hideAfterAutostart;
    }

    public void setHideAfterAutostart(boolean hideAfterAutostart) {
        this.hideAfterAutostart = hideAfterAutostart;
    }

    public boolean displayHideNotification() {
        return displayHideNotification;
    }

    public void setDisplayHideNotification(boolean displayHideNotification) {
        this.displayHideNotification = displayHideNotification;
    }

    public JSONObject toJSON(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataFileLocation", dataFile.getPath());
        jsonObject.put("startTrackerOnLaunch", startTrackerOnLaunch);
        jsonObject.put("hideAfterAutostart", hideAfterAutostart);
        jsonObject.put("displayHideNotification", displayHideNotification);
        return jsonObject;
    }
}
