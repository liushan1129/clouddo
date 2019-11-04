package com.bootdo.clouddocommon.utils;

import com.bootdo.clouddocommon.enums.ResponseCode;

/**
 * @Author : liushan
 * @Date : 2019/11/1 5:18 PM
 */
public abstract class Responses {
    public Responses() {
    }

    public static <T> Response<T> of(T data) {
        Response<T> response = new Response();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMsg(ResponseCode.SUCCESS.getMsg());
        response.setData(data);
        return response;
    }

    public static <T> Response<T> of(T data, String msg) {
        Response<T> response = of(data);
        response.setMsg(msg);
        return response;
    }

    public static <T> Response<T> fail(int code, String msg) {
        if (code == ResponseCode.SUCCESS.getCode()) {
            throw new IllegalArgumentException("Invalid code.");
        } else if (msg != null && ResponseCode.SUCCESS.getMsg().equals(msg)) {
            throw new IllegalArgumentException("Invalid msg.");
        } else {
            Response<T> response = new Response();
            response.setCode(code);
            response.setMsg(msg);
            return response;
        }
    }

    public static <T> Response<T> unknownInnerError() {
        return fail(ResponseCode.UNKNOWN_INNER_ERROR.getCode(), ResponseCode.UNKNOWN_INNER_ERROR.getMsg());
    }

    public static <T> Response<T> unsupportedRequest() {
        return fail(ResponseCode.UNSUPPORTED_REQUEST.getCode(), ResponseCode.UNSUPPORTED_REQUEST.getMsg());
    }

    public static <T> Response<T> requestTimeout() {
        return fail(ResponseCode.REQUEST_TIMEOUT.getCode(), ResponseCode.REQUEST_TIMEOUT.getMsg());
    }
}
