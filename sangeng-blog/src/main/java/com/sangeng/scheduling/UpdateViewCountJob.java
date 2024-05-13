package com.sangeng.scheduling;

import com.sangeng.domain.entity.SgArticle;
import com.sangeng.service.ISgArticleService;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISgArticleService articleService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount() {
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap =
                redisCache.getCacheMap("article:viewCount");
        List<SgArticle> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new SgArticle(Long.valueOf(entry.getKey()),
                        entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);
        System.out.println("更新成功");
    }
}