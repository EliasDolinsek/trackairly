package com.dolinsek.elias.runningTracker.ui.controller;

import com.dolinsek.elias.runningTracker.Config;
import com.dolinsek.elias.runningTracker.ConfigProvider;
import com.dolinsek.elias.runningTracker.core.data.DataProvider;
import com.dolinsek.elias.runningTracker.ui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class SettingsController {

    @FXML
    private CheckBox cbAutoTrackerStart;

    @FXML
    private TextField txtDataFileLocation;

    @FXML
    private Button btnChangeDataFileLocation, btnActivateAutostart, btnDeactivateAutostart;

    private final Config config = ConfigProvider.getConfig();

    private final File WINDOWS_AUTOSTART_FILE = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\runningTracker.bat");

    public void initialize() {
        displaySettings();
        cbAutoTrackerStart.selectedProperty().addListener((observable, oldValue, newValue) -> {
            config.setStartTrackerOnLaunch(newValue);
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
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });

        btnDeactivateAutostart.setOnAction(event -> deactivateWindowsAutostart());
    }

    private void displaySettings() {
        cbAutoTrackerStart.setSelected(config.startTrackerOnLaunch());
        txtDataFileLocation.setText(config.getDataFile().getAbsolutePath());
        btnActivateAutostart.setDisable(WINDOWS_AUTOSTART_FILE.exists());
        btnDeactivateAutostart.setDisable(!WINDOWS_AUTOSTART_FILE.exists());
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
        if (!WINDOWS_AUTOSTART_FILE.exists()) {

            WINDOWS_AUTOSTART_FILE.createNewFile();

        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(WINDOWS_AUTOSTART_FILE));
        writer.write("java -jar " + getPathToJar());
        writer.close();
    }

    private void deactivateWindowsAutostart(){
        WINDOWS_AUTOSTART_FILE.delete();
    }

    private String getPathToJar() throws URISyntaxException {
        return new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
                .toURI()).getPath();
    }

}
