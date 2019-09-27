package com.dolinsek.elias.trackairly.core;

import org.json.JSONObject;

public class Action {

    private String name, notification, command;
    private int hours, minutes;

    private boolean displayNotification, shutdownDevice, exitTrackairly, runCustomCommand;

    public Action(String name, String notification, String command, int hours, int minutes, boolean displayNotification, boolean shutdownDevice, boolean exitTrackairly, boolean runCustomCommand) {
        this.name = name;
        this.notification = notification;
        this.command = command;
        this.hours = hours;
        this.minutes = minutes;
        this.displayNotification = displayNotification;
        this.shutdownDevice = shutdownDevice;
        this.exitTrackairly = exitTrackairly;
        this.runCustomCommand = runCustomCommand;
    }

    public Action(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public boolean isDisplayNotification() {
        return displayNotification;
    }

    public void setDisplayNotification(boolean displayNotification) {
        this.displayNotification = displayNotification;
    }

    public boolean isShutdownDevice() {
        return shutdownDevice;
    }

    public void setShutdownDevice(boolean shutdownDevice) {
        this.shutdownDevice = shutdownDevice;
    }

    public boolean isExitTrackairly() {
        return exitTrackairly;
    }

    public void setExitTrackairly(boolean exitTrackairly) {
        this.exitTrackairly = exitTrackairly;
    }

    public boolean isRunCustomCommand() {
        return runCustomCommand;
    }

    public void setRunCustomCommand(boolean runCustomCommand) {
        this.runCustomCommand = runCustomCommand;
    }

    public JSONObject toJSON(){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("notification", notification);
        jsonObject.put("command", command);

        jsonObject.put("hours", hours);
        jsonObject.put("minutes", minutes);

        jsonObject.put("displayNotification", displayNotification);
        jsonObject.put("shutdownDevice", shutdownDevice);
        jsonObject.put("exitTrackairly", exitTrackairly);
        jsonObject.put("runCustomCommand", runCustomCommand);

        return jsonObject;
    }

    public static Action fromJSON(JSONObject jsonObject){
        final Action action = new Action();
        action.setName(jsonObject.getString("name"));
        action.setNotification(jsonObject.getString("notification"));
        action.setCommand(jsonObject.getString("command"));

        action.setHours(jsonObject.getInt("hours"));
        action.setMinutes(jsonObject.getInt("minutes"));

        action.setDisplayNotification(jsonObject.getBoolean("name"));
        action.setShutdownDevice(jsonObject.getBoolean("name"));
        action.setExitTrackairly(jsonObject.getBoolean("name"));
        action.setRunCustomCommand(jsonObject.getBoolean("name"));

        return action;
    }
}
