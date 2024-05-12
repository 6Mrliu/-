package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SysUser;
import com.sangeng.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("userInfo")
    public ResponseResult updateUserInfo(@RequestBody SysUser user){
        return userService.updateUserInfo(user);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("register")
    public ResponseResult register(@RequestBody SysUser user){
        return userService.register(user);
    }
}
