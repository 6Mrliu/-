package com.sangeng.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sg_comment")
public class SgComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论类型（0代表文章评论，1代表友链评论）
     */
    @TableField("type")
    private String type;

    /**
     * 文章id
     */
    @TableField("article_id")
    private Long articleId;

    /**
     * 根评论id
     */
    @TableField("root_id")
    private Long rootId;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

    /**
     * 所回复的目标评论的userid
     */
    @TableField("to_comment_user_id")
    private Long toCommentUserId;

    /**
     * 回复目标评论id
     */
    @TableField("to_comment_id")
    private Long toCommentId;

    @TableField("create_by")
    private Long createBy;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_by")
    private Long updateBy;

    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableField("del_flag")
    private Integer delFlag;


}
