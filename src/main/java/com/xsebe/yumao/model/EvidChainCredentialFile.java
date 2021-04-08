package com.xsebe.yumao.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EvidChainCredentialFile implements Serializable {

    /**
     * id
     */
    private String id;
    /**
     * 证据链
     */
    private EvidChain evidChain;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件URI
     */
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EvidChain getEvidChain() {
        return evidChain;
    }

    public void setEvidChain(EvidChain evidChain) {
        this.evidChain = evidChain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
