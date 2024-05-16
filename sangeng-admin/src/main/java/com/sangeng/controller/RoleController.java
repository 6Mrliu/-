package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddRoleDTO;
import com.sangeng.domain.dto.RoleChangeDTO;
import com.sangeng.domain.dto.RolePageQueryDTO;
import com.sangeng.domain.entity.SysRole;
import com.sangeng.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色相关接口
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    ISysRoleService roleService;
    /**
     * 获取角色列表
     * @param rolePageQueryDTO 请求参数
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listAllRole(RolePageQueryDTO rolePageQueryDTO) {
        return roleService.listAllRole(rolePageQueryDTO);
    }

    /**
     * 角色状态修改
     * @param roleChangeDTO
     * @return
     */
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleChangeDTO roleChangeDTO) {
        return roleService.changeStatus(roleChangeDTO);
    }

    /**
     * 新增角色接口
     */
    @PostMapping
    public ResponseResult addRole(@RequestBody AddRoleDTO addRoleDTO) {
        return roleService.addRole(addRoleDTO);
    }

    /**
     * 角色信息回显
     */
    @GetMapping("/{id}")
    public ResponseResult getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    /**
     * 更新角色信息
     */
    @PutMapping
    public ResponseResult updateRole(@RequestBody AddRoleDTO addRoleDTO) {
        return roleService.updeteRole(addRoleDTO);
    }
    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable Long id) {
        return roleService.deleteById(id);
    }



}
