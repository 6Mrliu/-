package com.sangeng.handler.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sangeng.utils.SecurityUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandle implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充 [insert].......");
        Long userId = SecurityUtils.getUserId();
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createBy", userId);
        metaObject.setValue("updateBy", userId);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充 [update].......");
        // TODO Long userId = SecurityUtils.getUserId();

        Long userId = SecurityUtils.getUserId();

        metaObject.setValue("updateTime", LocalDateTime.now());
        // TODO metaObject.setValue("updateBy", userId);
        if (userId != null)
            metaObject.setValue("updateBy", userId);
    }
}
