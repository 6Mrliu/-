package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageQueryDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String userName;
    private String phonenumber;
    private String status;
}
