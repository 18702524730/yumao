package com.xsebe.yumao.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OriginalWorksCredentialFile implements Serializable {

    public static final int CATEGORY_NOTARY = 1;

    private String id;

    private OriginalWorks originalWorks;

    private int category;

    private String name;

    private String uri;

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

    public int getCategory() {
        return category;
    }

    public void setCategory(final int category) {
        this.category = category;
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
}
