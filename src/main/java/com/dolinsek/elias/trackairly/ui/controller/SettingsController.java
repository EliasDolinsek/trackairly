package com.dolinsek.elias.trackairly.ui.controller;

import com.dolinsek.elias.trackairly.Config;
import com.dolinsek.elias.trackairly.ConfigProvider;
import com.dolinsek.elias.trackairly.core.data.DataProvider;
import com.dolinsek.elias.trackairly.ui.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import mslinks.ShellLink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class SettingsController {

    @FXML
    private CheckBox cbAutoTrackerStart, cbDisplayHideNotification, cbDisplayActionNotifications;

    @FXML
    private TextField txtDataFileLocation;

    @FXML
    private Button btnChangeDataFileLocation, btnActivateAutostart, btnDeactivateAutostart;

    //@FXML
    //private CheckBox cbHideAfterAutostart;

    private final Config config = ConfigProvider.getConfig();

    private final File WINDOWS_AUTOSTART_FILE = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\trackairly_autostart.lnk");

    public void initialize() {
        displaySettings();
        cbAutoTrackerStart.selectedProperty().addListener((observable, oldValue, newValue) -> {
            config.setStartTrackerOnLaunch(newValue);
            writeConfigAndUpdate();
        });

        cbDisplayHideNotification.selectedProperty().addListener((observable, oldValue, newValue) -> {
            config.setDisplayHideNotification(newValue);
            writeConfigAndUpdate();
        });

        cbDisplayActionNotifications.selectedProperty().addListener((observable, oldValue, newValue) -> {
            config.setDisplayActionNotifications(newValue);
            writeConfigAndUpdate();
        });

        btnChangeDataFileLocation.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON File", "data.json"));
            File selectedDirectory = fileChooser.showSaveDialog(Main.getCurrentStage());

            if (selectedDirectory != null) {
                config.setDataFile(selectedDirectory);
                writeData();
                writeConfigAndUpdate();
            }
        });

        btnActivateAutostart.setOnAction(event -> {
            try {
                activateWindowsAutostart();
                displaySettings();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });

        btnDeactivateAutostart.setOnAction(event -> {
            deactivateWindowsAutostart();
            displaySettings();
        });

        /*
        TODO implement
        cbHideAfterAutostart.selectedProperty().addListener((observable, oldValue, newValue) -> {
            config.setHideAfterAutostart(newValue);
            writeConfigAndUpdate();
        });
        */
    }

    private void displaySettings() {
        cbAutoTrackerStart.setSelected(config.startTrackerOnLaunch());
        cbDisplayHideNotification.setSelected(config.displayHideNotification());
        cbDisplayActionNotifications.setSelected(config.displayActionNotifications());
        txtDataFileLocation.setText(config.getDataFile().getAbsolutePath());
        btnActivateAutostart.setDisable(WINDOWS_AUTOSTART_FILE.exists());
        btnDeactivateAutostart.setDisable(!WINDOWS_AUTOSTART_FILE.exists());
        //cbHideAfterAutostart.setSelected(config.hideAfterAutostart()); TODO implement
    }

    private void writeConfigAndUpdate() {
        try {
            ConfigProvider.getDataHandler().writeConfig(config, Config.DEFAULT_CONFIG_FILE);
            displaySettings();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeData() {
        try {
            ConfigProvider.getDataHandler().writeData(DataProvider.getTrackingData(), config.getDataFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void activateWindowsAutostart() throws IOException, URISyntaxException {
        ShellLink.createLink(getPathToJar(), WINDOWS_AUTOSTART_FILE.getPath());
    }

    private void deactivateWindowsAutostart() {
        WINDOWS_AUTOSTART_FILE.delete();
    }

    private String getPathToJar() throws URISyntaxException {
        return new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
                .toURI()).getPath();
    }

}
