package com.njtech.controller;


import com.njtech.service.UserInfoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userInfo")
@CrossOrigin
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

}

