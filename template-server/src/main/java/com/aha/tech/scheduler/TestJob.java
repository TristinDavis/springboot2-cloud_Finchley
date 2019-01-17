package com.aha.tech.scheduler;

import com.aha.tech.service.TestService;
import com.aha.tech.utils.KeyGenerateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * @Author: luweihong
 * @Date: 2018/7/25
 * 测试用定时任务
 */
@Component
public class TestJob {

    private Logger LOGGER = LoggerFactory.getLogger(TestJob.class);

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private TestService testService;

    /**
     * 每6000毫秒 执行一次
     */
//    @Scheduled(fixedRate = 60_000L)
    @Scheduled(cron = "7 0/1 * * * ?")
    public void simpleJob() {
        LOGGER.debug("这是一个简单的定时任务,通过redis setNx 在分布式部署的情况下 让一台机器执行");
        String key = KeyGenerateUtil.Task.testKey();
        Long exp = 1_000L * 10;
        Boolean f = redisTemplate.opsForValue().setIfAbsent(key, "123");
        if (!f) {
            LOGGER.info("没有获得lock");
            return;
        }
        redisTemplate.expire(key, exp, TimeUnit.MILLISECONDS);
        testService.printTest();
    }

}
