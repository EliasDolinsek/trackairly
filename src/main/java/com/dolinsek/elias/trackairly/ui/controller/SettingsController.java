package com.dolinsek.elias.trackairly.ui.controller;

import com.dolinsek.elias.trackairly.Config;
import com.dolinsek.elias.trackairly.ConfigProvider;
import com.dolinsek.elias.trackairly.core.data.DataProvider;
import com.dolinsek.elias.trackairly.core.networking.Networking;
import com.dolinsek.elias.trackairly.core.networking.ServerStatus;
import com.dolinsek.elias.trackairly.ui.Main;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import mslinks.ShellLink;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class SettingsController {

    @FXML
    private CheckBox cbAutoTrackerStart, cbDisplayHideNotification, cbDisplayActionNotifications, cbExitOnCloseRequest, cbCheckForUpdates;

    @FXML
    private TextField txtDataFileLocation;

    @FXML
    private Button btnChangeDataFilesLocation, btnActivateAutostart, btnDeactivateAutostart, btnCheckForUpdates, btnServerStatus;
    

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

        cbExitOnCloseRequest.selectedProperty().addListener((observable, oldValue, newValue) -> {
            config.setExitOnCloseRequest(newValue);
            writeConfigAndUpdate();
        });

        cbCheckForUpdates.selectedProperty().addListener((observable, oldValue, newValue) -> {
        	config.setCheckForUpdatesOnLaunch(newValue);
        	writeConfigAndUpdate();
        });

        btnChangeDataFilesLocation.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File directory = directoryChooser.showDialog(Main.getCurrentStage());

            if (directory != null) {
                config.setDataFile(new File(directory.getAbsolutePath() + File.separator + "data.json"));
                config.setActionsFile(new File(directory.getAbsolutePath() + File.separator + "actions.json"));

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

        btnCheckForUpdates.setOnAction(event -> {
        	
        });
        
        btnServerStatus.setOnAction(event -> {
            ServerStatus serverStatus = new ServerStatus("ERROR", "Couldn't connect to trackairly server", 0);
        	try {
        	    serverStatus = Networking.getServerStatus(ConfigProvider.getConfig().getVersionsServerURL() + "/serverstatus");
            } catch (Exception e){
        	    e.printStackTrace();
            }

            final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        	alert.setTitle("trackairly server status");
        	alert.setHeaderText(serverStatus.getServerStatus());
        	alert.setContentText(serverStatus.getDescription());
        	alert.show();
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
        cbCheckForUpdates.setSelected(config.checkForUpdatesOnLaunch());
        cbExitOnCloseRequest.setSelected(config.exitOnCloseRequest());
        btnActivateAutostart.setDisable(WINDOWS_AUTOSTART_FILE.exists());
        btnDeactivateAutostart.setDisable(!WINDOWS_AUTOSTART_FILE.exists());
        //cbHideAfterAutostart.setSelected(config.hideAfterAutostart()); TODO implement

        if (config.getDataFile().isDirectory()){
            txtDataFileLocation.setText(config.getDataFile().getAbsolutePath());
        } else {
            txtDataFileLocation.setText(config.getDataFile().getParent());
        }
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
            ConfigProvider.getDataHandler().writeTrackingData(DataProvider.getTrackingData(), config.getDataFile());
            ConfigProvider.getDataHandler().writeActions(DataProvider.getActions(), config.getActionsFile());
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
