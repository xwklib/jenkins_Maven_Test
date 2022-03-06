package com.njtech.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "identity")
    private Integer identity;
}
