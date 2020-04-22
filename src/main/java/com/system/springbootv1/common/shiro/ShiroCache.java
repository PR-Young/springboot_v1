package com.system.springbootv1.common.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: yy 2020/4/21
 **/
public class ShiroCache<K, V> implements Cache<K, V> {
    private static final String REDIS_SHIRO_CACHE = "ARES_SHIRO_CACHE";
    private String cacheKey;
    @Resource(name = "redisTemplateObj")
    private RedisTemplate<K, V> redisTemplate;
    private long globExpire = 1800;


    public ShiroCache(String name, RedisTemplate redisTemplate) {
        this.cacheKey = REDIS_SHIRO_CACHE + ":" + name + ":";
        this.redisTemplate = redisTemplate;
    }

    public ShiroCache(String name, RedisTemplate redisTemplate, long expire) {
        this.cacheKey = REDIS_SHIRO_CACHE + ":" + name + ":";
        this.redisTemplate = redisTemplate;
        this.globExpire = expire;
    }

    @Override
    public V get(K k) throws CacheException {
        redisTemplate.boundValueOps(getCacheKey(k)).expire(globExpire, TimeUnit.SECONDS);
        return redisTemplate.boundValueOps(getCacheKey(k)).get();
    }

    @Override
    public V put(K k, V v) throws CacheException {
        V old = get(k);
        redisTemplate.boundValueOps(getCacheKey(k)).set(v);
        return old;
    }

    @Override
    public V remove(K k) throws CacheException {
        V old = get(k);
        redisTemplate.delete(getCacheKey(k));
        return old;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys(getCacheKey("*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> set = keys();
        List<V> list = new ArrayList<V>();
        for (K s : set) {
            list.add(get(s));
        }
        return list;
    }

    private K getCacheKey(Object k) {
        return (K) (this.cacheKey + k);
    }
}
