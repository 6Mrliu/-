package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class articleListVo {
    private Long id;
    private String title;
    private String summary;
    private String categoryName;
    private Long viewCount;
    private LocalDate createTime;
    private String thumbnail;
    private Long categoryId;
}
