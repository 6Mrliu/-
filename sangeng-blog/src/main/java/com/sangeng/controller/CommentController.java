package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SgComment;
import com.sangeng.service.ISgCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论相关接口
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ISgCommentService commentService;

    /**
     * 获取评论列表
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize ){

        return commentService.commentList(articleId,pageNum,pageSize);
    }

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @PostMapping
    public ResponseResult addComment(@RequestBody SgComment comment){
        return commentService.addComment(comment);
    }


}
