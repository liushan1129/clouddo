package com.bootdo.clouddocommon.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author : liushan
 * @Date : 2019/11/1 2:55 PM
 */
@Data
public class UserRequest implements Serializable {

    private Long userId;
    // 用户名
    private String userName;
    // 用户真实姓名
    @NotNull(message = "真实姓名不能为空")
    private String name;
    // 密码
    @NotNull(message = "密码不能为空")
    private String password;
    // 部门
    private Long deptId;
    //部门名称
    private String deptName;
    //邮箱
    private String email;
    // 手机号
    @NotNull(message = "手机号不能为空")
    private String mobile;
    // 状态 0:禁用，1:正常
    private Integer status;
    // 创建用户id
    private Long userIdCreate;
    // 创建时间
    private Date gmtCreate;
    // 修改时间
    private Date gmtModified;
    //角色
    @NotNull(message = "角色不能为空")
    private List<Long> roleIds;
    //性别
    private Long sex;
    //出身日期
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    //图片ID
    private Long picId;
    //现居住地
    private String liveAddress;
    //爱好
    private String hobby;
    //省份
    private String province;
    //所在城市
    private String city;
    //所在地区
    private String district;
}
