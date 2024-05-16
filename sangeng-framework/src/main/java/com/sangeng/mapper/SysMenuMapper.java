package com.sangeng.mapper;

import com.sangeng.domain.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-05-14
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<String> selectMenuPermsByUserId(Long userId);

    List<SysMenu> getAllRouterMenu();

    List<SysMenu> selectRouterMenuTreeById(Long id);

    List<Long> selectMenuListByRoleId(Long id);
}
