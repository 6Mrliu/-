package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.service.ISgTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标签相关接口
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private ISgTagService tagService;

    /**
     * 查询标签列表
     * @return
     */
    @RequestMapping("/list")
    public ResponseResult list()
    {
        return ResponseResult.okResult(tagService.list());
    }
}
