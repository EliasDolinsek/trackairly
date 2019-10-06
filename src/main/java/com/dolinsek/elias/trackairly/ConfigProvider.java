package com.dolinsek.elias.trackairly;

import com.dolinsek.elias.trackairly.core.data.DataHandler;
import com.dolinsek.elias.trackairly.core.data.OfflineDataHandler;
import com.dolinsek.elias.trackairly.core.timeEvents.TimeEventsTrigger;

public class ConfigProvider {

    private static Config config;
    private static DataHandler dataHandler;

    public static void init() throws Exception {
        dataHandler = new OfflineDataHandler();
        config = Config.defaultFromDataHandler(dataHandler);
    }

    public static Config getConfig() {
        return config;
    }

    public static DataHandler getDataHandler() {
        return dataHandler;
    }
}
