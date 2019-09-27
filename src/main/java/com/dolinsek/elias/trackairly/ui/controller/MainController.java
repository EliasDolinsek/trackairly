package com.dolinsek.elias.trackairly.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;


public class MainController {

    @FXML
    private BorderPane root;

    @FXML
    private VBox vBoxHome, vBoxStatistics, vBoxSettings, vBoxActions;

    @FXML
    private ImageView imgHome, imgStatistics, imgSettings, imgActions;

    @FXML
    private Text txtHome, txtStatistics, txtSettings, txtActions;

    @FXML
    private AnchorPane contentRoot; //TODO also in fxml

    private final Parent home, statistics, actions, settings;

    public MainController() throws IOException {
        home = FXMLLoader.load(getClass().getResource("/home.fxml"));
        statistics = FXMLLoader.load(getClass().getResource("/statistics_navigation.fxml"));
        actions = FXMLLoader.load(getClass().getResource("/actions.fxml"));
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

        vBoxActions.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            setTextsFillBlack();
            setBlackImages();

            txtActions.setFill(Paint.valueOf("#ffffff"));
            imgActions.setImage(new Image(getClass().getResourceAsStream("/actions_white_48dp.png")));

            root.setCenter(actions);
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
        txtActions.setFill(Paint.valueOf("#757de8"));
        txtSettings.setFill(Paint.valueOf("#757de8"));
    }

    private void setBlackImages(){
        imgHome.setImage(new Image(getClass().getResourceAsStream("/home_black_48dp.png")));
        imgStatistics.setImage(new Image(getClass().getResourceAsStream("/statistics_black_48dp.png")));
        imgActions.setImage(new Image(getClass().getResourceAsStream("/actions_black_48dp.png")));
        imgSettings.setImage(new Image(getClass().getResourceAsStream("/settings_black_48dp.png")));
    }
}
