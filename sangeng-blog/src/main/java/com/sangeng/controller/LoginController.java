package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SysUser;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录相关接口
 */
@RestController

public class LoginController {
    @Autowired
    LoginService loginService;

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("login")
    public ResponseResult login(@RequestBody SysUser user){
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

}
