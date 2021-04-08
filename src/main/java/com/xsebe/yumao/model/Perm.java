package com.xsebe.yumao.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Perm implements Serializable {
    
    private String id;
    
    private String name;
    
    private String label;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }
}
