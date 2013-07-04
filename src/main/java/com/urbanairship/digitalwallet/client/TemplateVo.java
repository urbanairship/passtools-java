package com.urbanairship.digitalwallet.client;


import com.urbanairship.digitalwallet.client.data.TemplateOb;
import com.urbanairship.digitalwallet.client.http.HttpException;
import com.urbanairship.digitalwallet.client.http.HttpUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class TemplateVo {
    private final static String _templateId = "templateId"; /* todo move this to an enum */

    public static Long createTemplate(TemplateOb template) throws IOException, HttpException {
        Long templateId = null;
        String json = createTemplateJSON(template.toJSON().toJSONString());
        JSONParser parser = new JSONParser();
        try {
            JSONObject o = (JSONObject) parser.parse(json);
            Object id = o.get(_templateId);
            if (id != null && id instanceof Long) {
                templateId = (Long)id;
            }
        } catch (ParseException e) {
            e.printStackTrace();    /* todo handle this */
        }
        return templateId;
    }

    public static Long createTemplate(String externalId, TemplateOb template) {
        return null;        /* todo implement this method */
    }

    public static TemplateOb getTemplate(long templateId) {
        return null;        /* todo implement this method */
    }

    public static TemplateOb getTemplate(String externalId) {
        return null;        /* todo implement this method */
    }

    public static void updateTemplate(long templateId, TemplateOb template) throws IOException, HttpException {
        updateTemplateJSON(templateId, template.toJSON().toJSONString());
    }

    public static void updateTemplate(String externalId, TemplateOb template) throws IOException, HttpException {
        updateTemplateJSON(externalId, template.toJSON().toJSONString());
    }


    /* direct JSON */
    public static String listTemplates() throws IOException, HttpException {
        return HttpUtil.get("/template/headers", null);
    }

    public static String getTemplateJSON(long templateId) throws IOException, HttpException {
        return HttpUtil.get("/template/" + templateId, null);
    }

    public static String getTemplateJSON(String externalId) throws IOException, HttpException {
        return HttpUtil.get("/template/id/" + externalId, null);
    }

    public static String createTemplateJSON(String json) throws IOException, HttpException {
        return HttpUtil.post("/template", VoHelper.buildParameter("json", json));
    }

    public static String createTemplateJSON(long projectId, String json) throws IOException, HttpException {
        return HttpUtil.post("/template/" + projectId, VoHelper.buildParameter("json", json));
    }

    public static String updateTemplateJSON(long templateId, String json) throws IOException, HttpException {
        return HttpUtil.put("/template/" + templateId, "json=" + json, null);
    }

    public static String updateTemplateJSON(String externalId, String json) throws IOException, HttpException {
        return HttpUtil.put("/template/id/" + externalId, "json=" + json, null);
    }

    public static String deleteTemplate(long templateId) throws IOException, HttpException {
        return HttpUtil.delete("/template/" + templateId, null);
    }

    public static String deleteTemplate(String externalId) throws IOException, HttpException {
        return HttpUtil.delete("/template/id/" + externalId, null);
    }
}
