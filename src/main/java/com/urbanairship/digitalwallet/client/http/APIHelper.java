package com.urbanairship.digitalwallet.client.http;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIHelper {
    private static Map<String, String> buildParameter(String name, String value) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(name, value);
        return parameters;
    }

    public static String createPass(long templateId, String json) throws IOException, HttpException {
        return HttpUtil.post("/pass/" + templateId, buildParameter("json", json));
    }

    public static String createPass(long templateId, String json, String externalId) throws IOException, HttpException {
        return HttpUtil.post("/pass/" + templateId + "/id/" + externalId, buildParameter("json", json));
    }

    public static String createPass(String templateExternalId, String json, String externalId) throws IOException, HttpException {
        return HttpUtil.post("/pass/id/" + templateExternalId + "/id/" + externalId, buildParameter("json", json));
    }

    public static String updatePass(long passId, String json) throws IOException, HttpException {
        return HttpUtil.put("/pass/" + passId, json, null);
    }

    public static String updatePass(String externalId, String json) throws IOException, HttpException {
        return HttpUtil.put("/pass/id/" + externalId, json, null);
    }

    public static String updatePass(long passId, String json, long delayTime) throws IOException, HttpException {
        return HttpUtil.put("/pass/" + passId, json, buildParameter("delayTime", String.valueOf(delayTime)));
    }

    public static String updatePass(String externalId, String json, long delayTime) throws IOException, HttpException {
        Map<String, String> map = buildParameter("delayTime", String.valueOf(delayTime));
        return HttpUtil.put("/pass/id/" + externalId, json, map);
    }

    public static String getPass(Long passId) throws IOException, HttpException {
        return HttpUtil.get("/pass/" + passId, null);
    }

    public static String getPass(String externalId) throws IOException, HttpException {
        return HttpUtil.get("/pass/id/" + externalId, null);
    }

    /*
        /v1/pass/[pass id]/tags
        /v1/pass/id/[uuid]/tags
     */
    public static String addTags(Long passId, JSONArray tags) throws IOException, HttpException {
        return HttpUtil.put("/pass/" + passId + "/tags", null, buildParameter("json", tags.toJSONString()));
    }

    public static String addTags(String externalId, JSONArray tags) throws IOException, HttpException {
        return HttpUtil.put("/pass/id/" + externalId + "/tags", null, buildParameter("json", tags.toJSONString()));

    }

    public static String listPasses() throws IOException, HttpException {
        return HttpUtil.get("/pass/", null);
    }

    public static String viewJSON(Long passId) throws IOException, HttpException {
        return HttpUtil.get("/pass/" + passId + "/viewJSONPass", null);
    }

    public static String viewJSON(String externalId) throws IOException, HttpException {
        return HttpUtil.get("/pass/id/" + externalId + "/viewJSONPass", null);
    }

    public static String removeTag(String tag, Long passId) throws IOException, HttpException {
        return HttpUtil.delete("/tag/" + tag + "/pass/" + passId, null );
    }

    public static String removeTag(String tag, String externalId) throws IOException, HttpException {
        return HttpUtil.delete("/tag/" + tag + "/pass/id/" + externalId, null );
    }

    public static String listTagsForPass(Long passId) throws IOException, HttpException {
        return HttpUtil.get("/pass/" + passId + "/tags", null);
    }

    public static String listTagsForPass(String externalId) throws IOException, HttpException {
        return HttpUtil.get("/pass/id/" + externalId + "/tags", null);
    }

    public static String listTags() throws IOException, HttpException {
        return HttpUtil.get("/tag/", null);
    }

    public static String listPassesForTag(String tag) throws IOException, HttpException {
        return HttpUtil.get("/tag/" + tag + "/passes", null);
    }

    public static String deleteTag(String tag) throws IOException, HttpException {
        return HttpUtil.delete("/tag/" + tag, null);
    }

    public static String updateTag(String tag, String json) throws IOException, HttpException {
        return HttpUtil.put("/tag/" + tag + "/passes", json, null);
    }

    /* Templates */
    public static String listTemplates() throws IOException, HttpException {
        return HttpUtil.get("/template/headers", null);
    }

    public static String viewTemplate(long templateId) throws IOException, HttpException {
        return HttpUtil.get("/template/" + templateId, null);
    }

    public static String viewTemplate(String externalId) throws IOException, HttpException {
        return HttpUtil.get("/template/id/" + externalId, null);
    }

    public static String createTemplate(String json) throws IOException, HttpException {
        return HttpUtil.post("/template", buildParameter("json", json));
    }

    public static String createTemplate(Long projectId, String json) throws IOException, HttpException {
        return HttpUtil.post("/template/" + projectId, buildParameter("json", json));
    }

    public static String updateTemplate(long templateId, String json) throws IOException, HttpException {
        return HttpUtil.put("/template/" + templateId, "json=" + json, null);
    }

    public static String updateTemplate(String externalId, String json) throws IOException, HttpException {
        return HttpUtil.put("/template/id/" + externalId, "json=" + json, null);
    }

    public static String deleteTemplate(long templateId) throws IOException, HttpException {
        return HttpUtil.delete("/template/" + templateId, null);
    }

    public static String deleteTemplate(String externalId) throws IOException, HttpException {
        return HttpUtil.delete("/template/id/" + externalId, null);
    }

    public static String createProject(String json) throws IOException, HttpException {
        return HttpUtil.post("/project", buildParameter("json", json));
    }

    public static String getProject(long projectId) throws IOException, HttpException {
        return HttpUtil.get("/project/" + projectId, null);
    }

    public static String getProject(String externalId) throws IOException, HttpException {
        return HttpUtil.get("/project/id/" + externalId, null);
    }
    public static String deleteProject(long projectId) throws IOException, HttpException {
        return HttpUtil.delete("/project/" + projectId, null);
    }

    public static String deleteProject(String externalId) throws IOException, HttpException {
        return HttpUtil.delete("/project/id/"  + externalId, null);
    }

    public static String updateProject(long projectId, String json) throws IOException, HttpException {
        return HttpUtil.put("/project/" + projectId, null, buildParameter("json", json));
    }

    public static String installLayout(long layoutId, String json) throws IOException, HttpException {
        return HttpUtil.post("/project/" + layoutId, null);
    }
    public static String updateProject(String externalId, String json) throws IOException, HttpException {
        return HttpUtil.put("/project/id/" + externalId , null, buildParameter("json", json));
    }

    private static JSONObject getTagsJson(List<String> tags) {
        JSONObject o = new JSONObject();
        JSONArray a = new JSONArray();
        for (String current : tags) {
            a.add(current);
        }
        o.put("tags", a);
        return o;
    }
}
