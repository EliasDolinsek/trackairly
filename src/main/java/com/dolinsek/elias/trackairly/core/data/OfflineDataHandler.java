package com.dolinsek.elias.trackairly.core.data;


import com.dolinsek.elias.trackairly.Config;
import com.dolinsek.elias.trackairly.core.timeEvents.Action;
import com.dolinsek.elias.trackairly.core.times.*;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class OfflineDataHandler implements DataHandler {

    @Override
    public Config getConfig(File file) throws Exception {
        if (!file.exists()) Config.defaultConfig();
        final String fileContent = getFileContent(file);

        if (fileContent.trim().isEmpty()) return Config.defaultConfig();
        final JSONObject jsonObject = new JSONObject(getFileContent(file));
        return Config.defaultConfig().fromJSON(jsonObject);
    }

    public TrackingData getTrackingData(File file) throws Exception {
        return trackingDataFromJSON(getFileContent(file));
    }

    @Override
    public ArrayList<Action> getActions(File file) throws Exception {
        return actionsFromJSON(getFileContent(file));
    }

    @Override
    public void writeActions(ArrayList<Action> actions, File file) throws Exception {
        if (!file.exists()) file.createNewFile();
        writeToFile(Action.actionsToJSON(actions).toString(), file);
    }

    private ArrayList<Action> actionsFromJSON(String json){
        if (json == null || json.trim().equals("")) return new ArrayList<>();
        final JSONObject jsonObject = new JSONObject(json);
        ArrayList<Action> actions = new ArrayList<>();

        try {
            actions = Action.actionsFromJSONArray(jsonObject.getJSONArray("actions"));
        } catch (Exception ignored) {}

        return actions;
    }

    public String getFileContent(File file) throws Exception {
        if (!file.exists()) {
            return "";
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }

    private TrackingData trackingDataFromJSON(String json) {
        if (json == null || json.trim().equals("")) return TrackingData.defaultTrackingData();
        return TrackingData.defaultTrackingData().fromJSON(new JSONObject(json));
    }

    public void writeTrackingData(TrackingData trackingData, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }

        writeToFile(trackingData.toJSON().toString(), file);
    }

    @Override
    public void writeConfig(Config config, File file) throws Exception {
        if (!file.exists()) {
            file.createNewFile();
        }

        writeToFile(config.toJSON().toString(), file);
    }

    void writeToFile(String content, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(content);
        bufferedWriter.close();
    }
}
