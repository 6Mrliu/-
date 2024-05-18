package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.LinkDTO;
import com.sangeng.domain.dto.LinkPageQueryDTO;
import com.sangeng.domain.entity.SgLink;
import com.sangeng.domain.vo.LinkVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.mapper.SgLinkMapper;
import com.sangeng.service.ISgLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 友链 服务实现类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-04-28
 */
@Service
public class SgLinkServiceImpl extends ServiceImpl<SgLinkMapper, SgLink> implements ISgLinkService {

    /**
     * 查询所有友链
     * @return
     */
    public ResponseResult getAllLink() {
        List<SgLink> linkList = lambdaQuery()
                .eq(SgLink::getStatus, SystemConstants.LINK_STATUS_NORMAL)
                .list();
        return ResponseResult.okResult(linkList);
    }

    /**
     * 分页查询友链
     * @return
     */
    public ResponseResult linkPageQuery(LinkPageQueryDTO linkPageQueryDTO) {
        LambdaQueryWrapper<SgLink> wrapper = new LambdaQueryWrapper<SgLink>()
                .eq(StringUtils.hasText(linkPageQueryDTO.getStatus()), SgLink::getStatus, linkPageQueryDTO.getStatus())
                .like(StringUtils.hasText(linkPageQueryDTO.getName()), SgLink::getName, linkPageQueryDTO.getName());

        Page<SgLink> page = page(new Page<>(linkPageQueryDTO.getPageNum(), linkPageQueryDTO.getPageSize()), wrapper);

        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(page.getRecords(), LinkVo.class);

        PageVo pageVo = new PageVo(page.getTotal(), linkVos);

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 添加友链
     * @param link
     * @return
     */
    @Override
    public ResponseResult addLink(LinkDTO link) {
        SgLink sgLink = BeanCopyUtils.copyBean(link, SgLink.class);
        save(sgLink);
        return ResponseResult.okResult();
    }

    /**
     * 根据id查询友链
     * @param id
     * @return
     */
    @Override
    public ResponseResult getLinkById(Long id) {
        SgLink sgLink = getById(id);
        LinkVo linkVo = BeanCopyUtils.copyBean(sgLink, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    /**
     * 修改友链
     * @param link
     * @return
     */
    public ResponseResult updateLink(LinkDTO link) {
        SgLink sgLink = BeanCopyUtils.copyBean(link, SgLink.class);
        updateById(sgLink);
        return ResponseResult.okResult();
    }
}
