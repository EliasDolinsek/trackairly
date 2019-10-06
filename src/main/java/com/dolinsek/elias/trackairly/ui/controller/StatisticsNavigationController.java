package com.dolinsek.elias.trackairly.ui.controller;

import com.dolinsek.elias.trackairly.core.data.DataProvider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class StatisticsNavigationController {

    @FXML
    private ToggleButton btnOverview, btnDays, btnMonths, btnYears;

    @FXML
    private Button btnRefresh;

    @FXML
    private BorderPane root;

    private final ToggleGroup tgButtons = new ToggleGroup();

    private final Parent overview, charts;
    private final StatisticsOverviewController statisticsOverviewController;
    private final StatisticsChartsController statisticsChartsController;

    public StatisticsNavigationController() throws IOException {
        FXMLLoader overviewLoader = new FXMLLoader(getClass().getResource("/statistics_overview.fxml"));
        overview = overviewLoader.load();
        statisticsOverviewController = overviewLoader.getController();

        FXMLLoader chartsLoader = new FXMLLoader(getClass().getResource("/statistics_chart.fxml"));
        charts = chartsLoader.load();
        statisticsChartsController = chartsLoader.getController();
    }

    public void initialize() {
        btnOverview.setToggleGroup(tgButtons);
        btnDays.setToggleGroup(tgButtons);
        btnMonths.setToggleGroup(tgButtons);
        btnYears.setToggleGroup(tgButtons);

        btnRefresh.getStyleClass().add("primary-color-button");

        tgButtons.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null){
                tgButtons.selectToggle(oldValue);
            } else {
                setupForToggledButton(newValue);
            }
        });
        btnRefresh.setOnAction(event -> {
            if (DataProvider.getTrackingData().hasData()){
                statisticsOverviewController.update();
                statisticsChartsController.update();
            }

            setTogglesClickabilities();
        });

        setupForToggledButton(btnOverview);
        setTogglesClickabilities();
    }

    private void setTogglesClickabilities() {
        if (!DataProvider.getTrackingData().hasData()) {
            btnOverview.setDisable(true);
            btnDays.setDisable(true);
            btnMonths.setDisable(true);
            btnYears.setDisable(true);
        } else {
            btnOverview.setDisable(false);
            btnDays.setDisable(false);
            btnMonths.setDisable(false);
            btnYears.setDisable(false);
        }

        if (DataProvider.getTrackingData().hasData() && DataProvider.getTrackingData().getDataYears().size() <= 1){
            btnYears.setDisable(true);
        } else {
            btnYears.setDisable(false);
        }
    }

    private void setupForToggledButton(Toggle newValue) {
        setToggleButtonsStyleForSelection();
        if (newValue == btnOverview) {
            root.setCenter(overview);
        } else if (newValue == btnDays) {
            root.setCenter(charts);
            statisticsChartsController.loadDaysChart();
        } else if (newValue == btnMonths) {
            root.setCenter(charts);
            statisticsChartsController.loadMonthsStatistics();
        } else {
            root.setCenter(charts);
            statisticsChartsController.loadYearsStatistics();
        }
    }

    private void setToggleButtonsStyleForSelection(){
        ToggleButton selectedButton = (ToggleButton)tgButtons.getSelectedToggle();
        setToggleButtonStyle(btnOverview, selectedButton == btnOverview);
        setToggleButtonStyle(btnDays, selectedButton == btnDays);
        setToggleButtonStyle(btnMonths, selectedButton == btnMonths);
        setToggleButtonStyle(btnYears, selectedButton == btnYears);
    }

    private void setToggleButtonStyle(ToggleButton button, boolean selected){
        button.getStyleClass().remove("white-button");
        button.getStyleClass().remove("primary-color-button");

        if (selected){
            button.getStyleClass().add("primary-color-button");
        } else {
            button.getStyleClass().add("white-button");
        }
    }
}
