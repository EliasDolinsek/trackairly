package com.dolinsek.elias.runningTracker.ui.controller;

import com.dolinsek.elias.runningTracker.core.data.DataProvider;
import com.dolinsek.elias.runningTracker.core.times.DataDay;
import com.dolinsek.elias.runningTracker.core.times.DataMonth;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class StatisticsChartsController {

    @FXML
    private BarChart<String, Double> barChart;

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private NumberAxis numberAxis;

    public void initialize(){

    }

    public void loadDaysChart(){
        barChart.getData().clear();
        barChart.getData().add(getDaysSeriesForDataMonth(DataProvider.getTrackingData().getDataYears().get(0).getDataMonths().get(0)));
    }

    private XYChart.Series<String, Double> getDaysSeriesForDataMonth(DataMonth dataMonth){
        final XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Test");

        for (DataDay dataDay:dataMonth.getDataDays()){
            series.getData().add(new XYChart.Data<String, Double>(String.valueOf(dataDay.getDay()), totalRunningTimeInHours(dataDay.getTotalRunningTime())));
        }

        return series;
    }

    private double totalRunningTimeInHours(long millis){
        return (double) (millis / 1000 / 60 / 60);
    }
}
