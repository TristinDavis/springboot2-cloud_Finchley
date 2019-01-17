package com.aha.tech.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: luweihong
 * @Date: 2018/12/28
 */
@Configuration
public class RedissonConfig {

    @Value("${private.redis.host}")
    private String host;

    @Value("${private.redis.port}")
    private String port;

    @Value("${private.redis.password}")
    private String password;

    @Value("${private.redis.database}")
    private int database;

    @Value("${private.redis.max-active}")
    private int maxActive;

    @Value("${private.redis.max-idle}")
    private int maxIdle;

    @Value("${private.redis.timeout}")
    private int timeout;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setConnectionPoolSize(maxActive)
                .setConnectionMinimumIdleSize(maxIdle)
                .setDatabase(database)
                .setTimeout(timeout);
        if (StringUtils.isNotBlank(password)) {
            config.useSingleServer().setPassword(password);
        }
        //添加主从配置
//        config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});

        return Redisson.create(config);
    }


}
