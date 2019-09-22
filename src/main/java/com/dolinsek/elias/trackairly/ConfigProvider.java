package com.dolinsek.elias.trackairly;

import com.dolinsek.elias.trackairly.core.data.DataHandler;
import com.dolinsek.elias.trackairly.core.data.OfflineDataHandler;

public class ConfigProvider {

    private static Config config;
    private static DataHandler dataHandler;

    public static void init() throws Exception {
        config = Config.defaultFromDataHandler(new OfflineDataHandler());
        dataHandler = new OfflineDataHandler();
    }

    public static Config getConfig() {
        return config;
    }

    public static DataHandler getDataHandler() {
        return dataHandler;
    }
}
