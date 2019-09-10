package com.dolinsek.elias.runningTracker.ui.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;


public class MainController {

    @FXML
    private BorderPane root;

    @FXML
    private VBox vBoxHome, vBoxStatistics, vBoxSettings;

    @FXML
    private ImageView imgHome, imgStatistics, imgSettings;

    @FXML
    private Text txtHome, txtStatistics, txtSettings;

    private final Parent home;

    public MainController() throws IOException {
        home = FXMLLoader.load(getClass().getResource("/home.fxml"));
    }

    public void initialize() {
        vBoxHome.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> setupHome());

        vBoxStatistics.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            setTextsFillBlack();
            setBlackImages();

            txtStatistics.setFill(Paint.valueOf("#ffffff"));
            imgStatistics.setImage(new Image(getClass().getResourceAsStream("/statistics_white_48dp.png")));

            root.setCenter(new Text("STATISTICS"));
        });
        vBoxSettings.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            setTextsFillBlack();
            setBlackImages();

            txtSettings.setFill(Paint.valueOf("#ffffff"));
            imgSettings.setImage(new Image(getClass().getResourceAsStream("/settings_white_48dp.png")));

            root.setCenter(new Text("SETTINGS"));
        });

        setupHome();
    }

    private void setupHome() {
        setTextsFillBlack();
        setBlackImages();

        txtHome.setFill(Paint.valueOf("#ffffff"));
        imgHome.setImage(new Image(getClass().getResourceAsStream("/home_white_48dp.png")));

        root.setCenter(home);
    }

    private void setTextsFillBlack(){
        txtHome.setFill(Paint.valueOf("#000000"));
        txtStatistics.setFill(Paint.valueOf("#000000"));
        txtSettings.setFill(Paint.valueOf("#000000"));
    }

    private void setBlackImages(){
        imgHome.setImage(new Image(getClass().getResourceAsStream("/home_black_48dp.png")));
        imgStatistics.setImage(new Image(getClass().getResourceAsStream("/statistics_black_48dp.png")));
        imgSettings.setImage(new Image(getClass().getResourceAsStream("/settings_black_48dp.png")));
    }
}
