package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.LoginUser;
import com.sangeng.domain.entity.SysMenu;
import com.sangeng.domain.entity.SysRole;
import com.sangeng.domain.entity.SysUser;
import com.sangeng.domain.vo.AdminUserInfoVo;
import com.sangeng.domain.vo.MenusAndChildrenVo;
import com.sangeng.domain.vo.UserInfoVo;
import com.sangeng.mapper.SysMenuMapper;
import com.sangeng.mapper.SysRoleMapper;
import com.sangeng.service.AdminLoginService;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.JwtUtil;
import com.sangeng.utils.RedisCache;
import com.sangeng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    SysMenuMapper menuMapper;
    @Autowired
    SysRoleMapper roleMapper;
    @Autowired
    RedisCache redisCache;

    /**
     * 后端登录
     * @param user
     * @return
     */
    public ResponseResult login(SysUser user) {
        // 调用AuthenticationManager的authenticate方法进行认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        Authentication authenticated = authenticationManager.authenticate(authenticationToken);

        if (Objects.isNull(authenticated)){
            return ResponseResult.errorResult(500,"用户名或密码错误");
        }

        // 获取登录用户信息
        LoginUser loginUser = (LoginUser) authenticated.getPrincipal();

        if (Objects.isNull(loginUser)){
            return ResponseResult.errorResult(500,"用户名或密码错误");
        }

        //生成jwt
        String jwt = JwtUtil.createJWT(loginUser.getUser().getId().toString());

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token",jwt);

        //将用户信息存入redis
        redisCache.setCacheObject("login:"+loginUser.getUser().getId(),loginUser);

        //返回结果
        return ResponseResult.okResult(hashMap);

    }

    /**
     * 用户权限查询
     * @return
     */
//    @Override
//    public ResponseResult getUserInfo() {
//        //获取用户id
//        Long userId = SecurityUtils.getUserId();
//        //获取用户信息
//        SysUser user = SecurityUtils.getLoginUser().getUser();
//        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
//        //获取用户权限信息
//        List<String> permsList = menuMapper.selectMenuPermsByUserId(userId);
//        //获取用户角色信息
//        SysRole role = roleMapper.selectRoleByUserId(userId);
//
//        if (role.getId() == 1L ){
//            role = new SysRole();
//            role.setRoleName("admin");
//        }
//
//
//        //数据封装
//        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(permsList, role, userInfoVo);
//
//        return ResponseResult.okResult(adminUserInfoVo);
//    }

    @Override
    public ResponseResult getRouters() {

        //查询所有父菜单
        LambdaQueryWrapper<SysMenu> wrapper =
                new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, SystemConstants.TYPE_MENU_PARENT);
        List<SysMenu> sysMenus = menuMapper.selectList(wrapper);
        List<MenusAndChildrenVo> menusAndChildrenVos = BeanCopyUtils.copyBeanList(sysMenus, MenusAndChildrenVo.class);
        for (MenusAndChildrenVo menusAndChildrenVo : menusAndChildrenVos) {
            //查询子菜单
            LambdaQueryWrapper<SysMenu> wrapper1 =
                    new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, menusAndChildrenVo.getId());
            List<SysMenu> sysMenus1 = menuMapper.selectList(wrapper1);
            List<MenusAndChildrenVo> menusAndChildrenVos1 = BeanCopyUtils.copyBeanList(sysMenus1, MenusAndChildrenVo.class);
            menusAndChildrenVo.setChildren(menusAndChildrenVos1);
        }

        return ResponseResult.okResult(menusAndChildrenVos);
    }

    @Override
    public ResponseResult logout() {
        Long userId = SecurityUtils.getLoginUser().getUser().getId();
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }


}
