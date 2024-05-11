package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SgComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-04-29
 */
public interface ISgCommentService extends IService<SgComment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(SgComment comment);

}
