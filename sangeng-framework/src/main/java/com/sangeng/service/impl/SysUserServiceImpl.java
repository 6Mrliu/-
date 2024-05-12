package com.sangeng.service.impl;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SysUser;
import com.sangeng.domain.vo.UserInfoVo;
import com.sangeng.mapper.SysUserMapper;
import com.sangeng.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.SecurityUtils;
import org.springframework.stereotype.Service;

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
    public ResponseResult updateUserInfo() {
        return null;
    }
}
