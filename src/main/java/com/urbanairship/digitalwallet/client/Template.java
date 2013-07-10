package com.urbanairship.digitalwallet.client;


import com.google.common.base.Preconditions;
import com.urbanairship.digitalwallet.client.exception.InvalidParameterException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Template extends PassToolsClient {

    private Map<String, Object> templateHeader;
    private Map<String, JSONObject> fieldsModel; /* field key + field values = {value, label, changeMessage..} */
    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private String type;
    private String projectType;
    private Date updatedAt;
    private Date createdAt;
    private String vendor;
    private Long vendorId;
    private boolean deleted;
    private boolean disabled;
    private String externalId;

    private static final String missingTemplateIdError = "please pass a valid template Id in!";
    private static final String missingExternalIdError = "please pass a valid external Id in!";

    /*
     * Method      Path                            Description
     **  GET         /headers                        list out the headers of all templates for this user.
     **  GET         /{templateId}                   Get a template based on its id
     **  GET         /id/{externalId}                Get a template based on its external id
     ** DELETE      /{templateId}                   Delete a template based on its template id
     ** DELETE      /id/{externalId}                Delete a template based on its external id
     *
     ** POST        /                               Create a new template
     ** POST        /id/{externalId}                Create a new template and assign it an external id
     ** POST        /{projectId}                    Create a template and assign it to a project
     ** POST        /{projectId}/id/{externalId}    Create a template and assign it to a project, and give the template an external id
     ** POST        /duplicate/{templateId}         Create a new template with the contents of the specified template.
     ** POST        /duplicate/id/{externalId}      Create a new template with the contents of the specified template, by external id.
     ** PUT         /{strTemplateId}                Modify the specified template
     ** PUT         /id/{externalId}                Modify the specified template
     *
     * POST        /{templateId}/locations         Add locations to a template
     * POST        /id/{externalId}/locations      Add locations to a template based on the templates external id
     * DELETE      /{templateId}/location/{locationId} Delete a location from a template
     * DELETE      /id/{externalId}/location/{locationId} Delete a location from a template based on external id
     */

    public Template() {

    }

    public Template(JSONObject o) {
        assign(o);
    }


    /**
     * Create a template with the provided fields
     *
     * @param name         Name of the template
     * @param description  Description of the template
     * @param templateType Template Type, should be an enum instead of a string.
     * @param headers      Map of header values, JSON Object including value, fieldType and formatType
     * @param fields       Map of fields for the template. JSON Object of the fields.
     * @return Template ID of the newly created template.
     * @throws InvalidParameterException if missing a required parameter
     *                                   RuntimeException with other errors.
     */
    public static Long createTemplate(String name, String description, String templateType, Map<String, Object> headers, Map<String, Object> fields) {
        return createTemplateInternal(name, description, templateType, headers, fields, null, null);
    }

    /**
     * create a template with the provided fields and assign it to the project, projectId
     *
     * @param projectId    ID of the project you want to assign this template to.
     * @param name         Name of the template
     * @param description  Description of the template
     * @param templateType Template Type, should be an enum instead of a string.
     * @param headers      Map of header values, JSON Object including value, fieldType and formatType
     * @param fields       Map of fields for the template. JSON Object of the fields.
     * @return Template ID of the newly created template.
     * @throws InvalidParameterException if missing a required parameter
     *                                   RuntimeException with other errors.
     */
    public static Long createTemplate(Long projectId, String name, String description, String templateType, Map<String, Object> headers, Map<String, Object> fields) {
        return createTemplateInternal(name, description, templateType, headers, fields, projectId, null);
    }

    /**
     * create a template with the provided fields and assign it to the external ID specified by externalId
     *
     * @param externalId   external Id you want to be able to use to manipulate this newly created template.
     * @param name         Name of the template
     * @param description  Description of the template
     * @param templateType Template Type, should be an enum instead of a string.
     * @param headers      Map of header values, JSON Object including value, fieldType and formatType
     * @param fields       Map of fields for the template. JSON Object of the fields.
     * @return Template ID of the newly created template.
     * @throws InvalidParameterException if missing a required parameter
     *                                   RuntimeException with other errors.
     */
    public static Long createTemplate(String externalId, String name, String description, String templateType, Map<String, Object> headers, Map<String, Object> fields) {
        return createTemplateInternal(name, description, templateType, headers, fields, null, externalId);
    }

    /**
     * create a template with the provided fields and assign it to the project specified by projectId, and give it the
     * external ID specified by externalId
     *
     * @param projectId    ID of the project you want to assign this template to.
     * @param externalId   external Id you want to be able to use to manipulate this newly created template.
     * @param name         Name of the template
     * @param description  Description of the template
     * @param templateType Template Type, should be an enum instead of a string.
     * @param headers      Map of header values, JSON Object including value, fieldType and formatType
     * @param fields       Map of fields for the template. JSON Object of the fields.
     * @return Template ID of the newly created template.
     * @throws InvalidParameterException if missing a required parameter
     *                                   RuntimeException with other errors.
     */
    public static Long createTemplate(Long projectId, String externalId, String name, String description, String templateType, Map<String, Object> headers, Map<String, Object> fields) {
        return createTemplateInternal(name, description, templateType, headers, fields, projectId, externalId);
    }

    /**
     * Gets the template specified by templateId
     *
     * @param templateId ID of the template you want.
     * @return A Template Object with the values from templateId
     */
    public static Template getTemplate(Long templateId) {
        try {
            checkNotNull(templateId, missingTemplateIdError);

            String url = PassTools.API_BASE + "/template/" + templateId.toString();
            PassToolsResponse response = get(url);

            return new Template(response.getBodyAsJSONObject());
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the template associated with the externalId
     *
     * @param externalId External ID of the template you are looking for.
     * @return A template object associated with the externalId
     */
    public static Template getTemplate(String externalId) {
        try {
            checkNotNull(externalId, missingExternalIdError);

            String url = PassTools.API_BASE + "/template/id/" + URLEncoder.encode(externalId, "UTF-8");
            PassToolsResponse response = get(url);
            return new Template(response.getBodyAsJSONObject());
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Delete a template based on its template id
     *
     * @param templateId id of the template you want to delete.
     */
    public static void delete(Long templateId) {
        try {
            checkNotNull(templateId, missingTemplateIdError);

            String url = PassTools.API_BASE + "/template/" + templateId.toString();
            PassToolsResponse response = delete(url);
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete a template based on its external id
     *
     * @param externalId external id of the template you want to delete.
     */
    public static void deleteX(String externalId) {
        try {
            checkNotNull(externalId, missingExternalIdError);

            String url = PassTools.API_BASE + "/template/id/" + URLEncoder.encode(externalId, "UTF-8");
            PassToolsResponse response = delete(url);
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Duplicate the specified template and return the newly created templates id.
     *
     * @param templateId Template ID of the template you want to duplicate
     * @return The template ID of the newly created template.
     */
    public static Long duplicate(Long templateId) {
        try {
            Long id = null;
            checkNotNull(templateId, missingTemplateIdError);


            String url = PassTools.API_BASE + "/template/duplicate/" + templateId;
            PassToolsResponse response = post(url, emptyJSON());
            JSONObject jsonObj = response.getBodyAsJSONObject();
            Object o = jsonObj.get("templateId");

            if (o != null) {
                if (o instanceof Long) {
                    id = (Long) o;
                } else {
                    try {
                        id = Long.valueOf(o.toString());
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }

            }
            return id;
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Duplicate the specified template and return the newly created templates id.
     *
     * @param externalId External ID of the template you want to duplicate
     * @return The template ID of the newly created template.
     */
    public static Long duplicate(String externalId) {
        try {
            Long id = null;
            checkNotNull(externalId, missingExternalIdError);

            String url = PassTools.API_BASE + "/template/duplicate/id/" + URLEncoder.encode(externalId, "UTF-8");
            PassToolsResponse response = post(url, emptyJSON());
            JSONObject jsonObj = response.getBodyAsJSONObject();
            Object o = jsonObj.get("templateId");

            if (o != null) {
                if (o instanceof Long) {
                    id = (Long) o;
                } else {
                    try {
                        id = Long.valueOf(o.toString());
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }

            }
            return id;
        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the template headers.
     *
     * @return A list of template headers.
     *         <p/>
     *         todo need to add pagination
     */
    @SuppressWarnings("unchecked")
    public static List<JSONObject> getMyTemplateHeaders() {
        try {

            String url = PassTools.API_BASE + "/template/headers";
            PassToolsResponse response = get(url);

            JSONObject jsonResponse = response.getBodyAsJSONObject();
            JSONArray templatesArray = (JSONArray) jsonResponse.get("templateHeaders");

            return (List<JSONObject>) templatesArray;

        } catch (RuntimeException rte) {
            throw rte;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the template with the specified id.
     *
     * @param templateId  ID of the template you want to update.
     * @param name        Name you want the template to have.
     * @param description Description you want the template to have.
     * @param headers     Headers for the updated template.
     * @param fields      Fields for the updated template.
     */
    public static void updateTemplate(Long templateId, String name, String description, Map<String, Object> headers, Map<String, Object> fields) {
        checkNotNull(templateId, missingTemplateIdError);
        updateTemplateInternal(name, description, headers, fields, templateId, null);
    }

    /**
     * Update the template with the specified externalId.
     *
     * @param externalId  external id of the template you want to update.
     * @param name        Name you want the template to have.
     * @param description Description you want the template to have.
     * @param headers     Headers for the updated template.
     * @param fields      Fields for the updated template.
     */
    public static void updateTemplate(String externalId, String name, String description, Map<String, Object> headers, Map<String, Object> fields) {
        checkNotNull(externalId, missingExternalIdError);
        updateTemplateInternal(name, description, headers, fields, null, externalId);
    }

    /**
     * **************
     * Getters
     * ***************
     */
    public Map<String, Object> getTemplateHeader() {
        return templateHeader;
    }

    public Map<String, JSONObject> getFieldsModel() {
        return fieldsModel;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getProjectType() {
        return projectType;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getVendor() {
        return vendor;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public String getExternalId() {
        return externalId;
    }

    /**
     * **************
     * private methods
     * ***************
     */

    private void assignHeaders(Map<String, Object> headers) {
        for (Map.Entry<String, Object> current : headers.entrySet()) {
            String key = current.getKey();
            Object value = current.getValue();
            if (key.equals("id")) {
                this.id = toLong(value);
            } else if (key.equals("name")) {
                this.name = value.toString();
            } else if (key.equals("description")) {
                this.description = value.toString();
            } else if (key.equals("projectType")) {
                this.projectType = value.toString();
            } else if (key.equals("type")) {
                this.type = value.toString();
            } else if (key.equals("updatedAt")) {
                this.updatedAt = toTime(value.toString());
            } else if (key.equals("createdAt")) {
                this.createdAt = toTime(value.toString());
            } else if (key.equals("disabled")) {
                this.disabled = toBool(value);
            } else if (key.equals("deleted")) {
                this.deleted = toBool(value);
            } else if (key.equals("projectId")) {
                this.projectId = toLong(value);
            } else if (key.equals("vendor")) {
                this.vendor = value.toString();
            } else if (key.equals("vendorId")) {
                this.vendorId = toLong(value);
            } else if (key.equals("externalId")) {
                this.externalId = value.toString();
            } else {
                this.templateHeader.put(key, current.getValue());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void assign(JSONObject response) {
        reset();

        JSONObject headers = (JSONObject) response.get("templateHeader");
        this.templateHeader = new JSONObject();
        if (headers != null) {
            this.fieldsModel = (JSONObject) response.get("fieldsModel");
            assignHeaders(headers);
        } else {
            assignHeaders(response);
        }
    }

    private void reset() {
        this.id = null;
        this.description = null;
        this.type = null;
        this.name = null;
        this.templateHeader = null;
        this.fieldsModel = null;
        this.projectId = null;
        this.projectType = null;
        this.updatedAt = null;
        this.createdAt = null;
        this.vendor = null;
        this.vendorId = null;
    }

    private static Long createTemplateInternal(String name, String description, String templateType, Map<String, Object> headers, Map<String, Object> fields, Long projectId, String externalId) {
        try {
            /* check preconditions */
            try {
                Preconditions.checkNotNull(fields, "please pass a map of fields in!");
                Preconditions.checkNotNull(headers, "please pass a map of headers in!");
                Preconditions.checkNotNull(name, "please pass a template name in!");
                Preconditions.checkNotNull(description, "please pass a template description in!");
                Preconditions.checkNotNull(templateType, "please pass a template type in!");
            } catch (NullPointerException e) {
                /* thrown by preconditions checks */
                throw new InvalidParameterException(e.getMessage());
            }

            StringBuilder builder = new StringBuilder(PassTools.API_BASE);
            builder.append("/template");

            if (projectId != null) {
                builder.append('/').append(projectId);
            }

            if (externalId != null) {
                builder.append("/id/").append(URLEncoder.encode(externalId, "UTF-8"));
            }

            JSONObject jsonFields = new JSONObject(fields);
            JSONObject jsonHeaders = new JSONObject(headers);

            Map<String, Object> formFields = new HashMap<String, Object>();
            Map<String, Object> json = new HashMap<String, Object>();

            json.put("fields", jsonFields);
            json.put("headers", jsonHeaders);
            json.put("name", name);
            json.put("description", description);
            json.put("type", templateType);
            formFields.put("json", new JSONObject(json));

            PassToolsResponse response = post(builder.toString(), formFields);
            JSONObject jsonObj = response.getBodyAsJSONObject();
            Object o = jsonObj.get("templateId");

            if (o != null) {
                Long id = null;
                if (o instanceof Long) {
                    id = (Long) o;
                } else {
                    try {
                        id = Long.valueOf(o.toString());
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }
                return id;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static Map emptyJSON() {
        Map<String, Object> formFields = new HashMap<String, Object>();
        Map<String, Object> json = new HashMap<String, Object>();
        formFields.put("json", new JSONObject(json));
        return formFields;
    }

    private static Long updateTemplateInternal(String name, String description, Map<String, Object> headers, Map<String, Object> fields, Long templateId, String externalId) {
        try {
            Long id = null;

            /* check preconditions */
            try {
                Preconditions.checkNotNull(fields, "please pass a map of fields in!");
                Preconditions.checkNotNull(headers, "please pass a map of headers in!");
                Preconditions.checkNotNull(name, "please pass a template name in!");
                Preconditions.checkNotNull(description, "please pass a template description in!");
            } catch (NullPointerException e) {
                /* thrown by preconditions checks */
                throw new InvalidParameterException(e.getMessage());
            }

            StringBuilder builder = new StringBuilder(PassTools.API_BASE);
            builder.append("/template");

            if (templateId != null) {
                builder.append('/').append(templateId);
            } else if (externalId != null) {
                builder.append("/id/").append(URLEncoder.encode(externalId, "UTF-8"));
            }

            JSONObject jsonFields = new JSONObject(fields);
            JSONObject jsonHeaders = new JSONObject(headers);

            Map<String, Object> formFields = new HashMap<String, Object>();
            Map<String, Object> json = new HashMap<String, Object>();

            json.put("fields", jsonFields);
            json.put("headers", jsonHeaders);
            json.put("name", name);
            json.put("description", description);
            formFields.put("json", new JSONObject(json));

            PassToolsResponse response = put(builder.toString(), formFields);

            JSONObject jsonObj = response.getBodyAsJSONObject();

            Object o = jsonObj.get("templateId");

            if (o != null) {
                id = null;
                if (o instanceof Long) {
                    id = (Long) o;
                } else {
                    try {
                        id = Long.valueOf(o.toString());
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
