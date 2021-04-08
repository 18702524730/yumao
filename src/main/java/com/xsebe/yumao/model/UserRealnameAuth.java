package com.xsebe.yumao.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserRealnameAuth implements Serializable {
    public static final String ENT_BANK_NO_DICT_TYPE = "ent_bank_no";
    /**
     * 认证成功。
     */
    public static final int STATUS_AUTH_SUCCESS = 1;
    /**
     * 认证失败。
     */
    public static final int STATUS_AUTH_FAILURE = 2;
    /**
     * 未审核（企业）。
     */
    public static final int STATUS_AUDIT_WAITING = 3;
    /**
     * 待修改（企业）。
     */
    public static final int STATUS_MODIFY_WAITING = 4;
    /**
     * 打款中（企业）。
     */
    public static final int STATUS_REMITTANCE_WAITING = 5;
    /**
     * 打款成功（企业）。
     */
    public static final int STATUS_REMITTANCE_SUCCESS = 6;
    /**
     * 打款失败（企业）。
     */
    public static final int STATUS_REMITTANCE_FAILURE = 7;

    private String id;

    private User user;

    private String realname;

    private String idcard;

    private String bankCardNo;

    private String bankReservedMobile;

    private String entName;

    private String entBankName;

    private String entBankNoDictValue;

    private String entBankPublicAccountNo;

    private String entIdPicture;

    private int status;

    private String failureCause;

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

    public String getRealname() {
        return realname;
    }

    public void setRealname(final String realname) {
        this.realname = realname;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(final String idcard) {
        this.idcard = idcard;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(final String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankReservedMobile() {
        return bankReservedMobile;
    }

    public void setBankReservedMobile(final String bankReservedMobile) {
        this.bankReservedMobile = bankReservedMobile;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(final String entName) {
        this.entName = entName;
    }

    public String getEntBankName() {
        return entBankName;
    }

    public void setEntBankName(final String entBankName) {
        this.entBankName = entBankName;
    }

    public String getEntBankNoDictValue() {
        return entBankNoDictValue;
    }

    public void setEntBankNoDictValue(final String entBankNoDictValue) {
        this.entBankNoDictValue = entBankNoDictValue;
    }

    public String getEntBankPublicAccountNo() {
        return entBankPublicAccountNo;
    }

    public void setEntBankPublicAccountNo(final String entBankPublicAccountNo) {
        this.entBankPublicAccountNo = entBankPublicAccountNo;
    }

    public String getEntIdPicture() {
        return entIdPicture;
    }

    public void setEntIdPicture(final String entIdPicture) {
        this.entIdPicture = entIdPicture;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public String getFailureCause() {
        return failureCause;
    }

    public void setFailureCause(final String failureCause) {
        this.failureCause = failureCause;
    }
}
