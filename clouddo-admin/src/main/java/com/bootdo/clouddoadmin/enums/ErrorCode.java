package com.bootdo.clouddoadmin.enums;

/**
 * @Author : liushan
 * @Date : 2019/11/1 5:27 PM
 */
public enum ErrorCode {

    USER_SAVE_ERROR(10001, "保存用户失败"),
    USER_QUERY_ERROR(10002, "查询用户信息失败"),
    USER_EXIST_ERROR(10003, "用户手机号冲突"),
    PARAMS_ERROR(11111, "参数错误");


    private int code;
    private String msg;

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    private ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
