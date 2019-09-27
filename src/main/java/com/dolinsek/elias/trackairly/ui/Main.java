package com.dolinsek.elias.trackairly.ui;

import com.dolinsek.elias.trackairly.ConfigProvider;
import com.dolinsek.elias.trackairly.core.data.DataProvider;
import com.dolinsek.elias.trackairly.core.data.OfflineDataHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static Stage currentStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        checkAlreadyRunning();

        Platform.setImplicitExit(false);
        currentStage = primaryStage;

        initApp();
        setupShutdownHook();
        setupTray(primaryStage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(event -> {
            if (ConfigProvider.getConfig().exitOnCloseRequest()){
                Platform.exit();
                System.exit(1);
            } else {
                NotificationManager.displayHideNotificationByConfig();
            }
        });

        primaryStage.setTitle("trackairly");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/ic_launcher.png")));

        if (!getParameters().getRaw().contains("autostart")) primaryStage.show();
    }

    private void setupShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                stopAndSave();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    @Override
    public void stop() throws Exception {
        stopAndSave();
    }

    private void stopAndSave() throws IOException {
        DataProvider.getTracker().stop();
        new OfflineDataHandler().writeTrackingData(DataProvider.getTrackingData(), ConfigProvider.getConfig().getDataFile());
    }

    private void initApp() throws Exception {
        ConfigProvider.init();
        DataProvider.init();
    }

    public static Stage getCurrentStage() {
        return currentStage;
    }

    private void setupTray(Stage primaryStage) {
        if (!SystemTray.isSupported()) {
            System.err.println("No system tray supported");
            return;
        }

        final PopupMenu popupMenu = new PopupMenu();
        final java.awt.Image trayImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ic_launcher_tray.png"));
        final TrayIcon trayIcon = new TrayIcon(trayImage);

        final SystemTray systemTray = SystemTray.getSystemTray();

        final MenuItem exitItem = new MenuItem("EXIT trackairly");
        final MenuItem hideItem = new MenuItem("HIDE");
        final MenuItem showItem = new MenuItem("SHOW");

        exitItem.addActionListener(e -> {
            Platform.exit();
            System.exit(0);
        });

        hideItem.addActionListener(e -> Platform.runLater(primaryStage::hide));
        showItem.addActionListener(e -> Platform.runLater(primaryStage::show));

        popupMenu.add(showItem);
        popupMenu.add(hideItem);
        popupMenu.add(exitItem);

        trayIcon.setPopupMenu(popupMenu);
        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void checkAlreadyRunning() {
        try {
            RandomAccessFile randomFile =
                    new RandomAccessFile("lock.class", "rw");

            FileChannel channel = randomFile.getChannel();

            if (channel.tryLock() == null) {
                showAlreadyRunningAlertDialog();
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlreadyRunningAlertDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("trackairly");
        alert.setHeaderText("trackairly is already running");
        alert.setContentText("To open trackairly, go to the Tray-Menu and click on SHOW");
        alert.showAndWait();
    }
}
