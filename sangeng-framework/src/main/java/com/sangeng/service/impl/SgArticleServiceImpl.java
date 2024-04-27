package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SgArticle;
import com.sangeng.domain.entity.SgCategory;
import com.sangeng.domain.vo.HotArticleVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.articleListVo;
import com.sangeng.mapper.SgArticleMapper;
import com.sangeng.service.ISgArticleService;
import com.sangeng.service.ISgCategoryService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param categoryId 类别
     *
     *     要求：
     *        1.只能查询正式发布的文章
     *        2.置顶的文章要显示在最前面
     */
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {

        Page<SgArticle> articlePage = lambdaQuery()
                .eq(categoryId!=0,SgArticle::getCategoryId, categoryId)
                .eq(SgArticle::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .orderByDesc(SgArticle::getIsTop)
                .page(new Page<>(pageNum, pageSize));

        List<SgArticle> records = articlePage.getRecords();

        //数据封装
        List<articleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, articleListVo.class);

        articleListVos = articleListVos.stream()
                .map(articleListVo -> {
                    //根据分类id查询分类名
                    Long categoryId1 = articleListVo.getCategoryId();
                    SgCategory category = categoryService.getById(categoryId1);
                    if (category!=null){
                        String categoryName = category.getName();
                        articleListVo.setCategoryName(categoryName);
                    }
                    return articleListVo;
                }).collect(Collectors.toList());

        PageVo pageVo = new PageVo(articlePage.getTotal(), articleListVos);

        return ResponseResult.okResult(pageVo);
    }
}
