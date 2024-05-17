package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddUserDTO {

    private String userName;

    private String nickName;

    private String password;

    private String email;

    private String phonenumber;

    private String status;

    private List<Long> roleIds;

    private String sex;
}
