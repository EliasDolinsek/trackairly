package com.dolinsek.elias.trackairly.core.times;

import com.dolinsek.elias.trackairly.core.data.DataObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class DataYear extends DataCollection {

    private int year;
    private ArrayList<DataMonth> dataMonths;

    public DataYear(int year, ArrayList<DataMonth> dataMonths) {
        this.year = year;
        this.dataMonths = dataMonths;
    }

    private DataYear() {
        year = 2019;
        dataMonths = new ArrayList<>();
    }

    public static DataYear defaultDataYear(){
        return new DataYear();
    }

    public static DataYear now(){
        return new DataYear(Calendar.getInstance().get(Calendar.YEAR), new ArrayList<>());
    }

    public int getYear() {
        return year;
    }

    public ArrayList<DataMonth> getDataMonths() {
        return dataMonths;
    }

    public ArrayList<DataMonth> getCompleteDataMonths(){
        final ArrayList<DataMonth> allDataMonths = new ArrayList<>(getDataMonths());
        for (int i = 0; i<12; i++){
            if (getDataMonthByMonth(i, allDataMonths) == null){
                allDataMonths.add(i, DataMonth.defaultDataMonth(i));
            }
        }

        return allDataMonths;
    }

    private DataMonth getDataMonthByMonth(int month, ArrayList<DataMonth> months){
        for (DataMonth dataMonth:months){
            if (dataMonth.getMonth() == month) return dataMonth;
        }

        return null;
    }

    public void addDataTime(DataTime dataTime){
        final Calendar calendar = Calendar.getInstance();
        addCustomDataTime(dataTime, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void addCustomDataTime(DataTime dataTime, int month, int day){
        getSecureMonth(month).addCustomDataTime(dataTime, day);
    }

    private DataMonth getMonth(int month){
        for(DataMonth dataMonth:dataMonths){
            if(dataMonth.getMonth() == month){
                return dataMonth;
            }
        }

        return null;
    }

    private DataMonth getSecureMonth(int month){
        final DataMonth dataMonth = getMonth(month);
        if (dataMonth == null){
            final DataMonth newMonth = new DataMonth(month, new ArrayList<>());
            dataMonths.add(newMonth);
            return newMonth;
        } else {
            return dataMonth;
        }
    }

    @Override
    public JSONObject toJSON() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("year", year);

        JSONArray jsonArray = new JSONArray();
        for (DataMonth dataMonth : dataMonths) {
            jsonArray.put(dataMonth.toJSON());
        }
        jsonObject.put("dataMonths", jsonArray);

        return jsonObject;
    }

    @Override
    public DataYear fromJSON(JSONObject jsonObject) {
        int year = 2019;
        ArrayList<DataMonth> dataMonths = new ArrayList<>();

        try {
            year = jsonObject.getInt("year");
        } catch (Exception ignored) {}

        try {
            dataMonths = dataMonthsFromJSONArray(jsonObject.getJSONArray("dataMonths"));
        } catch (Exception ignored) {}

        return new DataYear(year, dataMonths);
    }

    private ArrayList<DataMonth> dataMonthsFromJSONArray(JSONArray jsonArray){
        ArrayList<DataMonth> dataMonths = new ArrayList<>();
        for (int i = 0; i<jsonArray.length(); i++){
            dataMonths.add(DataMonth.defaultDataMonth().fromJSON(jsonArray.getJSONObject(i)));
        }

        return dataMonths;
    }

    @Override
    public long getTotalRunningTime() {
        long totalRunningTime = 0;
        for (DataMonth dataMonth : dataMonths) {
            totalRunningTime += dataMonth.getTotalRunningTime();
        }

        return totalRunningTime;
    }

    @Override
    public void sort() {
        super.sort();
        Collections.sort(dataMonths, Comparator.comparingInt(DataMonth::getMonth));

        for (DataMonth dataMonth:dataMonths){
            dataMonth.sort();
        }
    }
}
