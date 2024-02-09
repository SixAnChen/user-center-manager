package com.example.springboot.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-10-02 08:59
 */
@Data
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
}
