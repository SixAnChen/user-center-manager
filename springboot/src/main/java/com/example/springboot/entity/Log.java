package com.example.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Table(name = "log")
@Data
@AllArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "time")
    private String time;
    @Column(name = "username")
    private String username;
    @Column(name = "ip")
    private String ip;
}