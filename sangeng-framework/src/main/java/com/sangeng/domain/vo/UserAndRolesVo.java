package com.sangeng.domain.vo;

import com.sangeng.domain.entity.SysRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndRolesVo {
    private List<String> roleIds;
    private List<SysRole> roles;
    private UserVo user;
}
