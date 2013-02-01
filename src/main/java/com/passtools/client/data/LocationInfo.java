package com.passtools.client.data;


import com.passtools.client.exception.InvalidParameterException;
import org.json.simple.JSONObject;

public class LocationInfo {
    public Long id;
    public Double longitude,latitude;
    public String relevantText;
    public String streetAddress1,streetAddress2;
    public String city;
    public String region,regionCode;
    public String country;


    public static void validate(LocationInfo locInfo) throws Exception {

        if (locInfo == null){
            throw new InvalidParameterException("please pass a non null locationInfo!");
        }

        if (locInfo.latitude == null || locInfo.longitude == null){
            throw new InvalidParameterException("latitude and longitude are required!");
        }

    }


    public  JSONObject toJSON() {
        JSONObject json = new JSONObject();




        if (longitude != null) {
            json.put("longitude", longitude);
        }

        if (latitude != null) {
            json.put("latitude", latitude);
        }

        if (city != null) {
            json.put("city", city);
        }

        if (region != null) {
            json.put("region", region);
        }

        if (regionCode != null) {
            json.put("regionCode", regionCode);
        }

        if (relevantText != null) {
            json.put("relevantText", relevantText);
        }

        if (streetAddress1 != null) {
            json.put("streetAddress1", streetAddress1);
        }

        if (streetAddress2 != null) {
            json.put("streetAddress2", streetAddress2);
        }


        return json;

    }


    public static LocationInfo fromJSON(JSONObject json){
        LocationInfo loc = new LocationInfo();

        if (json.containsKey("id")) {
            loc.id = (Long) json.get("id");
        }




        if (json.containsKey("longitude")) {
            loc.longitude = (Double) json.get("longitude");
        }

        if (json.containsKey("latitude")) {
            loc.latitude = (Double) json.get("latitude");
        }

        if (json.containsKey("relevantText")) {
            loc.relevantText = (String) json.get("relevantText");
        }

        if (json.containsKey("city")) {
            loc.city = (String) json.get("city");
        }

        if (json.containsKey("country")) {
            loc.country = (String) json.get("country");
        }

        if (json.containsKey("region")) {
            loc.region = (String) json.get("region");
        }

        if (json.containsKey("regionCode")) {
            loc.regionCode = (String) json.get("regionCode");
        }

        if (json.containsKey("streetAddress1")) {
            loc.streetAddress1 = (String) json.get("streetAddress1");
        }

        if (json.containsKey("streetAddress2")) {
            loc.streetAddress2 = (String) json.get("streetAddress2");
        }

        return loc;

    }

}
