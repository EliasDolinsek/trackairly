package com.dolinsek.elias.trackairly.core.timeEvents;

import com.dolinsek.elias.trackairly.core.data.DataObject;
import org.json.JSONObject;

public abstract class TimeEvent implements DataObject {

    protected String name;
    protected int hours, minutes;
    protected TimeEventTriggerType timeEventTriggerType;


    public TimeEvent(String name, int hours, int minutes, TimeEventTriggerType timeEventTriggerType) {
        this.name = name;
        this.hours = hours;
        this.minutes = minutes;
        this.timeEventTriggerType = timeEventTriggerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public TimeEventTriggerType getTimeEventTriggerType() {
        return timeEventTriggerType;
    }

    public void setTimeEventTriggerType(TimeEventTriggerType timeEventTriggerType) {
        this.timeEventTriggerType = timeEventTriggerType;
    }

    public static enum  TimeEventTriggerType {
        TIME_OF_DAY, RUNNING_TIME;

        @Override
        public String toString(){
            switch (this){
                case TIME_OF_DAY: return "TIME_OF_DAY";
                case RUNNING_TIME: return "RUNNING_TIME";
                default: return "RUNNING_TIME";
            }
        }
    }
}
