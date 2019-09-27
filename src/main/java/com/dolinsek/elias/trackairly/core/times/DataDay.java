package com.dolinsek.elias.trackairly.core.times;

import com.dolinsek.elias.trackairly.core.data.DataObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataDay extends DataCollection {

    private int day;
    private ArrayList<DataTime> dataTimes;

    public DataDay(int day, ArrayList<DataTime> dataTimes) {
        this.day = day;
        this.dataTimes = dataTimes;
    }

    private DataDay(){
        day = 0;
        dataTimes = new ArrayList<>();
    }

    public static DataDay defaultDataDay(){
        return new DataDay();
    }
    public static DataDay emptyDataDay(int day){
        return new DataDay(day, new ArrayList<>());
    }

    public int getDay() {
        return day;
    }

    public ArrayList<DataTime> getDataTimes() {
        return dataTimes;
    }

    @Override
    public JSONObject toJSON() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("day", day);

        JSONArray jsonArray = new JSONArray();
        for (DataTime dataTime : dataTimes) {
            jsonArray.put(dataTime.toJSON());
        }
        jsonObject.put("dataTimes", jsonArray);

        return jsonObject;
    }

    @Override
    public DataDay fromJSON(JSONObject jsonObject) {
        int day = 0;
        ArrayList<DataTime> dataTimes = new ArrayList<>();

        try {
            day = jsonObject.getInt("day");
        } catch (Exception ignore) {}

        try {
            dataTimes = dataTimesFromJSONArray(jsonObject.getJSONArray("dataTimes"));
        } catch (Exception ignore) {}

        return new DataDay(day, dataTimes);
    }

    private ArrayList<DataTime> dataTimesFromJSONArray(JSONArray jsonArray){
        ArrayList<DataTime> dataTimes = new ArrayList<>();
        for (int i = 0; i<jsonArray.length(); i++){
            dataTimes.add(DataTime.defaultDataTime().fromJSON(jsonArray.getJSONObject(i)));
        }

        return dataTimes;
    }

    public void addDataTime(DataTime dataTime){
        dataTimes.add(dataTime);
    }

    @Override
    public long getTotalRunningTime() {
        long totalRunningTime = 0;
        for (DataTime dataTime : dataTimes) {
            totalRunningTime += dataTime.getTotalRunningTime();
        }

        return totalRunningTime;
    }

    @Override
    public void sort() {
        dataTimes.sort((o1, o2) -> (int) (o1.getStartTime() - o2.getStartTime()));
    }
}
