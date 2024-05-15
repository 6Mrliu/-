package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SysUser;

public interface AdminLoginService {
    ResponseResult login(SysUser user);


    //ResponseResult getUserInfo();

    ResponseResult getRouters();

    ResponseResult logout();
}
