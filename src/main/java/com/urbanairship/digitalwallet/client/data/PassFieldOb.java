package com.urbanairship.digitalwallet.client.data;

import org.json.simple.JSONObject;

public class PassFieldOb implements BaseOb {
    private String label;
    private String value;
    private String changeMessage;
    private Long passId;
    private Long id;
    private Long fieldId;
    private String formatType;
    private int order;
    private String fieldType;
    private boolean required;
    private boolean hideEmpty;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getChangeMessage() {
        return changeMessage;
    }

    public void setChangeMessage(String changeMessage) {
        this.changeMessage = changeMessage;
    }

    public Long getPassId() {
        return passId;
    }

    public void setPassId(Long passId) {
        this.passId = passId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isHideEmpty() {
        return hideEmpty;
    }

    public void setHideEmpty(boolean hideEmpty) {
        this.hideEmpty = hideEmpty;
    }
}
