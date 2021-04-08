package com.xsebe.yumao.model;

import java.io.Serializable;
/**
 * 
 * @Description:用户订单服务表
 * @date:   2018年8月22日 下午2:16:12 
 */
@SuppressWarnings("serial")
public class UserOrderService implements Serializable {

    private String id;

    private String name;

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
}
