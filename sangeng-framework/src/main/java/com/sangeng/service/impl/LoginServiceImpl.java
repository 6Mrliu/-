package com.sangeng.service.impl;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.LoginUser;
import com.sangeng.domain.entity.SysUser;
import com.sangeng.domain.vo.BlogUserLoginVo;
import com.sangeng.domain.vo.UserInfoVo;
import com.sangeng.service.LoginService;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.JwtUtil;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisCache redisCache;
    /**
     * 登录
     * @param user
     * @return
     */
    public ResponseResult login(SysUser user) {

        //1.封装Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //2.调用authenticationManager.authenticate()进行认证
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticated)) {
            throw new RuntimeException("用户名或密码错误");
        }

        //3.获取用户id
        LoginUser loginUser = (LoginUser) authenticated.getPrincipal();
        SysUser user1 = loginUser.getUser();
        String userId = user1.getId().toString();

        //4.根据id生成token
        String jwt = JwtUtil.createJWT(userId);

        //5.将用户信息存入redis
        redisCache.setCacheObject("login:" + userId, loginUser);

        //6.封装数据token + user
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user1, UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);

        //7.返回
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout() {
        //获取用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //在redis中删除用户信息
        redisCache.deleteObject("login:" + userId);
        return ResponseResult.okResult();
    }
}
