package com.sangeng.service;

import com.sangeng.domain.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.vo.MenusAndChildrenVo;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-05-15
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<String> getPermissionsByUserId(Long id);

    List<MenusAndChildrenVo> selectRouterMenuTreeByUserId(Long id);

}
