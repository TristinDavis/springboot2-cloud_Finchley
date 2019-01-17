package com.aha.tech.utils;

import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author: luweihong
 * @Date: 2018/5/2
 */
public class RedisLock {

    public static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private final static String LOCK_PREFIX = "redis:lock:";

    private String lock_key;

    private final static Long EXPIRE_TIME = 5 * 1000l;

    private final static Long WAIT_TIME = 10 * 1000l;

    private final RedissonUtil redissonUtil = (RedissonUtil) SpringContextUtil.getBean("redissonUtil");

    public RedisLock(String lock_key) {
        this.lock_key = LOCK_PREFIX + lock_key;
    }

    /**
     *获得锁
     */
    public boolean lock() throws Exception {
        RLock rLock = redissonUtil.getRLock(lock_key);
        // 尝试加锁，最多等待wait time 毫秒，上锁以后 EXPIRE_TIME 毫秒秒自动解锁
        if (rLock.tryLock(WAIT_TIME, EXPIRE_TIME, TimeUnit.MILLISECONDS)) {
            return true;
        }

        logger.warn("获取redis 分布式锁 失败");
        return false;
    }

    /**
     *解锁
     */
    public void unlock() {
        RLock rLock = redissonUtil.getRLock(lock_key);
        rLock.unlock();
    }

}
