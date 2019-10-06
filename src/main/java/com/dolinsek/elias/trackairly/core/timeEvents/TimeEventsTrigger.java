package com.dolinsek.elias.trackairly.core.timeEvents;

import java.util.ArrayList;
import java.util.Calendar;

public class TimeEventsTrigger implements Runnable {

    private ArrayList<Action> actions;
    private volatile boolean checkDayTimeChanges = false;

    public TimeEventsTrigger(ArrayList<Action> actions) {
        this.actions = actions;
    }

    public void onDayRunningTimeChanged(long dayRunningTime) {
        int minutes = (int) ((dayRunningTime / (1000 * 60)) % 60);
        int hours = (int) ((dayRunningTime / (1000 * 60 * 60)) % 24);

        for (TimeEvent timeEvent : actions) {
            if (!timeEvent.isExecuted() && timeEvent.timeEventTriggerType == TimeEvent.TimeEventTriggerType.RUNNING_TIME && timeEvent.hours == hours && timeEvent.minutes == minutes) {
                timeEvent.execute();
            }
        }
    }

    private void onDayTimeChanged(int hour, int minutes) {
        for (TimeEvent timeEvent : actions) {
            if (!timeEvent.isExecuted() && timeEvent.timeEventTriggerType == TimeEvent.TimeEventTriggerType.TIME_OF_DAY && timeEvent.hours == hour && timeEvent.minutes == minutes) {
                timeEvent.execute();
            }
        }
    }

    public void checkDayTimeChanges() {
        checkDayTimeChanges = true;
        new Thread(this).start();
    }

    public void stopCheckingForDayTimeChanges() {
        checkDayTimeChanges = false;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    @Override
    public void run() {
        final Calendar calendar = Calendar.getInstance();
        while (checkDayTimeChanges) {
            try {
                calendar.setTimeInMillis(System.currentTimeMillis());
                onDayTimeChanged(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
