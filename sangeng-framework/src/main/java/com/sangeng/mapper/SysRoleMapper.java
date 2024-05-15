package com.sangeng.mapper;

import com.sangeng.domain.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-05-14
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<String> selectRoleByUserId(Long userId);

}
