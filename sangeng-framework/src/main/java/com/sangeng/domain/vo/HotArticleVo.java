package com.sangeng.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 热门文章视图
 */
public class HotArticleVo {
    private Long id;
    private String title;
    private Long viewCount;
}
