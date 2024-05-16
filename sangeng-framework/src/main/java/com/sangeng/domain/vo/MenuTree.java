package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true) //链式编程
public class MenuTree {
    private List<MenuTree> children;
    private Long id;
    private String label;
    private String parentId;

    public static List<MenuTree> buildTree(List<MenusAndChildrenVo> menusAndChildrenVos) {
        List<MenuTree> menuTree = new ArrayList<>();
        menusAndChildrenVos.forEach(menusAndChildrenVo -> {
            List<MenusAndChildrenVo> children1 = menusAndChildrenVo.getChildren();
            List<MenuTree> menuTrees = buildTree(children1);
            MenuTree menuTree1 = new MenuTree()
                        .setId(menusAndChildrenVo.getId())
                        .setLabel(menusAndChildrenVo.getMenuName())
                        .setParentId(menusAndChildrenVo.getParentId().toString())
                        .setChildren(menuTrees);
                menuTree.add(menuTree1);
    });
        return menuTree;
    }
}
