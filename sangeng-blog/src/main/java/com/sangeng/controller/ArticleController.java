package com.sangeng.controller;


import com.sangeng.domain.ResponseResult;
import com.sangeng.service.ISgArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 文章相关接口
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ISgArticleService sgArticleService;

    /**
     * 查询热门文章
     */
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
        // 查询热门文章，封装成ResponseResult返回
        ResponseResult result = sgArticleService.hotArticleList();

        return result;
    }

    /**
     * 分页查询文章列表
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param categoryId 类别
     */
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {

        return sgArticleService.articleList(pageNum, pageSize, categoryId);
    }


}
