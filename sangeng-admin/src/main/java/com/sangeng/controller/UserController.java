package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SysUser;
import com.sangeng.service.AdminLoginService;
import com.sangeng.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 后端登录相关接口
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    AdminLoginService loginService;

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody SysUser user){

        return loginService.login(user);
    }


}
