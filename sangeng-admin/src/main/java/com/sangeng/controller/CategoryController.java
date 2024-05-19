package com.sangeng.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.http.HttpResponse;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddCategoryDTO;
import com.sangeng.domain.dto.CategoryPageQueryDTO;
import com.sangeng.domain.entity.SgCategory;
import com.sangeng.domain.vo.CategoryVo;
import com.sangeng.domain.vo.ExcelCategoryVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.service.ISgCategoryService;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
     *
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
     *
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/list")
    public ResponseResult categoryPageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        return categoryService.categoryPageQuery(categoryPageQueryDTO);
    }

    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody AddCategoryDTO category) {
        return categoryService.addCategory(category);
    }

    /**
     * 删除分类
     *
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
     *
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
     *
     * @param category
     * @return
     */
    @PutMapping
    public ResponseResult updateCategory(@RequestBody SgCategory category) {
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    /**
     * 导出分类
     *
     * @return
     */
    @PreAuthorize("@ps.hasPerm('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            //获取需要导出的数据
            List<SgCategory> categoryVos = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos =
                    BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            //参数 outputStream 是要写入的输出流
            //ExcelCategoryVo.class 表示要导出的数据对应的 Java 对象类型
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class)
                    //设置是否在写入完成后关闭输出流
                    .autoCloseStream(Boolean.FALSE)
                    //设置sheet名
                    .sheet("分类导出")
                    //设置表头
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result =
                    ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }


}
