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
 * 文章列表视图
 */
public class ArticleListVo {
    private Long id;
    private String title;
    private String summary;
    private String categoryName;
    private Long viewCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") // 格式化时间
    private LocalDateTime createTime;
    private String thumbnail;
    private Long categoryId;
}
