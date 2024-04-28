package com.sangeng.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 文章详情视图对象
 */
public class ArticleDetailVo {
    private Long id;
    private String title;
    private Long categoryId;
    private String categoryName;
    private String content;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;
    private String isComment;
    private Long viewCount;

}
