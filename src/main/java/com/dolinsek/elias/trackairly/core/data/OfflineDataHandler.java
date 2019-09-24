package com.dolinsek.elias.trackairly.core.data;


import com.dolinsek.elias.trackairly.Config;
import com.dolinsek.elias.trackairly.core.times.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class OfflineDataHandler implements DataHandler {

    public Config getConfigFromDefaultLocation() throws Exception {
        return getConfig(Config.DEFAULT_CONFIG_FILE);
    }

    @Override
    public Config getConfig(File file) throws Exception {
        if (!file.exists()) Config.defaultConfig();
        final String fileContent = getFileContent(file);

        if (fileContent.trim().isEmpty()) return Config.defaultConfig();
        final JSONObject jsonObject = new JSONObject(getFileContent(file));

        try {
            final File dataFile = new File(jsonObject.getString("dataFileLocation"));
            final boolean startTrackerOnLaunch = jsonObject.getBoolean("startTrackerOnLaunch");
            final boolean hideAufterAutostart = jsonObject.getBoolean("hideAfterAutostart");
            final boolean displayHideNotification = jsonObject.getBoolean("displayHideNotification");

            return new Config(dataFile, startTrackerOnLaunch, hideAufterAutostart, displayHideNotification);
        } catch (Exception e) {
            return Config.defaultConfig();
        }
    }

    public TrackingData getData(File file) throws Exception {
        return trackingDataFromJSON(getFileContent(file));
    }

    private String getFileContent(File file) throws Exception {
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
        if (json == null || json.trim().equals("")) return new TrackingData(new ArrayList<>());
        final JSONObject jsonObject = new JSONObject(json);
        final JSONArray yearsJSON = jsonObject.getJSONArray("years");

        final ArrayList<DataYear> dataYears = new ArrayList<>();
        for (int i = 0; i < yearsJSON.length(); i++) {
            final JSONObject year = yearsJSON.getJSONObject(i);
            dataYears.add(dataYearFromJSON(year));
        }

        return new TrackingData(dataYears);
    }

    private DataYear dataYearFromJSON(JSONObject json) {
        return new DataYear(json.getInt("year"), dataMonthsFromJSON(json));
    }

    private ArrayList<DataMonth> dataMonthsFromJSON(JSONObject jsonObject) {
        final JSONArray dataMonthsJSON = jsonObject.getJSONArray("dataMonths");
        final ArrayList<DataMonth> dataMonths = new ArrayList<>();

        for (int i = 0; i < dataMonthsJSON.length(); i++) {
            final JSONObject dataMonthJSON = dataMonthsJSON.getJSONObject(i);
            dataMonths.add(dataMonthFromJSON(dataMonthJSON));
        }

        return dataMonths;
    }

    private DataMonth dataMonthFromJSON(JSONObject jsonObject) {
        return new DataMonth(jsonObject.getInt("month"), dataDaysFromJSON(jsonObject));
    }

    private ArrayList<DataDay> dataDaysFromJSON(JSONObject jsonObject) {
        final JSONArray dataDaysJSON = jsonObject.getJSONArray("dataDays");
        final ArrayList<DataDay> dataDays = new ArrayList<>();

        for (int i = 0; i < dataDaysJSON.length(); i++) {
            final JSONObject dataDayJSON = dataDaysJSON.getJSONObject(i);
            dataDays.add(dataDayFromJSON(dataDayJSON));
        }

        return dataDays;
    }

    private DataDay dataDayFromJSON(JSONObject dataDayJSON) {
        return new DataDay(dataDayJSON.getInt("day"), dataTimesFromJSON(dataDayJSON));
    }

    private ArrayList<DataTime> dataTimesFromJSON(JSONObject dataDayJSON) {
        final JSONArray dataTimesJSON = dataDayJSON.getJSONArray("dataTimes");
        final ArrayList<DataTime> dataTimes = new ArrayList<>();

        for (int i = 0; i < dataTimesJSON.length(); i++) {
            final JSONObject dataTimeJSON = dataTimesJSON.getJSONObject(i);
            dataTimes.add(dataTimeFromJSON(dataTimeJSON));
        }

        return dataTimes;
    }

    private DataTime dataTimeFromJSON(JSONObject dataTimeJSON) {
        return new DataTime(dataTimeJSON.getLong("startTime"), dataTimeJSON.getLong("stopTime"), dataTimeJSON.getBoolean("dayChangeStart"), dataTimeJSON.getBoolean("dayChangeStop"));
    }

    public void writeData(TrackingData trackingData, File file) throws IOException {
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
