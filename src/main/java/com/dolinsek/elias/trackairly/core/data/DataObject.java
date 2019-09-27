package com.dolinsek.elias.trackairly.core.data;

import com.dolinsek.elias.trackairly.Config;
import org.json.JSONObject;

public interface DataObject {

    public abstract JSONObject toJSON();
    public abstract DataObject fromJSON(JSONObject jsonObject);
}
