package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SysMenu;
import com.sangeng.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单相关接口
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private ISysMenuService menuService;

    /**
     * 菜单列表
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list(String status,String menuName) {
        List<SysMenu> list = menuService.menuList(status, menuName);
        return ResponseResult.okResult(list);
    }

    /**
     * 新增菜单
     */
    @PostMapping
    public ResponseResult addMenu(@RequestBody SysMenu menu) {
        return ResponseResult.okResult(menuService.save(menu));
    }

    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult byIdMenu(@PathVariable Long id) {
        return ResponseResult.okResult(menuService.getById(id));
    }

    /**
     * 修改菜单
     */
    @PutMapping
    public ResponseResult updateMenu(@RequestBody SysMenu menu) {
        return menuService.updeteMenu(menu);
    }


    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable Long menuId) {
        return menuService.deleteById(menuId);
    }

    /**
     * 获取菜单树列表
     */
    @GetMapping("/treeselect")
    public ResponseResult treeselect() {
        return menuService.selectMenuTree();
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping("/roleMenuTreeselect/{roleId}")
    public ResponseResult roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        return menuService.selectRouterMenuTreeById(roleId);
    }
}
