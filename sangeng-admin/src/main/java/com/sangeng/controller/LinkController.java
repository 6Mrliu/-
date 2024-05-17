package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.LinkDTO;
import com.sangeng.domain.dto.LinkPageQueryDTO;
import com.sangeng.service.ISgLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    ISgLinkService linkService;

    /**
     * 分页查询友链
     *
     * @return
     */
    @GetMapping("list")
    public ResponseResult LinkPageQuery(LinkPageQueryDTO linkPageQueryDTO) {
        return linkService.linkPageQuery(linkPageQueryDTO);
    }

    /**
     * 添加友链
     * @param link
     * @return
     */
    @PostMapping
    public ResponseResult addLink(@RequestBody LinkDTO link) {
        return linkService.addLink(link);
    }

    /**
     * 根据id查询友链
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable Long id) {
        return linkService.getLinkById(id);
    }

    /**
     * 修改友链
     * @param link
     * @return
     */
    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkDTO link) {
        return linkService.updateLink(link);
    }

    /**
     * 删除友链
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long id) {
        linkService.removeById(id);
        return ResponseResult.okResult();
    }

}
