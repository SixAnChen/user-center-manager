package com.example.springboot.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-09-23 14:36
 */
@Data
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;
    private String password;
    private Integer age;
    private String sex;
    private String address;
    private String email;
    private String phone;
    private String avatar;

    @Transient
    private String token;

    /**
     * @Transient：代表该属性在数据库中不存在
     */
    @Transient
    private String oldPassword;
    @Transient
    private String newPassword;
}
