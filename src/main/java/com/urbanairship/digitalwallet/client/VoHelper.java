package com.urbanairship.digitalwallet.client;

import java.util.HashMap;
import java.util.Map;


public class VoHelper {
    public static Map<String, String> buildParameter(String name, String value) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(name, value);
        return parameters;
    }
}
