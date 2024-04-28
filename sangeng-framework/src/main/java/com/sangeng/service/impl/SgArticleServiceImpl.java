package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SgArticle;
import com.sangeng.domain.entity.SgCategory;
import com.sangeng.domain.vo.ArticleDetailVo;
import com.sangeng.domain.vo.HotArticleVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.ArticleListVo;
import com.sangeng.mapper.SgArticleMapper;
import com.sangeng.service.ISgArticleService;
import com.sangeng.service.ISgCategoryService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SgArticleServiceImpl extends ServiceImpl<SgArticleMapper, SgArticle> implements ISgArticleService {

    @Autowired
    private ISgCategoryService categoryService;

    /**
     * 查询热门文章
     */
    public ResponseResult hotArticleList() {
        // 封装查询条件
        LambdaQueryWrapper<SgArticle> wrapper = new LambdaQueryWrapper<>();
        //1.必须是正式文章
        wrapper.eq(SgArticle::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //2.按照浏览量进行排序
        wrapper.orderByDesc(SgArticle::getViewCount);
        //3.查询
        Page<SgArticle> page = page(new Page<SgArticle>(1, 10), wrapper);
        List<SgArticle> records = page.getRecords();

     /*   //4.封装
        ArrayList<HotArticleVo> HotArticleVo = new ArrayList<>();
        for (SgArticle record : records) {
            HotArticleVo vo = new HotArticleVo();
            BeanUtils.copyProperties(record,vo);
            HotArticleVo.add(vo);
        }*/
        List<HotArticleVo> HotArticleVo = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);
        //5.返回
        return ResponseResult.okResult(HotArticleVo);
    }


    /**
     * 分页查询文章列表
     *
     * @param pageNum    页码
     * @param pageSize   页面大小
     * @param categoryId 类别
     *                   <p>
     *                   要求：
     *                   1.只能查询正式发布的文章
     *                   2.置顶的文章要显示在最前面
     */
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {

        //获取查询结果
        Page<SgArticle> articlePage = lambdaQuery()
                .eq(categoryId != 0, SgArticle::getCategoryId, categoryId)//查询指定分类下的文章
                .eq(SgArticle::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)//查询正式发布的文章
                .orderByDesc(SgArticle::getIsTop)//降序排列
                .page(new Page<>(pageNum, pageSize));//分页查询

        List<SgArticle> records = articlePage.getRecords();

        //VO数据封装
        List<ArticleListVo> ArticleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);

        //填充分类名
        ArticleListVos = ArticleListVos.stream()
                .map(ArticleListVo -> {
                    //根据分类id查询分类名
                    Long categoryId1 = ArticleListVo.getCategoryId();
                    SgCategory category = categoryService.getById(categoryId1);
                    if (category != null) {
                        String categoryName = category.getName();
                        ArticleListVo.setCategoryName(categoryName);
                    }
                    return ArticleListVo;
                }).collect(Collectors.toList());

        PageVo pageVo = new PageVo(articlePage.getTotal(), ArticleListVos);

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 获取文章详情
     *
     * @param id
     * @return 要求在文章列表点击阅读全文时能够跳转到文章详情页面，可以让用户阅读文章正文。
     * 要求：①要在文章详情中展示其分类名
     */
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        SgArticle article = getById(id);
        //封装VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //封装分类名
        if (articleDetailVo.getCategoryId() != null) {
            articleDetailVo.setCategoryName(categoryService.getById(articleDetailVo.getCategoryId()).getName());
        }
        //返回数据
        return ResponseResult.okResult(articleDetailVo);
    }
}
