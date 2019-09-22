package com.dolinsek.elias.trackairly.ui.controller;

import com.dolinsek.elias.trackairly.ConfigProvider;
import com.dolinsek.elias.trackairly.core.data.DataProvider;
import com.dolinsek.elias.trackairly.core.times.DataCollection;
import com.dolinsek.elias.trackairly.core.times.OnTimeChangedListener;
import com.dolinsek.elias.trackairly.core.times.Tracker;
import com.dolinsek.elias.trackairly.ui.Main;
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

    private int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    private final OnTimeChangedListener onTimeChangedListener = () -> {
        checkAndReactToDateChange();
        update();
    };

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

        btnHide.setOnAction(event -> Main.getCurrentStage().hide());

        if (ConfigProvider.getConfig().startTrackerOnLaunch()) {
            startTracker();
        }
    }

    private void startTracker() {
        try {
            tracker.start(onTimeChangedListener);
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
        } catch (Exception e) {
            txtTime.setText(tracker.getRunningTimeAsString());
        }
    }

    private void checkAndReactToDateChange() {
        final int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if (day != currentDay) {
            try {
                tracker.stopForDayChange();
                tracker.startForDayChange(onTimeChangedListener);
                day = currentDay;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
