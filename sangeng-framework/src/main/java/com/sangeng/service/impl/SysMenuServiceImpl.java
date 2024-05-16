package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SysMenu;
import com.sangeng.domain.vo.MenuTree;
import com.sangeng.domain.vo.MenusAndChildrenVo;
import com.sangeng.domain.vo.RoleMenuTreeVo;
import com.sangeng.mapper.SysMenuMapper;
import com.sangeng.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
        List<MenusAndChildrenVo> menusTree = buildMenuTree(sysMenus);

        return menusTree;
    }

    /**
     * 查询菜单列表。
     *
     * @param status 菜单的状态，用于过滤菜单。如果状态非空，则只查询对应状态的菜单。
     * @param menuName 菜单的名称，用于模糊查询。如果名称非空，则只查询名称包含该参数的菜单。
     * @return 返回按照订单号升序排列的菜单列表。
     */
    public List<SysMenu> menuList(String status,String menuName) {
        // 构建查询条件：根据菜单名称和状态进行查询，同时按照订单号升序排列
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
                .like(StringUtils.hasText(menuName), SysMenu::getMenuName, menuName)
                .eq(StringUtils.hasText(status), SysMenu::getStatus, status)
                .orderByAsc(SysMenu::getOrderNum);
        // 根据查询条件获取菜单列表
        List<SysMenu> list = list(wrapper);
        return list;
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    public ResponseResult deleteById(Long menuId) {
        //查询该菜单是否有子菜单
        LambdaQueryWrapper<SysMenu> wrapper =
                new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, menuId);
        long count = count(wrapper);
        if (count > 0){
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        removeById(menuId);
        return ResponseResult.okResult();
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    public ResponseResult updeteMenu(SysMenu menu) {
        //不能添加自己为父菜单
        if (Objects.equals(menu.getParentId(), menu.getId())){
            return ResponseResult.errorResult(500,"修改菜单'写博文'失败，上级菜单不能选择自己");
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    /**
     * 查询并构建菜单树结构
     *
     * 本方法不接受任何参数，主要执行以下操作：
     * 1. 从数据库中查询所有父路由（parent_id = 0）菜单信息。
     * 2. 将查询到的菜单信息转换为包含菜单及其子菜单的Vo对象列表。
     * 3. 使用这些Vo对象构建菜单树结构。
     * 4. 返回构建好的菜单树结构作为响应结果。
     *
     * @return ResponseResult 包含菜单树结构的响应结果对象
     */
    public ResponseResult selectMenuTree() {
        // 从数据库获取所有父路由菜单
        List<SysMenu> allRouterMenu = menuMapper.getAllRouterMenu();
        // 将菜单列表转换为包含子菜单的Vo对象列表
        List<MenusAndChildrenVo> menusAndChildrenVos = buildMenuTreeStructure(allRouterMenu);
        // 构建菜单树
        List<MenuTree> menuTrees = MenuTree.buildTree(menusAndChildrenVos);
        // 返回构建好的菜单树
        return ResponseResult.okResult(menuTrees);
    }

    /**
     * 根据角色id查询菜单树
     * 该方法通过给定的角色ID, 查询与该角色相关的菜单树结构。
     *
     * @param id 角色的唯一标识符
     * @return 返回一个包含菜单树结构的ResponseResult对象
     */
    @Override
    public ResponseResult selectRouterMenuTreeById(Long id) {
        // 根据角色id查询菜单列表
        List<SysMenu> sysMenus = menuMapper.selectRouterMenuTreeById(id);
        // 将查询到的菜单列表转换为树状结构的Vo对象列表
        List<MenusAndChildrenVo> menusAndChildrenVos = buildMenuTreeStructure(sysMenus);
        // 构建菜单树
        List<MenuTree> menuTrees = MenuTree.buildTree(menusAndChildrenVos);
        //查询该用户的所拥有的菜单id
        List<Long> checkedKeys = menuMapper.selectMenuListByRoleId(id);

        // 创建并设置角色菜单树Vo对象，准备返回
        RoleMenuTreeVo roleMenuTreeVo = new RoleMenuTreeVo();
        roleMenuTreeVo.setMenus(menuTrees);
        roleMenuTreeVo.setCheckedKeys(checkedKeys); // 这里可能根据实际需要设置已选择的菜单键值

        // 返回构建好的菜单树结构
        return ResponseResult.okResult(roleMenuTreeVo);
    }


    /**
     * 构建菜单树
     * 该方法通过递归的方式，将菜单列表转换成树状结构。首先，通过拷贝系统菜单列表到目标Vo列表完成初步转换。然后，对每个Vo实例，
     * 查询其子菜单，并将子菜单也转换成树状结构。最终形成一个完整的菜单树。
     *
     * @param sysMenus 父菜单列表，即顶级菜单的集合。
     * @return 返回转换后的菜单树结构，每个节点包含自身的菜单信息及子菜单的集合。
     */
    @NotNull
    private List<MenusAndChildrenVo> buildMenuTree(List<SysMenu> sysMenus) {
        // 初始拷贝系统菜单到目标Vo列表
        List<MenusAndChildrenVo> menusAndChildrenVos = BeanCopyUtils.copyBeanList(sysMenus, MenusAndChildrenVo.class);
        for (MenusAndChildrenVo menusAndChildrenVo : menusAndChildrenVos) {
            // 查询当前菜单下的子菜单
            LambdaQueryWrapper<SysMenu> wrapper1 =
                    new LambdaQueryWrapper<SysMenu>()
                            .eq(SysMenu::getParentId, menusAndChildrenVo.getId())
                            .in(SysMenu::getMenuType, SystemConstants.TYPE_MENU_MENU);
            List<SysMenu> sysMenus1 = menuMapper.selectList(wrapper1);
            // 拷贝子菜单到Vo列表，并通过递归转换成树状结构
            List<MenusAndChildrenVo> menusAndChildrenVos1 = BeanCopyUtils.copyBeanList(sysMenus1, MenusAndChildrenVo.class);
            menusAndChildrenVos1 = buildMenuTree(sysMenus1);
            // 设置子菜单到当前菜单节点
            menusAndChildrenVo.setChildren(menusAndChildrenVos1);
        }
        return menusAndChildrenVos;
    }


    /**
     * 获取菜单及其子菜单的Vo列表。
     * 该方法通过递归调用，将所有菜单及其子菜单转换成MenusAndChildrenVo对象的列表。
     *
     * @param sysMenus 系统菜单列表，来源为SysMenu实体类的列表。
     * @return 返回一个包含菜单及子菜单信息的MenusAndChildrenVo对象的列表，确保不为空。
     */
    @NotNull
    private List<MenusAndChildrenVo> buildMenuTreeStructure(List<SysMenu> sysMenus) {
        // 将SysMenu列表复制到MenusAndChildrenVo列表
        List<MenusAndChildrenVo> menusAndChildrenVos = BeanCopyUtils.copyBeanList(sysMenus, MenusAndChildrenVo.class);
        for (MenusAndChildrenVo menusAndChildrenVo : menusAndChildrenVos) {
            // 查询当前菜单的所有子菜单
            LambdaQueryWrapper<SysMenu> wrapper1 =
                    new LambdaQueryWrapper<SysMenu>()
                            .eq(SysMenu::getParentId, menusAndChildrenVo.getId())
                            //主要是按钮和菜单类型
                            .in(SysMenu::getMenuType, SystemConstants.TYPE_MENU_MENU, SystemConstants.TYPE_MENU_BUTTON);
            List<SysMenu> sysMenus1 = menuMapper.selectList(wrapper1);
            // 将子菜单复制到MenusAndChildrenVo列表，并通过递归调用，获取子菜单的子菜单
            List<MenusAndChildrenVo> menusAndChildrenVos1 = BeanCopyUtils.copyBeanList(sysMenus1, MenusAndChildrenVo.class);
            menusAndChildrenVos1 = buildMenuTreeStructure(sysMenus1);
            // 设置子菜单到当前菜单Vo
            menusAndChildrenVo.setChildren(menusAndChildrenVos1);
        }
        return menusAndChildrenVos;
    }
}
