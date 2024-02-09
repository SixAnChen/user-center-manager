package com.example.springboot.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-10-03 01:03
 */
@Table(name = "notice")
@Data
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "content")
    private String content;
    @Column(name = "time")
//    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private String time;

    private Boolean status;

}
