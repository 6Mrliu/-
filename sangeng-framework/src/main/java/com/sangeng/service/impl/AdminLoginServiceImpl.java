package com.sangeng.service.impl;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.LoginUser;
import com.sangeng.domain.entity.SysUser;
import com.sangeng.service.AdminLoginService;
import com.sangeng.utils.JwtUtil;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    RedisCache redisCache;

    /**
     * 后端登录
     * @param user
     * @return
     */
    public ResponseResult login(SysUser user) {
        // 调用AuthenticationManager的authenticate方法进行认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        Authentication authenticated = authenticationManager.authenticate(authenticationToken);

        if (Objects.isNull(authenticated)){
            return ResponseResult.errorResult(500,"用户名或密码错误");
        }

        // 获取登录用户信息
        LoginUser loginUser = (LoginUser) authenticated.getPrincipal();

        if (Objects.isNull(loginUser)){
            return ResponseResult.errorResult(500,"用户名或密码错误");
        }

        //生成jwt
        String jwt = JwtUtil.createJWT(loginUser.getUser().getId().toString());

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token",jwt);

        //将用户信息存入redis
        redisCache.setCacheObject("login:"+loginUser.getUser().getId(),loginUser);

        //返回结果
        return ResponseResult.okResult(hashMap);

    }


}
