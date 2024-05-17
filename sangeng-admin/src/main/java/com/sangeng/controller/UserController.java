package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddUserDTO;
import com.sangeng.domain.dto.UpdataUserDTO;
import com.sangeng.domain.dto.UserPageQueryDTO;
import com.sangeng.domain.entity.SysUser;
import com.sangeng.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关接口
 */
@RestController
@RequestMapping("system/user")
public class UserController {
    @Autowired
    ISysUserService userService;

    /**
     * 用户列表
     * @param userPageQueryDTO
     * @return
     */
    @GetMapping("/list")
    public ResponseResult userPageQuery(UserPageQueryDTO userPageQueryDTO){
        return userService.userpageQuery(userPageQueryDTO);
    }

    /**
     * 新增用户
     * @param addUserDTO
     * @return
     */
    @PostMapping
    public ResponseResult addUser(@RequestBody AddUserDTO addUserDTO) {
        return userService.addUser(addUserDTO);
    }

    /**
     * 根据id查询用户信息
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getUserAndRoleById(@PathVariable Long id) {
        return userService.getUserAndRoleById(id);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody UpdataUserDTO user) {
        return userService.updateUser(user);
    }
    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}
