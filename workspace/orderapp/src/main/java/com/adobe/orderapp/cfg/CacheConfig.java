package com.adobe.orderapp.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CacheConfig {
    @Autowired
    private CacheManager cacheManager;

//    @Scheduled(fixedRate = 1000)
    //8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day
    @Scheduled(cron = "0 0/30 8-10 * * *")
    public void clearCache() {
        System.out.println("Called Cache Clear");
        cacheManager.getCacheNames().forEach(cache -> {
            cacheManager.getCache(cache).clear();
        });
    }
}
