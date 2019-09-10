package com.dolinsek.elias.runningTracker.ui;

import com.dolinsek.elias.runningTracker.Config;
import com.dolinsek.elias.runningTracker.ConfigProvider;
import com.dolinsek.elias.runningTracker.core.data.DataProvider;
import com.dolinsek.elias.runningTracker.core.data.OfflineDataHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initApp();
        setupShutdownHook();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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
        new OfflineDataHandler().writeData(DataProvider.getTrackingData(), ConfigProvider.getConfig().getDataFile());
    }

    private void initApp() throws Exception {
        ConfigProvider.init();
        DataProvider.init();
    }
}
