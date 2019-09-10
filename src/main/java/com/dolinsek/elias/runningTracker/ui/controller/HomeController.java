package com.dolinsek.elias.runningTracker.ui.controller;

import com.dolinsek.elias.runningTracker.ConfigProvider;
import com.dolinsek.elias.runningTracker.core.times.Tracker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class HomeController {

    @FXML
    private Text txtTime;

    @FXML
    private Button btnTracking;

    private Tracker tracker = ConfigProvider.getConfig().getTracker();

    public void initialize(){

    }
}
