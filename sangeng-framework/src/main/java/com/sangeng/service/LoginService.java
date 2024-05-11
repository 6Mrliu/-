package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SysUser;

public interface LoginService {
    ResponseResult login(SysUser user);

    ResponseResult logout();
}
