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
 * 文章表
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)//忽略属性值相同
@Accessors(chain = true) //链式编程
@TableName("sg_article")//表名
public class SgArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 文章内容
     */
    @TableField("content")
    private String content;

    /**
     * 文章摘要
     */
    @TableField("summary")
    private String summary;

    /**
     * 所属分类id
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 缩略图
     */
    @TableField("thumbnail")
    private String thumbnail;

    /**
     * 是否置顶（0否，1是）
     */
    @TableField("is_top")
    private String isTop;

    /**
     * 状态（0已发布，1草稿）
     */
    @TableField("status")
    private String status;

    /**
     * 访问量
     */
    @TableField("view_count")
    private Long viewCount;

    /**
     * 是否允许评论 1是，0否
     */
    @TableField("is_comment")
    private String isComment;

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
