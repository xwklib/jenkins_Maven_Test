package com.njtech.dao;

import com.njtech.entity.UserInfo;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface UserInfoDao extends Mapper<UserInfo> {

}
