package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SgComment;
import com.sangeng.domain.vo.CommentListVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.mapper.SgCommentMapper;
import com.sangeng.service.ISgCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-04-29
 */
@Service
public class SgCommentServiceImpl extends ServiceImpl<SgCommentMapper, SgComment> implements ISgCommentService {

    /**
     * 查询评论列表
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        // 查询条件
        LambdaQueryWrapper<SgComment> wrapper = new LambdaQueryWrapper<SgComment>()
                .eq(SgComment::getArticleId, articleId)
                .orderByDesc(SgComment::getCreateTime);

        Page<SgComment> page = page(new Page<>(pageNum, pageSize), wrapper);
        List<SgComment> records = page.getRecords();
        List<CommentListVo> commentListVos1 = BeanCopyUtils.copyBeanList(records, CommentListVo.class);


        // 获取子评论
        List<CommentListVo> commentListVos = commentListVos1.stream().map(sgComment -> {
           sgComment.setChildren(lambdaQuery().eq(SgComment::getToCommentId, sgComment.getId()).orderByDesc(SgComment::getCreateTime).list());
           return sgComment;
        }).collect(Collectors.toList());

        // 封装
        PageVo pageVo = new PageVo();
        pageVo.setRows(commentListVos);
        pageVo.setTotal(page.getTotal());

        // 返回
        return ResponseResult.okResult(pageVo);
    }
}
