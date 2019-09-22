package com.dolinsek.elias.trackairly.core.times;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class DataMonth extends DataCollection{

    private int month;
    private ArrayList<DataDay> dataDays;

    public DataMonth(int month, ArrayList<DataDay> dataDays) {
        this.month = month;
        this.dataDays = dataDays;
    }

    public static DataMonth emptyDataMonth(int month){
        return new DataMonth(month, new ArrayList<>());
    }

    public int getMonth() {
        return month;
    }

    public ArrayList<DataDay> getDataDays() {
        return dataDays;
    }

    public ArrayList<DataDay> getCompleteDataDays(){
        final ArrayList<DataDay> allDataDays = new ArrayList<>(getDataDays());

        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i< YearMonth.of(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)).lengthOfMonth(); i++){
            if (getDataDay(i) == null){
                allDataDays.add(i, DataDay.emptyDataDay(i));
            }
        }

        return allDataDays;
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
        getSecureDataDay(day).addDataTime(dataTime);
    }

    private DataDay getDataDay(int day){
        for(DataDay dataDay:dataDays){
            if (dataDay.getDay() == day){
                return dataDay;
            }
        }

        return null;
    }

    private DataDay getSecureDataDay(int day){
        final DataDay dataDay = getDataDay(day);
        if (dataDay == null){
            final DataDay newDay = new DataDay(day, new ArrayList<>());
            dataDays.add(newDay);
            return newDay;
        } else {
            return dataDay;
        }
    }

    @Override
    public long getTotalRunningTime() {
        long totalRunningTime = 0;
        for(DataDay dataDay:dataDays){
            totalRunningTime += dataDay.getTotalRunningTime();
        }

        return totalRunningTime;
    }

    @Override
    public void sort() {
        super.sort();
        Collections.sort(dataDays, Comparator.comparingInt(DataDay::getDay));

        for (DataDay dataDay:dataDays){
            dataDay.sort();
        }
    }

}
