package com.sangeng.utils;

import com.sangeng.domain.entity.SgArticle;
import com.sangeng.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {
    }


    public static <V> V copyBean(Object source,Class<V> clazz) {

        V result;
        try {
            //创建目标对象
            result = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source, result);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        //返回
        return result;
    }

    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){

        return list.stream()
                .map(obj -> copyBean(obj, clazz))
                .collect(Collectors.toList());
    }


}
