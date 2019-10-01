package com.dolinsek.elias.trackairly.ui.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.dolinsek.elias.trackairly.ConfigProvider;
import com.dolinsek.elias.trackairly.core.data.DataProvider;
import com.dolinsek.elias.trackairly.core.data.OfflineDataHandler;
import com.dolinsek.elias.trackairly.core.timeEvents.Action;
import com.dolinsek.elias.trackairly.core.timeEvents.TimeEvent.TimeEventTriggerType;

public class ActionsController implements Initializable {

    @FXML
    private TextField txtName, txtTime;

    @FXML
    private RadioButton rbTimeOfDay, rbRunningTime;

    @FXML
    private Button btnDelete, btnSaveCreate;

    @FXML
    private Text txtError;

    @FXML
    private VBox vboxActions;
    
    @FXML
    private ListView<String> listActions;
    
    final ToggleGroup timeEventTrigerTypesGroup = new ToggleGroup();
    
    final ObservableList<String> loadedActions = FXCollections.observableArrayList();
    ArrayList<Action> actionsList = new ArrayList<>();
    Action currentAction = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	setup();
    }
    
    private void loadAction(Action action) {
    	currentAction = action;
    	txtName.setText(currentAction.getName());
    	txtTime.setText(String.format("%02d:%02d", currentAction.getHours(), currentAction.getMinutes()));
    	
    	if (currentAction.getTimeEventTriggerType() == TimeEventTriggerType.TIME_OF_DAY){
    		rbTimeOfDay.setSelected(true);
    	} else {
    		rbRunningTime.setSelected(true);
    	}
    }
    
    private void setup() {
    	setupRadioButtons();
    	setupTextFields();
    	loadActionsList();
    	setupActionsList();
    }
    
    private void setupTextFields() {
    	txtName.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(currentAction != null) {
    			currentAction.setName(newValue);
    			loadActionsList();
    		}
    	});
    }
    private void setupRadioButtons() {
    	rbRunningTime.setToggleGroup(timeEventTrigerTypesGroup);
    	rbTimeOfDay.setToggleGroup(timeEventTrigerTypesGroup);
    	
    	timeEventTrigerTypesGroup.selectedToggleProperty().addListener((ChangeListener<? super Toggle>) (observable, oldValue, newValue) -> {
			if(currentAction != null) {
				if(newValue == rbTimeOfDay) {
					currentAction.setTimeEventTriggerType(TimeEventTriggerType.TIME_OF_DAY);
				} else {
					currentAction.setTimeEventTriggerType(TimeEventTriggerType.RUNNING_TIME);
				}
			}
		});
    }
    
    private void loadActionsList() {
    	actionsList = DataProvider.getActions();
    	loadedActions.clear();
    	for(Action action:DataProvider.getActions()) {
    		loadedActions.add(action.getName());
    	}
    	
    	listActions.setItems(loadedActions);
    }
    
    private void setupActionsList() {
    	listActions.getSelectionModel().selectedIndexProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
			loadAction(actionsList.get((int) newValue));
		});
    }
}
