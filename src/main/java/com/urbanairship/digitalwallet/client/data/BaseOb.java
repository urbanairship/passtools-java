package com.urbanairship.digitalwallet.client.data;

import org.json.simple.JSONObject;

public interface BaseOb {
    JSONObject toJSON();
    void fromJSON(JSONObject json);
    void fromJSON(String json);
}
