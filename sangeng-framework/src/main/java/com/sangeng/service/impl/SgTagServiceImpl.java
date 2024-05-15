package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sangeng.domain.dto.TagPageQueryDTO;
import com.sangeng.domain.entity.SgTag;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.mapper.SgTagMapper;
import com.sangeng.service.ISgTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 标签 服务实现类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-05-14
 */
@Service
public class SgTagServiceImpl extends ServiceImpl<SgTagMapper, SgTag> implements ISgTagService {

    @Override
    public PageVo tagPageQuery(TagPageQueryDTO tagPageQueryDTO) {
        LambdaQueryWrapper<SgTag> wrapper = new LambdaQueryWrapper<SgTag>()
                .select(SgTag::getId, SgTag::getName, SgTag::getRemark)
                .like(StringUtils.hasText(tagPageQueryDTO.getName()),SgTag::getName, tagPageQueryDTO.getName())
                .like(StringUtils.hasText(tagPageQueryDTO.getRemark()),SgTag::getRemark, tagPageQueryDTO.getRemark());
        Page<SgTag> page = page(new Page<>(tagPageQueryDTO.getPageNum(), tagPageQueryDTO.getPageSize()),wrapper);

        return new PageVo(page.getTotal(), page.getRecords());
    }

    @Override
    public void addTag(SgTag tag) {
        save(tag);
    }



    @Override
    public SgTag listById(Long id) {
        SgTag tag = Db.lambdaQuery(SgTag.class).eq(SgTag::getId, id).one();
        return tag;
    }
}
