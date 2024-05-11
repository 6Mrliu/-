package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 子评论视图
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListChildrenVo {
    private Long articleId;//文章id
    private String toCommentUserName;//评论者昵称
    private Long id;
    private String content; // 评论内容
    private String username;
    private LocalDateTime createTime;
    private Long toCommentUserId; // 被回复的用户ID
    private Long toCommentId; // 被回复的评论ID
    private Long createBy;  // 创建者
    private Long rootId; //根评论id
}
