package com.sangeng.domain.vo;

import com.sangeng.domain.entity.SgComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 评论列表视图
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListVo extends SgComment {

 /*   private String articleId;//文章id
    private String toCommentUserName;//评论者昵称
    private Long id;
    private String content; // 评论内容
    private String username;
    private String createTime;
    private Long toCommentUserId; // 被回复的用户ID
    private String toCommentId; // 被回复的评论ID
    private String createBy;  // 创建者
    private String rootid; //根评论id*/
    private List<SgComment> children;
}
