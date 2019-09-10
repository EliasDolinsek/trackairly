package com.dolinsek.elias.runningTracker;

import com.dolinsek.elias.runningTracker.core.data.OfflineDataHandler;

public class ConfigProvider {

    private static Config config;

    public static void init() throws Exception {
        config = Config.defaultFromDataHandler(new OfflineDataHandler());
    }

    public static Config getConfig() {
        return config;
    }
}
