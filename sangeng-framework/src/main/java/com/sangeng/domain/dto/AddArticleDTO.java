package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleDTO {
    private String content;//文章内容
    private String summary;//文章摘要
    private String thumbnail;// 缩略图
    private String title;//标题
    private String isTop;//是否置顶
    private String status;//状态
    private Long categoryId;//分类id
    private List<Long> tags;//标签id
    private String isComment;//是否允许评论
}
