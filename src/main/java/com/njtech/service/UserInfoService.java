package com.njtech.service;

import com.njtech.dao.UserInfoDao;
import com.njtech.entity.UserInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    public UserInfo getById(String userId) {
        return userInfoDao.selectByPrimaryKey(userId);
    }

    public boolean addUser(UserInfo userInfo) {
        return userInfoDao.insert(userInfo) == 1;
    }
}

