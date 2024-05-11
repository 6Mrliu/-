package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sangeng.domain.entity.LoginUser;
import com.sangeng.domain.entity.SysUser;
import com.sangeng.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    SysUserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //1.根据用户名查询用户信息
        LambdaQueryWrapper<SysUser> wrapper =
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, username);
        SysUser user = userMapper.selectOne(wrapper);

        //1.1无该用户抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }

        //TODO：获取权限信息

        //2.封装成UserDetails对象返回
        return new LoginUser(user);
    }
}
