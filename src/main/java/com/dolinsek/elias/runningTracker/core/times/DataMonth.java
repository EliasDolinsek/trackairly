package com.dolinsek.elias.runningTracker.core.times;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class DataMonth extends DataCollection{

    private int month;
    private ArrayList<DataDay> dataDays;

    public DataMonth(int month, ArrayList<DataDay> dataDays) {
        this.month = month;
        this.dataDays = dataDays;
    }

    public int getMonth() {
        return month;
    }

    public ArrayList<DataDay> getDataDays() {
        return dataDays;
    }

    @Override
    public JSONObject toJSON() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("month", month);

        final JSONArray jsonArray = new JSONArray();
        for(DataDay dataDay:dataDays){
            jsonArray.put(dataDay.toJSON());
        }
        jsonObject.put("dataDays", jsonArray);

        return jsonObject;
    }

    public void addDataTime(DataTime dataTime){
        addCustomDataTime(dataTime, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    public void addCustomDataTime(DataTime dataTime, int day){
        getDataDay(day).addDataTime(dataTime);
    }

    private DataDay getDataDay(int day){
        for(DataDay dataDay:dataDays){
            if (dataDay.getDay() == day){
                return dataDay;
            }
        }

        final DataDay newDataDay = new DataDay(day, new ArrayList<>());
        dataDays.add(newDataDay);
        return newDataDay;
    }

    @Override
    public long getTotalRunningTime() {
        long totalRunningTime = 0;
        for(DataDay dataDay:dataDays){
            totalRunningTime += dataDay.getTotalRunningTime();
        }

        return totalRunningTime;
    }
}
