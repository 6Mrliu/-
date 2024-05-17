package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddRoleDTO;
import com.sangeng.domain.dto.RoleChangeDTO;
import com.sangeng.domain.dto.RolePageQueryDTO;
import com.sangeng.domain.entity.SysRole;
import com.sangeng.domain.entity.SysRoleMenu;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.RoleById;
import com.sangeng.mapper.SysMenuMapper;
import com.sangeng.mapper.SysRoleMapper;
import com.sangeng.mapper.SysRoleMenuMapper;
import com.sangeng.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    SysRoleMenuServiceImpl roleMenuService;


    /**
     * 根据用户id查询角色
     * @param id
     * @return
     */
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

    /**
     * 查询所有角色信息。
     *
     * @param rolePageQueryDTO 分页查询参数，包含角色名和状态等条件。
     * @return 返回角色信息的分页响应结果。
     */
    public ResponseResult listAll(RolePageQueryDTO rolePageQueryDTO) {
        // 构造查询条件
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<SysRole>()
                .like(StringUtils.hasText(rolePageQueryDTO.getRoleName()), SysRole::getRoleName, rolePageQueryDTO.getRoleName())
                .eq(StringUtils.hasText(rolePageQueryDTO.getStatus()), SysRole::getStatus, rolePageQueryDTO.getStatus());
        // 执行分页查询
        Page<SysRole> page = page(new Page<>(rolePageQueryDTO.getPageNum(), rolePageQueryDTO.getPageSize()), wrapper);
        // 封装查询结果到PageVo中
        PageVo pageVo = new PageVo(page.getTotal(), page.getRecords());
        // 返回查询结果
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 修改角色状态
     * @param roleChangeDTO
     * @return
     */
    public ResponseResult changeStatus(RoleChangeDTO roleChangeDTO) {
        SysRole role = new SysRole().setId(Long.valueOf(roleChangeDTO.getRoleId())).setStatus(roleChangeDTO.getStatus());
        updateById(role);
        return ResponseResult.okResult();
    }

    /**
     * 添加角色及其菜单权限。
     * 该方法首先会创建一个角色对象并存储到数据库中，然后将该角色与指定的菜单权限进行关联。
     *
     * @param addRoleDTO 添加角色的数据传输对象，包含角色信息和角色对应的菜单ID集合。
     * @return 返回操作结果，如果操作成功，则返回成功的响应结果。
     */
    @Transactional
    public ResponseResult addRole(AddRoleDTO addRoleDTO) {
        // 将添加角色DTO对象拷贝到角色实体对象
        SysRole role = BeanCopyUtils.copyBean(addRoleDTO, SysRole.class);
        // 保存角色信息到数据库
        save(role);

        // 将菜单ID集合转换为角色菜单关联对象集合，并存储到数据库中
        List<String> menuIds = addRoleDTO.getMenuIds();
        List<SysRoleMenu> roleMenus = menuIds.stream().map(menuId -> new SysRoleMenu(role.getId(), Long.valueOf(menuId))).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);

        // 返回操作成功的响应结果
        return ResponseResult.okResult();
    }


    /**
     * 角色信息回显
     * @param id
     * @return
     */
    public ResponseResult getRoleById(Long id) {
        SysRole role = getById(id);
        RoleById roleById = BeanCopyUtils.copyBean(role, RoleById.class);
        return ResponseResult.okResult(roleById);
    }

    /**
     * 更新角色信息
     * @param addRoleDTO
     * @return
     */
    @Transactional
    public ResponseResult updeteRole(AddRoleDTO addRoleDTO) {
        //更新角色菜单关联表
        //删除该角色的菜单权限
        LambdaQueryWrapper<SysRoleMenu> wrapper =
                new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, addRoleDTO.getId());
        roleMenuService.remove(wrapper);
        List<String> menuIds = addRoleDTO.getMenuIds();
        List<SysRoleMenu> rleMenu = menuIds.stream().map(menuId -> {
            return new SysRoleMenu(addRoleDTO.getId(), Long.valueOf(menuId));
        }).collect(Collectors.toList());
        roleMenuService.saveBatch(rleMenu);
        //更新角色信息
        SysRole role = BeanCopyUtils.copyBean(addRoleDTO, SysRole.class);
        updateById(role);
        return ResponseResult.okResult();
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @Transactional
    public ResponseResult deleteById(Long id) {
        //删除关联表中的信息
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id);
        roleMenuService.remove(wrapper);
        //删除角色信息
        removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public ResponseResult listAllRole() {
        // 查询所有角色
        LambdaQueryWrapper<SysRole> wrapper =
                new LambdaQueryWrapper<SysRole>().eq(SysRole::getStatus, SystemConstants.ROLE_STATUS_NORMAL);
        List<SysRole> roleList = list(wrapper);
        return ResponseResult.okResult(roleList);
    }
}
