package com.urbanairship.digitalwallet.client;


import com.google.gson.Gson;
import com.urbanairship.digitalwallet.client.data.LocationInfo;
import com.urbanairship.digitalwallet.client.exception.InvalidParameterException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pass extends PassToolsClient {
    public Map fields; // (field key, JSONObject )
    public Long passId;
    public Long templateId;
    public String url;

    private static void checkNotNullPassId(Long passId) {
        if (passId == null) {
            throw new InvalidParameterException("please input a valid Pass!");
        }
    }

    /* Creates a pass with the Map fiedsModel set. The Map fiedsModel can be retrieved from the getTemplateModel() function given a templateId provided by the UI Template Builder */
    public static Pass create(Long templateId, Map passFields) {
        try {
            if (passFields == null) {
                throw new InvalidParameterException("please pass a map of fields in!");
            }

            String url = PassTools.API_BASE + "/pass/" + templateId.toString();
            JSONObject jsonObj = new JSONObject(passFields);

            Map formFields = new HashMap<String, Object>();
            formFields.put("json", jsonObj);

            PassToolsResponse response = post(url, formFields);
            Pass pass = new Pass();

            JSONObject jsonObjResponse = response.getBodyAsJSONObject();

            pass.passId = new Long((String) jsonObjResponse.get("id"));
            pass.templateId = templateId;
            pass.url = (String) jsonObjResponse.get("url");
            pass.fields = (Map<String, String>) (jsonObjResponse.get("passFields"));

            return pass;

        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(Pass pass) {
        try {
            if (pass == null || pass.passId == null) {
                throw new InvalidParameterException("please input a valid Pass!");
            }

            String url = PassTools.API_BASE + "/pass/" + pass.passId.toString();
            JSONObject jsonObj = new JSONObject(pass.fields);

            Map formFields = new HashMap<String, Object>();
            formFields.put("json", jsonObj);

            PassToolsResponse response = put(url, formFields);
            JSONObject jsonObjResponse = response.getBodyAsJSONObject();
            pass.url = (String) jsonObjResponse.get("url");
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Pass get(Long passId) {
        try {
            checkNotNullPassId(passId);
            String url = PassTools.API_BASE + "/pass/" + passId.toString();
            PassToolsResponse response = get(url);

            JSONObject jsonObjResponse = response.getBodyAsJSONObject();

            Pass pass = new Pass();

            pass.passId = new Long((String) jsonObjResponse.get("id"));
            pass.templateId = new Long((String) jsonObjResponse.get("templateId"));
            pass.url = (String) jsonObjResponse.get("url");
            pass.fields = (Map<String, String>) (jsonObjResponse.get("passFields"));

            return pass;
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void downloadPass(Long passId, File to) {
        if (to == null || !to.exists()){
            throw new IllegalArgumentException("please pass a valid file in!");
        }

        try {
            String url = PassTools.API_BASE + "/pass/" + passId.toString() + "/download";
            PassToolsResponse response = get(url);
            InputStream is = response.response.getEntity().getContent();
            FileOutputStream fos = new FileOutputStream(to);

            try {
                int lastUpdate = 0;
                int c;
                while ((c = is.read()) != -1) {
                    fos.write(c);
                }
            } finally {
                is.close();
                fos.close();
            }

        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete(Long passId){
        try {

            checkNotNullPassId(passId);

            String url = PassTools.API_BASE + "/pass/" + passId.toString();

            PassToolsResponse response = delete(url);

        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject viewPassbookJSONPass(Long passId){
        try {
            checkNotNullPassId(passId);
            String url = PassTools.API_BASE + "/pass/" + passId.toString() +"/viewJSONPass";
            PassToolsResponse response = get(url);
            return response.getBodyAsJSONObject();
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject push(Long passId) {
        try {
            checkNotNullPassId(passId);
            String url = PassTools.API_BASE + "/pass/" + passId.toString() + "/push";
            PassToolsResponse response = put(url, Collections.emptyMap());
            return response.getBodyAsJSONObject();
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONArray addLocations(Long passId,List<LocationInfo> locationInfos) {
        try {
            String url = PassTools.API_BASE + "/pass/" +passId.toString() +"/locations";
            JSONArray array = new JSONArray();
            Gson gson = new Gson();

            for (LocationInfo locationInfo : locationInfos){
                array.add(gson.toJson(locationInfo));
            }

            Map formFields = new HashMap<String, JSONArray>();
            formFields.put("json", array);

            PassToolsResponse response = post(url,formFields);

            return response.getBodyAsJSONArray();
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* deletes a location from the template */
    public static void deleteLocation(Long passId, Long passLocationId){
        try {

            String url = PassTools.API_BASE + "/pass/" +passId.toString() +"/location/" + passLocationId.toString();
            delete(url);

        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
