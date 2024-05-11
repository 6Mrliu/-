package com.sangeng.handler.security;

import com.alibaba.fastjson.JSON;
import com.sangeng.domain.ResponseResult;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse
            response, AuthenticationException authException) throws IOException,ServletException {

        //BadCredentialsException 密码错误
        if (authException.getClass().isAssignableFrom(BadCredentialsException.class)){
            ResponseResult result =
                    ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), "用户名或密码错误");
            String json = JSON.toJSONString(result);
            WebUtils.renderString(response, json);
        }
        //InsufficientAuthenticationException 认证失败
        else if (authException.getClass().isAssignableFrom(InsufficientAuthenticationException.class)){
            ResponseResult result =
                    ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            String json = JSON.toJSONString(result);
            WebUtils.renderString(response, json);
        }
        else {
            ResponseResult result =
                    ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, authException.getMessage());
            String json = JSON.toJSONString(result);
            WebUtils.renderString(response, json);
        }

    }
}