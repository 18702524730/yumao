package com.xsebe.yumao.exception;

@SuppressWarnings("serial")
public class YumaoException extends Exception {

    public YumaoException(final int happenCode, final String happenMessage, final Throwable cause) {
        super(cause);
        this.happenCode = happenCode;
        this.happenMessage = happenMessage;
    }

    public YumaoException(final int happenCode, final String happenMessage) {
        this.happenCode = happenCode;
        this.happenMessage = happenMessage;
    }

    private int happenCode;

    private String happenMessage;

    public int getHappenCode() {
        return happenCode;
    }

    public String getHappenMessage() {
        return happenMessage;
    }
}
