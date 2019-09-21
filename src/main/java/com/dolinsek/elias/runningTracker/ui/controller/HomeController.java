package com.dolinsek.elias.runningTracker.ui.controller;

import com.dolinsek.elias.runningTracker.ConfigProvider;
import com.dolinsek.elias.runningTracker.core.data.DataProvider;
import com.dolinsek.elias.runningTracker.core.times.DataCollection;
import com.dolinsek.elias.runningTracker.core.times.DataDay;
import com.dolinsek.elias.runningTracker.core.times.Tracker;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class HomeController {

    @FXML
    private Text txtTime;

    @FXML
    private Button btnTracking;

    private Tracker tracker = DataProvider.getTracker();

    public void initialize() {
        update();
        btnTracking.setOnAction(event -> {
            try {
                if (tracker.isRunning()) {
                    tracker.stop();
                    update();
                } else {
                    startTracker();
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        });

        if (ConfigProvider.getConfig().startTrackerOnLaunch()) {
            startTracker();
        }
    }

    private void startTracker() {
        try {
            tracker.start(() -> Platform.runLater(() -> update()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        displayTexts();
        if (tracker.isRunning()) {
            btnTracking.setText("STOP TRACKING");
        } else {
            btnTracking.setText("START TRACKING");
        }
    }

    private void displayTexts() {
        try {
            txtTime.setText(DataCollection.getTotalRunningTimeAsString(DataProvider.getThisDataDay(DataProvider.getThisDataMonth(DataProvider.getThisDataYear())).getTotalRunningTime()));
        } catch (Exception e){
            txtTime.setText(tracker.getRunningTimeAsString());
        }
    }
}
