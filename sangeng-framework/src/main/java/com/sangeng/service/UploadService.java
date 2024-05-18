package com.sangeng.service;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.sangeng.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

public interface UploadService {
    public ResponseResult<String> uploadImg(MultipartFile img) ;


}
