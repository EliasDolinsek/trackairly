package com.dolinsek.elias.runningTracker.ui.controller;

import com.dolinsek.elias.runningTracker.core.data.DataProvider;
import com.dolinsek.elias.runningTracker.core.times.*;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class StatisticsChartsController {

    @FXML
    private BarChart<String, Double> barChart;

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private NumberAxis numberAxis;

    @FXML
    private Button btnNext, btnPrevious, btnDate;

    private ChartDisplay chartDisplay = ChartDisplay.DAYS;

    private int selectedIndex;
    private ArrayList<DataCollection> statisticsData = new ArrayList<>();

    public void initialize() {
        barChart.setAnimated(false);
        barChart.setLegendVisible(false);
        barChart.setCategoryGap(0.0);
        loadStatisticsData();
    }

    public void update(){
        loadStatisticsData();
        loadStatistics();
    }

    public void loadDaysChart() {
        chartDisplay = ChartDisplay.DAYS;

        loadStatisticsData();
        selectedIndex = statisticsData.size() - 1;
        loadStatistics();
    }

    public void loadMonthsStatistics(){
        chartDisplay = ChartDisplay.MONTHS;

        loadStatisticsData();
        selectedIndex = statisticsData.size() - 1;
        loadStatistics();
    }

    public void loadYearsStatistics(){
        chartDisplay = ChartDisplay.YEARS;
        loadStatisticsData();
        loadStatistics();
    }

    private void loadStatistics() {
        manageMonthStepButtonsDisableables();
        setupButtons();

        loadCharts();
        setupDataBtnText();
    }

    private void loadStatisticsData() {
        statisticsData.clear();

        final TrackingData data = DataProvider.getTrackingData();
        data.sort();

        if (chartDisplay == ChartDisplay.DAYS) {
            for (DataYear dataYear : data.getDataYears()) {
                statisticsData.addAll(dataYear.getDataMonths());
            }
        } else if(chartDisplay == ChartDisplay.MONTHS || chartDisplay == ChartDisplay.YEARS){
            statisticsData.addAll(data.getDataYears());
        }
    }

    private void setupButtons() {
        btnNext.setOnAction(event -> {
            if (canStepForward()) {
                selectedIndex++;
                loadStatistics();
            }
        });

        btnPrevious.setOnAction(event -> {
            if (canStepBack()) {
                selectedIndex--;
                loadStatistics();
            }
        });
    }

    private void loadCharts() {
        barChart.getData().clear();
        if (chartDisplay == ChartDisplay.DAYS) {
            barChart.getData().add(getDaysSeriesForDataMonth((DataMonth) statisticsData.get(selectedIndex)));
        } else if (chartDisplay == ChartDisplay.MONTHS){
            barChart.getData().add(getDataSeriesForDataYear((DataYear) statisticsData.get(selectedIndex)));
        } else if (chartDisplay == ChartDisplay.YEARS){
            barChart.getData().add(getDataSeriesForDataYears());
        }
    }

    private void manageMonthStepButtonsDisableables() {
        if (statisticsData.size() == 0 || chartDisplay == ChartDisplay.YEARS) {
            btnPrevious.setDisable(true);
            btnNext.setDisable(true);
        } else {
            if (canStepBack()) {
                btnPrevious.setDisable(false);
            } else {
                btnPrevious.setDisable(true);
            }

            if (canStepForward()) {
                btnNext.setDisable(false);
            } else {
                btnNext.setDisable(true);
            }
        }
    }

    private boolean canStepBack() {
        return chartDisplay != ChartDisplay.YEARS && selectedIndex > 0 && statisticsData.size() != 0;
    }

    private boolean canStepForward() {
        return chartDisplay != ChartDisplay.YEARS && statisticsData.size() != 0 && selectedIndex < (statisticsData.size() - 1);
    }

    private void setupDataBtnText() {
        btnDate.setDisable(false);
        if (chartDisplay == ChartDisplay.DAYS) {
            DataMonth month = (DataMonth) statisticsData.get(selectedIndex);
            btnDate.setText(DataCollection.monthToString(month.getMonth()));
        } else if (chartDisplay == ChartDisplay.MONTHS) {
            DataYear dataYear = (DataYear) statisticsData.get(selectedIndex);
            btnDate.setText(String.valueOf(dataYear.getYear()));
        } else if (chartDisplay == ChartDisplay.YEARS){
            btnDate.setText("ALL YEARS");
            btnDate.setDisable(true);
        }
    }

    private XYChart.Series<String, Double> getDaysSeriesForDataMonth(DataMonth dataMonth) {
        final XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName(DataCollection.monthToString(dataMonth.getMonth()));

        for (DataDay dataDay : dataMonth.getCompleteDataDays()) {
            series.getData().add(new XYChart.Data<>(dataDay.getDay() + ".", totalRunningTimeInHours(dataDay.getTotalRunningTime())));
        }

        return series;
    }

    private XYChart.Series<String, Double> getDataSeriesForDataYear(DataYear dataYear){
        final XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName(String.valueOf(dataYear.getYear()));

        for (DataMonth dataMonth:dataYear.getCompleteDataMonths()){
            series.getData().add(new XYChart.Data<>(DataCollection.monthToString(dataMonth.getMonth()), totalRunningTimeInHours(dataMonth.getTotalRunningTime())));
        }

        return series;
    }

    private XYChart.Series<String, Double> getDataSeriesForDataYears(){
        final XYChart.Series series = new XYChart.Series();
        series.setName("ALL YEARS");

        for (DataCollection data:statisticsData){
            final DataYear dataYear = (DataYear) data;
            series.getData().add(new XYChart.Data(String.valueOf(dataYear.getYear()), totalRunningTimeInHours(dataYear.getTotalRunningTime())));
        }

        return series;
    }

    private double totalRunningTimeInHours(long millis) {
        return ((double) (millis / 1000) / 60 / 60);
    }

    private enum ChartDisplay {
        DAYS, MONTHS, YEARS
    }
}
