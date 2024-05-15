package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddArticleDTO;
import com.sangeng.domain.dto.ArticlePageQueryDTO;
import com.sangeng.domain.entity.SgArticle;
import com.sangeng.service.ISgArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章相关接口
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    ISgArticleService articleService;
    /**
     * 新增博文
     */
    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDTO articleDTO) {
        articleService.insertArticle(articleDTO);
        return ResponseResult.okResult();
    }

    /**
     * 分页查询博文
     * @param articlePageQueryDTO
     * @return
     */
    @GetMapping("/list")
    public ResponseResult articlePageQuery(ArticlePageQueryDTO articlePageQueryDTO) {
        return articleService.articlePageQuery(articlePageQueryDTO);
    }

    /**
     * 根据id查询博文
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult byIdArticle(@PathVariable Long id) {
        return articleService.byIdArticle(id);
    }

    /**
     * 修改博文
     * @param article
     * @return
     */
    @PutMapping
    public ResponseResult updateArticle(@RequestBody SgArticle article) {
        articleService.updateArticleById(article);
        return ResponseResult.okResult();
    }

    /**
     * 删除博文
     */
    @DeleteMapping("{id}")
    public ResponseResult deleteArticle(@PathVariable(value = "id") List<Long> ids) {
        articleService.removeArticleByIds(ids);
        return ResponseResult.okResult();
    }

}
