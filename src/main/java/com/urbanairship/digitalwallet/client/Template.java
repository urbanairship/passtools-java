package com.urbanairship.digitalwallet.client;


import com.urbanairship.digitalwallet.client.exception.InvalidParameterException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public class Template extends PassToolsClient {

    public JSONObject templateHeader; /* keys are id,name,description... */
    public Map<String,JSONObject> fieldsModel; /* field key + field values = {value, label, changeMessage..} */

    /**
     *
     * Method      Path                            Description
     * GET         /headers                        list out the headers of all templates for this user.
     * GET         /{templateId}                   Get a template based on its id
     * GET         /id/{externalId}                Get a template based on its external id
     * DELETE      /{templateId}                   Delete a template based on its template id
     * DELETE      /id/{externalId}                Delete a template based on its external id

     POST        /                               Create a new template
     POST        /id/{externalId}                Create a new template and assign it an external id
     POST        /{projectId}                    Create a template and assign it to a project
     POST        /{projectId}/id/{externalId}    Create a template and assign it to a project, and give the template an external id
     POST        /duplicate/{templateId}         Create a new template with the contents of the specified template.
     POST        /duplicate/id/{externalId}      Create a new template with the contents of the specified template, by external id.
     PUT         /{strTemplateId}                Modify the specified template
     PUT         /id/{externalId}                Modify the specified template

     POST        /{templateId}/locations         Add locations to a template
     POST        /id/{externalId}/locations      Add locations to a template based on the templates external id
     DELETE      /{templateId}/location/{locationId} Delete a location from a template
     DELETE      /id/{externalId}/location/{locationId} Delete a location from a template based on external id
     */

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

            String url = PassTools.API_BASE + "/template/" + templateId.toString();
            PassToolsResponse response = get(url);

            JSONObject jsonResponse = response.getBodyAsJSONObject();

            Template template = new Template();

            template.templateHeader = (JSONObject)jsonResponse.get("templateHeader");
            template.fieldsModel = (JSONObject)jsonResponse.get("fieldsModel");


            return template;

        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Template getTemplate(String externalId) {
        try {
            String url = PassTools.API_BASE + "/template/id/" + externalId;
            PassToolsResponse response = get(url);

            JSONObject jsonResponse = response.getBodyAsJSONObject();

            Template template = new Template();

            template.templateHeader = (JSONObject)jsonResponse.get("templateHeader");
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

    public static void deleteX(String externalId){
        try {
            String url = PassTools.API_BASE + "/template/id/" + externalId;
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
}










