package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.LinkDTO;
import com.sangeng.domain.dto.LinkPageQueryDTO;
import com.sangeng.domain.entity.SgLink;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 友链 服务类
 * </p>
 *
 * @author 哈纳桑
 * @since 2024-04-28
 */
public interface ISgLinkService extends IService<SgLink> {

    ResponseResult getAllLink();

    ResponseResult linkPageQuery(LinkPageQueryDTO linkPageQueryDTO);

    ResponseResult addLink(LinkDTO link);

    ResponseResult getLinkById(Long id);

    ResponseResult updateLink(LinkDTO link);
}
