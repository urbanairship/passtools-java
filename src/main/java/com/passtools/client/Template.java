package com.passtools.client;


import com.google.gson.Gson;
import com.passtools.client.exception.InvalidParameterException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Template extends PassToolsClient {
    public JSONObject templateHeader; /* keys are id,name,description... */
    public Map<String,JSONObject> fieldsModel; /* field key + field values = {value, label, changeMessage..} */


    private static void checkNotNull(Long templateId) {
        if (templateId == null) {
            throw new InvalidParameterException("please pass a valid template Id in!");
        }
    }


    public Long getId(){
        if (templateHeader!=null){
            return new Long((String)templateHeader.get("id"));
        } else {
            throw new RuntimeException("please set your templateHeader id first");
        }

    }


    public static Template getTemplate(Long templateId) {
        try {

            checkNotNull(templateId);

            String url = PassTools.API_BASE + "/template/" +templateId.toString();
            PassToolsResponse response = get(url);

            JSONObject jsonResponse = response.getBodyAsJSONObject();

            Template template = new Template();

            JSONObject jsonHeader = (JSONObject)jsonResponse.get("templateHeader");

            template.templateHeader = jsonHeader;
            template.fieldsModel = (JSONObject)jsonResponse.get("fieldsModel");


            return template;

        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public static void delete(Long templateId){

        try {

            checkNotNull(templateId);

            String url = PassTools.API_BASE + "/template/" + templateId.toString();

            PassToolsResponse response = delete(url);


        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }




    public static List<JSONObject> getMyTemplateHeaders() {
        try {

            String url = PassTools.API_BASE + "/template/headers";
            PassToolsResponse response = get(url);


            JSONObject jsonResponse = response.getBodyAsJSONObject();
            JSONArray templatesArray = (JSONArray)jsonResponse.get("templateHeaders");

            return (List<JSONObject>)templatesArray;

        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }





    /* adds locations to the template */
    public static JSONArray addLocations(Long templateId,List<Location> locations) {
        try {

            String url = PassTools.API_BASE + "/template/" +templateId.toString() +"/locations";

            JSONArray array = new JSONArray();
            Gson gson = new Gson();


            for (Location location:locations){
                array.add(gson.toJson(location));
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
    public static void deleteLocation(Long templateId, Long locationId){
        try {

            String url = PassTools.API_BASE + "/template/" +templateId.toString() +"/location/" + locationId.toString();
            delete(url);

        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



}
