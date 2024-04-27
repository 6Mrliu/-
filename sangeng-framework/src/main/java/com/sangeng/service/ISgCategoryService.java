package com.sangeng.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.entity.SgCategory;
import com.sangeng.domain.vo.CategoryListVo;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-04-27
 */
public interface ISgCategoryService extends IService<SgCategory> {

    List<CategoryListVo> getCategoryList();
}
