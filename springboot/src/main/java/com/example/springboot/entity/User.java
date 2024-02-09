package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import javax.persistence.*;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-09-20 22:50
 */
@Data
public class User {
    private Integer id;
    @Alias("姓名")
    private String username;
    private String password;
    private String name;
    @Alias("电话")
    private String phone;
    @Alias("邮箱")
    private String email;
    @Alias("地址")
    private String address;
    private String avatar;
    private String introduce;

    private Integer typeId;

    @Transient
    private String typeName;
}
