package com.njtech.service;

import cn.hutool.crypto.SecureUtil;
import com.njtech.common.ResultCode;
import com.njtech.dao.UserInfoDao;
import com.njtech.entity.UserInfo;
import com.njtech.exception.CustomException;
import com.njtech.vo.Account;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class AccountService {

    @Resource
    private UserInfoDao userInfoDao;

    public ResultCode login(String userId, String password) {
        UserInfo userInfo = userInfoDao.selectByPrimaryKey(userId);
        if (userInfo == null) {
            return ResultCode.USER_NOT_EXIST_ERROR;
        }
        String hashpasswd = userInfo.getPassword();
        if (!SecureUtil.md5(password).equalsIgnoreCase(hashpasswd)) {
            System.out.println(userInfo.getUserId() + "登录失败" + new Date().toString());
            return ResultCode.USER_ACCOUNT_ERROR;
        }
        System.out.println(userInfo.getUserId() + "登录成功" + new Date().toString());
        return ResultCode.SUCCESS;
    }

    public boolean addUser(UserInfo userInfo) {
        String hashedPassword = SecureUtil.md5(userInfo.getPassword());
        userInfo.setPassword(hashedPassword);
        return userInfoDao.insert(userInfo) == 1;
    }

    public Account getById(String userId) {
        UserInfo tmp = userInfoDao.selectByPrimaryKey(userId);
        return new Account(tmp);
    }

    public boolean updateUser(UserInfo userInfo) {
        String hashedPassword = SecureUtil.md5(userInfo.getPassword());
        userInfo.setPassword(hashedPassword);
        return userInfoDao.updateByPrimaryKey(userInfo) == 1;
    }
}
