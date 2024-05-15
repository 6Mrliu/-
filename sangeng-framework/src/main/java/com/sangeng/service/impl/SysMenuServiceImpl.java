package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.entity.SysMenu;
import com.sangeng.domain.vo.MenusAndChildrenVo;
import com.sangeng.mapper.SysMenuMapper;
import com.sangeng.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.SecurityUtils;
import org.apache.catalina.security.SecurityUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-05-15
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;
    @Override
    public List<String> getPermissionsByUserId(Long id) {
        if (SecurityUtils.isAdmin()){
            //如果是管理员
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
                    .in(SysMenu::getMenuType, SystemConstants.TYPE_MENU_MENU, SystemConstants.TYPE_MENU_BUTTON)
                    .eq(SysMenu::getStatus, SystemConstants.STATUS_NORMAL);
            List<SysMenu> menuList = list(wrapper);
            List<String> permsList = menuList.stream().map(SysMenu::getPerms).collect(Collectors.toList());

            return permsList;

        }else {
            List<String> permsList = menuMapper.selectMenuPermsByUserId(id);
            return permsList;
        }
    }

    @Override
    public List<MenusAndChildrenVo> selectRouterMenuTreeByUserId(Long id) {
        List<SysMenu> sysMenus = null;
        if (SecurityUtils.isAdmin()){
            //判断是否为管理员
            sysMenus = menuMapper.getAllRouterMenu();
        }else {
            //否则查询该用户的所拥有菜单
            sysMenus = menuMapper.selectRouterMenuTreeById(id);
        }
        //构建树
        List<MenusAndChildrenVo> menusTree = getMenusAndChildrenVos(sysMenus);

        return menusTree;
    }

    /**
     * 构建菜单树
     * @param sysMenus 父菜单
     * @return
     */
    @NotNull
    private List<MenusAndChildrenVo> getMenusAndChildrenVos(List<SysMenu> sysMenus) {
        List<MenusAndChildrenVo> menusAndChildrenVos = BeanCopyUtils.copyBeanList(sysMenus, MenusAndChildrenVo.class);
        for (MenusAndChildrenVo menusAndChildrenVo : menusAndChildrenVos) {
            //查询子菜单
            LambdaQueryWrapper<SysMenu> wrapper1 =
                    new LambdaQueryWrapper<SysMenu>()
                            .eq(SysMenu::getParentId, menusAndChildrenVo.getId())
                            .in(SysMenu::getMenuType, SystemConstants.TYPE_MENU_MENU);
            List<SysMenu> sysMenus1 = menuMapper.selectList(wrapper1);
            List<MenusAndChildrenVo> menusAndChildrenVos1 = BeanCopyUtils.copyBeanList(sysMenus1, MenusAndChildrenVo.class);
            menusAndChildrenVos1 = getMenusAndChildrenVos(sysMenus1);
            menusAndChildrenVo.setChildren(menusAndChildrenVos1);
        }
        return menusAndChildrenVos;
    }
}
