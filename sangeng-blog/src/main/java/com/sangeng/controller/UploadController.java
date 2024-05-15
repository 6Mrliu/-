package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 通用接口
 */
@RestController
public class UploadController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("upload")
    public ResponseResult<String> upload(MultipartFile img) {

        try {
            //原始文件名
            String originalFilename = img.getOriginalFilename();
            //截取原始文件名的后缀   dfdfdf.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;
            //文件的请求路径
            String filePath = aliOssUtil.upload(img.getBytes(), objectName);
            return ResponseResult.okResult(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件上传失败");
        }

        return ResponseResult.errorResult(500,"文件上传失败");
    }
}
