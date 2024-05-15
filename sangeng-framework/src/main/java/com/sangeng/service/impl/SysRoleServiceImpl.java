package com.sangeng.service.impl;

import com.sangeng.domain.entity.SysRole;
import com.sangeng.mapper.SysMenuMapper;
import com.sangeng.mapper.SysRoleMapper;
import com.sangeng.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-05-15
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    SysRoleMapper roleMapper;
    @Override
    public List<String> getRolesByUserId(Long id) {

        //超级管理员
        if (id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }

        // 查询角色信息
        return roleMapper.selectRoleByUserId(id);
    }
}
