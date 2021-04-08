package com.xsebe.yumao.exception;

import org.apache.shiro.authc.AuthenticationException;

@SuppressWarnings("serial")
public class ShiroAccountNotFoundException extends AuthenticationException {

    public ShiroAccountNotFoundException(final String username) {
        this.username = username;
    }

    private String username;

    public String getUsername() {
        return username;
    }
}
