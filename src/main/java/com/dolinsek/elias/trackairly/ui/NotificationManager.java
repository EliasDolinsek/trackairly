package com.dolinsek.elias.trackairly.ui;

import com.dolinsek.elias.trackairly.ConfigProvider;

import java.awt.*;

public class NotificationManager {

    public static final long DURATION_SHORT = 1000;
    public static final long DURATION_MEDIUM = 2000;
    public static final long DURATION_LONG = 3000;

    public static void displayHideNotificationByConfig(){
        if (ConfigProvider.getConfig().displayHideNotification()){
            displayHideNotification();
        }
    }
    public static void displayHideNotification() {
        try {
            displayNotification("trackairly now hidden", "trackairly is now running in background", TrayIcon.MessageType.INFO, DURATION_MEDIUM);
        } catch (Exception e) {
            System.err.println("Error displaying tray: " + e.getMessage());
        }
    }

    public static void displayNotification(String title, String subtitle, TrayIcon.MessageType messageType, long duration) throws Exception {
        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/ic_launcher.png"));
            TrayIcon trayIcon = new TrayIcon(image, "trackairly");

            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("trackairly notification");

            systemTray.add(trayIcon);
            trayIcon.displayMessage(title, subtitle, messageType);

            new Thread(() -> {
                try {
                    Thread.sleep(duration);
                    systemTray.remove(trayIcon);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            System.err.println("System tray not supported!");
        }
    }
}
