package com.urbanairship.digitalwallet.client.data;


import org.json.simple.JSONObject;

import java.util.List;

public class PassOb implements BaseOb {
    private List<PassFieldOb> fields;
    private List<HeaderOb> headers;
    private Long passId;
    private Long templateId;
    private String url;

    @Override
    public JSONObject toJSON() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void fromJSON(JSONObject json) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void fromJSON(String json) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<PassFieldOb> getFields() {
        return fields;
    }

    public void setFields(List<PassFieldOb> fields) {
        this.fields = fields;
    }

    public List<HeaderOb> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HeaderOb> headers) {
        this.headers = headers;
    }

    public Long getPassId() {
        return passId;
    }

    public void setPassId(Long passId) {
        this.passId = passId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
