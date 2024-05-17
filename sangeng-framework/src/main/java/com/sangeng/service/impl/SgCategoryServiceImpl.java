package com.sangeng.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddCategoryDTO;
import com.sangeng.domain.dto.CategoryPageQueryDTO;
import com.sangeng.domain.entity.SgArticle;
import com.sangeng.domain.entity.SgCategory;
import com.sangeng.domain.vo.CategoryListVo;
import com.sangeng.domain.vo.CategoryVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.mapper.SgCategoryMapper;
import com.sangeng.service.ISgArticleService;
import com.sangeng.service.ISgCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-04-27
 */
@Service
public class SgCategoryServiceImpl extends ServiceImpl<SgCategoryMapper, SgCategory> implements ISgCategoryService {
    @Autowired
    private ISgArticleService articleService;

    /**
     * 查询所有分类
     * 1.要求只展示有发布正式文章的分类
     * 2.必须是正常状态的分类
     */
    public List<CategoryListVo> getCategoryList() {

        //查询文章表
        List<SgArticle> articleList = articleService.lambdaQuery()
                .eq(SgArticle::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL).list();
        //获取文章的分类id 去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<SgCategory> categoryList = lambdaQuery()
                .in(SgCategory::getId, categoryIds)
                .eq(SgCategory::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .list();
        //封装
        List<CategoryListVo> categoryListVoList = BeanCopyUtils.copyBeanList(categoryList, CategoryListVo.class);
        return categoryListVoList;
    }

    /**
     * 分页查询分类
     * @param categoryPageQueryDTO
     * @return
     */
    public ResponseResult categoryPageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        LambdaQueryWrapper<SgCategory> wrapper = new LambdaQueryWrapper<SgCategory>()
                .eq(StringUtils.hasText(categoryPageQueryDTO.getStatus()), SgCategory::getStatus, categoryPageQueryDTO.getStatus())
                .like(StringUtils.hasText(categoryPageQueryDTO.getName()), SgCategory::getName, categoryPageQueryDTO.getName());

        Page<SgCategory> page = page(new Page<>(categoryPageQueryDTO.getPageNum(), categoryPageQueryDTO.getPageSize()));

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryVo.class);

        PageVo pageVo = new PageVo(page.getTotal(), categoryVos);
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 添加分类
     * @param category
     * @return
     */
    public ResponseResult addCategory(AddCategoryDTO category) {
        SgCategory sgCategory = BeanCopyUtils.copyBean(category, SgCategory.class);
        save(sgCategory);
        return ResponseResult.okResult();
    }
}
