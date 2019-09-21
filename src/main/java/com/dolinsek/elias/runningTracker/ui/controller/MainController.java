package com.dolinsek.elias.runningTracker.ui.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

    @FXML
    private AnchorPane contentRoot; //TODO also in fxml

    private final Parent home, statistics, settings;

    public MainController() throws IOException {
        home = FXMLLoader.load(getClass().getResource("/home.fxml"));
        statistics = FXMLLoader.load(getClass().getResource("/statistics_navigation.fxml"));
        settings = FXMLLoader.load(getClass().getResource("/settings.fxml"));
    }

    public void initialize() {
        vBoxHome.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> setupHome());

        vBoxStatistics.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            setTextsFillBlack();
            setBlackImages();

            txtStatistics.setFill(Paint.valueOf("#ffffff"));
            imgStatistics.setImage(new Image(getClass().getResourceAsStream("/statistics_white_48dp.png")));

            root.setCenter(statistics);
        });
        vBoxSettings.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            setTextsFillBlack();
            setBlackImages();

            txtSettings.setFill(Paint.valueOf("#ffffff"));
            imgSettings.setImage(new Image(getClass().getResourceAsStream("/settings_white_48dp.png")));

            root.setCenter(settings);
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
        txtHome.setFill(Paint.valueOf("#757de8"));
        txtStatistics.setFill(Paint.valueOf("#757de8"));
        txtSettings.setFill(Paint.valueOf("#757de8"));
    }

    private void setBlackImages(){
        imgHome.setImage(new Image(getClass().getResourceAsStream("/home_black_48dp.png")));
        imgStatistics.setImage(new Image(getClass().getResourceAsStream("/statistics_black_48dp.png")));
        imgSettings.setImage(new Image(getClass().getResourceAsStream("/settings_black_48dp.png")));
    }
}
