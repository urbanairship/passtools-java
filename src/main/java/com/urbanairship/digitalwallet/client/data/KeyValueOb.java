package com.urbanairship.digitalwallet.client.data;

import org.json.simple.JSONObject;

public class KeyValueOb implements BaseOb {
    private String key;
    private String value;

    @Override
    public JSONObject toJSON() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void fromJSON(JSONObject json) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void fromJSON(String json) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
