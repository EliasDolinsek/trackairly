package com.dolinsek.elias.trackairly.ui.controller;

import com.dolinsek.elias.trackairly.ConfigProvider;
import com.dolinsek.elias.trackairly.core.data.DataProvider;
import com.dolinsek.elias.trackairly.core.timeEvents.Action;
import com.dolinsek.elias.trackairly.core.timeEvents.TimeEvent.TimeEventTriggerType;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ActionsController implements Initializable {

    @FXML
    private TextField txtName, txtTime;

    @FXML
    private RadioButton rbTimeOfDay, rbRunningTime;

    @FXML
    private Button btnDeleteAction, btnCreateNewAction;

    @FXML
    private Text txtInfoError;

    @FXML
    private TextArea taCommands;
    
    @FXML
    private ListView<String> listActions;

    @FXML
	private VBox vBoxDetails, vBoxActionsList;

    @FXML
	private BorderPane borderPaneRoot;

    private final Text txtActions = new Text("NO ACTIONS");

    private final ToggleGroup timeEventTrigerTypesGroup = new ToggleGroup();
    
    private final ObservableList<String> loadedActions = FXCollections.observableArrayList();
    private ArrayList<Action> actionsList = new ArrayList<>();
    private Action currentAction = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
		setupRadioButtons();
		setupTextFields();
		loadActionsList();
		setupActionsList();
		setupButtons();

    	loadFirstAction();
		displayInfoText();
    }

    private void setupListSelection(){
    	if (currentAction != null) listActions.getSelectionModel().select(actionsList.indexOf(currentAction));
	}

    private void loadFirstAction(){
    	if (!actionsList.isEmpty()) listActions.getSelectionModel().select(0);
	}
    
    private void loadAction(Action action) {
    	currentAction = action;
    	txtName.setText(currentAction.getName());
    	txtTime.setText(String.format("%02d:%02d", currentAction.getHours(), currentAction.getMinutes()));
    	setCommandsText();
    	
    	if (currentAction.getTimeEventTriggerType() == TimeEventTriggerType.TIME_OF_DAY){
    		rbTimeOfDay.setSelected(true);
    	} else {
    		rbRunningTime.setSelected(true);
    	}

    	setupListSelection();
    }

	private void setCommandsText() {
    	if(currentAction != null){
			StringBuilder commands = new StringBuilder();
			for (String command:currentAction.getCommands()){
				commands.append(command).append(System.lineSeparator());
			}

			taCommands.setText(commands.toString());
		}
	}

	private void setupButtons(){
		btnDeleteAction.setOnAction(event -> {
			DataProvider.getActions().remove(currentAction);
			currentAction = null;
			loadActionsList();
			loadFirstAction();
		});

    	btnCreateNewAction.setOnAction(event -> {
    		Action action = Action.defaultAction();
    		DataProvider.getActions().add(0, action);

    		loadActionsList();
    		currentAction = action;
    		loadAction(action);
		});
	}

    private void setupTextFields() {
    	txtName.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(currentAction != null) {
    			currentAction.setName(newValue);

    			loadActionsList();
    			writeActionsUpdate();
    		}
    	});

    	txtTime.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(currentAction != null){
    			try {
    				currentAction.setTimesFromString(newValue);
					currentAction.setExecuted(false);

    				displayInfoText();
    				writeActionsUpdate();
				} catch (Exception e){
    				displayError("Couldn't parse trigger time");
				}
			}
		});

    	taCommands.textProperty().addListener((observable, oldValue, newValue) -> {
    		if(currentAction != null){
    			currentAction.setCommandsFromString(newValue);
    			writeActionsUpdate();
			}
		});
    }

    private void displayError(String error){
		txtInfoError.setText(error);
		txtInfoError.setFill(Color.RED);
	}

	private void displayInfoText(){
		txtInfoError.setText("Separate commands with new line");
		txtInfoError.setFill(Color.BLACK);
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

				writeActionsUpdate();
			}
		});
    }

	private void writeActionsUpdate() {
		try {
			if (currentAction != null) currentAction.setExecuted(false);
			DataProvider.updateTimeEventsTriggerData();
			ConfigProvider.getDataHandler().writeActions(DataProvider.getActions(), ConfigProvider.getConfig().getActionsFile());
		} catch (Exception e) {
			displayError("Couldn't update action in file (" + e.getMessage() + ")");
			e.printStackTrace();
		}
	}

	private void loadActionsList() {
    	actionsList = DataProvider.getActions();

    	if (actionsList.isEmpty()){
			borderPaneRoot.setLeft(null);
    		borderPaneRoot.setCenter(txtActions);
		} else {
    		borderPaneRoot.setLeft(vBoxActionsList);
    		borderPaneRoot.setCenter(vBoxDetails);

			if (!loadedActions.isEmpty()) loadedActions.clear();
			for(Action action:DataProvider.getActions()) {
				loadedActions.add(action.getName());
			}

			listActions.setItems(loadedActions);
			setupListSelection();
		}
    }
    
    private void setupActionsList() {
    	listActions.getSelectionModel().selectedIndexProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
    		if (newValue.intValue() >= 0 && newValue.intValue() <= listActions.getItems().size()) loadAction(actionsList.get(newValue.intValue()));
		});
    }
}
