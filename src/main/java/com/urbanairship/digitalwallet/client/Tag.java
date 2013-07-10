package com.urbanairship.digitalwallet.client;

/*
    Method      Path                            Description
    GET         /                               list tags for this user
    GET         /{tag}/passes                   list passes on that tag
    PUT         /{tag}/passes                   update the passes on this tag
    DELETE      /{tag}                          Delete a tag and remove it from all of the passes it was associated with.
    DELETE      /{tag}/passes                   Remove a tag from all of its passes.
    DELETE      /{tag}/pass/{strPassId}         Remove the tag from the specified pass id.
    DELETE      /{tag}/pass/id/{externalId}     Remove a pass from a tag by it's external id.
 */

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Tag extends PassToolsClient {
    private Long id;
    private String tag;
    private List<Long> passIds;

    private final static String missingTagError = "Please provide a tag!";

    public Tag() {

    }

    public Tag(JSONObject o) {
        assign(o);
    }

    public Tag(Long id) {

    }

    public Long getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public List<Long> getPassIds() {
        return passIds;
    }

    public static List<Tag> getList(int pageSize, int page) {
        try {
            List<Tag> tags = new ArrayList<Tag>();
            StringBuilder builder = new StringBuilder(getBaseUrl());
            builder.append("?pageSize=").append(pageSize).append("&page=").append(page);
            PassToolsResponse response = get(builder.toString());

            JSONObject jsonResponse = response.getBodyAsJSONObject();
            JSONArray tagArray = (JSONArray) jsonResponse.get("tags");

            if (tagArray != null) {
                for (Object o : tagArray.toArray()) {
                    if (o instanceof JSONObject) {
                        tags.add(new Tag((JSONObject) o));
                    }
                }
            }

            return tags;
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Pass> getPasses(String tag) {
        checkNotNull(tag, missingTagError);
        try {
            List<Pass> passes = new ArrayList<Pass>();

            StringBuilder builder = new StringBuilder(getBaseUrl());
            builder.append("/").append(URLEncoder.encode(tag, "UTF-8")).append("/passes");
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

    public static Long updatePasses(String tag, Map fields) {
        checkNotNull(tag, missingTagError);
        try {
            StringBuilder builder = new StringBuilder(getBaseUrl());
            builder.append("/").append(URLEncoder.encode(tag, "UTF-8")).append("/passes");
            Map formParams = new HashMap<String, Object>();
            formParams.put("json", new JSONObject(fields));
            PassToolsResponse response = put(builder.toString(), formParams);

            JSONObject jsonObjResponse = response.getBodyAsJSONObject();
            Long ticketId = (Long) jsonObjResponse.get("ticketId");
            return ticketId;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean deleteTag(String tag) {
        checkNotNull(tag, missingTagError);
        try {
            String url = getBaseUrl() + "/" + URLEncoder.encode(tag, "UTF-8");
            PassToolsResponse response = delete(url);
            JSONObject jsonObjResponse = response.getBodyAsJSONObject();
            String status = (String) jsonObjResponse.get("status");
            if ((!StringUtils.isBlank(status)) && status.equals("success")) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean removeFromPasses(String tag) {
        checkNotNull(tag, missingTagError);
        try {
            StringBuilder builder = new StringBuilder(getBaseUrl());
            builder.append("/").append(URLEncoder.encode(tag, "UTF-8")).append("/passes");
            PassToolsResponse response = delete(builder.toString());
            JSONObject jsonObjResponse = response.getBodyAsJSONObject();
            String status = (String) jsonObjResponse.get("status");
            if ((!StringUtils.isBlank(status)) && status.equals("success")) {
                return true;
            } else {
                return false;
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static boolean removeFromPass(String tag, Long passId) {
        checkNotNull(tag, missingTagError);
        try {
            StringBuilder builder = new StringBuilder(getBaseUrl());
            builder.append("/").append(URLEncoder.encode(tag, "UTF-8")).append("/pass/").append(passId);
            PassToolsResponse response = delete(builder.toString());
            JSONObject jsonObjResponse = response.getBodyAsJSONObject();
            String status = (String) jsonObjResponse.get("status");
            if ((!StringUtils.isBlank(status)) && status.equals("success")) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static boolean removeFromPass(String tag, String externalId) {
        checkNotNull(tag, missingTagError);
        try {
            StringBuilder builder = new StringBuilder(getBaseUrl());
            builder.append("/").append(URLEncoder.encode(tag, "UTF-8")).append("/pass/id/").append(externalId);
            PassToolsResponse response = delete(builder.toString());
            JSONObject jsonObjResponse = response.getBodyAsJSONObject();
            String status = (String) jsonObjResponse.get("status");
            if ((!StringUtils.isBlank(status)) && status.equals("success")) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void reset() {
        id = null;
        tag = null;
        passIds = null;
    }

    private void assign(JSONObject o) {
        reset();
        if (o != null) {
            id = (Long) o.get("id");
            if (o.get("tag") != null) {
                tag = (String) o.get("tag");
            } else if (o.get("name") != null) {
                tag = (String) o.get("name");
            }
        }
    }

    private static String getBaseUrl() {
        return PassTools.API_BASE + "/tag";
    }
}
