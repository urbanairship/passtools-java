package com.urbanairship.digitalwallet.client;


import com.google.gson.Gson;
import com.urbanairship.digitalwallet.client.data.LocationInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/*
    * Method      Path                            Description
    * GET         /                               List all passes for this user
    * GET         /{strPassId}                    Get the pass based on the pass id
    * GET         /id/{externalId}                Get the pass based on the external id
    * GET         /{strPassId}/viewJSONPass       View the Apple JSON for a pass based on its id
    * GET         /id/{externalId}/viewJSONPass   View the Apple JSON for a pass based on its external id
    * GET         /{strPassId}/download           Download the pkPass for tha apple pass, based on its id
    * GET         /id/{externalId}/download       Download the pkPass for tha apple pass, based on its external id
    * GET         /{strPassId}/tags               Get the tags for the specified pass based on the pass id
    * GET         /id/{externalId}/tags           Get the tags for the specified pass based on the external id

    * POST        /{strTemplateId}                Create a pass on that template, based on the template id.
    * POST        /{strTemplateId}/id/{externalId}    Create a pass on that template, and give the pass an external id.
    * POST        /id/{strTemplateExternalId}/id/{externalId} Create a pass on a template, based on the templates external id
                                                    and give the pass an external id.
    * POST        /{strPassId}/locations          Add a location to a pass
    * POST        /id/{externalId}/locations      Add a location to a pass based on the pass' external id.

    * PUT         /{strPassId}/tags               Add tags to the specified pass, based on the pass' id.
    * PUT         /id/{externalId}/tags           Add tags to the specified pass, based on the pass' external id.
    * PUT         /{strPassId}                    Update the pass specified by the pass id.
    * PUT         /id/{externalId}                Update the pass specified by the external id.
    * PUT         /{strPassId}/push               Tell Apple that the pass has been updated, based on pass' id.
    * PUT         /id/{externalId}/push           Tell Apple that the pass has been updated, based on pass' external id.

    * DELETE      /{strPassId}                    Delete the pass specified by that pass id.
    * DELETE      /id/{externalId}                Delete the pass specified by that external id.

    * DELETE      /{strPassId}/location/{strLocationId}   Delete the location on the pass specified by pass id.
    * DELETE      /id/{externalId}/location/{strLocationId}   Delete the location on the pass specified by external id.
 */

public class Pass extends PassToolsClient {
    /***********
     * properties
     ***********/

    private Map fields; // (field key, JSONObject )
    private Long passId;
    private Long templateId;
    private String url;
    private String externalId;

    private static final String missingPassFieldsError = "please pass a map of fields in!";
    private static final String missingPassError = "please input a valid Pass!";
    private static final String missingExternalId = "please pass a valid external id in!";
    private static final String missingTagsError = "please pass in a valid list of tags";
    private static final String missingExternalTemplateIdError = "please pass a valid external template id in!";

    /***********
     * constructors
     ***********/

    public Pass() {

    }

    public Pass(JSONObject response) {
        assign(response);
    }

    private Pass(PassToolsResponse response) {
        assign(response.getBodyAsJSONObject());
    }

    /***********
     * methods
     ***********/

    public static List<Pass> listPasses(int pageSize, int page) {
        try {
            List<Pass> passes = new ArrayList<Pass>();
            StringBuilder builder = new StringBuilder(getBaseUrl());
            builder.append("?pageSize=").append(pageSize).append("&page=").append(page);
            PassToolsResponse response = get(builder.toString());

            JSONObject jsonResponse = response.getBodyAsJSONObject();
            JSONArray passArray = (JSONArray) jsonResponse.get("passes");

            if (passArray != null) {
                for (Object o : passArray.toArray()) {
                    if (o instanceof JSONObject) {
                        passes.add(new Pass((JSONObject) o));
                    }
                }
            }

            return passes;
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Tag> getTags(long passId) {
        return getTagsInternal(getBaseUrl(passId));
    }

    public static List<Tag> getTags(String externalId) {
        checkNotNull(externalId, missingExternalId);
        return getTagsInternal(getBaseUrl(externalId));
    }

    public static List<String> addTags(long passId, List<String> tags) {
        checkNotNull(tags, missingTagsError);
        return addTagsInternal(getBaseUrl(passId), tags);
    }

    public static List<String> addTags(String externalId, List<String> tags) {
        checkNotNull(externalId, missingExternalId);
        checkNotNull(tags, missingTagsError);
        return addTagsInternal(getBaseUrl(externalId), tags);
    }

    public static List<String> addTag(long passId, String tag) {
        checkNotNull(tag, missingTagsError);
        return addTagInternal(getBaseUrl(passId), tag);
    }

    public static List<String> addTag(String externalId, String tag) {
        checkNotNull(externalId, missingExternalId);
        checkNotNull(tag, missingTagsError);
        return addTagInternal(getBaseUrl(externalId), tag);
    }

    /* Creates a pass with the Map fieldsModel set. The Map fieldsModel can be retrieved from the getTemplateModel() function given a templateId provided by the UI Template Builder */

    public static Pass create(long templateId, Map passFields) {
        checkNotNull(passFields, missingPassFieldsError);
        return createInternal(getBaseUrl(templateId), passFields);
    }

    public static Pass create(long templateId, String passExternalId, Map passFields) {
        checkNotNull(passFields, missingPassFieldsError);
        checkNotNull(passExternalId, missingExternalId);
        return createInternal(getBaseUrl(templateId) + "/id/" + passExternalId, passFields);
    }

    public static Pass create(String templateExternalId, String passExternalId, Map passFields) {
        checkNotNull(templateExternalId, missingExternalTemplateIdError);
        checkNotNull(passFields, missingPassFieldsError);
        checkNotNull(passExternalId, missingExternalId);
        return createInternal(getBaseUrl(templateExternalId) + "/id/" + passExternalId, passFields);
    }



    @SuppressWarnings("unchecked")
    public static Pass update(long passId, Map fields) {
        checkNotNull(fields, missingPassFieldsError);

        try {
            String url = getBaseUrl(passId);
            JSONObject jsonObj = new JSONObject(fields);

            Map formFields = new HashMap<String, Object>();
            formFields.put("json", jsonObj);

            return new Pass(put(url, formFields));
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Pass update(String externalId, Map fields) {
        checkNotNull(externalId, missingExternalId);
        checkNotNull(fields, missingPassFieldsError);

        try {
            String url = getBaseUrl(externalId);
            JSONObject jsonObj = new JSONObject(fields);

            Map formFields = new HashMap<String, Object>();
            formFields.put("json", jsonObj);

            return new Pass(put(url, formFields));
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void update(Pass pass) {
        checkNotNull(pass, missingPassError);
        checkNotNull(pass.passId, missingPassError);

        try {
            String url = getBaseUrl(pass.passId);
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

    public static Pass get(long passId) {
        try {
            String url = getBaseUrl(passId);
            PassToolsResponse response = get(url);

            return new Pass(response.getBodyAsJSONObject());
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Pass getPass(String externalId) {
        checkNotNull(externalId, missingExternalId);

        try {
            String url = getBaseUrl(externalId);
            PassToolsResponse response = get(url);

            return new Pass(response.getBodyAsJSONObject());
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void downloadPass(long passId, File to) {
        downloadPassInternal(getBaseUrl(passId) + "/download", to);
    }

    public static void downloadPass(String externalId, File to) {
        checkNotNull(externalId, missingExternalId);
        downloadPassInternal(getBaseUrl(externalId) + "/download", to);
    }

    public static void delete(long passId) {
        try {
            String url = getBaseUrl(passId);
            PassToolsResponse response = delete(url);
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteX(String externalId) {
        checkNotNull(externalId, missingExternalId);

        try {
            String url = getBaseUrl(externalId);
            PassToolsResponse response = delete(url);
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject viewPassbookJSONPass(long passId) {
        try {
            String url = getBaseUrl(passId) + "/viewJSONPass";
            PassToolsResponse response = get(url);
            return response.getBodyAsJSONObject();
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject viewPassbookJSONPass(String externalId) {
        checkNotNull(externalId, missingExternalId);

        try {
            String url = getBaseUrl(externalId) + "/viewJSONPass";
            PassToolsResponse response = get(url);
            return response.getBodyAsJSONObject();
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject push(long passId) {
        try {
            String url = getBaseUrl(passId) + "/push";
            PassToolsResponse response = put(url, Collections.emptyMap());
            return response.getBodyAsJSONObject();
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject push(String externalId) {
        checkNotNull(externalId, missingExternalId);

        try {
            String url = getBaseUrl(externalId) + "/push";
            PassToolsResponse response = put(url, Collections.emptyMap());
            return response.getBodyAsJSONObject();
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONArray addLocations(long passId, List<LocationInfo> locationInfo) {
        return addLocationsInternal(getBaseUrl(passId) + "/locations", locationInfo);
    }

    public static JSONArray addLocations(String externalId, List<LocationInfo> locationInfo) {
        checkNotNull(externalId, missingExternalId);
        return addLocationsInternal(getBaseUrl(externalId) + "/locations", locationInfo);
    }

    /* deletes a location from the pass */
    public static void deleteLocation(long passId, long passLocationId) {
        try {
            String url = getBaseUrl(passId) + "/location/" + String.valueOf(passLocationId);
            delete(url);
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteLocation(String externalId, long passLocationId) {
        checkNotNull(externalId, missingExternalId);

        try {
            String url = getBaseUrl(externalId) + "/location/" + String.valueOf(passLocationId);
            delete(url);
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /***********
     * getters
     ***********/

    public Map getFields() {
        return fields;
    }

    public Long getPassId() {
        return passId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public String getUrl() {
        return url;
    }

    public String getExternalId() {
        return externalId;
    }

    /***********
     * private methods
     ***********/

    private void reset() {
        passId = null;
        templateId = null;
        externalId = null;
        url = null;
        fields = null;
    }

    @SuppressWarnings("unchecked")
    private void assign(JSONObject response) {
        reset();

        if (response != null) {

            passId = toLong(response.get("id"));
            templateId = toLong(response.get("templateId"));
            if (response.get("externalId") != null) {
                externalId = (String) response.get("externalId");
            }
            url = (String) response.get("url");

            if (response.get("passFields") != null) {
                fields = (Map<String, String>) (response.get("passFields"));
            }
        }
    }

    private static String getBaseUrl() {
        return PassTools.API_BASE + "/pass";
    }

    private static String getBaseUrl(long passId) {
        return PassTools.API_BASE + "/pass/" + passId;
    }

    private static String getBaseUrl(String externalId) {
        return PassTools.API_BASE + "/pass/id/" + externalId;
    }

    private static void downloadPassInternal(String url, File to) {
        if (to == null || !to.exists()) {
            throw new IllegalArgumentException("please pass a valid file in!");
        }

        try {
            PassToolsResponse response = get(url);
            InputStream is = response.response.getEntity().getContent();
            FileOutputStream fos = new FileOutputStream(to);

            try {
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

    @SuppressWarnings("unchecked")
    private static JSONArray addLocationsInternal(String url, List<LocationInfo> locationInfo) {
        try {
            JSONArray array = new JSONArray();
            Gson gson = new Gson();

            for (LocationInfo currentLocation : locationInfo) {
                array.add(gson.toJson(currentLocation));
            }

            Map formFields = new HashMap<String, JSONArray>();
            formFields.put("json", array);

            PassToolsResponse response = post(url, formFields);

            return response.getBodyAsJSONArray();
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Tag> getTagsInternal(String url) {
        try {
            PassToolsResponse response = get(url);
            JSONObject json = response.getBodyAsJSONObject();
            JSONArray jsonTags = (JSONArray)json.get("tags");
            List<Tag> tags = new ArrayList<Tag>();
            for (Object o : jsonTags.toArray()) {
                if (o instanceof JSONObject) {
                    tags.add(new Tag((JSONObject)o));
                }
            }

            return tags;
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> addTagInternal(String url, String tag) {
        List<String> tags = new ArrayList<String>();
        tags.add(tag);
        return addTagsInternal(url, tags);
    }

    @SuppressWarnings("unchecked")
    private static List<String> addTagsInternal(String url, List<String> tags) {
        url += "/tags";
        try {
            JSONObject json = new JSONObject();
            JSONObject tagsObj = new JSONObject();
            JSONArray jsonTags = new JSONArray();

            for (String currentTag : tags) {
                jsonTags.add(currentTag);
            }
            tagsObj.put("tags", jsonTags);
            json.put("json", tagsObj);

            PassToolsResponse response = put(url, json);
            JSONObject jsonResponse = response.getBodyAsJSONObject();
            JSONArray newTags = (JSONArray)jsonResponse.get("newTags");
            List<String> addedTags = new ArrayList<String>();
            for (Object currentObj : newTags.toArray()) {
                if (currentObj instanceof String) {
                    addedTags.add((String)currentObj);
                }
            }
            return addedTags;
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static Pass createInternal(String url, Map fields) {
        try {
            JSONObject jsonObj = new JSONObject(fields);

            Map formFields = new HashMap<String, Object>();
            formFields.put("json", jsonObj);
            return new Pass(post(url, formFields));
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
