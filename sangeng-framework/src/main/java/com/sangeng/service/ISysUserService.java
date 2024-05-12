package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-05-12
 */
public interface ISysUserService extends IService<SysUser> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo();
}
