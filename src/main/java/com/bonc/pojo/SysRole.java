package com.bonc.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class SysRole {
    private Long id;
    private String roleName;
    private Boolean enable;
    private Long createBy;
    private Date createTime;
    private SysUser user;
}
