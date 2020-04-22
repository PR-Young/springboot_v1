package com.system.springbootv1.common.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @description:
 * @author: yy 2020/4/21
 **/
public class ShiroCacheManager implements CacheManager {

    private long globExpire = 1800;

    public ShiroCacheManager() {

    }

    public ShiroCacheManager(long expire) {
        this.globExpire = expire;
    }

    @Resource(name = "redisTemplateObj")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        if (null == s) {
            return null;
        }
        return new ShiroCache<K, V>(s, redisTemplate, globExpire);
    }


}
