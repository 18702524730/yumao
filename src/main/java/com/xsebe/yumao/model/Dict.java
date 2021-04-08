package com.xsebe.yumao.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Dict implements Serializable {

    private String type;

    private String value;

    private String text;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
