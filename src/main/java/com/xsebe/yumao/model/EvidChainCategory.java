package com.xsebe.yumao.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EvidChainCategory implements Serializable{

    /**
     * id
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
