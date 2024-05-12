package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SysUser;
import com.sangeng.domain.vo.UserInfoVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.mapper.SysUserMapper;
import com.sangeng.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-05-12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    PasswordEncoder passwordEncoder;
    /**
     * 获取用户信息
     * @return
     */
    public ResponseResult userInfo() {
        // 获取当前用户id
        Long userId = SecurityUtils.getUserId();
        // 根据id查询用户信息
        SysUser sysUser = getById(userId);
        // 数据拷贝
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(sysUser, UserInfoVo.class);
        // 返回
        return ResponseResult.okResult(userInfoVo);
    }

    /**
     * 更新用户信息
     * @return
     */
    public ResponseResult updateUserInfo(SysUser user) {
        updateById(user);
        return ResponseResult.okResult(200,"操作成功");
    }

    /**
     * 注册
     * @param user
     * @return
     */
    public ResponseResult register(SysUser user) {
        //一系列的隔断操作
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        if (!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
        }
        if (!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_NICKNAME);
        }
        if (checkUserNameUnique(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (checkEmailUnique(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //用户加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        save(user);
        return ResponseResult.okResult(200,"操作成功");
    }

    private boolean checkUserNameUnique(String userName) {
        return count(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName,userName))>0;
    }
    private boolean checkEmailUnique(String email) {
        return count(new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail,email))>0;
    }
}
