package com.xsebe.yumao.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class OriginalWorksVO implements Serializable {
    
    private String id;

    private String name;

    private int category;

    private int type;

    private Date creationTime;

    private int protectionStatus;
    
    private Date uploadingTime;
    
    private Date protectionTime;

    private String fxEvidId;

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

    public int getCategory() {
        return category;
    }

    public void setCategory(final int category) {
        this.category = category;
    }

    public int getType() {
        return type;
    }

    public void setType(final int type) {
        this.type = type;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(final Date creationTime) {
        this.creationTime = creationTime;
    }

    public int getProtectionStatus() {
        return protectionStatus;
    }

    public void setProtectionStatus(final int protectionStatus) {
        this.protectionStatus = protectionStatus;
    }

    public Date getUploadingTime() {
        return uploadingTime;
    }

    public void setUploadingTime(final Date uploadingTime) {
        this.uploadingTime = uploadingTime;
    }

    public Date getProtectionTime() {
        return protectionTime;
    }

    public void setProtectionTime(final Date protectionTime) {
        this.protectionTime = protectionTime;
    }

    public String getFxEvidId() {
        return fxEvidId;
    }

    public void setFxEvidId(String fxEvidId) {
        this.fxEvidId = fxEvidId;
    }
}
