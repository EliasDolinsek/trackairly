package com.dolinsek.elias.trackairly.ui.controller;

import com.dolinsek.elias.trackairly.core.data.DataProvider;
import com.dolinsek.elias.trackairly.core.times.DataCollection;
import com.dolinsek.elias.trackairly.core.times.DataDay;
import com.dolinsek.elias.trackairly.core.times.DataMonth;
import com.dolinsek.elias.trackairly.core.times.DataYear;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.Calendar;

public class StatisticsOverviewController {

    @FXML
    private Text txtToday, txtThisMonth, txtThisYear, txtTotal;

    public void initialize() {
        update();
    }

    public void update(){
        if (DataProvider.getTrackingData().hasData()){
            setYearText();
            setMonthText();
            setTodayText();
            setTotalText();
        }
    }

    private void setTotalText(){
        long totalTime = 0;
        for (DataYear dataYear:DataProvider.getTrackingData().getDataYears()){
            totalTime += dataYear.getTotalRunningTime();
        }

        txtTotal.setText(DataCollection.getTotalRunningTimeAsString(totalTime, false));
    }

    private void setYearText() {
        final DataYear dataYear = DataProvider.getThisDataYear();
        if (dataYear != null) {
            txtThisYear.setText(DataCollection.getTotalRunningTimeAsString(dataYear.getTotalRunningTime(), false));
        } else {
            txtThisYear.setText("N/A");
        }
    }

    private void setMonthText() {
        final DataMonth dataMonth = DataProvider.getThisDataMonth(DataProvider.getThisDataYear());
        if (dataMonth != null){
            txtThisMonth.setText(DataCollection.getTotalRunningTimeAsString(dataMonth.getTotalRunningTime(), false));
        } else {
            txtThisMonth.setText("N/A");
        }
    }

    private void setTodayText(){
        final DataDay dataDay = DataProvider.getThisDataDay(DataProvider.getThisDataMonth(DataProvider.getThisDataYear()));
        if (dataDay != null){
            txtToday.setText(DataCollection.getTotalRunningTimeAsString(dataDay.getTotalRunningTime(), false));
        } else {
            txtToday.setText("N/A");
        }
    }
}
