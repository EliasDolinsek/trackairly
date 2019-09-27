package com.dolinsek.elias.trackairly.ui.controller;

import com.dolinsek.elias.trackairly.ConfigProvider;
import com.dolinsek.elias.trackairly.core.data.DataProvider;
import com.dolinsek.elias.trackairly.core.times.DataCollection;
import com.dolinsek.elias.trackairly.core.times.OnTimeChangedListener;
import com.dolinsek.elias.trackairly.core.times.Tracker;
import com.dolinsek.elias.trackairly.ui.Main;
import com.dolinsek.elias.trackairly.ui.NotificationManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Calendar;

public class HomeController {

    @FXML
    private Text txtTime;

    @FXML
    private Button btnTracking, btnHide;

    private Tracker tracker = DataProvider.getTracker();

    private final OnTimeChangedListener onTimeChangedListener = this::update;

    public void initialize() {
        DataProvider.getTracker().onTimeChangedListeners.add(onTimeChangedListener);
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
                e.printStackTrace();
            }
        });

        btnHide.setOnAction(event -> {
            Main.getCurrentStage().hide();
            NotificationManager.displayHideNotificationByConfig();
        });

        if (ConfigProvider.getConfig().startTrackerOnLaunch()) {
            startTracker();
        }
    }

    private void startTracker() {
        try {
            tracker.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        displayTexts();
        Platform.runLater(() -> {
            if (tracker.isRunning()) {
                btnTracking.setText("STOP TRACKING");
            } else {
                btnTracking.setText("START TRACKING");
            }
        });
    }

    private void displayTexts() {
        try {
            final long runningTime = DataProvider.getThisDataDay(DataProvider.getThisDataMonth(DataProvider.getThisDataYear())).getTotalRunningTime();
            txtTime.setText(DataCollection.getTotalRunningTimeAsString(runningTime, true));
        } catch (Exception e) {
            txtTime.setText(tracker.getRunningTimeAsString());
        }
    }
}
