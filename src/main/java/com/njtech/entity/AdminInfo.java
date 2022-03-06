package com.njtech.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "admin_info")
public class AdminInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "priority")
    private Integer priority;
}
