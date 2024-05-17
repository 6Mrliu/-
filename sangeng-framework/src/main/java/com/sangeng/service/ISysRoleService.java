package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddRoleDTO;
import com.sangeng.domain.dto.RoleChangeDTO;
import com.sangeng.domain.dto.RolePageQueryDTO;
import com.sangeng.domain.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-05-15
 */
public interface ISysRoleService extends IService<SysRole> {

    List<String> getRolesByUserId(Long id);

    ResponseResult listAll(RolePageQueryDTO rolePageQueryDTO);

    ResponseResult changeStatus(RoleChangeDTO roleChangeDTO);

    ResponseResult addRole(AddRoleDTO addRoleDTO);

    ResponseResult getRoleById(Long id);

    ResponseResult updeteRole(AddRoleDTO addRoleDTO);

    ResponseResult deleteById(Long id);

    ResponseResult listAllRole();
}
