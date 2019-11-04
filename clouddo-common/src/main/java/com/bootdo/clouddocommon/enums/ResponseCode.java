package com.bootdo.clouddocommon.enums;

/**
 * @Author : liushan
 * @Date : 2019/11/1 5:00 PM
 */
public enum ResponseCode {
    SUCCESS(1000, "success"),
    UNKNOWN_INNER_ERROR(2000, "unknown inner error"),
    UNSUPPORTED_REQUEST(2001, "unsupported request"),
    REQUEST_TIMEOUT(2002, "request timeout");

    private int code;
    private String msg;

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    private ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
