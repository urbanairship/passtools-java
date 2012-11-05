package com.passtools.client;


import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PassToolsResponse {
    public HttpResponse response;
    public JSONParser parser;

    public PassToolsResponse(HttpResponse resp) {
        response = resp;
        parser = new JSONParser();
    }


    public int getResponseCode() {
        return response.getStatusLine().getStatusCode();
    }


    public JSONObject getBodyAsJSONObject() {
        try {
            String responseStr = EntityUtils.toString(response.getEntity());
            return (JSONObject) parser.parse(responseStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public JSONArray getBodyAsJSONArray() {
        try {
            String responseStr = EntityUtils.toString(response.getEntity());
            return (JSONArray) parser.parse(responseStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
