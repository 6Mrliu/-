package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddUserDTO;
import com.sangeng.domain.dto.UpdataUserDTO;
import com.sangeng.domain.dto.UserPageQueryDTO;
import com.sangeng.domain.entity.SysRole;
import com.sangeng.domain.entity.SysUser;
import com.sangeng.domain.entity.SysUserRole;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.UserAndRolesVo;
import com.sangeng.domain.vo.UserInfoVo;
import com.sangeng.domain.vo.UserVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.mapper.SysRoleMapper;
import com.sangeng.mapper.SysUserMapper;
import com.sangeng.service.ISysRoleService;
import com.sangeng.service.ISysUserRoleService;
import com.sangeng.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private ISysUserRoleService userRoleService;
    @Autowired
    private SysRoleMapper roleMapper;
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

    @Override
    public ResponseResult userpageQuery(UserPageQueryDTO userPageQueryDTO) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .eq(StringUtils.hasText(userPageQueryDTO.getStatus()), SysUser::getStatus, userPageQueryDTO.getStatus())
                .like(StringUtils.hasText(userPageQueryDTO.getUserName()), SysUser::getUserName, userPageQueryDTO.getUserName())
                .like(StringUtils.hasText(userPageQueryDTO.getPhonenumber()), SysUser::getPhonenumber, userPageQueryDTO.getPhonenumber());

        //分页查询
        Page<SysUser> page = page(new Page<>(userPageQueryDTO.getPageNum(), userPageQueryDTO.getPageSize()), wrapper);
        PageVo pageVo = new PageVo(page.getTotal(), page.getRecords());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 添加用户
     * @param addUserDTO
     * @return
     */
    @Transactional
    public ResponseResult addUser(AddUserDTO addUserDTO) {
        if (!StringUtils.hasText(addUserDTO.getPassword())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        if (!StringUtils.hasText(addUserDTO.getNickName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_NICKNAME);
        }
        if (!StringUtils.hasText(addUserDTO.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (checkUserNameUnique(addUserDTO.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (checkEmailUnique(addUserDTO.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (checkPhoneUnique(addUserDTO.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        SysUser user = BeanCopyUtils.copyBean(addUserDTO, SysUser.class);
        save(user);
        List<Long> roleIds = addUserDTO.getRoleIds();
        List<SysUserRole> userRoles = roleIds.stream().map(roleId -> new SysUserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Transactional
    public ResponseResult deleteUser(Long id) {
        removeById(id);
        userRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId,id));
        return null;
    }

    /**
     * 根据id查询用户和角色信息
     * @return
     */
    @Transactional
    public ResponseResult getUserAndRoleById(Long id) {
        List<String> roleIds = roleMapper.selectRoleIdsByUserId(id);
        List<SysRole> roles = roleMapper.selectRolesByUserId(id);
        SysUser user = getById(id);
        UserVo userVo = BeanCopyUtils.copyBean(user, UserVo.class);
        return ResponseResult.okResult(new UserAndRolesVo(roleIds,roles,userVo));
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @Transactional
    public ResponseResult updateUser(UpdataUserDTO user) {
        //删除角色用户关联信息
        userRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId,user.getId()));
        //新增角色用户关联信息
        List<Long> roleIds = user.getRoleIds();
        List<SysUserRole> userRoles = roleIds.stream().map(roleId -> new SysUserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        SysUser sysUser = BeanCopyUtils.copyBean(user, SysUser.class);
        updateById(sysUser);
        return ResponseResult.okResult();
    }

    private boolean checkPhoneUnique(String phonenumber) {
        return count(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhonenumber,phonenumber))>0;
    }

    private boolean checkUserNameUnique(String userName) {
        return count(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName,userName))>0;
    }
    private boolean checkEmailUnique(String email) {
        return count(new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail,email))>0;
    }
}
