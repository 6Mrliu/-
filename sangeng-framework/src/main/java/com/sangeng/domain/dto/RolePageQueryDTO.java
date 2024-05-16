package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RolePageQueryDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String roleName;
    private String status;
}
