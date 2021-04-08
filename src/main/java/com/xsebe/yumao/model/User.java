package com.xsebe.yumao.model;

import java.io.Serializable;
import java.util.Date;

import com.xsebe.api.utility.DataDesensitUtility;

@SuppressWarnings("serial")
public class User implements Serializable {

    public static final int USER_TYPE_PERSONAL = 1;

    public static final int USER_TYPE_ENTERPRISE = 2;

    private String id;

    private String username;

    private String loginPasswordCipher;

    private String loginPasswordSalt;

    private int userType;

    private String mobile;

    private String mobileForDisplay;

    private String email;

    private String entUniCreditCode;

    private String entContactsRealname;

    private String entContactsMobile;

    private String entContactsMobileForDisplay;

    private Date registeredTime;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getLoginPasswordCipher() {
        return loginPasswordCipher;
    }

    public void setLoginPasswordCipher(final String loginPasswordCipher) {
        this.loginPasswordCipher = loginPasswordCipher;
    }

    public String getLoginPasswordSalt() {
        return loginPasswordSalt;
    }

    public void setLoginPasswordSalt(final String loginPasswordSalt) {
        this.loginPasswordSalt = loginPasswordSalt;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(final int userType) {
        this.userType = userType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
        mobileForDisplay = DataDesensitUtility.getMobile(mobile);
    }

    public String getMobileForDisplay() {
        return mobileForDisplay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEntUniCreditCode() {
        return entUniCreditCode;
    }

    public void setEntUniCreditCode(final String entUniCreditCode) {
        this.entUniCreditCode = entUniCreditCode;
    }

    public String getEntContactsRealname() {
        return entContactsRealname;
    }

    public void setEntContactsRealname(final String entContactsRealname) {
        this.entContactsRealname = entContactsRealname;
    }

    public String getEntContactsMobile() {
        return entContactsMobile;
    }

    public void setEntContactsMobile(final String entContactsMobile) {
        this.entContactsMobile = entContactsMobile;
        entContactsMobileForDisplay = DataDesensitUtility.getMobile(entContactsMobile);
    }

    public Date getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(final Date registeredTime) {
        this.registeredTime = registeredTime;
    }

    public String getEntContactsMobileForDisplay() {
        return entContactsMobileForDisplay;
    }

}
