package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SgCategory;
import com.sangeng.domain.vo.CategoryListVo;
import com.sangeng.service.ISgCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类相关接口
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ISgCategoryService categoryService;
    /**
     * 查询分类列表
     */
    @RequestMapping("/getCategoryList")
    public ResponseResult list() {
        List<CategoryListVo> categoryListVoList = categoryService.getCategoryList();
        return ResponseResult.okResult(categoryListVoList);
    }

}
