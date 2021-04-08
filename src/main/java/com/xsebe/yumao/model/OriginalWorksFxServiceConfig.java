package com.xsebe.yumao.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OriginalWorksFxServiceConfig implements Serializable {

    private String id;

    private OriginalWorks originalWorks;

    private String fxEvidId;

    private String fxWorkId;

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

    public String getFxEvidId() {
        return fxEvidId;
    }

    public void setFxEvidId(final String fxEvidId) {
        this.fxEvidId = fxEvidId;
    }

    public String getFxWorkId() {
        return fxWorkId;
    }

    public void setFxWorkId(final String fxWorkId) {
        this.fxWorkId = fxWorkId;
    }
}
