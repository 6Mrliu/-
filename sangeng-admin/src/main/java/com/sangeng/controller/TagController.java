package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.TagPageQueryDTO;
import com.sangeng.domain.entity.SgTag;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.TagVo;
import com.sangeng.service.ISgTagService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list(TagPageQueryDTO tagPageQueryDTO) {
        PageVo pageVo = tagService.tagPageQuery(tagPageQueryDTO);
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 添加标签
     *
     * @param tag
     * @return
     */
    @PostMapping()
    public ResponseResult addTag(@RequestBody SgTag tag) {
        tagService.addTag(tag);
        return ResponseResult.okResult();
    }

    /**
     * 删除标签
     *
     * @param ids
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseResult deleteTag(@PathVariable List<Long> ids) {
        tagService.removeByIds(ids);
        return ResponseResult.okResult();
    }

    /**
     * 根据id查询标签
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult byIdTag(@PathVariable Long id) {
        SgTag sgTag = tagService.listById(id);
        return ResponseResult.okResult(sgTag);
    }

    /**
     * 修改标签
     */
    @PutMapping()
    public ResponseResult updateTag(@RequestBody SgTag tag) {
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }

    /**
     * 获取所有标签
     */
    @GetMapping("listAllTag")
    public ResponseResult listAllTag() {
        List<SgTag> tagList = tagService.list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tagList, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }

}
