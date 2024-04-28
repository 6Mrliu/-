package com.sangeng.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.SgLink;
import com.sangeng.mapper.SgLinkMapper;
import com.sangeng.service.ISgLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}
