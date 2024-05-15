package com.sangeng.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddArticleDTO;
import com.sangeng.domain.dto.ArticlePageQueryDTO;
import com.sangeng.domain.entity.SgArticle;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-04-27
 */


public interface ISgArticleService extends IService<SgArticle> {

    // 查询热门文章
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    void insertArticle(AddArticleDTO articleDTO);

    ResponseResult articlePageQuery(ArticlePageQueryDTO articlePageQueryDTO);

    ResponseResult byIdArticle(Long id);

    void updateArticleById(SgArticle article);

    void removeArticleByIds(List<Long> ids);

}
