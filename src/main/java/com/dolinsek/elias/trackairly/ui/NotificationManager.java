package com.dolinsek.elias.trackairly.ui;

import com.dolinsek.elias.trackairly.ConfigProvider;

import java.awt.*;

public class NotificationManager {

    public static void displayHideNotificationByConfig(){
        if (ConfigProvider.getConfig().displayHideNotification()){
            displayHideNotification();
        }
    }
    public static void displayHideNotification() {
        try {
            displayTray("trackairly now hidden", "trackairly is now running in background", TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            System.err.println("Error displaying tray: " + e.getMessage());
        }
    }

    public static void displayTray(String title, String subtitle, TrayIcon.MessageType messageType) throws Exception {
        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/ic_launcher.png"));
            TrayIcon trayIcon = new TrayIcon(image, "trackairly");

            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("trackairly notification");

            systemTray.add(trayIcon);
            trayIcon.displayMessage(title, subtitle, messageType);
        } else {
            System.err.println("System tray not supported!");
        }
    }
}
