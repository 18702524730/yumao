package com.xsebe.yumao.model;

import java.util.Date;

/**
 * @Description:用户意见反馈(留言表)实体类
 * @date: 2018年8月2日 下午4:55:50
 * @ClassName: 周伯通
 */
public class UserProvideMessage {

    private String id;
    // 用户id
    private String userId;
    // 标题
    private String title;
    // 内容
    private String content;
    // 邮箱
    private String contantsEmail;
    // 联系电话
    private String contantsTelphone;
    // 反馈时间
    private Date recordTime;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getContantsEmail() {
        return contantsEmail;
    }

    public void setContantsEmail(final String contantsEmail) {
        this.contantsEmail = contantsEmail;
    }

    public String getContantsTelphone() {
        return contantsTelphone;
    }

    public void setContantsTelphone(final String contantsTelphone) {
        this.contantsTelphone = contantsTelphone;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(final Date recordTime) {
        this.recordTime = recordTime;
    }
}
