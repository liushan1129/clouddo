package com.bootdo.clouddocommon.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author : liushan
 * @Date : 2019/11/1 2:47 PM
 */
@Data
public class LoginRequest {
    @NotNull
    private String username;
    @NotNull
    private String pwd;
}
