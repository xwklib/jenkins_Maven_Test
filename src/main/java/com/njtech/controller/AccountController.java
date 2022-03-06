package com.njtech.controller;

import com.njtech.common.JwtUtils;
import com.njtech.common.PassToken;
import com.njtech.common.Result;
import com.njtech.common.ResultCode;
import com.njtech.entity.UserInfo;
import com.njtech.exception.CustomException;
import com.njtech.service.AccountService;
import com.njtech.vo.Account;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/account-info")
@CrossOrigin
public class AccountController {

    @Resource
    AccountService accountService;

    @PassToken
    @GetMapping("/test")
    public UserInfo test() {
        UserInfo i = new UserInfo();
        i.setUserId("123");
        i.setPassword("123123");
        i.setName("xwk");
        i.setIdentity(1);
        return i;
    }

    /**
     * 用户登录：获取账号密码并登录，如果不对就报错，对了就返回用户的登录信息
     * 同时生成jwt返回给用户
     */
    @PassToken
    @PostMapping("/login")
    public Result<Account> login(@RequestBody UserInfo userInfo) throws CustomException {
        String userId = userInfo.getUserId();
        String password = userInfo.getPassword();
        ResultCode resultCode = accountService.login(userId, password);
        Result<Account> res = new Result<>();
        res.setCode(resultCode.code);
        res.setMsg(resultCode.msg);

        if ("200".equals(res.getCode())) {
            //如果成功了，聚合需要返回的信息
            Account user = accountService.getById(userId);
            //给分配一个token 然后返回
            String jwtToken = JwtUtils.createToken(user);
            user.setToken(jwtToken);
            res.setData(user);
        }
        return res;
    }

    @PassToken
    @PostMapping("/register")
    public Result<UserInfo> register(@RequestBody UserInfo userInfo) {
        if (userInfo.getIdentity() == null) {
            return Result.error("4001", "请指定注册用户类型identity（发包方或众包工人）");
        }
        if (accountService.addUser(userInfo)) {
            return Result.success(userInfo);
        }
        return Result.error("2001", "账号已经存在，请尝试登录");
    }

    @PostMapping("/update")
    public ResultCode update(@RequestBody UserInfo userInfo) {
        if (accountService.updateUser(userInfo)) {
            return ResultCode.SUCCESS;
        }
        return ResultCode.ERROR;
    }

}
