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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Get the list of your tags.
     *
     * @param pageSize  Number of tags you want returned per call
     * @param page      Page you want returned, starting with 1.
     * @return          A list of tags that you own.
     */
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

    /**
     * Get the list of passes on a tag.
     *
     * @param tag   Tag you want the list of passes for.
     * @param pageSize  Number of passes you want returned per page
     * @param page      The page you want returned, starting with 1.
     * @return      a list of passes.
     */
    public static List<Pass> getPasses(String tag, int pageSize, int page) {
        checkNotNull(tag, missingTagError);
        try {
            List<Pass> passes = new ArrayList<Pass>();

            StringBuilder builder = new StringBuilder(getBaseUrl());
            builder.append("/").append(URLEncoder.encode(tag, "UTF-8")).append("/passes")
                    .append("?pageSize=").append(pageSize).append("&page=").append(page);
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

    /**
     * Update all of the passes on a tag.
     *
     * @param tag       Tag you want to update the passes for.
     * @param fields    Fields you want to update.
     * @return          A ticket id for this update operation.
     */
    @SuppressWarnings("unchecked")
    public static Long updatePasses(String tag, Map fields) {
        checkNotNull(tag, missingTagError);
        try {
            StringBuilder builder = new StringBuilder(getBaseUrl());
            builder.append("/").append(URLEncoder.encode(tag, "UTF-8")).append("/passes");
            Map formParams = new HashMap<String, Object>();
            formParams.put("json", new JSONObject(fields));
            PassToolsResponse response = put(builder.toString(), formParams);

            JSONObject jsonObjResponse = response.getBodyAsJSONObject();
            return (Long) jsonObjResponse.get("ticketId");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Delete a tag and remove it from all passes.
     *
     * @param tag   Tag you want to delete
     * @return      A JSON Object with
     *                  status: "success" if successful.
     *                  count: the number of passes removed from the tag.
     *                  tagId: the id of the deleted tag.
     */
    public static JSONObject deleteTag(String tag) {
        checkNotNull(tag, missingTagError);
        try {
            String url = getBaseUrl() + "/" + URLEncoder.encode(tag, "UTF-8");
            PassToolsResponse response = delete(url);
            return response.getBodyAsJSONObject();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Remove all of the passes from a tag.
     *
     * @param tag   the tag you want to remove the passes from.
     * @return      A JSON Object with
     *                  status: "success" if successful.
     *                  count: the number of passes removed from the tag.
     *                  tagId: the id of the deleted tag.
     */
    public static JSONObject removeFromPasses(String tag) {
        checkNotNull(tag, missingTagError);
        try {
            StringBuilder builder = new StringBuilder(getBaseUrl());
            builder.append("/").append(URLEncoder.encode(tag, "UTF-8")).append("/passes");
            PassToolsResponse response = delete(builder.toString());
            return response.getBodyAsJSONObject();

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Remove the specified tag from the specified pass.
     *
     * @param tag       Tag you want to remove from the pass.
     * @param passId    Pass you want to un-tag.
     * @return          A JSONObject with
     *                      status: "success" if successful.
     *                      passId: The id of the pass that tag was removed from.
     *                      tagId: the id of the tag that the pass was removed from.
     */
    public static JSONObject removeFromPass(String tag, long passId) {
        checkNotNull(tag, missingTagError);
        try {
            StringBuilder builder = new StringBuilder(getBaseUrl());
            builder.append("/").append(URLEncoder.encode(tag, "UTF-8")).append("/pass/").append(passId);
            PassToolsResponse response = delete(builder.toString());
            return response.getBodyAsJSONObject();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Remove the specified tag from the specified pass.
     * @param tag           Tag you want to remove from the pass.
     * @param externalId    Pass you want to un-tag.
     * @return              A JSONObject with
     *                          status: "success" if successful.
     *                          passId: The id of the pass that tag was removed from.
     *                          tagId: the id of the tag that the pass was removed from.
     */
    public static JSONObject removeFromPass(String tag, String externalId) {
        checkNotNull(tag, missingTagError);
        try {
            StringBuilder builder = new StringBuilder(getBaseUrl());
            builder.append("/").append(URLEncoder.encode(tag, "UTF-8")).append("/pass/id/").append(externalId);
            PassToolsResponse response = delete(builder.toString());
            return response.getBodyAsJSONObject();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /*********
     * Private methods
     *********/

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
