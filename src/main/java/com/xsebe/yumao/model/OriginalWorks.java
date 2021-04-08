package com.xsebe.yumao.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class OriginalWorks implements Serializable {
    
    public static final int TYPE_INDIVIDUAL = 1;
    
    public static final int TYPE_LEGAL_PERSON = 2;
    
    public static final int PROTECTION_STATUS_UPLOADING = 1;
    
    public static final int PROTECTION_STATUS_PROTECTING = 2;
    
    public static final int PROTECTION_STATUS_UPLOADING_FAILURE = 3;

    private String id;

    private String name;

    private int category;

    private int type;

    private Date creationTime;

    private int protectionStatus;
    
    private Date uploadingTime;
    
    private Date protectionTime;


    private OriginalWorksFile originalWorksFile;//存证文件

    public OriginalWorksFile getOriginalWorksFile() {
        return originalWorksFile;
    }

    public void setOriginalWorksFile(OriginalWorksFile originalWorksFile) {
        this.originalWorksFile = originalWorksFile;
    }

    public OriginalWorksCredentialFile getOriginalWorksCredentialFile() {
        return originalWorksCredentialFile;
    }

    public void setOriginalWorksCredentialFile(OriginalWorksCredentialFile originalWorksCredentialFile) {
        this.originalWorksCredentialFile = originalWorksCredentialFile;
    }

    private OriginalWorksCredentialFile originalWorksCredentialFile;//存证证书文件

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
}
