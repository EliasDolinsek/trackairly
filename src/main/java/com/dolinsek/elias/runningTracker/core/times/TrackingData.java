package com.dolinsek.elias.runningTracker.core.times;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class TrackingData {

    private ArrayList<DataYear> dataYears;

    public TrackingData(ArrayList<DataYear> years) {
        this.dataYears = years;
    }

    public ArrayList<DataYear> getDataYears() {
        return dataYears;
    }

    public boolean hasData(){
        return dataYears != null && dataYears.size() != 0;
    }

    public JSONObject toJSON(){
        final JSONArray yearsJSON = new JSONArray();
        for(DataYear year:dataYears){
            yearsJSON.put(year.toJSON());
        }

        final JSONObject trackingDataJSON = new JSONObject();
        trackingDataJSON.put("years", yearsJSON);

        return trackingDataJSON;
    }

    public void addDataTime(DataTime dataTime){
        final Calendar calendar = Calendar.getInstance();
        addCustomDataTime(dataTime, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void addCustomDataTime(DataTime dataTime, int year, int month, int day){
        for (DataYear dataYear:dataYears){
            if (dataYear.getYear() == year){
                dataYear.addCustomDataTime(dataTime, month, day);
                return;
            }
        }

        final DataYear dataYear = new DataYear(year, new ArrayList<>());
        dataYear.addCustomDataTime(dataTime, month, day);
        dataYears.add(dataYear);
    }

    public void sort(){
        Collections.sort(dataYears, Comparator.comparingInt(DataYear::getYear));

        for (DataYear dataYear:dataYears){
            dataYear.sort();
        }
    }
}
