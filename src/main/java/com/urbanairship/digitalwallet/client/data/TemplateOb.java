package com.urbanairship.digitalwallet.client.data;


import org.json.simple.JSONObject;

import java.util.List;

public class TemplateOb implements BaseOb {
    private Long templateId;
    private List<PassFieldOb> fields;
    private List<HeaderOb> headers;

    public TemplateOb() {
    }

    public TemplateOb(JSONObject o) {
    }



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

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
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
}
