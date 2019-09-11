package com.dolinsek.elias.runningTracker.ui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

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

        tgButtons.selectedToggleProperty().addListener((observable, oldValue, newValue) -> setupForToggledButton(newValue));
        btnRefresh.setOnAction(event -> statisticsOverviewController.update());

        setupForToggledButton(btnOverview);
    }

    private void setupForToggledButton(Toggle newValue) {
        if (newValue == btnOverview) {
            root.setCenter(overview);
        } else if (newValue == btnDays){
            root.setCenter(charts);
            statisticsChartsController.loadDaysChart();
        } else {
            System.out.println("STATISTICS_NOT_IMPLEMENTED_YET");
        }
    }
}
