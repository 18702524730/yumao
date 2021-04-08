package com.xsebe.yumao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:TODO
 * @date:2019年3月1日 下午3:42:53
 * @author:周伯通
 */
@SuppressWarnings("serial")
public class UserFaq implements Serializable {

    private String id;

    private String title;

    private String content;
    
    private String contentHtml;

    private Date submitTime;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
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

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(final Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    
}
