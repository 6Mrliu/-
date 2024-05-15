package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageQueryDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String title;
    private String summary;// 文章摘要

}
