package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SgComment;
import com.sangeng.domain.entity.SysUser;
import com.sangeng.domain.vo.CommentListChildrenVo;
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
        // 查询所有父评论
        LambdaQueryWrapper<SgComment> wrapper = new LambdaQueryWrapper<SgComment>()
                .eq(SgComment::getArticleId, articleId)
                .orderByDesc(SgComment::getCreateTime)
                .eq(SgComment::getRootId, -1);

        //封装所有父评论
        Page<SgComment> page = page(new Page<>(pageNum, pageSize), wrapper);
        List<SgComment> records = page.getRecords();
        List<CommentListVo> commentListVos1 = BeanCopyUtils.copyBeanList(records, CommentListVo.class);


        // 完善父评论
        List<CommentListVo> commentListVos = setChildren(commentListVos1);

        // 封装
        PageVo pageVo = new PageVo();
        pageVo.setRows(commentListVos);
        pageVo.setTotal(page.getTotal());

        // 返回
        return ResponseResult.okResult(pageVo);
    }

    // 完善父评论
    private List<CommentListVo> setChildren(List<CommentListVo> commentListVos) {
        return commentListVos.stream().map(sgComment -> {
            //对父评论的用户昵称赋值
            String nickName = Db.lambdaQuery(SysUser.class).eq(SysUser::getId, sgComment.getCreateBy()).one().getNickName();
            sgComment.setUsername(nickName);
            //获取子评论
            List<SgComment> list = lambdaQuery().eq(SgComment::getRootId, sgComment.getId()).orderByDesc(SgComment::getCreateTime).list();
            //封装子评论
            List<CommentListChildrenVo> commentListChildrenVos = BeanCopyUtils.copyBeanList(list, CommentListChildrenVo.class);
            //对子评论中的用户昵称和回复者昵称赋值
            commentListChildrenVos.forEach(commentListChildrenVo -> {
                String username = Db.lambdaQuery(SysUser.class).eq(SysUser::getId, commentListChildrenVo.getCreateBy()).one().getNickName();
                commentListChildrenVo.setUsername(username);
                commentListChildrenVo.setToCommentUserName(nickName);
            });
            //将子评论赋值
            sgComment.setChildren(commentListChildrenVos);
            return sgComment;
        }).collect(Collectors.toList());
    }

}
