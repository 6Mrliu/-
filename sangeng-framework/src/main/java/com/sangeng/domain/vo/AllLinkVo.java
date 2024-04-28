package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllLinkVo {
    private Long id;
    private String name;
    private String logo;
    private String description;
    private String address;
}
