package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddCategoryDTO;
import com.sangeng.domain.dto.CategoryPageQueryDTO;
import com.sangeng.domain.entity.SgCategory;
import com.sangeng.domain.vo.CategoryVo;
import com.sangeng.service.ISgCategoryService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类相关接口
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    ISgCategoryService categoryService;

    /**
     * 查询所有分类
     * @return
     */
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        List<SgCategory> categoryList = categoryService.list();
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    /**
     * 分页查询分类
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/list")
    public ResponseResult categoryPageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        return categoryService.categoryPageQuery(categoryPageQueryDTO);
    }

    /**
     * 添加分类
     * @param category
     * @return
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody AddCategoryDTO category) {
        return categoryService.addCategory(category);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseResult deleteCategory(@PathVariable Long id) {
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable Long id) {
        SgCategory category = categoryService.getById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    /**
     * 修改分类
     * @param category
     * @return
     */
    @PutMapping
    public ResponseResult updateCategory(@RequestBody SgCategory category) {
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }


}
