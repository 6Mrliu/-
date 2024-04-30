package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 子评论视图
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListChildrenVo {
    private Long id;
    private String content; // 评论内容
    private String username;
    private String createTime;
    private Long toCommentUserId; // 被回复的用户ID
    private String toCommentId; // 被回复的评论ID
    private String createBy;  // 创建者
    private String rootid; //根评论id
}
