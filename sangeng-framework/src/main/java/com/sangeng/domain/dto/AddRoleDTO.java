package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleDTO{
    // TODO
    private Long id;
    private String roleName; //角色名称
    private String roleKey; //角色权限字符串
    private Integer roleSort; //角色顺序
    private String status; //角色状态（0正常 1停用）
    private List<String> menuIds; //菜单权限
    private String remark; //备注
}
