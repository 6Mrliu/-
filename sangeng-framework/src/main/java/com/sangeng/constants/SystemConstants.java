package com.sangeng.constants;

/**
 * 上下文信息
 */
public class SystemConstants {
    /**
     * 文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     * 文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 友链状态为审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * 评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为：友联评论
     */
    public static final String LINK_COMMENT = "1";
    /**
     * 菜单类型：父目录
     */
    public static final int TYPE_MENU_PARENT = 0;
    /**
     * 菜单类型：目录
     */
    public static final String TYPE_MENU_CATEGORY = "M";
    /**
     * 菜单类型：按钮
     */
    public static final String TYPE_MENU_BUTTON = "F";
    /**
     * 菜单类型：菜单
     */
    public static final String TYPE_MENU_MENU = "C";

    /**
     * 菜单状态：正常
     */
    public static final Object STATUS_NORMAL = 0;
}