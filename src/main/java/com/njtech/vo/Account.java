package com.njtech.vo;

import com.njtech.entity.UserInfo;
import lombok.Data;

@Data
public class Account extends UserInfo {
    String token;
    public Account(UserInfo userInfo){
        this.setUserId(userInfo.getUserId());
//        this.setPassword(userInfo.getPassword());
        this.setName(userInfo.getName());
        this.setIdentity(userInfo.getIdentity());
        this.token = null;
    }
}
