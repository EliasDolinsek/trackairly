package com.dolinsek.elias.runningTracker.ui.controller;

import com.dolinsek.elias.runningTracker.core.data.DataProvider;
import com.dolinsek.elias.runningTracker.core.times.DataCollection;
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
                    tracker.start(() -> Platform.runLater(() -> update()));
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        });
    }

    private void update() {
        txtTime.setText(DataCollection.getTotalRunningTimeAsString(DataProvider.getThisDataDay(DataProvider.getThisDataMonth(DataProvider.getThisDataYear())).getTotalRunningTime()));
        if (tracker.isRunning()) {
            btnTracking.setText("STOP TRACKING");
        } else {
            btnTracking.setText("START TRACKING");
        }
    }
}
