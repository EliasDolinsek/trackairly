package com.dolinsek.elias.runningTracker.ui.controller;

import com.dolinsek.elias.runningTracker.core.data.DataProvider;
import com.dolinsek.elias.runningTracker.core.times.DataCollection;
import com.dolinsek.elias.runningTracker.core.times.DataDay;
import com.dolinsek.elias.runningTracker.core.times.DataMonth;
import com.dolinsek.elias.runningTracker.core.times.DataYear;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.Calendar;

public class StatisticsOverviewController {

    @FXML
    private Text txtToday, txtThisWeek, txtThisMonth, txtThisYear, txtTotal;

    public void initialize() {
        update();
    }

    public void update(){
        if (DataProvider.getTrackingData().hasData()){
            setYearText();
            setMonthText();
            setTodayText();
            setTotalText();
            setThisWeekText();
        }
    }

    private void setTotalText(){
        long totalTime = 0;
        for (DataYear dataYear:DataProvider.getTrackingData().getDataYears()){
            totalTime += dataYear.getTotalRunningTime();
        }

        txtTotal.setText(DataCollection.getTotalRunningTimeAsString(totalTime));
    }

    private void setYearText() {
        final DataYear dataYear = DataProvider.getThisDataYear();
        if (dataYear != null) {
            txtThisYear.setText(DataCollection.getTotalRunningTimeAsString(dataYear.getTotalRunningTime()));
        } else {
            txtThisYear.setText("N/A");
        }
    }

    private void setMonthText() {
        final DataMonth dataMonth = DataProvider.getThisDataMonth(DataProvider.getThisDataYear());
        if (dataMonth != null){
            txtThisMonth.setText(DataCollection.getTotalRunningTimeAsString(dataMonth.getTotalRunningTime()));
        } else {
            txtThisMonth.setText("N/A");
        }
    }

    private void setTodayText(){
        final DataDay dataDay = DataProvider.getThisDataDay(DataProvider.getThisDataMonth(DataProvider.getThisDataYear()));
        if (dataDay != null){
            txtToday.setText(DataCollection.getTotalRunningTimeAsString(dataDay.getTotalRunningTime()));
        } else {
            txtToday.setText("N/A");
        }
    }

    private void setThisWeekText(){
        try {
            txtThisWeek.setText(DataCollection.getTotalRunningTimeAsString(getThisWeekTimes()));
        } catch (Exception e){
            txtThisWeek.setText("N/A");
        }
    }

    private long getThisWeekTimes() throws Exception{
        final DataMonth dataMonth = DataProvider.getThisDataMonth(DataProvider.getThisDataYear());
        if (dataMonth != null){
            long totalTimes = 0;
            for (DataDay dataDay:dataMonth.getDataDays()){
                if (dataDay.getDay() >= Calendar.getInstance().getFirstDayOfWeek() && dataDay.getDay() <= Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
                    totalTimes += dataDay.getTotalRunningTime();
                }
            }

            return totalTimes;
        } else {
            throw new IllegalStateException("No times");
        }
    }
}
