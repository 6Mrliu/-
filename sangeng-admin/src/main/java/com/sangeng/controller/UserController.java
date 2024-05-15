package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.LoginUser;
import com.sangeng.domain.entity.SysRole;
import com.sangeng.domain.entity.SysUser;
import com.sangeng.domain.vo.AdminUserInfoVo;
import com.sangeng.domain.vo.MenusAndChildrenVo;
import com.sangeng.domain.vo.RoutersVo;
import com.sangeng.domain.vo.UserInfoVo;
import com.sangeng.service.AdminLoginService;
import com.sangeng.service.ISysMenuService;
import com.sangeng.service.ISysRoleService;
import com.sangeng.service.LoginService;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后端登录相关接口
 */
@RestController
public class UserController {
    @Autowired
    AdminLoginService loginService;
    @Autowired
    ISysMenuService menuService;
    @Autowired
    ISysRoleService roleService;


    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody SysUser user) {

        return loginService.login(user);
    }

    /**
     * 用户权限
     */
    @GetMapping("/getInfo")
    public ResponseResult getUserInfo() {

        //获取用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据id查询权限信息
        List<String> permissions = menuService.getPermissionsByUserId(loginUser.getUser().getId());
        //查询角色信息
        List<String> role = roleService.getRolesByUserId(loginUser.getUser().getId());
        //查询用户信息
        SysUser user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        //封装
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(permissions, role, userInfoVo);

        //返回
        return ResponseResult.okResult(adminUserInfoVo);

    }

    /**
     * 菜单信息
     *
     * @return
     */
    @GetMapping("/getRouters")
    public ResponseResult getRouters() {
        //获取用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据id查询菜单信息 封装成菜单树
        List<MenusAndChildrenVo> menusAndChildrenVo = menuService.selectRouterMenuTreeByUserId(loginUser.getUser().getId());
        //返回
        RoutersVo routersVo = new RoutersVo(menusAndChildrenVo);
        return ResponseResult.okResult(routersVo);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/user/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }

}
