package com.xsebe.yumao.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserFxServiceConfig implements Serializable {

    private String id;

    private User user;

    private String fxUserId;

    private String fxUserPublicKey;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getFxUserId() {
        return fxUserId;
    }

    public void setFxUserId(final String fxUserId) {
        this.fxUserId = fxUserId;
    }

    public String getFxUserPublicKey() {
        return fxUserPublicKey;
    }

    public void setFxUserPublicKey(final String fxUserPublicKey) {
        this.fxUserPublicKey = fxUserPublicKey;
    }
}
