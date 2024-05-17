package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdataUserDTO {
    private String userName;

    private String nickName;

    private String email;

    private Long id;

    private String status;

    private String phonenumber;

    private List<Long> roleIds;

    private String sex;
}
