package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关接口
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    ISysUserService userService;

    /**
     * 查询个人信息
     * @return
     */
    @GetMapping("userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(){
        return userService.updateUserInfo();
    }
}
