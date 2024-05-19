package com.sangeng.service.impl;

import com.alibaba.fastjson.JSON;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.LoginUser;
import com.sangeng.utils.SecurityUtils;
import com.sangeng.utils.WebUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {
    /**
     * 判断当前用户是否有操作权限
     */
    public boolean hasPerm(String permission){
        //超级管理员
        if (SecurityUtils.isAdmin()){
            return true;
        }
        //否则判断该用户是否有权限
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<String> permission1 = loginUser.getPermission();
        if (!permission1.contains(permission)){
            System.out.println("没有权限");
            return false;
        }
        return permission1.contains("permission");
    }
}
