package com.dolinsek.elias.trackairly.core.timeEvents;

import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.PortableInterceptor.SUCCESSFUL;

import java.util.ArrayList;

public class Action extends TimeEvent{

    private ArrayList<String> commands;

    public Action(String name, int hours, int minutes, TimeEventTriggerType timeEventTriggerType, ArrayList<String> commands) {
        super(name, hours, minutes, timeEventTriggerType);
        this.commands = commands;
    }

    private Action(){
        super("", 0, 0, TimeEventTriggerType.RUNNING_TIME);
    }

    public static Action defaultAction(){
        return new Action();
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<String> commands) {
        this.commands = commands;
    }

    @Override
    public JSONObject toJSON() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", super.name);
        jsonObject.put("hours", super.hours);
        jsonObject.put("minutes", super.minutes);
        jsonObject.put("timeEventTriggerType", super.timeEventTriggerType.toString());
        jsonObject.put("commands", commandsToJSONArray());

        return jsonObject;
    }

    private JSONArray commandsToJSONArray(){
        final JSONArray jsonArray = new JSONArray();
        for(String command:commands){
            jsonArray.put(command);
        }

        return jsonArray;
    }

    @Override
    public Action fromJSON(JSONObject jsonObject) {
        final Action action = new Action();
        action.setName(jsonObject.getString("name"));
        action.setHours(jsonObject.getInt("hours"));
        action.setMinutes(jsonObject.getInt("minutes"));
        action.setTimeEventTriggerType(TimeEvent.TimeEventTriggerType.valueOf(jsonObject.getString("timeEventTriggerType")));
        action.setCommands(commandsFromJSONArray(jsonObject.getJSONArray("commands")));

        return action;
    }

    private ArrayList<String> commandsFromJSONArray(JSONArray jsonArray){
        final ArrayList<String> commands = new ArrayList<>();
        for (int i = 0; i<jsonArray.length(); i++){
            commands.add(jsonArray.getString(i));
        }

        return commands;
    }

    public static ArrayList<Action> actionsFromJSONArray(JSONArray jsonArray){
        final ArrayList<Action> actions = new ArrayList<>();
        for (int i = 0; i<jsonArray.length(); i++){
            actions.add(Action.defaultAction().fromJSON(jsonArray.getJSONObject(i)));
        }

        return actions;
    }

    public static JSONObject actionsToJSON(ArrayList<Action> actions){
        final JSONObject jsonObject = new JSONObject();
        final JSONArray jsonArray = new JSONArray();
        jsonObject.put("actions", jsonArray);

        for (Action action:actions){
            jsonArray.put(action.toJSON());
        }

        return jsonObject;
    }
}
