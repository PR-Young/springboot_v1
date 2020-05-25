package com.system.springbootv1.common.redis;

import com.system.springbootv1.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @description: redis操作类
 * @author: yy 2020/04/09
 **/
@Component
public class RedisUtil {
    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Resource
    private RedisTemplate<String, Object> template;
    private static RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "redisTemplateObj")
    private RedisTemplate<String, Object> redisTemplateObj;

    @PostConstruct
    public void init() {
        redisTemplate = template;

        logger.info("===========init redis start===========");
        redisTemplateObj.delete(redisTemplate.keys("*"));
        logger.info("===========init redis end===========");
    }

    /**
     * 将数据放入redis
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public static boolean set(String key, Object value, long expire) {
        try {
            if (expire > 0) {
                redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error("redis set {} error ,message {}", key, StringUtils.substring(e.toString(), 0, 2000));
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取数据
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除操作
     *
     * @param keys
     */
    public static void del(String... keys) {
        if (null != keys && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(keys));
            }
        }
    }

    /**
     * 删除操作
     *
     * @param keys
     */
    public static void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    public static boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error("find {} error,message {}", key, StringUtils.substring(e.toString(), 0, 2000));
            return false;
        }
    }

    /**
     * 获取 hash 数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public static Object getHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * set hash 数据
     *
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public static boolean setHash(String key, String hashKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        } catch (Exception e) {
            logger.error("setHash key:{} error,message:{}", key, StringUtils.substring(e.toString(), 0, 2000));
            return false;
        }
    }

    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            logger.error("expire key:{} error,message:{}", key, StringUtils.substring(e.toString(), 0, 2000));
            return false;
        }
    }
}
