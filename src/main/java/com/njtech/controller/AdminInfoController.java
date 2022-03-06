package com.njtech.controller;

import com.njtech.service.AdminInfoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin-info")
public class AdminInfoController {
    @Resource
    private AdminInfoService adminInfoService;



}

