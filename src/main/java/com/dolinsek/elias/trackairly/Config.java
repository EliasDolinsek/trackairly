package com.dolinsek.elias.trackairly;

import com.dolinsek.elias.trackairly.core.data.DataHandler;
import com.dolinsek.elias.trackairly.core.data.DataObject;
import org.json.JSONObject;

import java.io.File;

public class Config implements DataObject {

    public static String DEFAULT_VERSIONS_SERVER_URL = "http://localhost:8080/api/v1";

    public static File DEFAULT_CONFIG_FILE = new File("config.json");
    public static File DEFAULT_DATA_FILE = new File("data.json");
    public static File DEFAULT_ACTIONS_FILE = new File("actions.json");

    private String versionsServerURL;
    private File dataFile, actionsFile;
    private boolean startTrackerOnLaunch, hideAfterAutostart, displayHideNotification, displayActionNotifications, exitOnCloseRequest, checkForUpdatesOnLaunch;

    public Config(File dataFile, File actionsFile, boolean startTrackerOnLaunch, boolean hideAfterAutostart, boolean displayHideNotification, boolean displayActionNotifications, boolean exitOnCloseRequest, boolean checkForUpdatesOnLaunch, String versionsServerURL){
        this.dataFile = dataFile;
        this.actionsFile = actionsFile;
        this.startTrackerOnLaunch = startTrackerOnLaunch;
        this.hideAfterAutostart = hideAfterAutostart;
        this.displayHideNotification = displayHideNotification;
        this.displayActionNotifications = displayActionNotifications;
        this.exitOnCloseRequest = exitOnCloseRequest;
        this.checkForUpdatesOnLaunch = checkForUpdatesOnLaunch;
        this.versionsServerURL = versionsServerURL;
    }

    public static Config defaultConfig(){
        return new Config(DEFAULT_DATA_FILE, DEFAULT_ACTIONS_FILE, true, false, true, true, false, true, DEFAULT_VERSIONS_SERVER_URL);
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

    public void setDisplayActionNotifications(boolean displayActionNotifications) {
        this.displayActionNotifications = displayActionNotifications;
    }

    public boolean displayActionNotifications() {
        return displayActionNotifications;
    }

    public boolean exitOnCloseRequest() {
        return exitOnCloseRequest;
    }

    public void setExitOnCloseRequest(boolean exitOnCloseRequest) {
        this.exitOnCloseRequest = exitOnCloseRequest;
    }

    public void setActionsFile(File actionsFile) {
        this.actionsFile = actionsFile;
    }

    public File getActionsFile() {
        return actionsFile;
    }

    public boolean checkForUpdatesOnLaunch() {
		return checkForUpdatesOnLaunch;
	}

	public void setCheckForUpdatesOnLaunch(boolean checkForUpdatesOnLaunch) {
		this.checkForUpdatesOnLaunch = checkForUpdatesOnLaunch;
	}

    public String getVersionsServerURL() {
        return versionsServerURL;
    }

    public void setVersionsServerURL(String versionsServerURL) {
        this.versionsServerURL = versionsServerURL;
    }

    @Override
    public JSONObject toJSON(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataFileLocation", dataFile.getPath());
        jsonObject.put("actionsFileLocation", actionsFile.getPath());
        jsonObject.put("startTrackerOnLaunch", startTrackerOnLaunch);
        jsonObject.put("hideAfterAutostart", hideAfterAutostart);
        jsonObject.put("displayHideNotification", displayHideNotification);
        jsonObject.put("displayActionNotifications", displayActionNotifications);
        jsonObject.put("exitOnCloseRequest", exitOnCloseRequest);
        jsonObject.put("checkForUpdatesOnLaunch", checkForUpdatesOnLaunch);
        jsonObject.put("versionsServerURL", versionsServerURL);

        return jsonObject;
    }

    @Override
    public Config fromJSON(JSONObject jsonObject) {
        String versionsServerURL = DEFAULT_VERSIONS_SERVER_URL;
        File dataFile = DEFAULT_DATA_FILE, actionsFile = DEFAULT_ACTIONS_FILE;
        boolean startTrackerOnLaunch = true, hideAufterAutostart = true, displayHideNotification = true, displayActionNotifications = true, exitOnCloseRequest = false, checkForUpdatesOnLaunch = true;

        try {
            dataFile = new File(jsonObject.getString("dataFileLocation"));
        } catch (Exception e){
        	System.err.println(e.getMessage());
        }

        try {
            actionsFile = new File(jsonObject.getString("actionsFileLocation"));
        } catch (Exception e){
        	System.err.println(e.getMessage());
        }

        try {
            startTrackerOnLaunch = jsonObject.getBoolean("startTrackerOnLaunch");
        } catch (Exception e){
        	System.err.println(e.getMessage());
        }

        try {
            hideAufterAutostart = jsonObject.getBoolean("hideAfterAutostart");
        } catch (Exception e){
        	System.err.println(e.getMessage());
        }

        try {
            displayHideNotification = jsonObject.getBoolean("displayHideNotification");
        } catch (Exception e){
        	System.err.println(e.getMessage());
        }

        try {
            displayActionNotifications = jsonObject.getBoolean("displayActionNotifications");
        } catch (Exception e){
        	System.err.println(e.getMessage());
        }

        try {
            exitOnCloseRequest = jsonObject.getBoolean("exitOnCloseRequest");
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        }

        try {
            checkForUpdatesOnLaunch = jsonObject.getBoolean("checkForUpdatesOnLaunch");
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        }

        try {
            versionsServerURL = jsonObject.getString("versionsServerURL");
        } catch (Exception e){
            System.err.println(e.getMessage());
        }

        return new Config(dataFile, actionsFile, startTrackerOnLaunch, hideAufterAutostart, displayHideNotification, displayActionNotifications, exitOnCloseRequest, checkForUpdatesOnLaunch, versionsServerURL);
    }
}
