package com.example.springboot.entity;

import lombok.Data;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-09-21 16:21
 */
@Data
public class Params {
    private String username;
    private String name;
    private String address;
    private Integer userId;

    private Integer pageNum;
    private Integer pageSize;
}
