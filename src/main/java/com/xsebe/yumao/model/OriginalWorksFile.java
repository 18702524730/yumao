package com.xsebe.yumao.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OriginalWorksFile implements Serializable {

    private String id;

    private OriginalWorks originalWorks;

    private String name;

    private String uri;

    private String fxFileKey;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public OriginalWorks getOriginalWorks() {
        return originalWorks;
    }

    public void setOriginalWorks(final OriginalWorks originalWorks) {
        this.originalWorks = originalWorks;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public String getFxFileKey() {
        return fxFileKey;
    }

    public void setFxFileKey(final String fxFileKey) {
        this.fxFileKey = fxFileKey;
    }
}
