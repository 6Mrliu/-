package com.sangeng.service;

import com.sangeng.domain.dto.TagPageQueryDTO;
import com.sangeng.domain.entity.SgTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.vo.PageVo;

/**
 * <p>
 * 标签 服务类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-05-14
 */
public interface ISgTagService extends IService<SgTag> {

    PageVo tagPageQuery(TagPageQueryDTO tagPageQueryDTO);

    void addTag(SgTag tag);



    SgTag listById(Long id);
}
