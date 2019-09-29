package com.dolinsek.elias.trackairly.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ActionsController implements Initializable {

    @FXML
    private TextField txtName, txtTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("TEST");
    }
}
