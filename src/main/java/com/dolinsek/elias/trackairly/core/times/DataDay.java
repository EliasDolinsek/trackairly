package com.dolinsek.elias.trackairly.core.times;

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

    public static DataDay emptyDataDay(int day){
        return new DataDay(day, new ArrayList<>());
    }

    public int getDay() {
        return day;
    }

    public ArrayList<DataTime> getDataTimes() {
        return dataTimes;
    }

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
